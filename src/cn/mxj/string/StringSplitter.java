/**
 * 
 */
package cn.mxj.string;

import java.util.ArrayList;
import java.util.List;

import cn.mxj.exception.ExceptionLevel;
import cn.mxj.io.AppLogger;

/**
 * 字符串分割器
 * 
 * @author fl
 * 
 */
public class StringSplitter {

	/**
	 * 使用默认分隔符逗号(,)
	 * 
	 */
	public StringSplitter() {
		this.separator = ",";
	}

	public StringSplitter(String separator) {
		this.separator = separator;
	}

	private String separator;

	/**
	 * 分割成 Integer 列表
	 * 
	 * @param value
	 * @return
	 */
	public List<Integer> splitToIntegerList(String value) {
		List<Integer> list = new ArrayList<Integer>();
		if (!StringUtil.isNullOrEmpty(value)) {
			String[] arr = value.split(",");
			for (String s : arr) {
				try {
					list.add(Integer.parseInt(s.trim()));
				} catch (Exception ex) {
					AppLogger.getInstance().exception(ex,
							ExceptionLevel.CanIgnore);
				}
			}
		}
		return list;
	}

	/**
	 * 分割成 Long 列表
	 * 
	 * @param value
	 * @return
	 */
	public List<Long> splitToLongList(String value) {
		List<Long> list = new ArrayList<Long>();
		if (!StringUtil.isNullOrEmpty(value)) {
			String[] arr = value.split(",");
			for (String s : arr) {
				try {
					list.add(Long.parseLong(s.trim()));
				} catch (Exception ex) {
					AppLogger.getInstance().exception(ex,
							ExceptionLevel.CanIgnore);
				}
			}
		}
		return list;
	}

	public String join(String[] values) {
		StringBuffer sb = new StringBuffer();
		for (String item : values) {
			sb.append(separator).append(item);
		}
		if (sb.length() > 0) {
			sb.delete(0, separator.length());
		}
		return sb.toString();
	}

	public String join(int[] values) {
		StringBuffer sb = new StringBuffer();
		for (int item : values) {
			sb.append(separator).append(item);
		}
		if (sb.length() > 0) {
			sb.delete(0, separator.length());
		}
		return sb.toString();
	}

	public String join(long[] values) {
		StringBuffer sb = new StringBuffer();
		for (long item : values) {
			sb.append(separator).append(item);
		}
		if (sb.length() > 0) {
			sb.delete(0, separator.length());
		}
		return sb.toString();
	}

	public String join(List<Integer> values) {
		StringBuffer sb = new StringBuffer();
		for (Integer item : values) {
			sb.append(separator).append(item.intValue());
		}
		if (sb.length() > 0) {
			sb.delete(0, separator.length());
		}
		return sb.toString();
	}

	public String join(Iterable<Object> values) {
		StringBuffer sb = new StringBuffer();
		for (Object item : values) {
			sb.append(separator).append(item.toString());
		}
		if (sb.length() > 0) {
			sb.delete(0, separator.length());
		}
		return sb.toString();
	}

}
