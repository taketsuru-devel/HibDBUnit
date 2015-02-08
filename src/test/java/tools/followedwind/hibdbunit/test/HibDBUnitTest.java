package tools.followedwind.hibdbunit.test;

import org.junit.Test;

import tools.followedwind.hibdbunit.HibDBUnit;
import tools.followedwind.hibdbunit.HibDBUnitSetting;


/**
 * HibDBUnitのテストケース<br>
 * 以下の場合を想定
 * testtablexml,testtablecsvに対してテスト実施
 * 定義はtestenv.sqlに記載
 * Hibernate設定ファイル：src/test/resources/hibernate.cfg.xml
 * テストデータ設定ファイル：src/test/resources/testdata.xml
 *                       src/test/resources/testdata.csv
 * それぞれのテーブルに対してテスト実施、完了時にテスト前のデータに復元
 * @author followedwind
 * @version 1.0
 */
public class HibDBUnitTest extends HibDBUnit {
	public HibDBUnitTest(){
		try {
			super.applySetting(new HibDBUnitSetting()
				.setHibCfg("src/test/resources/hibernate.cfg.xml")
				.setTestData(new String[]{
						"src/test/resources/testdata.xml",
						"src/test/resources/testdata.csv" //csvはディレクトリで渡す
				}));
		} catch (Exception e) {
			e.printStackTrace();
			fail("error on HibDBUnit constructer");
		}
	}
	
	@Test
	public void test_testtablecsv(){
		TesttableCsvHome thcsv = new TesttableCsvHome();
		TesttableCsv t = thcsv.findById(3);
		assertEquals((int)t.getScore(),5678);
	}
	@Test
	public void test_testtablexml(){
		TesttableXmlHome thxml = new TesttableXmlHome();
		TesttableXml t = thxml.findById(1);
		assertEquals(t.getData(),"tttt");
	}
}