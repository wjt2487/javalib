package cn.mxj.web;

public class AjaxUtil {
	public static String getJSON(String status, String fieldName, String message) {
		String fieldJSON = "";
		if (fieldName != null && fieldName.length() > 0 && message != null
				&& message.length() > 0)
			fieldJSON = "data:{" + fieldName + ":'" + message + "'}";
		return "{status:'" + status + "'"
				+ (fieldJSON.length() > 0 ? ("," + fieldJSON) : "") + "}";
	}
}
