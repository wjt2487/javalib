/**
 * 
 */
package cn.mxj.net;

import javax.servlet.http.HttpServletRequest;

import cn.mxj.string.StringUtil;

/**
 * @author fl
 * 
 */
public class RequestWrapper {

	public RequestWrapper(HttpServletRequest request) {
		this.request = request;
	}

	private HttpServletRequest request;

	public int getIntValue(String paramName, int defaultValue) {
		int value = defaultValue;
		String param = request.getParameter(paramName);
		if (!StringUtil.isNullOrEmpty(param)) {
			try {
				value = Integer.parseInt(param.trim());
			} catch (Exception ex) {
				value = defaultValue;
			}
		}
		return value;
	}

	public String getStringValue(String paramName, String defaultValue) {
		return getStringValue(paramName, defaultValue, false);
	}

	public String getStringValue(String paramName, String defaultValue,
			boolean encode) {
		String param = request.getParameter(paramName);
		if (StringUtil.isNullOrEmpty(param)) {
			return defaultValue;
		} else {
			try {
				return encode ? new String(param.getBytes("ISO8859_1"), "utf-8")
						: param;
			} catch (Exception e) {
				return defaultValue;
			}
		}
	}

	public boolean getBoolValue(String paramName, boolean defaultValue) {
		boolean value = defaultValue;
		String param = request.getParameter(paramName);
		if (StringUtil.isNullOrEmpty(param)) {
			return value;
		}

		param = param.trim().toLowerCase();
		if (param.equals("true") || param.equals("1")) {
			value = true;
		} else if (param.equals("false") || param.equals("0")) {
			value = false;
		}
		return value;
	}

}
