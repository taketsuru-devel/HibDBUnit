package tools.followedwind.hibdbunit;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.sql.SQLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibDBBackup {
	
	private File bkfile;
	private String tmpfilename;
	
	private static final Logger logger = LoggerFactory.getLogger(HibDBBackup.class);

	public HibDBBackup(){
		this(null);
	}
	
	public HibDBBackup( String tmpfilename ){
		this.tmpfilename = tmpfilename;
	}
	
	/**
	 * 
	 * @param tablenames
	 * @param tester
	 * @throws Exception
	 */
	public void backup( String[] tablenames, IDatabaseTester tester )
		throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			QueryDataSet dataset = new QueryDataSet(conn);
			for( String table : tablenames){
				dataset.addTable(table);
				logger.info("back up table: {}",table);
			}
			if ( this.tmpfilename == null ){
				this.bkfile = File.createTempFile("temp",".xml");
				this.bkfile.deleteOnExit();
				logger.info("using tmpfile {}, delete on exit",this.bkfile.getPath());
			} else {
				this.bkfile = File.createTempFile(this.tmpfilename,".xml");
				logger.info("using tmpfile {}",this.bkfile.getPath());
			}
			this.saveFile(dataset,this.bkfile);
			//FlatXmlDataSet.write(dataset,new FileOutputStream(this.bkfile));
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
	
	protected void saveFile( IDataSet dataset, File tmpfile ) throws DataSetException, FileNotFoundException, IOException{
		FlatXmlDataSet.write(dataset,new FileOutputStream(tmpfile));
	}
	
	public void restore( IDatabaseTester tester )
		throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			//IDataSet dataset = new FlatXmlDataSetBuilder().build(this.bkfile);
			//DatabaseOperation.CLEAN_INSERT.execute(conn,dataset);
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
	
	protected IDataSet loadFile( File file ) throws MalformedURLException, DataSetException{
		return new FlatXmlDataSetBuilder().build(this.bkfile);
	}
}