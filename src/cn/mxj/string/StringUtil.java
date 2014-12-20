package cn.mxj.string;

import java.util.Date;

/**
 * 提供常用的字符串操作的工具类
 * 
 * @author fl
 * 
 */
public class StringUtil {

	/**
	 * 检测给定的字符串是否是 null 或 空字符串(length == 0)
	 * 
	 * @param value
	 * @return
	 */
	public static boolean isNullOrEmpty(String value) {
		return (value == null) || (value.length() == 0);
	}

	/**
	 * 获取有效的字符串值。如果是 null 则返回 ""，否则返回自身
	 * 
	 * @param value
	 * @return
	 */
	public static String getValidString(String value) {
		return isNullOrEmpty(value) ? "" : value;
	}

	/**
	 * 获取有效的字符串值。如果是 null 则返回 defaultValue，否则返回自身
	 * 
	 * @param value
	 * @param defaultValue
	 * @return
	 */
	public static String getValidString(String value, String defaultValue) {
		return isNullOrEmpty(value) ? defaultValue : value;
	}

	/**
	 * 截取指定长度的字符串
	 * 
	 * @param sourceStr
	 * @param len
	 * @param more
	 *            代表被截掉的字符串
	 * @return
	 */
	public static String getLimitedString(String sourceStr, int len, String more) {
		StringWrapper sw = new StringWrapper(sourceStr);
		return sw.limitedString(len, more);
	}

	/**
	 * @param value
	 *            需要格式化的double值
	 * @param decimals
	 *            保留的小数位数
	 * @return
	 */
	public static String formatDouble(double value, int decimals) {
		String strVal = Double.toString(value);
		int pointPos = strVal.indexOf(".");
		if ((pointPos > -1) && (decimals >= 0)) {
			return strVal.substring(0, pointPos + decimals);
		} else {
			return strVal;
		}
	}

	/**
	 * @param value
	 *            需要格式化的float值
	 * @param decimals
	 *            保留的小数位数
	 * @return
	 */
	public static String formatFloat(float value, int decimals) {
		String strVal = Float.toString(value);
		int pointPos = strVal.indexOf(".");
		if ((pointPos > -1) && (decimals >= 0)) {
			return strVal.substring(0, pointPos + decimals);
		} else {
			return strVal;
		}
	}

	/**
	 * 格式化显示日期
	 * 
	 * @param dt
	 *            日期值
	 * @param pattern
	 *            希望的格式 eg: Date and Time Pattern Result "yyyy.MM.dd G 'at'
	 *            HH:mm:ss z" 2001.07.04 AD at 12:08:56 PDT "EEE, MMM d, ''yy"
	 *            Wed, Jul 4, '01 "h:mm a" 12:08 PM "hh 'o''clock' a, zzzz" 12
	 *            o'clock PM, Pacific Daylight Time "K:mm a, z" 0:08 PM, PDT
	 *            "yyyyy.MMMMM.dd GGG hh:mm aaa" 02001.July.04 AD 12:08 PM "EEE,
	 *            d MMM yyyy HH:mm:ss Z" Wed, 4 Jul 2001 12:08:56 -0700
	 *            "yyMMddHHmmssZ" 010704120856-0700 "yyyy-MM-dd'T'HH:mm:ss.SSSZ"
	 *            2001-07-04T12:08:56.235-0700
	 * @return
	 */
	public static String formatDate(Date dt, String pattern) {
		DateFormatter ft = new DateFormatter(dt);
		return ft.formatDate(pattern);
	}

	/**
	 * 得到日期部分
	 * 
	 * @param dt
	 *            需要格式的日期
	 * @return eg: 2007年8月21日
	 */
	public static String getDatePart(Date dt) {
		return formatDate(dt, "yyyy年MM月dd日");
	}

	/**
	 * 得到预定义好的时间格式（较长）
	 * 
	 * @param dt
	 *            需要格式的日期
	 * @return eg: 2007年8月21日 21:45
	 */
	public static String getLongTime(Date dt) {
		return formatDate(dt, "yyyy年MM月dd日 HH:mm");
	}
}
