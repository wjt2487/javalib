/**
 * 
 */
package cn.mxj.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.mxj.io.AppLogger;

/**
 * bean 工具类，提供一些简单对象的常用操作
 * 
 * @author fl
 * 
 */
public class BeansUtil {

	/**
	 * 从实体列表中提取属性值，并作为列表返回
	 * 
	 * @param <E>
	 *            property type
	 * @param beanList
	 * @param propName
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <E> List<E> pickProperty(List beanList, String propName) {
		List<E> l = new ArrayList<E>(beanList.size());

		Method m = null;
		for (Object o : beanList) {
			if (m == null) {
				try {
					m = o.getClass().getMethod(getPropertyMethodName(propName));
				} catch (NoSuchMethodException ex) {
					AppLogger.getInstance().exception(ex);
					return l;
				}
			}

			try {
				// unsafety cast
				l.add((E) m.invoke(o));
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}

		return l;
	}

	/**
	 * 将实例列表中的每个实例的属性提取出来连接成字符串
	 * 
	 * @param beanList
	 * @param propName
	 * @return
	 */
	public static String pickAndJoinProperty(List beanList, String propName) {
		return pickAndJoinProperty(beanList, propName, ",");
	}
	
	public static String pickAndJoinStringProperty(List beanList, String propName) {
		return pickAndJoinStringProperty(beanList, propName, ",");
	}

	/**
	 * 将实例列表中的每个实例的属性提取出来连接成字符串
	 * 
	 * @param beanList
	 * @param propName
	 * @param separator
	 * @return
	 */
	public static String pickAndJoinProperty(List beanList, String propName,
			String separator) {
		List<Object> list = pickProperty(beanList, propName);
		StringBuffer sb = new StringBuffer();
		for (Object obj : list) {
			sb.append(separator).append(obj.toString());
		}
		if (sb.length() > 0) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}
	
	/**
	 * 将实例列表中的每个实例的属性提取出来连接成字符串
	 * 
	 * @param beanList
	 * @param propName
	 * @param separator
	 * @return
	 */
	public static String pickAndJoinStringProperty(List beanList, String propName,
			String separator) {
		List<Object> list = pickProperty(beanList, propName);
		StringBuffer sb = new StringBuffer();
		for (Object obj : list) {
			sb.append(separator).append("'").append(obj.toString()).append("'");
		}
		if (sb.length() > 0) {
			sb.delete(0, 1);
		}
		return sb.toString();
	}

	/**
	 * 根据一个属性名称来获取对应的 get 方法名称 （如：name -> getName）
	 * 
	 * @param propName
	 * @return
	 */
	public static String getPropertyMethodName(String propName) {
		return "get" + propName.substring(0, 1).toUpperCase()
				+ propName.substring(1);
	}

	/**
	 * 获取属性名称，如（getName -> name, setName -> name）
	 * 
	 * @param getOrSetMethodName
	 * @return
	 */
	public static String getPropertyName(String getOrSetMethodName) {
		String s = getOrSetMethodName.substring(3);
		s = s.substring(0, 1).toLowerCase() + s.substring(1);
		return s;
	}
}
