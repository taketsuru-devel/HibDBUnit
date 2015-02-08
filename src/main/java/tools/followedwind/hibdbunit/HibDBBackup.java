package tools.followedwind.hibdbunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.AmbiguousTableNameException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 既存のデータをバックアップするクラス<br>
 * 対象のテーブルはバックアップ時に引数で指定され、一時ファイルに保存する<br>
 * 一時ファイルに対し名前を指定するとテスト終了後も残るが、指定しなければ削除される<br>
 * 対象はデータのみであり、AUTO_INCREMENT値などは対象外なので注意
 * @author followedwind
 * @version 1.0
 */
public class HibDBBackup {
	
	private File bkfile;
	private String filename = null;
	
	private static final Logger logger = LoggerFactory.getLogger(HibDBBackup.class);

	public HibDBBackup(){
	}

	/**
	 * バックアップに使用する一時ファイルを保存する場合にファイル名を指定
	 * 実際に保存されるファイル名は{@code #backup(String[], IDatabaseTester)}を参照
	 * @param filename 保存する場合のファイル名
	 */
	public void setBackupFileName( String filename ){
		this.filename = filename;
	}
	
	/**
	 * 既存のテーブルのバックアップを実施<br>
	 * バックアップファイルは[指定のファイル名][数字数桁].xmlで保存される<br>
	 * ファイル名はログに表示される
	 * @param tablenames バックアップ対象のテーブル
	 * @param tester DataBaseTesterからコネクションを取得するために使用
	 * @throws Exception DataBaseTesterからコネクションが取得できない場合
	 * @throws IOException 一時ファイルが生成できない場合
	 * @throws FileNotFoundException 一時ファイルに書き込めない場合
	 * @throws DataSetException バックアップ中に異常があった場合
	 * @throws AmbiguousTableNameException 引数のテーブル名に重複があった場合
	 */
	public void backup( String[] tablenames, IDatabaseTester tester ) throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			/* データ読み込み用クエリセット作成 */
			QueryDataSet dataset = new QueryDataSet(conn);
			for( String table : tablenames){
				dataset.addTable(table);
				logger.info("back up table: {}",table);
			}
			/* 一時ファイル作成 */
			if ( this.filename == null ){
				this.bkfile = File.createTempFile("temp",".xml");
				this.bkfile.deleteOnExit();
				logger.info("using tmpfile {}, delete on exit",this.bkfile.getPath());
			} else {
				this.bkfile = File.createTempFile(this.filename,".xml");
				logger.info("using tmpfile {}",this.bkfile.getPath());
			}
			/* 一時ファイルに保存 */
			this.saveFile(dataset,this.bkfile);
		} catch ( Exception e){
			throw e;
		} finally {
			if ( conn != null ){
				try {
					conn.close();
				} catch ( SQLException e ){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 一時ファイルにデータを保存<br>
	 * 保存する形式は{@code #loadFile(File)}で読み出す形式と揃える必要がある
	 * @param dataset 保存するデータ
	 * @param tmpfile 一時ファイル
	 * @throws IOException 一時ファイルが生成できない場合
	 * @throws FileNotFoundException 一時ファイルに書き込めない場合
	 * @throws DataSetException バックアップ中に異常があった場合
	 */
	protected void saveFile( IDataSet dataset, File tmpfile ) throws DataSetException, FileNotFoundException, IOException{
		FlatXmlDataSet.write(dataset,new FileOutputStream(tmpfile));
	}

	/**
	 * 一時ファイルから元のデータを復元<br>
	 * @param tester DataBaseTesterからコネクションを取得するために使用
	 * @throws Exception DataBaseTesterからコネクションが取得できない場合
	 * @throws SQLException データベースとの接続で異常があった場合
	 */
	public void restore( IDatabaseTester tester ) throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			DatabaseOperation.CLEAN_INSERT.execute(conn,this.loadFile(this.bkfile));
		} catch ( Exception e ){
			throw e;
		} finally {
			if ( conn != null ){
				try {
					conn.close();
				} catch ( SQLException e ){
					e.printStackTrace();
				}
			}
		}
	}
	
	/**
	 * 一時ファイルからデータセットを読み取る<br>
	 * 読み取る形式は{@code #saveFile(File)}で保存する形式と揃える必要がある
	 * @param file 一時ファイル
	 * @return ファイルから読み取ったデータセット
	 * @throws MalformedURLException XMLの解析中にエラーが発生した場合
	 * @throws DataSetException テストデータの定義に異常があった場合
	 */
	protected IDataSet loadFile( File file ) throws MalformedURLException, DataSetException{
		return new FlatXmlDataSetBuilder().build(this.bkfile);
	}
}