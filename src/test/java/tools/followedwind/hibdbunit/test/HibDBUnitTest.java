package tools.followedwind.hibdbunit.test;

import org.junit.Test;

import tools.followedwind.hibdbunit.HibDBUnit;
import tools.followedwind.hibdbunit.HibDBUnitSetting;



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