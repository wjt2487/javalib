package cn.mxj.ibatis;

import java.sql.SQLException;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.mxj.io.AppLogger;
import cn.mxj.net.OperationResult;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * 对 ibatis 操作数据库的常用方法进行包装，不提供自动的事务调用
 * 
 * @author fl
 * 
 */
public class IBatisSimpleDao implements IDao {

	protected SqlMapClient sqlMap;

	protected AppLogger logger = AppLogger.getInstance();

	/**
	 * 使用默认的 SqlMapClient ，通过 cn.skyclass.ibatis.SqlConfig 类的
	 * getSqlMapInstance() 方法获取
	 * 
	 */
	public IBatisSimpleDao() {
		this.sqlMap = SqlConfig.getSqlMapInstance();
	}

	/**
	 * 使用给定的 SqlMapClient
	 * 
	 * @param sqlMap
	 *            dao 将使用此 SqlMapClient 访问数据库
	 */
	public IBatisSimpleDao(SqlMapClient sqlMap) {
		this.sqlMap = sqlMap;
	}

	public void startTransaction() throws SQLException {
		this.sqlMap.startTransaction();
	}

	public void commitTransaction() throws SQLException {
		this.sqlMap.commitTransaction();
	}

	public void endTransaction() {
		try {
			this.sqlMap.endTransaction();
		} catch (Exception e) {
			logger.exception(e);
		}
	}

	public SqlMapClient getSqlMap() {
		return this.sqlMap;
	}

	public List getList(String sqlId, Object param) {
		try {
			List list = sqlMap.queryForList(sqlId, param);
			return list;
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
	}

	public List getList(String sqlId, Object param, int pageSize, int pageNo) {
		int skip = (pageNo - 1) * pageSize;
		int max = pageSize;
		return getListLimit(sqlId, param, skip, max);
	}

	public List getListLimit(String sqlId, Object param, int skip, int max) {
		try {
			List list = sqlMap.queryForList(sqlId, param, skip, max);
			return list;
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
	}

	public void queryWithRowHandler(String sqlId, Object param,
			RowHandler handler) {
		try {
			sqlMap.queryWithRowHandler(sqlId, param, handler);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
	}

	public Object getObject(String sqlId, Object param) {
		try {
			Object obj = sqlMap.queryForObject(sqlId, param);
			return obj;
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
	}

	public Object getFirstObject(String sqlId, Object param) {
		try {
			List list = sqlMap.queryForList(sqlId, param);
			if (list != null && list.size() > 0) {
				return list.get(0);
			} else {
				return null;
			}
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
	}

	public OperationResult executeInsert(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			Object newId = sqlMap.insert(sqlId, param);
			result.setStrId(String.valueOf(newId));
			if (newId instanceof Integer) {
				result.setId((Integer) newId);
			}
			if (newId instanceof Long) {
				result.setLongId((Long) newId);
			}
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public OperationResult executeInsertBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			sqlMap.startBatch();
			for (Object object : list) {
				sqlMap.insert(sqlId, object);
			}
			int row = sqlMap.executeBatch();
			result.setIntValue(row);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public OperationResult executeUpdate(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			int row = sqlMap.update(sqlId, param);
			result.setIntValue(row);
			result.setSuccessful(row > 0);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public OperationResult executeUpdateBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			sqlMap.startBatch();
			for (Object object : list) {
				sqlMap.update(sqlId, object);
			}
			int row = sqlMap.executeBatch();
			result.setIntValue(row);
			result.setSuccessful(row > 0);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public OperationResult executeDelete(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			int row = sqlMap.delete(sqlId, param);
			result.setIntValue(row);
			result.setSuccessful(row > 0);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public OperationResult executeDeleteBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			sqlMap.startBatch();
			for (Object object : list) {
				sqlMap.delete(sqlId, object);
			}
			int row = sqlMap.executeBatch();
			result.setIntValue(row);
			result.setSuccessful(row > 0);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return result;
	}

	public Integer getCount(String sqlId, Object param) {
		Integer count = 0;
		try {
			count = (Integer) sqlMap.queryForObject(sqlId, param);
		} catch (Exception e) {
			logger.exception(e);
			throw new RuntimeException(e);
		}
		return count;
	}

	/**
	 * 拷贝对象
	 * 
	 * @param source
	 * @return
	 */
	protected Object cloneObject(Object source) {
		if (source != null) {
			try {
				Object result = source.getClass().newInstance();
				BeanUtils.copyProperties(result, source);
				return result;
			} catch (Exception e) {
				logger.exception(e);
				throw new RuntimeException(e);
			}
		}
		return null;
	}
}
