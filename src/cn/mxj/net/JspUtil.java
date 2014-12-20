/**
 * 
 */
package cn.mxj.net;

import cn.mxj.string.StringUtil;

/**
 * @author fl
 * 
 */
public class JspUtil {

	public static String plainTextToHtml(String value) {
		if (StringUtil.isNullOrEmpty(value))
			return "";
		String s = value;
		s = s.replaceAll("\\r\\n", "<br>");
		s = s.replaceAll("  ", "　");
		return s;
	}

	public static String getAlertForwardJs(String msg, String url) {
		String s = "<script type=\"text/javascript\">";
		if (msg.length() > 0)
			s += "alert(\"" + msg + "\");";
		if (url.length() > 0)
			s += "document.location.replace(\"" + url + "\");";
		s += "</script>";
		return s;
	}

	public static String getAlertBackJs(String msg) {
		String s = "<script type=\"text/javascript\">";
		if (msg.length() > 0)
			s += "alert(\"" + msg + "\");";
		s += "history.back();";
		s += "</script>";
		return s;
	}
}
