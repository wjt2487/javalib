/**
 * 
 */
package cn.mxj.hibernate;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.mxj.exception.CustomException;
import cn.mxj.io.AppLogger;
import cn.mxj.net.OperationResult;
import cn.mxj.net.UserToken;

/**
 * 提供数据持久层的事务处理机制
 * 
 * @author fl
 * 
 */
public class TransactionBll {

	private UserToken token;

	private Session currentSession;

	protected AppLogger logger = AppLogger.getInstance();

	protected Session getCurrentSession() {
		return this.currentSession;
	}

	public UserToken getToken() {
		return this.token;
	}

	public void setToken(UserToken token) {
		this.token = token;
	}

	protected Session getHbtSession() {
		return DaoUtil.getHbtSession();
	}

	/**
	 * 开始执行操作
	 * 
	 * @return
	 */
	public OperationResult execute() {
		if (token == null || !token.validate()) {
			return OperationResult.ILLEGAL_USER.clone();
		}

		this.currentSession = this.getHbtSession();
		Session s = this.currentSession;
		Transaction ta = null;

		try {
			ta = s.beginTransaction();
			doExecute();
			ta.commit();
		} catch (CustomException ex) {
			ta.rollback();
			logger.exception(ex);
			return new OperationResult(false, ex.getMessage());
		} catch (Exception ex) {
			ta.rollback();
			logger.exception(ex);
			return OperationResult.SYS_EXCEPTION.clone();
		} finally {
			try {
				// s.close();
				this.currentSession = null;
			} catch (Exception ex) {
				logger.exception(ex);
			}
		}

		OperationResult or = OperationResult.SUCCESS.clone();
		this.onExecuted(or);
		return or;
	}

	/**
	 * 具体执行每一步的操作，在任何一步中抛出异常的话，所有的操作将被回滚
	 * 
	 * @throws CustomException
	 */
	protected void doExecute() throws CustomException {

	}

	/**
	 * 所有事务执行完毕后执行的工作
	 * 
	 * @param or
	 */
	protected void onExecuted(OperationResult or) {
		// do nothing
	}

	/**
	 * 在一个事务操作中新建一个 bean
	 * 
	 * @param bean
	 */
	protected void createBean(IHibernateBean bean) {
		IHibernateBean po;
		try {
			po = bean.getClass().newInstance();
			BeanUtils.copyProperties(po, bean);
			po.numIdSet(null);
			po.validate(null);
			this.currentSession.save(po);
			bean.numIdSet(po.numIdGet());
		} catch (Exception ex) {
			logger.exception(ex);

			// throw a runtime exception to make current Transaction to rollback
			throw new RuntimeException();
		}
	}

	/**
	 * 在一个事务操作中更新一个 bean 数据
	 * 
	 * @param bean
	 */
	protected void updateBean(IHibernateBean bean) {
		IHibernateBean po = (IHibernateBean) this.currentSession.get(bean
				.getClass(), bean.numIdGet());
		try {
			BeanUtils.copyProperties(po, bean);
			po.validate(null);
			this.currentSession.update(po);
		} catch (Exception ex) {
			logger.exception(ex);
			throw new RuntimeException();
		}
	}

	/**
	 * 在一个事务操作中删除一个 bean 数据
	 * 
	 * @param bean
	 */
	protected void deleteBean(IHibernateBean bean) {
		try {
			IHibernateBean po = (IHibernateBean) this.currentSession.get(bean
					.getClass(), bean.numIdGet());
			this.currentSession.delete(po);
		} catch (Exception ex) {
			logger.exception(ex);
			throw new RuntimeException();
		}
	}

	/**
	 * 在一个事务操作中删除一个 bean 数据
	 * 
	 * @param <E>
	 * @param beanCla
	 * @param beanId
	 */
	protected <E extends IHibernateBean> void deleteBean(Class<E> beanCla,
			Number beanId) {
		try {
			Object po = this.currentSession.get(beanCla, beanId);
			this.currentSession.delete(po);
		} catch (Exception ex) {
			logger.exception(ex);
			throw new RuntimeException();
		}
	}

	/**
	 * 在一个事务操作中执行一条 hql 语句
	 * 
	 * @param hql
	 * @return
	 */
	protected int executeUpdate(String hql) {
		try {
			Query q = this.currentSession.createQuery(hql);
			return q.executeUpdate();
		} catch (Exception ex) {
			logger.exception(ex);
			throw new RuntimeException();
		}
	}

}
