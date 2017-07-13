/**
 * <p>
 * Title: HttpUtils.java
 * </p>
 * <p>
 * Description: 网络访问操作工具类
 * </p>
 * <p>
 * 1、获取用户UA头信息
 * 2、判断是否支持断点续传、下载
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午11:44:11
 * @version V1.0
 */
package com.xwc1125.droidutils.http;

import java.net.HttpURLConnection;
import java.util.Locale;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;

/**
 * 
 * <p>
 * Title: HttpUtils
 * </p>
 * <p>
 * Description:网络访问工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年1月20日下午5:42:50
 * 
 */
public class HttpUtils {
	private static final String TAG = HttpUtils.class.getName();
	private static final Boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: getUserAgent
	 * </p>
	 * <p>
	 * Description: 获取ua头
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月11日下午3:01:21
	 */
	@SuppressLint("DefaultLocale")
	public static String getUserAgent(Context context) {
		String webUserAgent = null;
		if (context != null) {
//			try {
//				Class<?> sysResCls = Class
//						.forName("com.android.internal.R$string");
//				Field webUserAgentField = sysResCls
//						.getDeclaredField("web_user_agent");
//				Integer resId = (Integer) webUserAgentField.get(null);
//				webUserAgent = context.getString(resId);
//			} catch (Throwable ignored) {
//				LogUtils.e(TAG, ignored.getMessage(), isDebug);
//				LogHandler.newInstance(context).saveLog(ignored);
//			}
		}
		if (TextUtils.isEmpty(webUserAgent)) {
			webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
		}
		try {
			Locale locale = Locale.getDefault();
			StringBuffer buffer = new StringBuffer();
			// Add version
			final String version = Build.VERSION.RELEASE;
			if (version.length() > 0) {
				buffer.append(version);
			} else {
				// default to "1.0"
				buffer.append("1.0");
			}
			buffer.append("; ");
			final String language = locale.getLanguage();
			if (language != null) {
				buffer.append(language.toLowerCase());
				final String country = locale.getCountry();
				if (country != null) {
					buffer.append("-");
					buffer.append(country.toLowerCase());
				}
			} else {
				// default to "en"
				buffer.append("en");
			}
			// add the model for the release build
			if ("REL".equals(Build.VERSION.CODENAME)) {
				final String model = Build.MODEL;
				if (model.length() > 0) {
					buffer.append("; ");
					buffer.append(model);
				}
			}
			final String id = Build.ID;
			if (id.length() > 0) {
				buffer.append(" Build/");
				buffer.append(id);
			}
			webUserAgent = String.format(webUserAgent, buffer, "Mobile ");
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return webUserAgent;
	}

	/**
	 * 
	 * <p>
	 * Title: isSupportRange
	 * </p>
	 * <p>
	 * Description: 是否支持断点续传
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param connection
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午12:43:37
	 */
	public static boolean isSupportRange(final HttpURLConnection connection) {
		if (connection == null)
			return false;
		String value = connection.getHeaderField("Accept-Ranges");
		if (StringUtils.isNotEmpty(value)) {
			return "bytes".equals(value);
		}
		String rangeValue = connection.getHeaderField("Content-Range");
		if (StringUtils.isNotEmpty(rangeValue)) {
			return value.startsWith("bytes");
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: getFileNameFromHttpResponse
	 * </p>
	 * <p>
	 * Description: 根据http响应结果获取文件名称
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param connection
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月20日下午2:33:53
	 */
	public static String getFileNameFromHttpResponse(
			final HttpURLConnection connection) {
		String fileName = null;
		try {
			if (connection != null) {
				String headerField = connection
						.getHeaderField("Content-Disposition");
				if (StringUtils.isNotEmpty(headerField)) {
					String contentDisposition = new String(
							headerField.getBytes("ISO-8859-1"), "GBK");
					if (StringUtils.isNotEmpty(contentDisposition)) {
						fileName = contentDisposition.substring(
								contentDisposition.indexOf('\"') + 1,
								contentDisposition.lastIndexOf("\""));
					}
				}
			}
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return fileName;
	}
}
