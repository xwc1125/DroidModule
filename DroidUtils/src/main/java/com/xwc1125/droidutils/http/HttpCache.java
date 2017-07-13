/**
 * <p>
 * Title: HttpCache.java
 * </p>
 * <p>
 * Description: 网络请求结果缓存类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月17日 上午9:19:16
 * @version V1.0
 */
package com.xwc1125.droidutils.http;

import android.annotation.SuppressLint;
import android.text.TextUtils;


import com.xwc1125.droidutils.http.cache.LruCache;

import java.util.concurrent.ConcurrentHashMap;

/**
 * 
 * <p>
 * Title: HttpCache
 * </p>
 * <p>
 * Description: 用于缓存网络请求结果
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年8月17日 上午9:41:50
 * 
 */
public class HttpCache {
	/**
	 * key: url ；value: 网络访问结果
	 */
	private final LruCache<String, String> mMemoryCache;

	/**
	 * 缓存内容长度
	 */
	private final static int DEFAULT_CACHE_SIZE = 1024 * 100;
	/**
	 * 默认缓存时间(有效期)
	 */
	private final static long DEFAULT_EXPIRY_TIME = 1000 * 60;
	/**
	 * 缓存大小
	 */
	private int cacheSize = DEFAULT_CACHE_SIZE;

	/**
	 * 缓存时间
	 */
	private static long defaultExpiryTime = DEFAULT_EXPIRY_TIME;

	/**
	 * 
	 * <p>
	 * Title: HttpCache
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 */
	public HttpCache() {
		this(HttpCache.DEFAULT_CACHE_SIZE, HttpCache.DEFAULT_EXPIRY_TIME);
	}

	/**
	 * 
	 * <p>
	 * Title: HttpCache
	 * </p>
	 * <p>
	 * Description: 带参构造方法
	 * </p>
	 * 
	 * @param strLength
	 *            ：缓存大小
	 * @param defaultExpiryTime
	 *            ：缓存有效期
	 */
	public HttpCache(int strLength, long defaultExpiryTime) {
		this.cacheSize = strLength;
		HttpCache.defaultExpiryTime = defaultExpiryTime;

		mMemoryCache = new LruCache<String, String>(this.cacheSize) {
			@Override
			protected int sizeOf(String key, String value) {
				if (value == null)
					return 0;
				return value.length();
			}
		};
	}

	/**
	 * @param strLength
	 *            the strLength to set
	 */
	public void setCacheSize(int strLength) {
		mMemoryCache.setMaxSize(strLength);
	}

	/**
	 * @param defaultExpiryTime
	 *            the defaultExpiryTime to set
	 */
	public static void setDefaultExpiryTime(long defaultExpiryTime) {
		HttpCache.defaultExpiryTime = defaultExpiryTime;
	}

	/**
	 * @return defaultExpiryTime
	 */
	public static long getDefaultExpiryTime() {
		return HttpCache.defaultExpiryTime;
	}

	/**
	 * 
	 * <p>
	 * Title: put
	 * </p>
	 * <p>
	 * Description: 新添缓存（默认有效期）
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param url
	 * @param result
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:47:45
	 */
	public void put(String url, String result) {
		put(url, result, defaultExpiryTime);
	}

	/**
	 * 
	 * <p>
	 * Title: put
	 * </p>
	 * <p>
	 * Description: 新添带有效期的缓存
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param url
	 * @param result
	 * @param expiry
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:49:33
	 */
	public void put(String url, String result, long expiry) {
		if (url == null || result == null || expiry < 1)
			return;

		mMemoryCache.put(url, result, System.currentTimeMillis() + expiry);
	}

	/**
	 * 
	 * <p>
	 * Title: get
	 * </p>
	 * <p>
	 * Description: 获取缓存值
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param url
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:50:08
	 */
	public String get(String url) {
		return (url != null) ? mMemoryCache.get(url) : null;
	}

	/**
	 * 
	 * <p>
	 * Title: clear
	 * </p>
	 * <p>
	 * Description: 清除缓存内容
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:50:41
	 */
	public void clear() {
		mMemoryCache.evictAll();
	}

	/**
	 * 
	 * <p>
	 * Title: isEnabled
	 * </p>
	 * <p>
	 * Description:根据网络请求方式判断缓存是否可用
	 * </p>
	 * <p>
	 * 默认只有GET请求方式才使用缓存功能
	 * </p>
	 * 
	 * @param method
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:52:00
	 */
	public boolean isEnabled(HttpRequest.HttpMethod method) {
		if (method == null)
			return false;

		Boolean enabled = httpMethod_enabled_map.get(method.toString());
		return enabled == null ? false : enabled;
	}

	/**
	 * 
	 * <p>
	 * Title: isEnabled
	 * </p>
	 * <p>
	 * Description: 根据网络请求方式判断缓存是否可用
	 * </p>
	 * <p>
	 * 默认只有GET请求方式才使用缓存功能
	 * </p>
	 * 
	 * @param method
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:53:46
	 */
	@SuppressLint("DefaultLocale")
	public boolean isEnabled(String method) {
		if (TextUtils.isEmpty(method))
			return false;

		Boolean enabled = httpMethod_enabled_map.get(method.toUpperCase());
		return enabled == null ? false : enabled;
	}

	/**
	 * 
	 * <p>
	 * Title: setEnabled
	 * </p>
	 * <p>
	 * Description: 设置指定的请求方式可使用缓存功能
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param method
	 * @param enabled
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午9:56:06
	 */
	public void setEnabled(HttpRequest.HttpMethod method, boolean enabled) {
		httpMethod_enabled_map.put(method.toString(), enabled);
	}

	/**
	 * 可用缓存功能的请求方式集合
	 */
	private final static ConcurrentHashMap<String, Boolean> httpMethod_enabled_map;

	/**
	 * 初始化可用缓存功能的请求方式集合，并添加默认的GET请求方式
	 */
	static {
		httpMethod_enabled_map = new ConcurrentHashMap<String, Boolean>(10);
		httpMethod_enabled_map.put(HttpRequest.HttpMethod.GET.toString(), true);

		cacheMap = new ConcurrentHashMap<String, CacheInfo>(10);
	}

	/**
	 * 
	 * <p>
	 * Title: CacheInfo
	 * </p>
	 * <p>
	 * Description: cache信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2017年2月15日 上午11:51:36
	 *
	 */
	public class CacheInfo {
		private String cacheKey;
		private String method;
		private boolean isCacheEnable;

		public CacheInfo() {
		}

		/**
		 * <p>
		 * Title:
		 * </p>
		 * <p>
		 * Description:
		 * </p>
		 * 
		 * @param cacheKey
		 * @param method
		 * @param isCacheEnable
		 */
		public CacheInfo(String cacheKey, String method, boolean isCacheEnable) {
			super();
			this.cacheKey = cacheKey;
			this.method = method;
			this.isCacheEnable = isCacheEnable;
		}

		/**
		 * @return the cacheKey
		 */
		public String getCacheKey() {
			return cacheKey;
		}

		/**
		 * @param cacheKey
		 *            the cacheKey to set
		 */
		public void setCacheKey(String cacheKey) {
			this.cacheKey = cacheKey;
		}

		/**
		 * @return the method
		 */
		public String getMethod() {
			return method;
		}

		/**
		 * @param method
		 *            the method to set
		 */
		public void setMethod(String method) {
			this.method = method;
		}

		/**
		 * @return the isCache
		 */
		public boolean isCacheEnable() {
			return isCacheEnable;
		}

		/**
		 * @param isCacheEnable
		 *            the isCache to set
		 */
		public void setCacheEnable(boolean isCacheEnable) {
			this.isCacheEnable = isCacheEnable;
		}

		/*
		 * （非 Javadoc） <p> Title: toString </p> <p> Description: </p>
		 * 
		 * @return
		 * 
		 * @see java.lang.Object#toString()
		 * 
		 * @author Administrator
		 * 
		 * @date 2017年2月15日 下午3:34:15
		 */
		@Override
		public String toString() {
			return "CacheInfo [cacheKey=" + cacheKey + ", method=" + method + ", isCacheEnable=" + isCacheEnable + "]";
		}

	}

	private static ConcurrentHashMap<String, CacheInfo> cacheMap;

	/**
	 * <p>
	 * Title: setCacheInfo
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param cacheKey
	 * @tags @param method
	 * @tags @param enabled
	 * 
	 * @author xwc1125
	 * @date 2017年2月15日 上午11:52:25
	 */
	public void setCacheInfo(String cacheKey, HttpRequest.HttpMethod method, boolean enabled) {
		try {
			if (method != null && cacheKey != null) {
				CacheInfo cacheInfo = new CacheInfo();
				cacheInfo.setCacheKey(cacheKey);
				cacheInfo.setMethod(method.toString());
				cacheInfo.setCacheEnable(enabled);
				cacheMap.put(cacheKey, cacheInfo);
			}
		} catch (Exception e) {
		}
	}

	public CacheInfo newCacheInfo() {
		return new CacheInfo();
	}

	/**
	 * <p>
	 * Title: isEnabled
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param cacheInfo2
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2017年2月15日 下午12:05:30
	 */
	public boolean isEnabled(CacheInfo cacheInfo2) {
		try {
			if (cacheInfo2 != null && cacheInfo2.getCacheKey() != null
					&& cacheMap.containsKey(cacheInfo2.getCacheKey())) {
				CacheInfo cacheInfo = cacheMap.get(cacheInfo2.getCacheKey());
				if (cacheInfo != null && cacheInfo2.getCacheKey().equals(cacheInfo.getCacheKey())
						&& cacheInfo2.getMethod().equals(cacheInfo.getMethod()) && cacheInfo.isCacheEnable) {
					return true;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
}
