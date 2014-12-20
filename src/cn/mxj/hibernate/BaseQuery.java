package cn.mxj.hibernate;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import cn.mxj.exception.ExceptionLevel;
import cn.mxj.io.AppLogger;
import cn.mxj.net.SysRtContext;
import cn.mxj.string.StringUtil;

/**
 * 基于 Hibernate, 查询 bean 实例列表
 * 
 * @author fl
 * 
 * @param <E>
 */
public class BaseQuery<E extends IHibernateBean> {

	public BaseQuery(Class<E> beanType) {
		this.beanType = beanType;
		this.resetCriteria();
	}

	protected Session getHbtSession() {
		return DaoUtil.getHbtSession();
	}

	protected AppLogger logger = AppLogger.getInstance();

	private Class<E> beanType;

	private SysRtContext context;

	private Criteria criteria;

	public SysRtContext getContext() {
		return this.context;
	}

	public void setContext(SysRtContext context) {
		this.context = context;
	}

	/**
	 * 基于当前 bean 类型的 Criteria
	 * 
	 * @return
	 */
	public Criteria getCriteria() {
		return this.criteria;
	}

	/**
	 * 重新创建一个 Criteria
	 * 
	 * @return
	 */
	public Criteria resetCriteria() {
		this.criteria = this.getHbtSession().createCriteria(beanType);
		return this.criteria;
	}

	/**
	 * 根据 Id 来寻找对应的 bean
	 * 
	 * @param id
	 *            bean 的标识
	 * @return 若找不到相应的实例则返回 null
	 */
	public E getBeanById(Number id) {
		if (id == null) {
			return null;
		}

		try {
			IHibernateBean po = (IHibernateBean) this.getHbtSession().get(
					beanType, id);
			E out = null;
			if (po != null) {
				out = beanType.newInstance();
				BeanUtils.copyProperties(out, po);
			}
			return out;
		} catch (Exception ex) {
			String msg = String.format(
					"getBeanById failed. Bean Name:%1$s, Bean Id:%2$s",
					beanType.getName(), id);
			logger.exception(ex);
			logger.info(msg);
			return null;
		}
	}

	/**
	 * 查询全部符合条件的实例
	 * 
	 * @return
	 * 
	 */
	public ResultWrapper<E> query() {
		return this.query(0, -1);
	}

	/**
	 * 查询部分符合条件的实例
	 * 
	 * @param startIndex
	 *            起始位置，从 0 开始
	 * @param count
	 *            需要的实例数目， 若此值 <= 0， 则查询全部符合条件的实例
	 * @return
	 */
	public ResultWrapper<E> query(int startIndex, int count) {
		if (count > 0) {
			criteria.setFirstResult((startIndex < 0) ? 0 : startIndex);
			criteria.setMaxResults(count);
		}
		return this.createAndFillBeansResult(criteria.list());
	}

	/**
	 * 查询全部符合条件的实例
	 * 
	 * @param hqlCondition
	 *            hql 语句的查询条件表达式，如 id>10 and name='leo'
	 * @return
	 */
	public ResultWrapper<E> query(String hqlCondition) {
		return this.query(hqlCondition, 0, -1);
	}

	/**
	 * 查询部分符合条件的实例
	 * 
	 * @param hqlCondition
	 *            hql 语句的查询条件表达式，如 id>10 and name='leo'
	 * @param startIndex
	 *            起始位置，从 0 开始
	 * @param count
	 *            需要的实例数目， 若此值 <= 0， 则查询全部符合条件的实例
	 * @return
	 */
	public ResultWrapper<E> query(String hqlCondition, int startIndex, int count) {
		String hql = "from " + this.beanType.getName();
		if (!StringUtil.isNullOrEmpty(hqlCondition)) {
			hql += " where " + hqlCondition;
		}
		Query q = this.getHbtSession().createQuery(hql);
		if (count > 0) {
			q.setFirstResult((startIndex < 0) ? 0 : startIndex);
			q.setMaxResults(count);
		}
		return this.createAndFillBeansResult(q.list());
	}

	/**
	 * 根据一条完整的 hql 查询全部符合条件的实例
	 * 
	 * @param wholeHql
	 *            example: select BeanInfo where id > 10
	 * @return
	 */
	public ResultWrapper<E> queryByHql(String wholeHql) {
		Query q = this.getHbtSession().createQuery(wholeHql);
		return this.createAndFillBeansResult(q.list());
	}

	/**
	 * 根据一条完整的 sql 查询全部符合条件的实例
	 * 
	 * @param wholeSql
	 *            example: select * from file_info where id > 10
	 * @return
	 */
	public ResultWrapper<E> queryBySql(String wholeSql) {
		SQLQuery q = this.getHbtSession().createSQLQuery(wholeSql);
		q.addEntity(this.beanType);
		return this.createAndFillBeansResult(q.list());
	}

	@SuppressWarnings("unchecked")
	protected ResultWrapper<E> createAndFillBeansResult(List list) {
		List<E> result = new ArrayList<E>(list.size());
		for (Object po : list) {
			try {
				E e = this.beanType.newInstance();
				BeanUtils.copyProperties(e, po);
				result.add(e);
			} catch (Exception ex) {
				logger.exception(ex, ExceptionLevel.CanIgnore);
			}
		}
		return new ResultWrapper<E>(result);
	}

}
