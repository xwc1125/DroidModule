/**
 * <p>
 * Title: KeyExpiryMap.java
 * </p>
 * <p>
 * Description: 有效期缓存
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.yuancy.framework.http.cache;

import java.util.concurrent.ConcurrentHashMap;

/***
 * 
 * <p>
 * Title: KeyExpiryMap
 * </p>
 * <p>
 * Description:有效期缓存
 * </p>
 * <p>
 * ConcurrentHashMap 线程安全的HashMap, 适用于多线程操作
 * </p>
 * 
 * @author zhangqy
 * @date 2016年5月11日下午1:36:18
 * 
 * @param <K>
 * @param <V>
 */
public class KeyExpiryMap<K, V> extends ConcurrentHashMap<K, Long> {
	/**
	 * serialVersionUID
	 * 
	 * @author zhangqy
	 * @date 2016年5月11日下午1:42:55
	 */
	private static final long serialVersionUID = 5514969596535320724L;
	/**
	 * 并发级别
	 */
	private static final int DEFAULT_CONCURRENCY_LEVEL = 16;

	/**
	 * 
	 * <p>
	 * Title:KeyExpiryMap
	 * </p>
	 * <p>
	 * Description:构造方法
	 * </p>
	 * 
	 * @param initialCapacity
	 *            容量，即数组大小。
	 * @param loadFactor
	 *            比例，用于容量扩充
	 * @param concurrencyLevel
	 *            用户估计的并发级别
	 */
	public KeyExpiryMap(int initialCapacity, float loadFactor,
			int concurrencyLevel) {
		super(initialCapacity, loadFactor, concurrencyLevel);
	}

	/**
	 * 
	 * <p>
	 * Title: KeyExpiryMap
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * 
	 * @param initialCapacity
	 *            ：容量，即数组大小。
	 * @param loadFactor
	 *            ： 比例，用于容量扩充
	 */
	public KeyExpiryMap(int initialCapacity, float loadFactor) {
		super(initialCapacity, loadFactor, DEFAULT_CONCURRENCY_LEVEL);
	}

	/**
	 * 
	 * <p>
	 * Title: KeyExpiryMap
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * 
	 * @param initialCapacity
	 *            :容量，即数组大小。
	 */
	public KeyExpiryMap(int initialCapacity) {
		super(initialCapacity);
	}

	/**
	 * 
	 * <p>
	 * Title: KeyExpiryMap
	 * </p>
	 * <p>
	 * Description: 无参构造方法
	 * </p>
	 */
	public KeyExpiryMap() {
		super();
	}

	/*
	 * （非 Javadoc） <p> Title: get </p> <p> Description: 获取key的有效期 </p>
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @see java.util.concurrent.ConcurrentHashMap#get(java.lang.Object)
	 * 
	 * @author zhangqy
	 * 
	 * @date 2016年8月16日 下午5:22:59
	 */
	@Override
	public synchronized Long get(Object key) {
		if (this.containsKey(key)) {
			return super.get(key);
		} else {
			return null;
		}
	}

	/*
	 * （非 Javadoc） <p> Title: put </p> <p> Description: 设置key的有效期 </p>
	 * 
	 * @param key
	 * 
	 * @param expiryTimestamp
	 * 
	 * @return
	 * 
	 * @see java.util.concurrent.ConcurrentHashMap#put(java.lang.Object,
	 * java.lang.Object)
	 * 
	 * @author zhangqy
	 * 
	 * @date 2016年8月16日 下午5:23:09
	 */
	@Override
	public synchronized Long put(K key, Long expiryTimestamp) {
		if (this.containsKey(key)) {
			this.remove(key);
		}
		return super.put(key, expiryTimestamp);
	}

	/*
	 * （非 Javadoc） <p> Title: containsKey </p> <p> Description:
	 * 判断是否存在key，并且未过有效期 </p>
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @see java.util.concurrent.ConcurrentHashMap#containsKey(java.lang.Object)
	 * 
	 * @author zhangqy
	 * 
	 * @date 2016年8月16日 下午5:25:22
	 */
	@Override
	public synchronized boolean containsKey(Object key) {
		boolean result = false;
		Long expiryTimestamp = super.get(key);
		if (expiryTimestamp != null
				&& System.currentTimeMillis() < expiryTimestamp) {
			result = true;
		} else {
			this.remove(key);
		}
		return result;
	}

	/*
	 * （非 Javadoc） <p> Title: remove </p> <p> Description: 移除 </p>
	 * 
	 * @param key
	 * 
	 * @return
	 * 
	 * @see java.util.concurrent.ConcurrentHashMap#remove(java.lang.Object)
	 * 
	 * @author zhangqy
	 * 
	 * @date 2016年8月16日 下午5:26:06
	 */
	@Override
	public synchronized Long remove(Object key) {
		return super.remove(key);
	}

	/*
	 * （非 Javadoc） <p> Title: clear </p> <p> Description: 全部移除 </p>
	 * 
	 * @see java.util.concurrent.ConcurrentHashMap#clear()
	 * 
	 * @author zhangqy
	 * 
	 * @date 2016年8月16日 下午5:26:37
	 */
	@Override
	public synchronized void clear() {
		super.clear();
	}
}
