package sy.dao.impl;

import java.io.Serializable;
import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate3.support.OpenSessionInViewFilter;
import org.springframework.stereotype.Repository;

import sy.dao.BaseDaoI;

/**
 * 
 * @author ben
 *
 */
@Repository("baseDao")
public class BaseDaoImpl extends OpenSessionInViewFilter implements BaseDaoI {
	

	private SessionFactory sessionFactory;

	public SessionFactory getSessionFactory() {
		
		return sessionFactory;
	}

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	private Session getCurrentSession() {
		//return sessionFactory.openSession();
		Session session =  sessionFactory.getCurrentSession();
		//Session session = SessionFactoryUtils.openSession(sessionFactory);
		session.setFlushMode(FlushMode.AUTO);
		return session;
	}

	@Override
	public void save(Object o) {
		this.getCurrentSession().save(o);
	}

	@Override
	public void update(Object o) {
		this.getCurrentSession().update(o);
	}

	@Override
	public void saveOrUpdate(Object o) {
		this.getCurrentSession().saveOrUpdate(o);
	}

	@Override
	public void merge(Object o) {
		this.getCurrentSession().merge(o);
	}

	@Override
	public void delete(Object o) {
		this.getCurrentSession().delete(o);
	}

	@Override
	public List find(String hql, List param) {
		
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.list();
	}

	@Override
	public List find(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.list();
	}

	@Override
	public List find(String hql, int page, int rows, List param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public List find(String hql, int page, int rows, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return q.setFirstResult((page - 1) * rows).setMaxResults(rows).list();
	}

	@Override
	public Object get(Class c, Serializable id) {
		return this.getCurrentSession().get(c, id);
	}

	@Override
	public Object get(String hql, Object... param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public Object get(String hql, List param) {
		List l = this.find(hql, param);
		if (l != null && l.size() > 0) {
			return l.get(0);
		}
		return null;
	}

	@Override
	public Object load(Class c, Serializable id) {
		return this.getCurrentSession().load(c, id);
	}

	@Override
	public Long count(String hql, Object... param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.length > 0) {
			for (int i = 0; i < param.length; i++) {
				q.setParameter(i, param[i]);
			}
		}
		return (Long) q.uniqueResult();
	}

	@Override
	public Long count(String hql, List param) {
		Query q = this.getCurrentSession().createQuery(hql);
		if (param != null && param.size() > 0) {
			for (int i = 0; i < param.size(); i++) {
				q.setParameter(i, param.get(i));
			}
		}
		return (Long) q.uniqueResult();
	}

}
