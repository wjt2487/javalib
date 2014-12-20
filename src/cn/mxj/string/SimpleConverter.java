package cn.mxj.string;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleConverter {

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
	public static Date stringToDate(String dt, String formatStyle)
			throws ParseException {
		SimpleDateFormat ft = new SimpleDateFormat(formatStyle);
		return ft.parse(dt);
	}

	/**
	 * 安全的将字符串转化整型值
	 * 
	 * @param s
	 *            将要转化的字符串
	 * @return 转化后的整型值
	 */
	public static int safeParseInt(String s) {
		return safeParseInt(s, 0);
	}

	public static int safeParseInt(String s, int defaultValue) {
		if (StringUtil.isNullOrEmpty(s))
			return defaultValue;
		else
			return Integer.parseInt(s);
	}
}
