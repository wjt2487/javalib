/**
 * 
 */
package cn.mxj.string;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期数据的格式化工具类
 * 
 * @author fl
 * 
 */
public class DateFormatter {

	private Date d;

	public DateFormatter(Date d) {
		this.d = d;
	}

	public static DateFormatter now() {
		return new DateFormatter(new Date());
	}

	/**
	 * 日期字符串
	 * 
	 * @return example: 2007-08-25
	 */
	public String toDateString() {
		return String.format("%1$tY-%1$tm-%1$td", d);
	}

	/**
	 * 时间字符串
	 * 
	 * @return example: 16:50:37
	 */
	public String toTimeString() {
		return String.format("%1$tH:%1$tM:%1$tS", d);
	}

	/**
	 * 日期和时间字符串
	 * 
	 * @return example: 2007-08-25 16:50:37
	 */
	public String toDateTimeString() {
		return this.toDateString() + " " + this.toTimeString();
	}

	/**
	 * 纯数字的日期和时间
	 * 
	 * @return example: 20070825165037
	 */
	public String toDateTimeNumberString() {
		return String.format("%1$tY%1$tm%1$td%1$tH%1$tM%1$tS", d);
	}

	/**
	 * 毫秒值的字符串形式
	 * 
	 * @return
	 */
	public String toValueString() {
		return String.valueOf(d.getTime());
	}

	/**
	 * 格式化显示日期
	 * 
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
	public String formatDate(String pattern) {
		SimpleDateFormat ft = new SimpleDateFormat(pattern);
		return ft.format(d);
	}

	/**
	 * 得到日期部分
	 * 
	 * @param dt
	 *            需要格式的日期
	 * @return eg: 2007年8月21日
	 */
	public String toChineseDateString() {
		return formatDate("yyyy年MM月dd日");
	}

	/**
	 * 得到预定义好的时间格式（较长）
	 * 
	 * @param dt
	 *            需要格式的日期
	 * @return eg: 2007年8月21日 21:45
	 */
	public String toChineseDateTimeString() {
		return formatDate("yyyy年MM月dd日 HH:mm");
	}

}
