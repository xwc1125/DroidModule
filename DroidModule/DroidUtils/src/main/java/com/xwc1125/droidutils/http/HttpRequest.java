/**
 * <p>
 * Title: HttpRequest.java
 * </p>
 * <p>
 * Description: 网络访问请求对象
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 上午10:11:16
 * @version V1.0
 */
package com.xwc1125.droidutils.http;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.http.entity.RequestInfo;
import com.xwc1125.droidutils.http.utils.InnerHttpUtil;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.CookieHandler;
import java.net.CookieManager;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.Map;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * 
 * <p>
 * Title: HttpRequest
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 封装网络访问请求对象
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月17日 上午10:55:27
 * 
 * @param <T>
 */
public class HttpRequest<T> {
	private static final String TAG = "HttpUtils";
	public static boolean isDebug = HttpConfig.IS_SHOW_LOG;
	/**
	 * 编码方式
	 */
	public static final String CHARSET = "UTF-8";
	/**
	 * 使用https访问网络时运行所有主机进行验证
	 */
	private final static HostnameVerifier NOT_VERIFY = new HostnameVerifier() {
		public boolean verify(String hostname, SSLSession session) {
			return true;
		}
	};
	/**
	 * 上下文
	 */
	private Context context;
	/**
	 * 请求信息
	 */
	private RequestInfo<T> requestInfo;
	/**
	 * 请求链接对象
	 */
	private HttpURLConnection connection;
	/**
	 * 请求方式
	 */
	private String requestMethod;
	/**
	 * 请求地址
	 */
	private String requestUrl;
	/**
	 * 是否是重定向
	 */
	private Boolean isRedirect;
	/**
	 * 请求体头部参数
	 */
	private HashMap<String, Object> headerMap;

	/**
	 * 
	 * <p>
	 * Title: HttpRequest
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * 
	 * @param context
	 * @param requestInfo
	 *            ：请求信息
	 * @throws Exception
	 */
	public HttpRequest(Context context, RequestInfo<T> requestInfo) throws Exception {
		this.context = context;
		this.requestInfo = requestInfo;
		initClient();
	}

	/**
	 * 
	 * <p>
	 * Title: getClient
	 * </p>
	 * <p>
	 * Description: 获取HttpURLConnection
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @return
	 * @author xwc1125
	 * @throws Exception
	 * @date 2016年5月12日上午10:22:30
	 */
	public HttpURLConnection getClient() throws Exception {
		if (connection == null) {
			initClient();
		}
		return connection;
	}

	/**
	 * 
	 * <p>
	 * Title: setClient
	 * </p>
	 * <p>
	 * Description: 设置HttpURLConnection
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param connection
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 上午11:02:35
	 */
	public void setClient(HttpURLConnection connection) {
		this.connection = connection;
	}

	/**
	 * 
	 * <p>
	 * Title: getRedirectConnect
	 * </p>
	 * <p>
	 * Description: 重定向connect
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param url
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年5月13日下午5:56:07
	 */
	@SuppressLint("DefaultLocale")
	public HttpURLConnection getRedirectConnect(String url) {
		isRedirect = true;
		setCookieManager();// 初始化CookieHandler
		URL httpUrl = null;
		HttpURLConnection conn = null;
		try {
			httpUrl = new URL(url);
			String protocol = httpUrl.getProtocol();
			if (InnerHttpUtil.isNotEmpty(protocol)) {
				if ("https".equals(protocol.toLowerCase())) {// https
					trustAllHosts();// 信任所有证书 必须在openConnection执行之前调用
					HttpsURLConnection httpsConnect = (HttpsURLConnection) httpUrl.openConnection();
					httpsConnect.setHostnameVerifier(NOT_VERIFY);
					conn = httpsConnect;
				}
			}
			if (conn == null) {
				conn = (HttpURLConnection) httpUrl.openConnection();
			}
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setConnectTimeout(30000);
			conn.setReadTimeout(30000);
			conn.setInstanceFollowRedirects(true);
			// }
			String userAgent = InnerHttpUtil.getUserAgent(context);
			setHttpHeader(headerMap);
			setRequestProperty("user-agent", userAgent);

		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		if (conn != null) {
			connection = conn;
		}
		return connection;
	}

	/**
	 * app都采用指定的网络进行请求
	 * 
	 * 但是效果不是立马就能够实现。
	 * 
	 * @param url
	 */
	@TargetApi(21)
	private void setCellularConnect(String url) {
		setCookieManager();// 初始化CookieHandler
		final String httpUrl = url;
		final ConnectivityManager connectivityManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkRequest.Builder builder = new NetworkRequest.Builder();
		builder.addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET);
		builder.addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR);
		NetworkRequest networkRequest = builder.build();
		connectivityManager.requestNetwork(networkRequest, new ConnectivityManager.NetworkCallback() {
			@SuppressLint("DefaultLocale")
			@Override
			public void onAvailable(Network network) {
				// NetworkInfo networkInfo =
				// connectivityManager.getNetworkInfo(network);
				LogUtils.e(TAG, "setCellularConnect succ", isDebug);
				try {
					URL httpURL = new URL(httpUrl);
					String protocol = httpURL.getProtocol();
					URLConnection conn = null;
					if (InnerHttpUtil.isNotEmpty(protocol)) {
						if ("https".equals(protocol.toLowerCase())) {// https
							trustAllHosts();// 信任所有证书
											// 必须在openConnection执行之前调用
							conn = network.openConnection(httpURL);
							((HttpsURLConnection) conn).setHostnameVerifier(NOT_VERIFY);
						}
					}
					if (conn == null) {
						conn = network.openConnection(httpURL);
					}
					conn.setDoInput(true);
					conn.setDoOutput(true);
					conn.setConnectTimeout(30000);
					conn.setReadTimeout(30000);
					((HttpURLConnection) conn).setInstanceFollowRedirects(false);
					String userAgent = InnerHttpUtil.getUserAgent(context);
					setHttpHeader(headerMap);
					setRequestProperty("user-agent", userAgent);

					if (conn != null) {
						connection = (HttpURLConnection) conn;
						LogUtils.e(TAG, "setCellularConnect set succ", isDebug);
					}

				} catch (Exception e) {
					LogUtils.e(TAG, e.getMessage(), isDebug);
				}

				super.onAvailable(network);
			}
		});
	}

	/**
	 * 
	 * <p>
	 * Title: getRequestInfo
	 * </p>
	 * <p>
	 * Description: 获取请求消息
	 * </p>
	 * 
	 * @return
	 * @author xwc1125
	 * @date 2016年5月12日上午10:29:27
	 */
	public RequestInfo<T> getRequestInfo() {
		return requestInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: initClient
	 * </p>
	 * <p>
	 * Description: 初始化请求连接对象
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @throws Exception
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 上午11:03:34
	 */
	@SuppressLint("DefaultLocale")
	private void initClient() throws Exception {
		isRedirect = false;
		setCookieManager();// 初始化CookieHandler
		String realRequestUrl = "";
		// URL httpUrl = null;
		try {
			if (requestInfo != null) {
				requestMethod = requestInfo.getRequestMethod();
				requestUrl = requestInfo.getUrl();
				String requestParam = requestInfo.getURLParames();
				headerMap = requestInfo.getHeaderMap();
				if (requestMethod.equals(HttpMethod.GET.value)) {
					if (InnerHttpUtil.isNotEmpty(requestParam)) {
						realRequestUrl = requestUrl + "?" + requestParam;
					} else {
						realRequestUrl = requestUrl;
					}
				} else {
					realRequestUrl = requestUrl;
				}
				URL httpUrl = new URL(realRequestUrl);
				String protocol = httpUrl.getProtocol();
				if (InnerHttpUtil.isNotEmpty(protocol)) {
					if ("https".equals(protocol.toLowerCase())) {// https
						trustAllHosts();// 信任所有证书 必须在openConnection执行之前调用
						HttpsURLConnection httpsConnect = (HttpsURLConnection) httpUrl.openConnection();
						httpsConnect.setHostnameVerifier(NOT_VERIFY);
						connection = httpsConnect;
					}
				}
				if (connection == null) {
					connection = (HttpURLConnection) httpUrl.openConnection();
				}
				connection.setConnectTimeout(30000);
				connection.setReadTimeout(30000);
				connection.setInstanceFollowRedirects(true);
				String userAgent = InnerHttpUtil.getUserAgent(context);
				setHttpHeader(headerMap);
				setRequestProperty("user-agent", userAgent);
			} else {
				throw new Exception("requestInfo is null");
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			throw new Exception("网络访问出错");
		}
	}

	/**
	 * 
	 * <p>
	 * Title: execute
	 * </p>
	 * <p>
	 * Description: 执行
	 * </p>
	 * <p>
	 * 请求连接对象进行连接
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年5月12日上午11:03:35
	 */
	public HttpURLConnection execute(HttpURLConnection client) throws Exception {
		if (client != null) {
			if (requestInfo.hasFileParams()) {
				client.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + RequestInfo.boundary);
			} else if (requestInfo.hasFilesList()) {
				// 添加头部
				client.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + RequestInfo.boundary);
			} else {
				client.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			}
			client.setRequestProperty("Charset", "UTF-8");
			client.setRequestProperty("connection", "keep-alive");
			if (InnerHttpUtil.isNotEmpty(requestMethod) && requestMethod.equals(HttpMethod.POST.value) && !isRedirect) {
				connection.setDoOutput(true);
				client.setRequestMethod("POST");
				client.connect();
				if (requestInfo != null) {
					ByteArrayOutputStream outputStream = requestInfo.getParamsOutputStream();
					if (outputStream != null) {
						OutputStream os = connection.getOutputStream();
						os.write(outputStream.toByteArray());
					}
				}
			} else {
				client.setRequestMethod("GET");
				client.connect();
			}
		}
		return client;
	}

	/**
	 * 
	 * <p>
	 * Title: setHeader
	 * </p>
	 * <p>
	 * Description: 设置网络访问请求头
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年5月12日上午10:52:36
	 */
	public void setRequestProperty(String field, String newValue) {
		try {
			if (connection != null) {
				connection.setRequestProperty(field, newValue);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: setCookieManager
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年5月12日上午9:44:25
	 */
	@SuppressLint("NewApi")
	private void setCookieManager() {
		CookieManager cookieManager = new CookieManager();
		CookieHandler.setDefault(cookieManager);
	}

	/**
	 * 
	 * <p>
	 * Title: setHeadMap
	 * </p>
	 * <p>
	 * Description: 将外部自定义的头部放进头部中
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param headerMap
	 * 
	 * @author xwc1125
	 * @date May 14, 201610:22:13 PM
	 */
	public void setHttpHeader(HashMap<String, Object> headerMap) {
		if (headerMap == null || headerMap.size() == 0) {
			return;
		}
		for (Map.Entry<String, Object> entry : headerMap.entrySet()) {
			String key = entry.getKey();
			Object value = entry.getValue();
			setRequestProperty(key, value + "");
		}
	}

	/**
	 * 
	 * <p>
	 * Title: trustAllHosts
	 * </p>
	 * <p>
	 * Description: https访问信任所有证书
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年4月6日上午11:31:34
	 */
	@SuppressLint("TrulyRandom")
	private static void trustAllHosts() throws Exception {
		TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {

			public X509Certificate[] getAcceptedIssuers() {
				return new X509Certificate[] {};
			}

			public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}

			public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
			}
		} };
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			throw new Exception("maybe not support https");
		}
	}

	/**
	 * 
	 * <p>
	 * Title: HttpMethod
	 * </p>
	 * <p>
	 * Description: http的访问方式
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年5月12日上午10:08:49
	 * 
	 */
	public static enum HttpMethod {
		GET("GET"), POST("POST"), PUT("PUT"), HEAD("HEAD"), MOVE("MOVE"), COPY("COPY"), DELETE("DELETE"), OPTIONS(
				"OPTIONS"), TRACE("TRACE"), CONNECT("CONNECT");
		private final String value;

		HttpMethod(String value) {
			this.value = value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}
}
