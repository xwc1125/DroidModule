/**
 * <p>
 * Title: HttpHandler.java
 * </p>
 * <p>
 * Description: 网络访问请求异步处理类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月17日 上午10:11:16
 * @version V1.0
 */
package com.yuancy.framework.http;

import android.os.SystemClock;

import com.yuancy.framework.http.HttpCache.CacheInfo;
import com.yuancy.framework.http.entity.RequestInfo;
import com.yuancy.framework.http.entity.ResponseInfo;
import com.yuancy.framework.http.handler.ByteArrayDownloadHandler;
import com.yuancy.framework.http.handler.FileDownloadHandler;
import com.yuancy.framework.http.handler.RequestCallBackHandler;
import com.yuancy.framework.http.handler.StringDownloadHandler;
import com.yuancy.framework.http.task.PriorityAsyncTask;
import com.yuancy.framework.http.utils.InnerHttpUtil;
import com.yuancy.framework.log.LogUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * 
 * <p>
 * Title: HttpHandler
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 使用自定义的带请求优先级的异步任务进行网络访问请求
 * </p>
 * 
 * @author zhangqy
 * @date 2016年8月17日 上午10:01:04
 * 
 * @param <T>
 */
public class HttpHandler<T> extends PriorityAsyncTask<Object, Object, Void>
		implements RequestCallBackHandler {
	/**
	 * 请求结果缓存对象
	 */
	public final static HttpCache sHttpCache = new HttpCache();
	/**
	 * 请求结果缓存默认有效期
	 */
	private long expiry = HttpCache.getDefaultExpiryTime();
	/**
	 * 网络访问请求状态－开始请求
	 */
	private final static int UPDATE_START = 1;
	/**
	 * 网络访问请求状态－请求中
	 */
	private final static int UPDATE_LOADING = 2;
	/**
	 * 网络访问请求状态－请求失败
	 */
	private final static int UPDATE_FAILURE = 3;
	/**
	 * 网络访问请求状态－请求成功
	 */
	private final static int UPDATE_SUCCESS = 4;
	/**
	 * 网络请求对象(请求参数、地址等信息)
	 */
	private HttpRequest<T> request;
	/**
	 * 请求结果回调对象
	 */
	private RequestCallback<T> callback;
	/**
	 * 请求方式(GET|POST)
	 */
	private String requestMethod;
	/**
	 * 请求的url(网络访问地址)
	 */
	private String requestUrl;
	/**
	 * 请求状态（初始为等待请求状态）
	 */
	private State state = State.WAITING;
	/**
	 * 请求失败后的重试次数
	 */
	private int retryCount;// 重试次数
	/**
	 * 请求进度是否在进行外传
	 */
	private boolean isUploading = true;
	/**
	 * 上次外传请求进度的时间
	 */
	private long lastUpdateTime;
	/**
	 * 下载文件的保存路径
	 */
	private String fileSavePath = null;
	/**
	 * 该请求是否为下载文件的请求
	 */
	private boolean isDownloadingFile = false;
	/**
	 * Whether the downloading could continue from the point of interruption.
	 * 是否支持断点下载
	 */
	private Boolean autoResume = false;
	/**
	 * Whether rename the file by response header info when the download
	 * completely. 是否可根据请求响应的头信息进行文件重命名
	 */
	private Boolean autoRename = false;
	/**
	 * 缓存主键【xwc1125】
	 */
	private CacheInfo cacheInfo;
	private Boolean isReturnByteArray = false;

	/**
	 * 
	 * <p>
	 * Title: HttpHandler
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * 
	 * @param request
	 */
	public HttpHandler(HttpRequest<T> request) {
		initData(request);
	}

	/**
	 * 
	 * <p>
	 * Title: initData
	 * </p>
	 * <p>
	 * Description: 初始化数据
	 * </p>
	 * <p>
	 * 从请求对象中获取信息初始化全局变量
	 * </p>
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午1:21:34
	 */
	private void initData(HttpRequest<T> request) {
		if (request != null) {
			this.request = request;
			RequestInfo<T> requestInfo = request.getRequestInfo();
			if (requestInfo != null) {
				requestMethod = requestInfo.getRequestMethod();
				requestUrl = requestInfo.getUrl();
				expiry = requestInfo.getExpires();
				retryCount = requestInfo.getRetryCount();
				callback = requestInfo.getCallBack();
				// [xwc1125]
				cacheInfo = new HttpCache().newCacheInfo();
				cacheInfo.setCacheKey(requestInfo.getCacheKey());
				cacheInfo.setMethod(requestMethod);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Title: sendRequest
	 * </p>
	 * <p>
	 * Description: 执行请求
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param request
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午10:24:14
	 */
	@SuppressWarnings("unchecked")
	private ResponseInfo<T> sendRequest(HttpRequest<T> request) {
		ResponseInfo<T> responseInfo = null;
		try {
			RequestInfo<T> requestInfo = request.getRequestInfo();
			if (requestInfo != null) {
				if (sHttpCache.isEnabled(requestMethod)) {
					// 如果缓存可用，则直接返回缓存的结果
					String result = sHttpCache.get(requestUrl);
					if (result != null) {
						return new ResponseInfo<T>(0, (T) result, true);
					}
				}
				// [xwc1125]
				if (sHttpCache.isEnabled(cacheInfo)) {
					// 如果缓存可用，则直接返回缓存的结果
					String result = sHttpCache.get(cacheInfo.getCacheKey());
					if (result != null) {
						return new ResponseInfo<T>(0, (T) result, true);
					}
				}
			}

			if (autoResume && isDownloadingFile) {
				// 如果是下载文件的请求并支持断点下载，则设置RANGE
				File downloaFile = new File(fileSavePath);
				long fileLen = 0;
				if (downloaFile.isFile() && downloaFile.exists()) {
					fileLen = downloaFile.length();
				}
				if (fileLen > 0) {
					request.setRequestProperty("RANGE", "bytes=" + fileLen
							+ "-");
				}
			}

			// if (sHttpCache.isEnabled(requestMethod)) {
			// // 如果缓存可用，则直接返回缓存的结果
			// String result = sHttpCache.get(requestUrl);
			// if (result != null) {
			// return new ResponseInfo<T>(0, (T) result, true);
			// }
			// }
			if (!isCancelled()) {
				// 如果用户没有取消此次请求，则执行请求并处理请求结果
				responseInfo = handleResponse(request.execute(request
						.getClient()));
			}
		} catch (Throwable e) {// 异常，重试
			LogUtils.e(TAG, e.getMessage(), isDebug);
			responseInfo = retrySend(request);
		}
		if (responseInfo == null) { // 请求失败
			responseInfo = new ResponseInfo<T>(HttpConfig.STATUS_HTTP_ERROR,
					(T) "网络连接失败", false);
		}
		return responseInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: retrySend
	 * </p>
	 * <p>
	 * Description: 重试
	 * </p>
	 * <p>
	 * 请求出现异常后进行重试操作
	 * </p>
	 * 
	 * @param request
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午1:35:07
	 */
	private ResponseInfo<T> retrySend(HttpRequest<T> request) {
		ResponseInfo<T> responseInfo = null;
		if (retryCount > 0) {
			retryCount--;
			responseInfo = sendRequest(request);
		}
		return responseInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: doInBackground
	 * </p>
	 * <p>
	 * Description: 在线程中运行，执行请求
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param params
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午10:43:54
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected Void doInBackground(Object... params) {
		if (this.state == State.CANCELLED || params == null
				|| params.length == 0)
			return null;
		if (params.length == 4) { // 文件下载，获取相关参数
			fileSavePath = String.valueOf(params[1]);
			isDownloadingFile = fileSavePath != null;
			autoResume = (Boolean) params[2];
			autoRename = (Boolean) params[3];
		}
		if (params.length == 2) {
			isReturnByteArray = (Boolean) params[1];
		}
		try {
			request = (HttpRequest<T>) params[0];// 请求对象

			this.publishProgress(UPDATE_START);// 开始执行请求（告知外部）
			lastUpdateTime = SystemClock.uptimeMillis();
			ResponseInfo<T> responseInfo = sendRequest(request);// 执行请求
			if (responseInfo != null) {
				if (responseInfo.getCode() == 0) {
					this.publishProgress(UPDATE_SUCCESS, responseInfo);// 执行成功（告知外部）
				} else {
					this.publishProgress(UPDATE_FAILURE,
							responseInfo.getCode(), responseInfo.getResponse());// 执行失败（告知外部）
				}
				return null;
			}
		} catch (Exception e) {
			this.publishProgress(UPDATE_FAILURE, HttpConfig.STATUS_HTTP_ERROR,
					"网络访问出错");// 出现异常
								// 执行失败（告知外部）
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: onProgressUpdate
	 * </p>
	 * <p>
	 * Description: 请求状态、结果、进度实时回传到外部
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param values
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午10:50:10
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void onProgressUpdate(Object... values) {
		if (this.state == State.CANCELLED || values == null
				|| values.length == 0 || callback == null)
			return;
		switch ((Integer) values[0]) {
		case UPDATE_START:
			this.state = State.STARTED;
			callback.onStart();
			break;
		case UPDATE_LOADING:
			if (values.length != 3)
				return;
			this.state = State.LOADING;
			callback.onLoading(Long.valueOf(String.valueOf(values[1])),
					Long.valueOf(String.valueOf(values[2])), isUploading);
			break;
		case UPDATE_FAILURE:
			if (values.length != 3)
				return;
			this.state = State.FAILURE;
			callback.onFailure((Integer) values[1], (String) values[2]);
			break;
		case UPDATE_SUCCESS:
			if (values.length != 2)
				return;
			this.state = State.SUCCESS;
			callback.onSuccess((ResponseInfo<T>) values[1]);
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: updateProgress
	 * </p>
	 * <p>
	 * Description: 进度回传
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param total
	 * @param current
	 * @param forceUpdateUI
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 上午10:51:10
	 */
	@Override
	public boolean updateProgress(long total, long current,
			boolean forceUpdateUI) {
		if (callback != null && this.state != State.CANCELLED) {
			if (forceUpdateUI) {
				this.publishProgress(UPDATE_LOADING, total, current);
			} else {
				long currTime = SystemClock.uptimeMillis();
				if (currTime - lastUpdateTime >= callback.getRate()) {
					lastUpdateTime = currTime;
					this.publishProgress(UPDATE_LOADING, total, current);
				}
			}
		}
		return this.state != State.CANCELLED;
	}

	/**
	 * 
	 * <p>
	 * Title: handleResponse
	 * </p>
	 * <p>
	 * Description: 处理服务器响应信息
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param connection
	 * @return
	 * @throws IOException
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午1:36:51
	 */
	@SuppressWarnings("unchecked")
	private ResponseInfo<T> handleResponse(HttpURLConnection connection) {
		if (isCancelled())
			// 访问取消
			return new ResponseInfo<T>(HttpConfig.STATUS_HTTP_ERROR, (T) "网络访问已取消",
					false);
		try {
			long startTime = System.currentTimeMillis();
			int statusCode = connection.getResponseCode();
			LogUtils.d(TAG, "net请求host：" + connection.getURL().getHost()
					+ "\n net请求path：" + connection.getURL().getPath()
					+ "\n  net请求码：" + statusCode, isDebug);
			if (statusCode < 300) {
				if (isDebug) {
					LogUtils.i(TAG, "响应返回：code=" + statusCode + ";耗时="
							+ (System.currentTimeMillis() - startTime), isDebug);
				}
				// 访问成功
				Object result = null;
				if (connection != null) {
					isUploading = false;
					if (isDownloadingFile) {
						autoResume = autoResume
								&& InnerHttpUtil.isSupportRange(connection);
						String responseFileName = autoRename ? InnerHttpUtil
								.getFileNameFromHttpResponse(connection) : null;
						FileDownloadHandler downloadHandler = new FileDownloadHandler();
						result = downloadHandler.handleEntity(connection, this,
								fileSavePath, autoResume, responseFileName);
					}
					if (isReturnByteArray) {
						ByteArrayDownloadHandler downloadHandler = new ByteArrayDownloadHandler();
						result = downloadHandler.handleEntity(connection);

					} else {
						StringDownloadHandler downloadHandler = new StringDownloadHandler();
						result = downloadHandler.handleEntity(connection, this,
								HttpRequest.CHARSET);
						LogUtils.d(TAG, "net请求host："
								+ connection.getURL().getHost()
								+ "\n net请求path："
								+ connection.getURL().getPath()
								+ "\n  net请求结果：" + result, isDebug);
						if (sHttpCache.isEnabled(requestMethod)) {
							sHttpCache.put(requestUrl, (String) result, expiry);
						} else {
							if (sHttpCache.isEnabled(cacheInfo)) {
								// 如果缓存可用，则直接返回缓存的结果
								if (InnerHttpUtil.isNotEmpty((String) result)) {
									JSONObject jsonObject = new JSONObject(
											(String) result);
									int status = jsonObject.getInt("status");
									if (status == 100) {
										sHttpCache.put(cacheInfo.getCacheKey(),
												(String) result, expiry);
									}
								}
							}
						}
					}
				}
				return new ResponseInfo<T>(0, (T) result, false);
			} else if (statusCode == 301 || statusCode == 302) {
				// 重定向
				String location = connection.getHeaderField("Location");
				if (isDebug) {
					LogUtils.i(TAG, "响应返回：code=" + statusCode + ";" + "重定向地址："
							+ location + ";"
							+ (System.currentTimeMillis() - startTime), isDebug);
				}
				if (InnerHttpUtil.isNotEmpty(location) && request != null) {
					HttpURLConnection conn = request
							.getRedirectConnect(location);
					String cookie = connection.getHeaderField("Set-Cookie");
					if (InnerHttpUtil.isNotEmpty(cookie)) {
						conn.setRequestProperty("Cookie", cookie);
					}
					if (conn != null) {
						request.setClient(conn);
						return sendRequest(request);
					}
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			return new ResponseInfo<T>(HttpConfig.STATUS_HTTP_ERROR, (T) "网络连接已断开",
					false);
		}
		return new ResponseInfo<T>(HttpConfig.STATUS_HTTP_ERROR, (T) "网络连接失败", false);
	}

	/**
	 * 任务取消
	 */
	@Override
	public void cancel() {
		this.state = State.CANCELLED;
		if (request != null) {
			try {
				request.getClient().disconnect();
			} catch (Exception e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
		}
		if (!this.isCancelled()) {
			try {
				this.cancel(true);
			} catch (Throwable e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
		}
		if (callback != null) {
			callback.onCancelled();
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getState
	 * </p>
	 * <p>
	 * Description: 获取请求状态
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午1:20:48
	 */
	public State getState() {
		return state;
	}

	/**
	 * 
	 * <p>
	 * Title: State
	 * </p>
	 * <p>
	 * Description:状态
	 * </p>
	 * <p>
	 * 访问请求状态
	 * </p>
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午1:36:04
	 * 
	 */
	public enum State {

		WAITING(0), STARTED(1), LOADING(2), FAILURE(3), CANCELLED(4), SUCCESS(5);
		private int value = 0;

		State(int value) {
			this.value = value;
		}

		public static State valueOf(int value) {
			switch (value) {
			case 0:
				return WAITING;
			case 1:
				return STARTED;
			case 2:
				return LOADING;
			case 3:
				return FAILURE;
			case 4:
				return CANCELLED;
			case 5:
				return SUCCESS;
			default:
				return FAILURE;
			}
		}

		public int value() {
			return this.value;
		}
	}
}
