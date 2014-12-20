package cn.mxj.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import cn.mxj.io.AppLogger;

/**
 * 提供一些常规函数的工具类
 * 
 * @author fl
 * 
 */
public class CommonUtil {

	/**
	 * 实现泛型列表类型的相互转换
	 * 
	 * @param <To>
	 * @param <From>
	 * @param sour
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <To, From> List<To> genericListCast(List<From> sour) {
		List<To> lb = new ArrayList<To>(sour.size());
		for (From f : sour) {
			try {
				// unsafety cast
				lb.add((To) f);
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return lb;
	}

	/**
	 * 实现泛型列表类型的相互转换
	 * 
	 * @param <To>
	 * @param <From>
	 * @param sour
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static <To> List<To> genericListCast2(List sour) {
		List<To> lb = new ArrayList<To>(sour.size());
		for (Object f : sour) {
			try {
				// unsafety cast
				lb.add((To) f);
			} catch (Exception ex) {
				AppLogger.getInstance().exception(ex);
			}
		}
		return lb;
	}

	/**
	 * 调用一个类的某个方法
	 * 
	 * @param className
	 * @param methodName
	 * @return 调用结果
	 */
	public static Object invokeMethod(String className, String methodName) {
		try {
			Class c = Class.forName(className);
			Object o = c.newInstance();
			Method m = c.getMethod(methodName);
			return m.invoke(o);
		} catch (Exception e) {
			AppLogger.getInstance().exception(e);
			return null;
		}
	}
}
