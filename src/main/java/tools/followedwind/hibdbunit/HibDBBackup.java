package tools.followedwind.hibdbunit;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.SQLException;

import org.dbunit.IDatabaseTester;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;

public class HibDBBackup implements DBUnitDataProvider {
	
	protected IDataSet bkdataset = null;
	private File bkfile;

	public HibDBBackup(){
	}
	
	/**
	 * 
	 * @param tablenames
	 * @param tester
	 * @throws Exception
	 */
	public void store( String[] tablenames, IDatabaseTester tester )
		throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			QueryDataSet dataset = new QueryDataSet(conn);
			for( String table : tablenames){
				dataset.addTable(table);
			}
			this.bkdataset = dataset;
			this.bkfile = File.createTempFile("temp",".xml");
			FlatXmlDataSet.write(bkdataset,new FileOutputStream(this.bkfile));
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
	
	public void restore( IDatabaseTester tester )
		throws Exception {
		IDatabaseConnection conn = null;
		try {
			conn = tester.getConnection();
			this.bkdataset = new FlatXmlDataSetBuilder().build(this.bkfile);
			DatabaseOperation.CLEAN_INSERT.execute(conn,this.bkdataset);
			this.bkdataset = null;
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
	
	@Override
	public IDataSet getData() {
		return this.bkdataset;
	}
}