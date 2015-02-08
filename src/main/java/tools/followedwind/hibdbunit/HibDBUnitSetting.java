package tools.followedwind.hibdbunit;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.hibernate.cfg.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Hibernateの設定やテストデータを保持するクラス<br>
 * 各設定は自身のオブジェクトを返すためチェーン形式で設定可能
 * @author followedwind
 * @version 1.0
 */
public class HibDBUnitSetting {
	private Configuration conf = null;
	private IDataSet dataset = null;
	private List<HibDBUnitRelation> rel = null;
	private HibDBBackup backup = null;
	
	private static final Logger logger = LoggerFactory.getLogger(HibDBUnitSetting.class);

	public HibDBUnitSetting(){
		this.rel = Arrays.asList(HibDBUnitRelationDefault.values());
		this.backup = new HibDBBackup();
	}

	/**
	 * 指定されたファイルパスからHibernateの設定を読み込む
	 * @param cfgfile Hibernateの設定ファイルのパス
	 * @return 自身
	 */
	public HibDBUnitSetting setHibCfg( String cfgfile ) {
		return this.setHibCfg(new Configuration().configure(new File(cfgfile)));
	}
	/**
	 * 指定されたHibernateの設定を使用する
	 * @param cfg Hibernateの設定クラス
	 * @return 自身
	 */
	public HibDBUnitSetting setHibCfg( Configuration cfg ) {
		this.conf = cfg;
		logger.info("configured");
		return this;
	}
	/**
	 * テストデータをファイルから読み込む
	 * @param datafiles 1つ以上のテストデータのパス
	 * @return 自身
	 * @throws DataSetException テストデータのファイル形式に不備がある場合
	 * @throws IOException テストデータのファイルにアクセスできない場合
	 */
	public HibDBUnitSetting setTestData( String[] datafiles ) throws DataSetException, IOException {
		this.setTestData(new TestDataBuilder().build(datafiles));
		return this;
	}
	/**
	 * 指定されたテストデータを使用する
	 * @param dataset 使用するテストデータ
	 * @return 自身
	 */
	public HibDBUnitSetting setTestData( IDataSet dataset ){
		this.dataset = dataset;
		logger.info("set testData");
		return this;
	}
	/**
	 * 指定されたHibernateの設定とDBUnitの関連付けオブジェクトのリストを使用する<br>
	 * この項目は省略可能で、省略された場合はHibDBUnitRelationDefaultの各enumを使用する
	 * @see HibDBUnitRelation
	 * @see HibDBUnitRelationDefault
	 * @param rel 使用する関連付けリスト
	 * @return 自身
	 */
	public HibDBUnitSetting setRelationList( List<HibDBUnitRelation> rel ){
		this.rel = rel;
		return this;
	}
	/**
	 * 既存のデータベースのバックアップファイル名を指定する<br>
	 * この項目は省略可能で、省略された場合はバックアップファイルを使用後に削除する
	 * @param filename バックアップファイル名
	 * @return 自身
	 */
	public HibDBUnitSetting setBackUpFileName( String filename ){
		this.backup.setBackupFileName(filename);;
		return this;
	}
	
	/**
	 * 既存のデータベースのバックアップオブジェクトを指定する<br>
	 * この項目は省略可能で、省略された場合はHibDBBackupのインスタンスを使用する
	 * @param backup バックアップオブジェクト
	 * @return 自身
	 */
	public HibDBUnitSetting setBackupObj( HibDBBackup backup ){
		this.backup = backup;
		return this;
	}

	/**
	 * セットされたHibernateのConfigureオブジェクトを返す
	 * @return セットされたHibernateのConfigureオブジェクト
	 */
	public Configuration getConf() {
		return this.conf;
	}
	
	/**
	 * HibernateとDBUnitの関連付けオブジェクトを返す
	 * @return HibernateとDBUnitの関連付け
	 */
	public List<HibDBUnitRelation> getRelation(){
		return this.rel;
	}
	
	/**
	 * テストに使用するデータを返す
	 * @return テストに使用するデータ
	 */
	public IDataSet getDataSet() {
		return this.dataset;
	}
	
	/**
	 * テスト以前に存在していたデータベースのバックアップを返す
	 * @return テスト以前に存在していたデータベースのバックアップ
	 */
	public HibDBBackup getBackupObj(){
		return this.backup;
	}
}