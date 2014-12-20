/**
 * 
 */
package cn.mxj.ibatis;

import java.util.List;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author lfh
 * 
 */
public class BaseDao {

	protected SqlMapClient sqlMap;

	public BaseDao(SqlMapClient sql) {
		this.sqlMap = sql;
	}

	// 获取全部记录，返回对象列表
	protected List queryForList(String statementName, Object param) {
		List list = null;
		try {
			list = sqlMap.queryForList(statementName, param);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	protected Object queryForObject(String statementName, Object param) {
		Object result = null;
		try {
			result = sqlMap.queryForObject(statementName, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected int insert(String statementName, Object param) {
		int result = 0;
		try {
			result = (Integer) sqlMap.insert(statementName, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	protected int delete(String statementName, Object param) {
		int rows = 0;
		try {
			rows = sqlMap.delete(statementName, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}

	protected int update(String statementName, Object param) {
		int rows = 0;
		try {
			rows = sqlMap.update(statementName, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rows;
	}
}
