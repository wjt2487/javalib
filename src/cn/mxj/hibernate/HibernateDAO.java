/**
 */
package cn.mxj.hibernate;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public abstract class HibernateDAO {

	protected void saveObject(Object vo) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Transaction tc = dbSession.beginTransaction();
			dbSession.save(vo);
			tc.commit();
			vo = cloneObject(vo);
		} catch (HibernateException e) {
		}
	}

	protected void saveObject(Object[] vos) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Transaction tc = dbSession.beginTransaction();
			for (Object vo : vos) {
				dbSession.save(vo);
			}
			tc.commit();
			for (Object vo : vos) {
				vo = cloneObject(vo);
			}
		} catch (HibernateException e) {
		}
	}

	protected void updateObject(Object vo, Serializable id) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Object po = dbSession.load(vo.getClass(), id);
			if (po == null)
				po = dbSession.get(vo.getClass(), id);
			if (po != null) {
				copyObject(po, vo);
				Transaction tc = dbSession.beginTransaction();
				dbSession.update(po);
				tc.commit();
			}
		} catch (HibernateException e) {
		}
	}

	protected Object getObject(Class c, Serializable id) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Object po = dbSession.get(c, id);
			return cloneObject(po);

		} catch (HibernateException e) {
			return null;
		}
	}

	protected Object getObject(Class c, Map<String, Object> params) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Criteria criteria = dbSession.createCriteria(c);
			criteria.add(Restrictions.allEq(params));
			return criteria.uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}

	protected Object getObject(Class c, Criterion criterion) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Criteria criteria = dbSession.createCriteria(c);
			criteria.add(criterion);
			return criteria.uniqueResult();
		} catch (HibernateException e) {
			return null;
		}
	}

	protected List getObjects(Class c, Map<String, Object> params) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Criteria criteria = dbSession.createCriteria(c);
			criteria.add(Restrictions.allEq(params));
			return criteria.list();
		} catch (HibernateException e) {
			return null;
		}
	}

	protected List getObjects(Class c, Criterion criterion) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();
			Criteria criteria = dbSession.createCriteria(c);
			if (criterion != null)
				criteria.add(criterion);
			return criteria.list();
		} catch (HibernateException e) {
			return null;
		}
	}

	protected List getObjects(Class c, Criterion criterion, int pageSize,
			int currentPage) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Criteria criteria = dbSession.createCriteria(c);
			criteria.setFirstResult(currentPage * pageSize);
			criteria.setMaxResults(pageSize);

			if (criteria != null)
				criteria.add(criterion);
			return criteria.list();
		} catch (HibernateException e) {
			return null;
		}
	}

	protected void excuteUpdate(String hql) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Transaction tc = dbSession.beginTransaction();
			Query query = dbSession.createQuery(hql);
			query.executeUpdate();
			tc.commit();
		} catch (HibernateException e) {
		}
	}

	protected void deleteObject(Class c, Serializable id) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Transaction tc = dbSession.beginTransaction();
			dbSession.delete(dbSession.load(c, id));
			tc.commit();
		} catch (HibernateException e) {
		}
	}

	protected void deleteObject(Object vo) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();

			Transaction tc = dbSession.beginTransaction();
			dbSession.delete(vo);
			tc.commit();
		} catch (HibernateException e) {
		}
	}

	protected Query getQuery(String hql) {
		try {
			Session dbSession = HibernateSessionFactory.getSession();
			return dbSession.createQuery(hql);
		} catch (HibernateException e) {
			return null;
		}
	}

	protected void copyObject(Object target, Object source) {
		try {
			BeanUtils.copyProperties(target, source);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected Object cloneObject(Object source) {
		if (source != null) {
			try {
				Object result = source.getClass().newInstance();
				BeanUtils.copyProperties(result, source);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	protected Object cloneObject(Class c, Object source) {
		if (source != null) {
			try {
				Object result = c.newInstance();
				BeanUtils.copyProperties(result, source);
				return result;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}
}
