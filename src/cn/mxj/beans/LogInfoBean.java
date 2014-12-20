/**
 * 
 */
package cn.mxj.beans;

import java.io.Serializable;

/**
 * @author fl
 * 
 */
public class LogInfoBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3884038598879648667L;

	private String errId;

	private String errName;

	private String errMessage;

	private String errStackTrace;

	private String faultCode;

	private String faultString;

	private String faultDetail;

	private String info;

	private String className;

	private String codeLine;

	private boolean errorLog;

	private boolean faultLog;

	private boolean infoLog;

	public String getClassName() {
		return this.className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCodeLine() {
		return this.codeLine;
	}

	public void setCodeLine(String codeLine) {
		this.codeLine = codeLine;
	}

	public String getErrId() {
		return this.errId;
	}

	public void setErrId(String errId) {
		this.errId = errId;
	}

	public String getErrMessage() {
		return this.errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}

	public String getErrName() {
		return this.errName;
	}

	public void setErrName(String errName) {
		this.errName = errName;
	}

	public boolean isErrorLog() {
		return this.errorLog;
	}

	public void setErrorLog(boolean errorLog) {
		this.errorLog = errorLog;
	}

	public String getErrStackTrace() {
		return this.errStackTrace;
	}

	public void setErrStackTrace(String errStackTrace) {
		this.errStackTrace = errStackTrace;
	}

	public String getFaultCode() {
		return this.faultCode;
	}

	public void setFaultCode(String faultCode) {
		this.faultCode = faultCode;
	}

	public String getFaultDetail() {
		return this.faultDetail;
	}

	public void setFaultDetail(String faultDetail) {
		this.faultDetail = faultDetail;
	}

	public boolean isFaultLog() {
		return this.faultLog;
	}

	public void setFaultLog(boolean faultLog) {
		this.faultLog = faultLog;
	}

	public String getFaultString() {
		return this.faultString;
	}

	public void setFaultString(String faultString) {
		this.faultString = faultString;
	}

	public String getInfo() {
		return this.info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public boolean isInfoLog() {
		return this.infoLog;
	}

	public void setInfoLog(boolean infoLog) {
		this.infoLog = infoLog;
	}
}
