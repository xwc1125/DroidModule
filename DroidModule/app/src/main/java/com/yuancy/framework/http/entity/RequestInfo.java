/**
 * <p>
 * Title: RequestInfo.java
 * </p>
 * <p>
 * Description: 网络访问请求信息
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月17日 上午11:22:21
 * @version V1.0
 */
package com.yuancy.framework.http.entity;

import com.yuancy.framework.http.HttpCache;
import com.yuancy.framework.http.HttpConfig;
import com.yuancy.framework.http.HttpRequest;
import com.yuancy.framework.http.RequestCallback;
import com.yuancy.framework.http.utils.InnerHttpUtil;
import com.yuancy.framework.log.LogUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;
import java.util.UUID;

/**
 * 
 * <p>
 * Title: RequestInfo
 * </p>
 * <p>
 * Description: http访问请求信息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @param <T>
 * @date 2016年5月12日上午9:55:47
 * 
 */
public class RequestInfo<T> implements Serializable {
	private static final String TAG = RequestInfo.class.getName();
	private static final Boolean isDebug = HttpConfig.IS_SHOW_LOG;
	/**
	 * serialVersionUID
	 * 
	 * @author zhangqy
	 * @date 2016年6月2日上午10:09:30
	 */
	private static final long serialVersionUID = -8869881146515387822L;
	/**
	 * Http协议分割符
	 */
	public static String boundary = null;
	/**
	 * 默认的请求方式
	 */
	private String requestMethod = HttpRequest.HttpMethod.POST.toString();
	/**
	 * 请求地址
	 */
	private String url;
	/**
	 * 缓存过期时间:秒 默认为60秒
	 */
	private Long expires = HttpCache.getDefaultExpiryTime();
	/**
	 * 请求参数
	 */
	private Map<String, Object> params;
	/**
	 * 待上传的文件参数(FileInputStream格式) 日志附件上传目前使用的是此格式
	 */
	private TreeMap<String, FileInputStream> fileParams;
	/**
	 * 待上传的文件（File格式） 数字短信附件上传目前使用的是此格式
	 */
	private ArrayList<File> fileList;
	/**
	 * 请求体头部数据
	 */
	private HashMap<String, Object> headerMap;
	/**
	 * params＋fileParams的流
	 */
	private ByteArrayOutputStream baosParams;
	/**
	 * base64加密过滤器
	 */
	private List<String> filter;
	/**
	 * 重试次数, 默认为0,即不重试
	 */
	private int retryCount = 0;
	/**
	 * 请求结果回调对象
	 */
	private RequestCallback<T> callBack;
	/**
	 * [xwc1125]缓存的Key
	 */
	private String cacheKey;

	/**
	 * <p>
	 * Title: RequestInfo
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 */
	public RequestInfo() {
		super();
		baosParams = new ByteArrayOutputStream();
		boundary = UUID.randomUUID().toString();
	}

	/**
	 * @return the requestMethod
	 */
	public String getRequestMethod() {
		return requestMethod;
	}

	/**
	 * @param requestMethod
	 *            the requestMethod to set
	 */
	public void setRequestMethod(String requestMethod) {
		if (InnerHttpUtil.isNotEmpty(requestMethod)) {
			this.requestMethod = requestMethod;
		}
	}

	/**
	 * @param fileParams
	 *            the fileParams to set
	 */
	public void setFileParams(TreeMap<String, FileInputStream> fileParams) {
		this.fileParams = fileParams;
	}

	/**
	 * 
	 * <p>
	 * Title: hasFileParams
	 * </p>
	 * <p>
	 * Description: 是否有文件参数
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 下午1:46:44
	 */
	public Boolean hasFileParams() {
		if (fileParams == null || fileParams.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @param fileList
	 *            the fileList to set
	 */
	public void setFileList(ArrayList<File> fileList) {
		this.fileList = fileList;
	}

	/**
	 * 
	 * <p>
	 * Title: hasFilesList
	 * </p>
	 * <p>
	 * Description: 判断是否有文件参数
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月17日 下午1:47:45
	 */
	public boolean hasFilesList() {
		if (fileList == null || fileList.size() == 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		if (url != null && !url.startsWith("http")) {
			// [xwc1125]如果Url不含有https或http时，new URL(url)就会报错
			url = "http://" + url;
		}
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the expires
	 */
	public Long getExpires() {
		return expires;
	}

	/**
	 * @param expires
	 *            the expires to set
	 */
	public void setExpires(Long expires) {
		this.expires = expires;
	}

	/**
	 * @param params
	 *            the params to set
	 */
	public void setParams(Map<String, Object> params) {
		this.params = params;
	}

	/**
	 * @return the filter
	 */
	public List<String> getFilter() {
		return filter;
	}

	/**
	 * @param filter
	 *            the filter to set
	 */
	public void setFilter(List<String> filter) {
		this.filter = filter;
	}

	/**
	 * @return the retryCount
	 */
	public int getRetryCount() {
		return retryCount;
	}

	/**
	 * @param retryCount
	 *            the retryCount to set
	 */
	public void setRetryCount(int retryCount) {
		this.retryCount = retryCount;
	}

	/**
	 * @return the callBack
	 */
	public RequestCallback<T> getCallBack() {
		return callBack;
	}

	/**
	 * @param callBack
	 *            the callBack to set
	 */
	public void setCallBack(RequestCallback<T> callBack) {
		this.callBack = callBack;
	}

	/**
	 * @return the headerMap
	 */
	public HashMap<String, Object> getHeaderMap() {
		return headerMap;
	}

	/**
	 * @param headerMap
	 *            the headerMap to set
	 */
	public void setHeaderMap(HashMap<String, Object> headerMap) {
		this.headerMap = headerMap;
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
	 * 
	 * <p>
	 * Title: getParamsOutputStream
	 * </p>
	 * <p>
	 * Description: 获取参数流
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年6月2日上午10:56:05
	 */
	public ByteArrayOutputStream getParamsOutputStream() {
		try {
			if (baosParams == null) {
				baosParams = new ByteArrayOutputStream();
			}
			String urlParames = null;
			if (params != null) {
				urlParames = getURLParames();
			}
			if (fileParams != null && fileParams.size() > 0) { // 有文件
				baosParams.write(("--" + boundary + "\r\n").getBytes("utf-8"));
				baosParams.write(("Content-Disposition: form-data; name=\"params\"").getBytes("utf-8"));
				baosParams.write("\r\n\r\n".getBytes("utf-8"));
				baosParams.write(urlParames.getBytes("utf-8"));
				baosParams.write(("\r\n").getBytes("utf-8"));

				for (Entry<String, FileInputStream> entry : fileParams.entrySet()) {
					String fileName = entry.getKey();
					FileInputStream fis = entry.getValue();
					if (fis != null) {
						addPart(fileName, fis);
					}
				}
			} else if (fileList != null && fileList.size() > 0) {
				// 拼接文本类型的参数
				addParams();
				// 进行附件的添加
				andFiles();

			} else {
				baosParams.write(urlParames.getBytes("utf-8"));
			}

		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return baosParams;
	}

	/**
	 * <p>
	 * Title: addParams
	 * </p>
	 * <p>
	 * Description: 参数拼接
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags
	 * 
	 * @author xwc1125
	 * @date Jul 30, 201610:26:04 PM
	 */
	private void addParams() {
		try {
			final String PREFIX = "--";
			final String LINE_END = "\r\n";
			final String CHARSET = "UTF-8";
			StringBuilder textSb = new StringBuilder();
			if (params != null && params.size() > 0) {
				for (Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					textSb.append(PREFIX).append(boundary).append(LINE_END);
					textSb.append("Content-Disposition: form-data; name=\"" + key + "\"" + LINE_END);
					textSb.append("Content-Type: text/plain; charset=" + CHARSET + LINE_END);
					textSb.append("Content-Transfer-Encoding: 8bit" + LINE_END);
					textSb.append(LINE_END);
					if (value != null && InnerHttpUtil.isNotEmpty(key)) {
						String encodeValue = URLEncoder.encode(value.toString(), HttpRequest.CHARSET);
						textSb.append(encodeValue);
					}
					textSb.append(LINE_END);
				}
			}
			baosParams.write(textSb.toString().getBytes("utf-8"));
		} catch (UnsupportedEncodingException e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		} catch (IOException e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

	/**
	 * <p>
	 * Title: andFiles
	 * </p>
	 * <p>
	 * Description: 文件拼接
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags
	 * 
	 * @author xwc1125
	 * @date Jul 30, 20162:06:05 PM
	 */
	private void andFiles() {
		try {
			final String PREFIX = "--";
			final String LINE_END = "\r\n";
			final String CHARSET = "UTF-8";
			// 发送文件数据
			if (fileList != null) {
				for (File file : fileList) {
					if (file != null) {
						StringBuilder fileSb = new StringBuilder();
						fileSb.append(PREFIX).append(boundary).append(LINE_END);
						fileSb.append("Content-Disposition: form-data; name=\"" + file.getName() + "\"; filename=\""
								+ file.getAbsolutePath().substring(file.getName().lastIndexOf("/") + 1) + "\""
								+ LINE_END);
						fileSb.append("Content-Type: application/octet-stream; charset=" + CHARSET + LINE_END);
						fileSb.append(LINE_END);
						baosParams.write(fileSb.toString().getBytes());

						InputStream is = new FileInputStream(file);
						byte[] buffer = new byte[1024 * 8];
						int len;
						while ((len = is.read(buffer)) != -1) {
							baosParams.write(buffer, 0, len);
						}

						is.close();
						baosParams.write(LINE_END.getBytes());
					}
				}

			}
			// 请求结束标志
			baosParams.write((PREFIX + boundary + PREFIX + LINE_END).getBytes());
			baosParams.flush();
		} catch (final Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: addPart
	 * </p>
	 * <p>
	 * Description:文件拼接
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param fileName
	 * @param fin
	 * 
	 * @author zhangqy
	 * @date 2016年6月2日上午10:52:21
	 */
	public void addPart(final String fileName, final InputStream fin) {
		if (fin == null) {
			return;
		}
		try {
			baosParams.write(("--" + boundary + "\r\n").getBytes("utf-8"));
			String type = "Content-Type: application/octet-stream\r\n";
			baosParams.write(
					("Content-Disposition: form-data; name=\"" + fileName + "\"; filename=\"" + fileName + "\"\r\n")
							.getBytes("utf-8"));
			baosParams.write(type.getBytes("utf-8"));
			baosParams.write("Content-Transfer-Encoding: binary\r\n\r\n".getBytes("utf-8"));
			final byte[] tmp = new byte[4096];
			int l = 0;
			while ((l = fin.read(tmp)) != -1) {
				baosParams.write(tmp, 0, l);
			}
			baosParams.write(("\r\n--" + boundary + "--\r\n").getBytes("utf-8"));
			baosParams.flush();
		} catch (final Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		} finally {
			try {
				fin.close();
			} catch (final Exception e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getURLParames
	 * </p>
	 * <p>
	 * Description: 格式化网络请求参数
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * @throws Exception
	 * 
	 * @author zhangqy
	 * @date 2016年6月2日上午10:34:50
	 */
	public String getURLParames() throws Exception {
		StringBuilder stringBuilder = null;
		try {
			if (params != null) {
				stringBuilder = new StringBuilder();
				for (Entry<String, Object> entry : params.entrySet()) {
					String key = entry.getKey();
					Object value = entry.getValue();
					if (value != null && InnerHttpUtil.isNotEmpty(key)) {
						String encodeValue = URLEncoder.encode(value.toString(), HttpRequest.CHARSET);
						stringBuilder.append(key).append("=").append(encodeValue).append("&");
						// stringBuilder.append(key).append("=").append(value).append("&");
					}
				}
				stringBuilder.deleteCharAt(stringBuilder.length() - 1);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			throw new Exception("http请求参数出错");
		}
		return stringBuilder == null ? null : stringBuilder.toString();
	}

	/**
	 * <p>
	 * Title: setCacheEnable
	 * </p>
	 * <p>
	 * Description: 设置是否是否进行缓存
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param @param
	 *            cacheKey 缓存的key值
	 * @tags @param method 方法
	 * @tags @param isCache 是否缓存
	 * 
	 * @author xwc1125
	 * @date 2017年2月15日 上午11:45:50
	 */
	public void setCacheEnable(String cacheKey, HttpRequest.HttpMethod method, boolean enabled) {
		HttpCache httpCache = new HttpCache();
		httpCache.setCacheInfo(cacheKey, method, enabled);
	}
	
	public void setCacheEnable(HttpRequest.HttpMethod method, boolean enabled) {
		HttpCache httpCache = new HttpCache();
		httpCache.setEnabled(method, enabled);
	}
}