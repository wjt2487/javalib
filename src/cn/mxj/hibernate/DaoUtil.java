package cn.mxj.hibernate;

import java.lang.reflect.Method;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import cn.mxj.business.WebConfig;
import cn.mxj.exception.ExceptionLevel;
import cn.mxj.io.AppLogger;
import cn.mxj.string.StringUtil;

/**
 * 提供一些 Dao 相关的操作
 * 
 * @author fl
 * 
 */
public class DaoUtil {

	private static Class hibernateSessionFactory;

	/**
	 * 获取当前使用的 Hibernate Session 。默认使用 Factory 是
	 * cn.mxj.hibernate.HibernateSessionFactory 。如需要自定义 Factory，可以在配置文件
	 * web-config.xml 中的 appSettings 节中提供 HibernateSessionFactory
	 * 相关的键值对。关于自定义的配置文件信息，请参考 cn.skyclass.business.WebConfig 类。节点示例：<add
	 * key="HibernateSessionFactory"
	 * value="cn.skyclass.rc.hibernate.HibernateSessionFactory" />
	 * 
	 * @configFilePath 配置文件路径，如果此参数为空或是 null，则使用默认值
	 * @key xml 节点的键属性值，如果此参数空或是 null，则使用默认值字符串 HibernateSessionFactory
	 * @return
	 */
	public static Session getHbtSession(String configFilePath, String key) {
		if (hibernateSessionFactory == null) {
			if (StringUtil.isNullOrEmpty(key)) {
				key = "HibernateSessionFactory";
			}

			// get HibernateSessionFactory class name
			WebConfig cfg = WebConfig.getInstance(configFilePath);
			String claName = cfg.getAppSettingValue(key);
			try {
				if (!StringUtil.isNullOrEmpty(claName)) {
					hibernateSessionFactory = Class.forName(claName);
				} else {
					hibernateSessionFactory = HibernateSessionFactory.class;
				}
			} catch (ClassNotFoundException ex) {
				hibernateSessionFactory = HibernateSessionFactory.class;
				AppLogger.getInstance().exception(ex, ExceptionLevel.CanIgnore);
			}
		}

		try {
			Method m = hibernateSessionFactory.getMethod("getSession");
			return (Session) m.invoke(null);
		} catch (Exception ex) {
			AppLogger.getInstance().exception(ex, ExceptionLevel.Deadly);
			AppLogger.getInstance().info("get hibernate session failed.");
			return null;
		}
	}

	/**
	 * 使用默认的参数获取 Hibernate Session
	 * 
	 * @return
	 */
	public static Session getHbtSession() {
		return getHbtSession("", "");
	}

	/**
	 * 执行给定的 hql 语句，并返回影响的记录数
	 * 
	 * @param hql
	 * @return
	 */
	public static int executeUpdate(String hql) {
		int out = 0;
		Transaction ta = null;
		Session s = getHbtSession();

		try {
			ta = s.beginTransaction();
			Query q = s.createQuery(hql);
			out = q.executeUpdate();
			ta.commit();
		} catch (Exception ex) {
			ta.rollback();
			AppLogger.getInstance().exception(ex);
		} finally {
			try {
				// s.close();
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return out;
	}

	/**
	 * 执行给定的 hql 语句，返回一个结果对象
	 * 
	 * @param hql
	 * @param defaultValue
	 * @return
	 */
	public static Object executeScalar(String hql, Object defaultValue) {
		Object out = defaultValue;
		Transaction ta = null;
		Session s = getHbtSession();

		try {
			ta = s.beginTransaction();
			Query q = s.createQuery(hql);
			out = q.uniqueResult();
			ta.commit();
		} catch (Exception ex) {
			ta.rollback();
			AppLogger.getInstance().exception(ex);
		} finally {
			try {
				// s.close();
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return out;
	}

	/**
	 * 根据给定的 sql 语句，返回查询结果列表
	 * 
	 * @param sql
	 * @return
	 */
	public static List queryBySql(String sql) {
		Query q = getHbtSession().createSQLQuery(sql);
		return q.list();
	}
}
