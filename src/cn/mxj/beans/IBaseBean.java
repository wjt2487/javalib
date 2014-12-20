package cn.mxj.beans;

import java.io.Serializable;

/**
 * 基本的 bean 接口，主要提供主键标识。有两种类型的 id，可以任意选用。
 * 
 * @author fl
 * 
 */
public interface IBaseBean extends Serializable {

	/**
	 * 获取 Integer 类型的 id
	 * 
	 * @return
	 */
	Integer getId();

	/**
	 * 设置 Integer 类型的 id
	 * 
	 * @param value
	 */
	void setId(Integer value);

	/**
	 * 获取 Long 类型的 id
	 * 
	 * @return
	 */
	Long getLongId();

	/**
	 * 设置 Long 类型的 id
	 * 
	 * @param value
	 */
	void setLongId(Long value);
}
