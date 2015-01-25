package tools.followedwind.hibdbunit;

import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.dbunit.operation.DatabaseOperation;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Hibernateの設定をそのままDBUnitに流用したい<br>
 * ついでにテスト前のデータを一時退避させておき、テスト後に復帰させる<br>
 * 本クラスを継承し、引数が空のコンストラクタ内でset()をコールし設定を読み込ませる<br>
 * @author followedwind
 * @version 1.0
 */
public abstract class HibDBUnit extends DBTestCase {
	
	private HibDBUnitSetting setting;
	private HibDBBackup backup;
	private DBUnitDataProvider provider;

	private static final Logger logger = LoggerFactory.getLogger(HibDBUnit.class);
	
	protected HibDBUnit(){
	}
	
	/**
	 * 
	 * @param setting
	 * @throws Exception 設定
	 */
	protected HibDBUnit( HibDBUnitSetting setting ) throws Exception {
		this.set(setting);
	}
	
	/**
	 * Hibernateの設定ファイルやテストデータのファイルを指定した設定クラスを格納
	 * @param setting 設定クラス
	 * @throws IllegalStateException 設定クラスにおいて設定漏れがある場合
	 */
	public void set( HibDBUnitSetting setting ) throws IllegalStateException {
		this.backup = new HibDBBackup();
		this.setting = setting;
		assertNotNull("Setting is null", this.setting);
		assertNotNull("Setting is null", this.setting);
		assertNotNull("Setting is null", this.setting);
		
		/*
		 * DBTestCaseの69行目のnewDatabaseTester()でPropertiesBasedJdbcDatabaseTester()しているため
		 * (Connectionの取得に使用している)、Propertiesにセットが必要
		 */
		Properties properties = setting.getConf().getProperties();
		for( HibDBUnitRelation rel : setting.getRelation() ){
			String data = (String)properties.get(rel.getHibernate_key());
			System.setProperty(rel.getDBUnit_key(), data);
			//logger.info("apply settings {} : {}",rel.getType(),data);
			logger.info("apply settings: {}",rel.getType());
		}

	}
	
	@Override
	/**
	 * @inheritDoc
	 */
	protected IDataSet getDataSet() throws Exception {
		return this.provider.getData();
	}

	@Before
	public void setUp() throws Exception {
		this.provider = this.setting;
		logger.info("backup of existing data start");
		this.backup.store(this.getDataSet().getTableNames(), this.getDatabaseTester());
		logger.info("backup of existing data end");
		super.setUp();
	}
	
	@After
	public void tearDown() throws Exception{
		this.provider = this.backup;
		super.tearDown();
		logger.info("restore of backup data start");
		this.backup.restore(this.getDatabaseTester());
		logger.info("restore of backup data end");
	}

	/*
	@Override
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	@Override
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}
	*/
	
	
}
