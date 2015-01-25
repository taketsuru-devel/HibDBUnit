package tools.followedwind.hibdbunit.test;

// Generated 2015/01/01 18:42:00 by Hibernate Tools 4.0.0

import java.util.List;
import java.util.Map;

import javax.naming.InitialContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Testtable.
 * @see orz.test.testpackage.rev.Testtable
 * @author Hibernate Tools
 */
public class TesttableHome {

	private static final Log log = LogFactory.getLog(TesttableHome.class);

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

	public void persist(Testtable transientInstance) {
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

	public void attachDirty(Testtable instance) {
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

	public void attachClean(Testtable instance) {
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

	public void delete(Testtable persistentInstance) {
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

	public Testtable merge(Testtable detachedInstance) {
		log.debug("merging Testtable instance");
		try {
			//Testtable result = (Testtable) sessionFactory.getCurrentSession()
			Testtable result = (Testtable) sessionFactory.openSession()
					.merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Testtable findById(java.lang.Integer id) {
		log.debug("getting Testtable instance with id: " + id);
		try {
			//Testtable instance = (Testtable) sessionFactory.getCurrentSession()
			Testtable instance = (Testtable) sessionFactory.openSession()
					.get("tools.followedwind.hibdbunit.test.Testtable", id);
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

	public List<Testtable> findByExample(Testtable instance) {
		log.debug("finding Testtable instance by example");
		try {
			List<Testtable> results = (List<Testtable>) sessionFactory
					//.getCurrentSession()
					.openSession()
					.createCriteria("tools.followedwind.hibdbunit.test.Testtable")
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