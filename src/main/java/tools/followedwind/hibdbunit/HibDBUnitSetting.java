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
 * @author followedwind
 * @version 1.0
 */
public class HibDBUnitSetting {
	private Configuration conf = null;
	private IDataSet dataset = null;
	private List<HibDBUnitRelation> rel = null;
	
	private static final Logger logger = LoggerFactory.getLogger(HibDBUnitSetting.class);

	public HibDBUnitSetting(){
		this.rel = Arrays.asList(HibDBUnitRelationDefault.values());
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
	 * @param dataset
	 * @return 自身
	 */
	public HibDBUnitSetting setTestData( IDataSet dataset ){
		this.dataset = dataset;
		logger.info("set testData");
		return this;
	}
	/**
	 * 指定されたHibernateの設定とDBUnitの関連付けリストを使用する
	 * @return 自身
	 */
	public HibDBUnitSetting setRelationList( List<HibDBUnitRelation> rel ){
		this.rel = rel;
		return this;
	}
	
	public Configuration getConf() {
		return this.conf;
	}
	public List<HibDBUnitRelation> getRelation(){
		return this.rel;
	}
	public IDataSet getDataSet() {
		return this.dataset;
	}
	
}