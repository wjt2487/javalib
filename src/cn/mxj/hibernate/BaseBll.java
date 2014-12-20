package cn.mxj.hibernate;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.mxj.io.AppLogger;
import cn.mxj.net.UserToken;

/**
 * 基于 Hibernate 的基本的对 bean 的处理
 * 
 * @author fl
 * 
 */
public class BaseBll {

	protected UserToken token;

	protected Session getHbtSession() {
		return DaoUtil.getHbtSession();
	}

	protected AppLogger logger = AppLogger.getInstance();

	/**
	 * 保存给定的实例（持久化），如果不存在对应的 PO 实例，则创建新实例
	 * 
	 * @param obj
	 */
	public void save(IHibernateBean obj) {
		this.saveOrDelete(obj, true);
	}

	/**
	 * 创建给定的实例（持久化）
	 * 
	 * @param obj
	 */
	public void create(IHibernateBean obj) {
		if (obj != null) {
			obj.numIdSet(null);
			this.saveOrDelete(obj, true);
		}
	}

	/**
	 * 删除给定的实例
	 * 
	 * @param obj
	 */
	public void delete(IHibernateBean obj) {
		this.saveOrDelete(obj, false);
	}

	protected void saveOrDelete(IHibernateBean obj, boolean isSave) {
		Transaction ta = null;
		Session s = this.getHbtSession();
		try {
			// try get po
			IHibernateBean po = null;
			if (obj.numIdGet() != null) {
				try {
					po = (IHibernateBean) s.get(obj.getClass(), obj.numIdGet());
				} catch (Exception ex) {
					logger.exception(ex);
				}
			}

			ta = s.beginTransaction();
			if (isSave) {
				if (po == null) {
					// create a new po and save it
					po = obj.getClass().newInstance();
					BeanUtils.copyProperties(po, obj);
					po.validate(null);
					s.save(po);
					obj.numIdSet(po.numIdGet());
				} else {
					// just update this po
					BeanUtils.copyProperties(po, obj);
					po.validate(null);
					s.update(po);
				}
			} else if (po != null) {
				s.delete(po);
				obj.numIdSet(null);
			}

			ta.commit();
		} catch (Exception ex) {
			ta.rollback();
			String msg = "%1$s bean data failed. Bean Name:%2$s, Bean Id:%3$s";
			msg = String.format(msg, isSave ? "save" : "delete", obj.getClass()
					.getName(), obj.numIdGet());
			logger.info(msg);
			logger.exception(ex);
		} finally {
			try {
				// s.close();
			} catch (Exception ex) {
				logger.exception(ex);
			}
		}
	}
}
