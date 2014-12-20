package cn.mxj.beans;

import java.io.Serializable;

/**
 * 提供基本的 bean 实现，实现了常用的几个接口
 * 
 * @author fl
 * 
 */
public class BaseBean implements IBaseBean, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6313276283198158154L;

	private Number id;

	public Number numIdGet() {
		return id;
	}

	public void numIdSet(Number id) {
		this.id = id;
	}

	public Integer getId() {
		if (this.id instanceof Integer) {
			return (Integer) this.id;
		} else {
			return 0;
		}
	}

	public void setId(Integer value) {
		if (value != null && value.intValue() > 0) {
			this.id = value;
		}
	}

	public Long getLongId() {
		if (this.id instanceof Long) {
			return (Long) this.id;
		} else {
			return 0L;
		}
	}

	public void setLongId(Long value) {
		if (value != null && value.longValue() > 0) {
			this.id = value;
		}
	}

	/**
	 * 比较两个实例是否相等。当实例类型相同并且 id 值相等时，则返回 true，否则返回 false
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;

		final BaseBean other = (BaseBean) obj;

		if (this.id instanceof Integer) {
			if (this.getId() == null) {
				if (other.getId() != null)
					return false;
			} else if (!this.getId().equals(other.getId()))
				return false;
		} else if (this.id instanceof Long) {
			if (this.getLongId() == null) {
				if (other.getLongId() != null)
					return false;
			} else if (!this.getLongId().equals(other.getLongId()))
				return false;
		}

		return true;
	}

	/**
	 * 仅提供主键 id 的值类型是 Integer 和 Long 的 hashCode 值实现
	 */
	@Override
	public int hashCode() {
		final int PRIME = 31;
		int result = super.hashCode();
		if (this.id instanceof Integer) {
			result = PRIME * result
					+ ((this.getId() == null) ? 0 : this.getId().hashCode());
		} else if (this.id instanceof Long) {
			result = PRIME
					* result
					+ ((this.getLongId() == null) ? 0 : this.getLongId()
							.hashCode());
		}
		return result;
	}

}
