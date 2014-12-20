/**
 * 
 */
package cn.mxj.util;

import java.util.Hashtable;

/**
 * 原始数据类型和其包装类的相互转换
 * 
 * @author fl
 * 
 */
public class PrimitiveClassMapping {

	private static Hashtable<String, Class> claMapping;

	static {
		claMapping = new Hashtable<String, Class>();

		claMapping.put("java.lang.Boolean", boolean.class);
		claMapping.put("java.lang.Character", char.class);
		claMapping.put("java.lang.Byte", byte.class);
		claMapping.put("java.lang.Short", short.class);
		claMapping.put("java.lang.Integer", int.class);
		claMapping.put("java.lang.Long", long.class);
		claMapping.put("java.lang.Float", float.class);
		claMapping.put("java.lang.Double", double.class);
	}

	/**
	 * 获取 cla 对应的原始数据类型
	 * 
	 * @param cla
	 * @return 若 cla 存在对应的原始数据类型，则返回原始数据类型，否则返回自身
	 */
	public static Class toPrimitiveClass(Class cla) {
		Class out = claMapping.get(cla.getName());
		return out != null ? out : cla;
	}
}
