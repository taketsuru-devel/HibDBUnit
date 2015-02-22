package tools.followedwind.hibdbunit.test;

// Generated 2015/01/01 18:42:00 by Hibernate Tools 4.0.0

import java.util.List;

//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TesttableXml.
 * @see TesttableXml
 * @author Hibernate Tools
 */
public class TesttableXmlHome {

	//private static final Log log = LogFactory.getLog(TesttableXmlHome.class);
	private static final Logger log = LoggerFactory.getLogger(TesttableXmlHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			/*
			return (SessionFactory) new InitialContext()
					.lookup("SessionFactory");
			*/
			Configuration c = new Configuration().configure();
			StandardServiceRegistry ssr = new StandardServiceRegistryBuilder().applySettings(c.getProperties()).build();
			return new Configuration().configure().buildSessionFactory(ssr);
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException(
					"Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TesttableXml transientInstance) {
		log.debug("persisting Testtable instance");
		try {
			//sessionFactory.getCurrentSession().persist(transientInstance);
			sessionFactory.openSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TesttableXml instance) {
		log.debug("attaching dirty Testtable instance");
		try {
			//sessionFactory.getCurrentSession().saveOrUpdate(instance);
			sessionFactory.openSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	@SuppressWarnings("deprecation") //add
	public void attachClean(TesttableXml instance) {
		log.debug("attaching clean Testtable instance");
		try {
			//sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			sessionFactory.openSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TesttableXml persistentInstance) {
		log.debug("deleting Testtable instance");
		try {
			//sessionFactory.getCurrentSession().delete(persistentInstance);
			sessionFactory.openSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TesttableXml merge(TesttableXml detachedInstance) {
		log.debug("merging Testtable instance");
		try {
			//TesttableXml result = (TesttableXml) sessionFactory.getCurrentSession()
			TesttableXml result = (TesttableXml) sessionFactory.openSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TesttableXml findById(java.lang.Integer id) {
		log.debug("getting Testtable instance with id: " + id);
		try {
			//TesttableXml instance = (TesttableXml) sessionFactory.getCurrentSession()
			TesttableXml instance = (TesttableXml) sessionFactory.openSession()
					.get("tools.followedwind.hibdbunit.test.TesttableXml", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<TesttableXml> findByExample(TesttableXml instance) {
		log.debug("finding Testtable instance by example");
		try {
			@SuppressWarnings("unchecked") //add
			List<TesttableXml> results = (List<TesttableXml>) sessionFactory
					//.getCurrentSession()
					.openSession()
					.createCriteria("tools.followedwind.hibdbunit.test.TesttableXml")
					.add(create(instance)).list();
			log.debug("find by example successful, result size: "
					+ results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
