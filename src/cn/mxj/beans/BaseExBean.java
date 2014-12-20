package cn.mxj.beans;

/**
 * 扩展了 BaseBean ，额外提供了名称信息
 * 
 * @author fl
 * 
 */
public class BaseExBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -9013984133920967805L;

	private String name;

	private String description;

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
