/**
 * 
 */
package cn.mxj.string;

/**
 * @author fl
 * 
 */
public class NumberFormatter {

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
}
