package tools.followedwind.hibdbunit;

import org.dbunit.PropertiesBasedJdbcDatabaseTester;
import org.hibernate.cfg.AvailableSettings;


/**
 * 
 * @author followedwind
 *
 */
public enum HibDBUnitRelationDefault implements HibDBUnitRelation {
	DRIVER(
		PropertiesBasedJdbcDatabaseTester.DBUNIT_DRIVER_CLASS,
		AvailableSettings.DRIVER),
	URL(
		PropertiesBasedJdbcDatabaseTester.DBUNIT_CONNECTION_URL,
		AvailableSettings.URL),
	USER(
		PropertiesBasedJdbcDatabaseTester.DBUNIT_USERNAME,
		AvailableSettings.USER),
	PASS(
		PropertiesBasedJdbcDatabaseTester.DBUNIT_PASSWORD,
		AvailableSettings.PASS);
	private String DBUnit_key;
	private String Hibernate_key;
	private HibDBUnitRelationDefault( String DBUnit_key, String Hibernate_key ){
		this.DBUnit_key = DBUnit_key;
		this.Hibernate_key = Hibernate_key;
	}
	public String getDBUnit_key(){
		return this.DBUnit_key;
	}
	public String getHibernate_key(){
		return this.Hibernate_key;
	}
	public String getType(){
		return this.toString();
	}
	
}