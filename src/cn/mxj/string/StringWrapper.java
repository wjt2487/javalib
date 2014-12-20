/**
 * 
 */
package cn.mxj.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 对字符串的包装类，提供常规操作
 * 
 * @author fl
 * 
 */
public class StringWrapper {

	public StringWrapper(String value) {
		this.value = value;
	}

	private String value;

	public static StringWrapper newInstance(String str) {
		return new StringWrapper(str);
	}

	public String limitedString(int len) {
		return limitedString(len, "");
	}

	/**
	 * 截取指定长度的字符串
	 * 
	 * @param len
	 * @param more
	 *            代表被截掉的字符串
	 * @return
	 */
	public String limitedString(int len, String more) {
		if (StringUtil.isNullOrEmpty(this.value)) {
			return "";
		}

		if (value.length() <= len) {
			return value;
		} else if (len <= more.length()) {
			return value.substring(0, len);
		} else {
			return value.substring(0, len - more.length()) + more;
		}
	}

	/**
	 * 字符串的 int 值的表示形式
	 * 
	 * @return 如转换失败，返回默认值 0
	 */
	public int intValue() {
		return this.intValue(0);
	}

	/**
	 * 字符串的 int 值的表示形式
	 * 
	 * @param defaultValue
	 *            字符串不能合法的转为 int 值时，使用默认值作为返回值
	 * @return
	 */
	public int intValue(int defaultValue) {
		if (StringUtil.isNullOrEmpty(value)) {
			return defaultValue;
		}
		{
			return Integer.parseInt(value);
		}
	}

	/**
	 * 将一定格式的字符串转化为日期
	 * 
	 * @param dt
	 *            字符串格式的日期值
	 * @param formatStyle
	 *            字符串日期值的格式
	 * @return 日期
	 * @throws ParseException
	 *             抛出解析错误，调用者需处理此异常
	 */
	public static Date dateValue(String dt, String formatStyle)
			throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat(formatStyle);
		return ft.parse(dt);
	}

	/**
	 * 将字符串左对齐，右边补上指定的字符
	 * 
	 * @param totalWidth
	 * @param paddingChar
	 * @return
	 */
	public String padLeft(int totalWidth, char paddingChar) {
		return paddingString(totalWidth, paddingChar, true);
	}

	/**
	 * 将字符串右对齐，左边补上指定的字符
	 * 
	 * @param totalWidth
	 * @param paddingChar
	 * @return
	 */
	public String padRight(int totalWidth, char paddingChar) {
		return paddingString(totalWidth, paddingChar, false);
	}

	protected String paddingString(int totalWidth, char paddingChar,
			boolean left) {
		String s = value;
		if (s.length() >= totalWidth) {
			return s;
		}

		StringBuilder sb = new StringBuilder();
		if (left) {
			sb.append(s);
		}

		int padding = totalWidth - s.length();
		for (int i = 0; i < padding; ++i) {
			sb.append(paddingChar);
		}

		if (!left) {
			sb.append(s);
		}

		return sb.toString();
	}
}
