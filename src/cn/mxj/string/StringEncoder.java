package cn.mxj.string;

/**
 * 
 * 字符串的编码器
 * 
 * @author fl
 * 
 */
public class StringEncoder {

	public static String jsEncode(String s) {
		s = s.replace("\\", "\\\\");
		s = s.replace("\"", "\\\"");
		s = s.replace("'", "\\\'");
		s = s.replace("\r", "");
		s = s.replace("\n", "");
		return s;
	}

	public static String htmlEncode(String s) {
		if (StringUtil.isNullOrEmpty(s))
			return "";

		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); ++i)
			switch (s.charAt(i)) {
			case '<':
				sb.append("&lt;");
				break;
			case '>':
				sb.append("&gt;");
				break;
			case ' ':
				sb.append("&nbsp;");
				break;
			case '\n':
				sb.append("<br>");
				break;
			case '\'':
				sb.append("&#039;");
				break;
			case '\"':
				sb.append("&quot;");
				break;
			default:
				sb.append(s.charAt(i));
			}

		return sb.toString();
	}

	public static String sqlEncode(String value, boolean isInQuote) {
		return isInQuote ? value.replaceAll("'", "''") : value.replaceAll(";",
				"");
	}

	/**
	 * 将字符串按 utf-8 编码后输出
	 * 
	 * @param sour
	 *            原字符串
	 * @return 如果编码转换失败，则返回原字符串
	 */
	public static String encode(String sour) {
		return encode(sour, "utf-8");
	}

	/**
	 * 将字符串编码后输出
	 * 
	 * @param sour
	 *            原字符串
	 * @param encoding
	 *            编码类型
	 * @return 如果编码转换失败，则返回原字符串
	 */
	public static String encode(String sour, String encoding) {
		try {
			return new String(sour.getBytes(), encoding);
		} catch (Exception e) {
			return sour;
		}
	}
}
