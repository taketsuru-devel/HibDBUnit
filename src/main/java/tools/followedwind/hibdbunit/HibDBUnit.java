package tools.followedwind.hibdbunit;

import java.util.Properties;

import org.dbunit.DBTestCase;
import org.dbunit.dataset.IDataSet;
import org.junit.After;
import org.junit.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Hibernateの設定をそのままDBUnitに流用したい<br>
 * ついでにテスト前のデータを一時退避させておき、テスト後に復帰させる<br>
 * 本クラスを継承し、引数が空のコンストラクタ内でset()をコールし設定を読み込ませる<br>
 * 使用例は{@link tools.followedwind.hibdbunit.test.HibDBUnitTest}を参照
 * @author followedwind
 * @version 1.0
 */
public abstract class HibDBUnit extends DBTestCase {
	
	private HibDBUnitSetting setting;

	private static final Logger logger = LoggerFactory.getLogger(HibDBUnit.class);
	
	/**
	 * 後で設定を反映する場合のコンストラクタ
	 * 継承時にいろいろやってから設定を反映したい場合、こちらをオーバーライド
	 */
	protected HibDBUnit(){
	}
	
	/**
	 * 設定の反映を同時に行うコンストラクタ
	 * HibDBUnitSettingがチェーンで設定できるので、HibDBUnitTestのように設定オブジェクト作成と反映を同時に行うことも可能
	 * @param setting 設定オブジェクト
	 * @throws Exception 設定オブジェクトにおいて項目抜けなどの例外が発生した場合
	 */
	protected HibDBUnit( HibDBUnitSetting setting ) throws Exception {
		this.applySetting(setting);
	}
	
	/**
	 * Hibernateの設定ファイルやテストデータのファイルを指定した設定クラスを格納<br>
	 * 設定クラスがNullである場合はテストを中断する<br>
	 * @param setting 設定クラス
	 * @throws IllegalStateException 設定クラスにおいて設定漏れがある場合
	 */
	public void applySetting( HibDBUnitSetting setting ) throws IllegalStateException {
		this.setting = setting;
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
	/** @inheritDoc */
	protected IDataSet getDataSet() throws Exception {
		return this.setting.getDataSet();
	}

	@Before
	/** @inheritDoc */
	public void setUp() throws Exception {
		logger.info("backup of existing data start");
		this.setting.getBackupObj().backup(this.getDataSet().getTableNames(), this.getDatabaseTester());
		logger.info("backup of existing data end");
		super.setUp();
	}
	
	@After
	/** @inheritDoc */
	public void tearDown() throws Exception{
		super.tearDown();
		logger.info("restore of backup data start");
		this.setting.getBackupObj().restore(this.getDatabaseTester());
		logger.info("restore of backup data end");
	}

}
