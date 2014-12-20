/**
 * 
 */
package cn.mxj.hibernate;

import java.util.ArrayList;
import java.util.List;

import cn.mxj.util.BeansUtil;

/**
 * 对查询结果的包装类，提供对结果集的相关操作
 * 
 * @author fl
 * 
 */
public class ResultWrapper<E extends IHibernateBean> {

	public ResultWrapper(List<E> result) {
		this.result = result;
	}

	private List<E> result = new ArrayList<E>();

	public List<E> getResult() {
		return this.result;
	}

	/**
	 * 获取结果集的全部实例的 Id，并组装成列表
	 * 
	 * @return
	 */
	public List<Number> getIdList() {
		// 从实体列表中提取 Id

		List<Number> l = new ArrayList<Number>(this.result.size());
		for (E e : this.result) {
			l.add(e.numIdGet());
		}
		return l;
	}

	/**
	 * 提取结果集的 bean 的某个属性值
	 * 
	 * @param propName
	 * @return
	 */
	public <P> List<P> pickProperty(String propName) {
		return BeansUtil.pickProperty(this.result, propName);
	}

	/**
	 * 获取结果集的实例数目
	 * 
	 * @return
	 */
	public int getResultCount() {
		return this.result.size();
	}

	/**
	 * 
	 * 是否有查询结果
	 * 
	 * @return
	 */
	public boolean hasResult() {
		return this.result != null && this.result.size() > 0;
	}

	/**
	 * 获取结果集的第一个实例
	 * 
	 * @return 如果没有任何结果，则返回 null
	 */
	public E getFirstBean() {
		if (this.hasResult()) {
			return this.result.get(0);
		} else {
			return null;
		}
	}

}
