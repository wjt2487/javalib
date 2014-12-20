/**
 * 
 */
package cn.mxj.net;

import java.io.Serializable;

/**
 * 操作结果的包装类，主要用于封装 web service 的调用结果
 * 
 * @author fl
 * 
 */
public class OperationResult implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7766798484847419691L;

	public final static OperationResult SUCCESS = new OperationResult(true,
			"操作成功！");

	public final static OperationResult FAILED = new OperationResult(false,
			"操作失败，请重试！");

	public final static OperationResult SYS_EXCEPTION = new OperationResult(
			false, "发生系统错误，访问失败！");

	public final static OperationResult ILLEGAL_USER = new OperationResult(
			false, "您的身份未通过验证，访问失败！");

	public OperationResult(boolean successful, String msg) {
		this.successful = successful;
		this.msg = msg;
	}

	@Override
	public OperationResult clone() {
		OperationResult out = new OperationResult(this.successful, this.msg);
		out.id = this.id;
		out.longId = this.longId;
		out.strId = this.strId;
		out.intValue = this.intValue;
		out.strValue = this.strValue;
		return out;
	}

	private boolean successful;

	private String msg;

	private int id;

	private long longId;

	private String strId;

	private int intValue;

	private String strValue;

	public int getId() {
		return this.id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getIntValue() {
		return this.intValue;
	}

	public void setIntValue(int intValue) {
		this.intValue = intValue;
	}

	public long getLongId() {
		return this.longId;
	}

	public void setLongId(long longId) {
		this.longId = longId;
	}

	public String getStrId() {
		return this.strId;
	}

	public void setStrId(String strId) {
		this.strId = strId;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getStrValue() {
		return this.strValue;
	}

	public void setStrValue(String strValue) {
		this.strValue = strValue;
	}

	public boolean isSuccessful() {
		return this.successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

}
