package cn.mxj.business;

import java.util.Hashtable;

import cn.mxj.string.StringUtil;

/**
 * 提供简单的缓存服务
 * 
 * @author fl
 * 
 */
public class CacheService {

	private Hashtable<String, Object> data;

	private CacheService() {
		this.data = new Hashtable<String, Object>();
	}

	private static CacheService instance;

	private static Object locker = CacheService.class;

	public static CacheService getInstance() {
		synchronized (locker) {
			if (instance == null) {
				instance = new CacheService();
			}
			return instance;
		}
	}

	/**
	 * 将一项数据加入缓存中
	 * 
	 * @param key
	 * @param value
	 * @return 是否添加成功
	 */
	public boolean add(String key, Object value) {
		if (StringUtil.isNullOrEmpty(key) || value == null) {
			return false;
		}
		return (this.data.put(key, value) != null);
	}

	/**
	 * 从缓存中取回指定键值的数据项
	 * 
	 * @param key
	 * @return 找不到时则返回 null
	 */
	public Object get(String key) {
		if (StringUtil.isNullOrEmpty(key)) {
			return null;
		}
		return this.data.get(key);
	}

	public void clear() {
		instance = null;
	}
}
