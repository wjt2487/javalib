package cn.mxj.hibernate;

import java.io.Serializable;

/**
 * 标识此 bean 可以供 hibernate 与数据库交互数据使用
 * 
 * @author fl
 * 
 */
public interface IHibernateBean extends Serializable {

	/**
	 * 获取 bean 实体的唯一标识符
	 * 
	 * @return
	 */
	Number numIdGet();

	/**
	 * 设置 bean 实体的唯一标识符
	 * 
	 * @param value
	 */
	void numIdSet(Number value);

	/**
	 * 在存入数据库前对自身数据进行验证，保证数据的有效性
	 * 
	 * @param arg
	 *            验证过程所需的参数，无需参数则为 null
	 */
	void validate(Object arg);
}
