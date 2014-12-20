/**
 * 
 */
package cn.mxj.beans;

import java.util.Date;

/**
 * 存储键值对的数据
 * 
 * @author fl
 * 
 */
public class KeyValuePairBean extends BaseBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1123930918538869663L;

	private String name;

	private String value;

	private Date dateValue;

	private int type;

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getValue() {
		return this.value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public int getType() {
		return this.type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public Date getDateValue() {
		return this.dateValue;
	}

	public void setDateValue(Date dateValue) {
		this.dateValue = dateValue;
	}
}
