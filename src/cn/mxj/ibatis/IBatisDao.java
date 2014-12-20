package cn.mxj.ibatis;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;

import cn.mxj.io.AppLogger;
import cn.mxj.net.OperationResult;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * 对 ibatis 操作数据库的常用方法进行包装
 * 
 * @author fl
 * 
 */
public class IBatisDao implements IDao {

	protected SqlMapClient sqlMap;

	protected AppLogger logger = AppLogger.getInstance();

	/**
	 * 使用默认的 SqlMapClient ，通过 cn.skyclass.ibatis.SqlConfig 类的
	 * getSqlMapInstance() 方法获取
	 * 
	 */
	public IBatisDao() {
		this.sqlMap = SqlConfig.getSqlMapInstance();
	}

	/**
	 * 使用给定的 SqlMapClient
	 * 
	 * @param sqlMap
	 *            dao 将使用此 SqlMapClient 访问数据库
	 */
	public IBatisDao(SqlMapClient sqlMap) {
		this.sqlMap = sqlMap;
	}

	public SqlMapClient getSqlMap() {
		return this.sqlMap;
	}

	public List getList(String sqlId, Object param) {
		try {
			try {
				sqlMap.startTransaction();
				List list = sqlMap.queryForList(sqlId, param);
				sqlMap.commitTransaction();
				return list;
			} catch (Exception e) {
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			logger.exception(e);
		}
		return new ArrayList();
	}

	public List getList(String sqlId, Object param, int pageSize, int pageNo) {
		int skip = (pageNo - 1) * pageSize;
		int max = pageSize;
		return getListLimit(sqlId, param, skip, max);
	}

	public List getListLimit(String sqlId, Object param, int skip, int max) {
		try {
			try {
				sqlMap.startTransaction();
				List list = sqlMap.queryForList(sqlId, param, skip, max);
				sqlMap.commitTransaction();
				return list;
			} catch (Exception e) {
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			logger.exception(e);
		}
		return new ArrayList();
	}

	public void queryWithRowHandler(String sqlId, Object param,
			RowHandler handler) {
		try {
			try {
				sqlMap.startTransaction();
				sqlMap.queryWithRowHandler(sqlId, param, handler);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			logger.exception(e);
		}
	}

	public Object getObject(String sqlId, Object param) {
		try {
			try {
				sqlMap.startTransaction();
				Object obj = sqlMap.queryForObject(sqlId, param);
				sqlMap.commitTransaction();
				return obj;
			} catch (Exception e) {
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			logger.exception(e);
		}
		return null;
	}

	public Object getFirstObject(String sqlId, Object param) {
		try {
			try {
				sqlMap.startTransaction();
				List list = sqlMap.queryForList(sqlId, param);
				sqlMap.commitTransaction();

				if (list != null && list.size() > 0) {
					return list.get(0);
				} else {
					return null;
				}
			} catch (Exception e) {
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			logger.exception(e);
		}
		return null;
	}

	public OperationResult executeInsert(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				Object newId = sqlMap.insert(sqlId, param);
				result.setStrId(String.valueOf(newId));
				if (newId instanceof Integer) {
					result.setId((Integer) newId);
				}
				if (newId instanceof Long) {
					result.setLongId((Long) newId);
				}
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public OperationResult executeInsertBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				sqlMap.startBatch();
				for (Object object : list) {
					sqlMap.insert(sqlId, object);
				}
				int row = sqlMap.executeBatch();
				result.setIntValue(row);
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public OperationResult executeUpdate(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				int row = sqlMap.update(sqlId, param);
				result.setIntValue(row);
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public OperationResult executeUpdateBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				sqlMap.startBatch();
				for (Object object : list) {
					sqlMap.update(sqlId, object);
				}
				int row = sqlMap.executeBatch();
				result.setIntValue(row);
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public OperationResult executeDelete(String sqlId, Object param) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				int row = sqlMap.delete(sqlId, param);
				result.setIntValue(row);
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public OperationResult executeDeleteBatch(String sqlId, List list) {
		OperationResult result = new OperationResult(true, "");
		try {
			try {
				sqlMap.startTransaction();
				sqlMap.startBatch();
				for (Object object : list) {
					sqlMap.delete(sqlId, object);
				}
				int row = sqlMap.executeBatch();
				result.setIntValue(row);
				result.setSuccessful(true);
				sqlMap.commitTransaction();
			} catch (Exception e) {
				result.setSuccessful(false);
				result.setMsg(e.getMessage());
				logger.exception(e);
			} finally {
				sqlMap.endTransaction();
			}
		} catch (Exception e) {
			result.setSuccessful(false);
			result.setMsg(e.getMessage());
			logger.exception(e);
		}
		return result;
	}

	public Integer getCount(String sqlId, Object param) {
		Integer count = 0;
		try {
			count = (Integer) sqlMap.queryForObject(sqlId, param);
		} catch (Exception e) {
			logger.exception(e);
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
			}
		}
		return null;
	}
}
