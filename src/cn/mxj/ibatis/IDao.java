/**
 * 
 */
package cn.mxj.ibatis;

import java.util.List;

import cn.mxj.net.OperationResult;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.event.RowHandler;

/**
 * @author fl
 * 
 */
public interface IDao {

	/**
	 * 获取 dao 正在使用的 SqlMapClient
	 * 
	 * @return
	 */
	public SqlMapClient getSqlMap();
	

	/**
	 * 获取数据列表信息
	 * 
	 * @param sqlId
	 *            The name of the statement to execute.
	 * @param param
	 *            The parameter object
	 * @return 失败返回空列表而非 null 值
	 */
	public List getList(String sqlId, Object param);

	/**
	 * 指定分页大小和页码获取数据列表信息
	 * 
	 * @param sqlId
	 * @param param
	 * @param pageSize
	 *            分页大小
	 * @param pageNo
	 *            页码
	 * @return 失败返回空列表而非 null 值
	 */
	public List getList(String sqlId, Object param, int pageSize, int pageNo);

	/**
	 * 获取指定范围的数据列表信息
	 * 
	 * @param sqlId
	 * @param param
	 * @param skip
	 *            跳过的记录数
	 * @param max
	 *            查询的最大记录数
	 * @return 失败返回空列表而非 null 值
	 */
	public List getListLimit(String sqlId, Object param, int skip, int max);

	/**
	 * 通过 RowHandler 来进行查询操作
	 * 
	 * @param sqlId
	 * @param param
	 * @param handler
	 * 自定义的 RowHandler 实例
	 */
	public void queryWithRowHandler(String sqlId, Object param,
			RowHandler handler);

	/**
	 * 获取数据信息，需确保查询出的结果是单条记录
	 * 
	 * @param sqlId
	 * @param param
	 * @return 失败返回 null
	 */
	public Object getObject(String sqlId, Object param);

	/**
	 * 获取查询结果中的第一个对象
	 * 
	 * @param sqlId
	 * @param param
	 * @return 如果获取到多个对象则返回第一个对象；如果没有获取到对象则返回 null
	 */
	public Object getFirstObject(String sqlId, Object param);

	/**
	 * 插入数据记录。 如果插入成功，OperationResult.strId 存放新插入数据的主键值的字符串形式，OperationResult.id
	 * 存放 int 型主键，OperationResult.longId 存放 long 型主键； 如果插入失败，OperationResult.msg
	 * 存放错误信息
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public OperationResult executeInsert(String sqlId, Object param);

	/**
	 * 插入批处理，OperationResult.intValue 中存放操作影响的记录行数
	 * 
	 * @param sqlId
	 * @param list
	 * @return
	 */
	public OperationResult executeInsertBatch(String sqlId, List list);

	/**
	 * 更新数据，OperationResult.intValue 中存放操作影响的记录行数
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public OperationResult executeUpdate(String sqlId, Object param);

	/**
	 * 更新批处理，OperationResult.intValue 中存放操作影响的记录行数
	 * 
	 * @param sqlId
	 * @param list
	 * @return
	 */
	public OperationResult executeUpdateBatch(String sqlId, List list);

	/**
	 * 删除数据，OperationResult.intValue 中存放操作影响的记录行数
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public OperationResult executeDelete(String sqlId, Object param);

	/**
	 * 批量删除数据，OperationResult.intValue 中存放操作影响的记录行数
	 * 
	 * @param sqlId
	 * @param list
	 * @return
	 */
	public OperationResult executeDeleteBatch(String sqlId, List list);

	/**
	 * 查询记录数目，给出的 sql 语句应该返回一个 int 值
	 * 
	 * @param sqlId
	 * @param param
	 * @return
	 */
	public Integer getCount(String sqlId, Object param);

}
