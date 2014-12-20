/**
 * 
 */
package cn.mxj.net;

import java.util.ArrayList;

import cn.mxj.string.StringUtil;

/**
 * @author fl
 * 
 */
public class UrlParams {

	/**
	 * paramsStr : 参数字符串，默认是当前 url 的参数字符串。 paramsStr example: a=1&b=2&c=leo
	 */
	public UrlParams(String paramsStr, boolean ignoreCase) {
		this.params = paramsStr == null ? "" : paramsStr;
		this.ignoreCase = ignoreCase;
		this.keys = new ArrayList<String>();
		this.values = new ArrayList<String>();
		this.splitParams();
	}

	public UrlParams(String paramsStr) {
		this(paramsStr, true);
	}

	protected String params;

	protected boolean ignoreCase;

	protected ArrayList<String> keys;

	protected ArrayList<String> values;

	/**
	 * 获取 String 类型的 url 参数值，如果指定的参数不存在则返回 defaultValue
	 */
	public String getStrValue(String paramName, String defaultValue) {
		int i = this.searchKey(paramName);
		if (i >= 0) {
			String value = values.get(i);
			return StringUtil.isNullOrEmpty(value) ? value : defaultValue;
		} else {
			return defaultValue;
		}
	}

	/**
	 * 获取 boolean 类型的 url 参数值，如果指定的参数不存在则返回 defaultValue
	 */
	public boolean getBoolValue(String paramName, boolean defaultValue) {
		int i = this.searchKey(paramName);
		if (i >= 0) {
			String value = values.get(i);
			if ((value != null)
					&& (value.equalsIgnoreCase("true") || value.equals("1")))
				return true;
			else
				return false;
		} else {
			return defaultValue;
		}
	}

	/**
	 * 获取 int 类型的 url 参数值，如果指定的参数不存在则返回 defaultValue
	 */
	public int getIntValue(String paramName, int defaultValue) {
		int i = this.searchKey(paramName);
		if (i >= 0) {
			String value = values.get(i);
			try {
				return Integer.parseInt(value);
			} catch (Exception e) {
				return defaultValue;
			}
		} else {
			return defaultValue;
		}
	}

	/**
	 * 将全部的查询参数分解，然后分别放入键值对的数组中
	 */
	protected void splitParams() {
		String[] pairs = params.split("&");
		for (String p : pairs) {
			int ieq = p.indexOf("=");
			if (ieq > 0) {
				keys.add(p.substring(0, ieq));
				values.add(p.substring(ieq + 1));
			}
		}
	}

	/**
	 * return index of key if key exist else return -1
	 */
	protected int searchKey(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			return -1;
		}

		for (int i = 0; i < keys.size(); i++) {
			String k = keys.get(i);
			boolean match = ignoreCase ? key.equalsIgnoreCase(k) : key
					.equals(k);
			if (match) {
				return i;
			}
		}
		return -1;
	}
}
