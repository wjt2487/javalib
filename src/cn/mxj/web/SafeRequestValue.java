package cn.mxj.web;

import javax.servlet.http.HttpServletRequest;

import cn.mxj.string.SimpleConverter;
import cn.mxj.string.StringUtil;

/**
 * @author syg
 * 
 */
public class SafeRequestValue {

	/**
	 * getSafeRequestIntValue
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static int getSafeRequestIntValue(HttpServletRequest request,
			String name, int defaultValue) {
		return SimpleConverter.safeParseInt(request.getParameter(name),
				defaultValue);
	}

	/**
	 * getSafeRequestStringValue
	 * 
	 * @param request
	 * @param name
	 * @param defaultValue
	 * @return
	 */
	public static String getSafeRequestStringValue(HttpServletRequest request,
			String name, String defaultValue) {
		String value = defaultValue;

		if (!StringUtil.isNullOrEmpty(request.getParameter(name))) {
			value = request.getParameter(name);
		}
		return value;
	}

	public static boolean getSafeRequestBooleanValue(
			HttpServletRequest request, String name, boolean defaultValue) {
		boolean value = defaultValue;

		if (request.getParameter(name) != null) {
			String temp = request.getParameter(name).trim();
			if (temp.equalsIgnoreCase("true")) {
				value = true;
			} else if (temp.equalsIgnoreCase("false")) {
				value = false;
			}
		}

		return value;
	}
}
