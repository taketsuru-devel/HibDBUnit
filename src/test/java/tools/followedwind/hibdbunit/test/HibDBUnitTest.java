package tools.followedwind.hibdbunit.test;

import java.io.File;

import org.hibernate.cfg.Configuration;
import org.junit.Test;

import tools.followedwind.hibdbunit.HibDBUnit;
import tools.followedwind.hibdbunit.HibDBUnitSetting;



public class HibDBUnitTest extends HibDBUnit {
	public HibDBUnitTest(){
		try {
			super.set(new HibDBUnitSetting()
				.setHibCfg("/root/workspace/hibdbunit/src/test/resources/hibernate.cfg.xml")
				.setTestData(new String[]{
						"/root/text.xml"
				}));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void test(){
		TesttableHome th = new TesttableHome();
		Testtable t = new Testtable();
		t = th.findById(1);
		assertEquals(t.getData(),"tttt");
	}
}