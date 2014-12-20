package cn.mxj.ibatis;

import java.io.Reader;

import cn.mxj.io.AppLogger;

import com.ibatis.common.resources.Resources;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapClientBuilder;

/**
 * ibatis 的配置信息，需要在 src 根目录下放置配置文件，文件名为 SqlMapConfig.xml 。配置文件可参考此包中的
 * SqlMapConfig.xml
 * 
 * @author fl
 * 
 */
public class SqlConfig {

	private static String configFile = "SqlMapConfig.xml";

	private static SqlMapClient sqlMap;

	/**
	 * 获取 SqlMapClient 实例
	 * 
	 * @return
	 */
	public static SqlMapClient getSqlMapInstance() {
		if (sqlMap == null) {
			try {
				Reader reader = Resources.getResourceAsReader(configFile);
				sqlMap = SqlMapClientBuilder.buildSqlMapClient(reader);
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return sqlMap;
	}

}
