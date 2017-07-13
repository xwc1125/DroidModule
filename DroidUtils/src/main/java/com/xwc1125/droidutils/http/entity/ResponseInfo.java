/**
 * <p>
 * Title: ResponseInfo.java
 * </p>
 * <p>
 * Description: 网络访问请求响应信息
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午2:04:18
 * @version V1.0
 */
package com.xwc1125.droidutils.http.entity;

/**
 * 
 * <p>
 * Title: ResponseInfo
 * </p>
 * <p>
 * Description:http访问响应消息
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @param <T>
 * @date 2016年5月12日上午9:59:08
 * 
 */
public final class ResponseInfo<T> {
	/**
	 * 结果码
	 */
	private int code;
	/**
	 * 请求结果
	 */
	private T response;
	/**
	 * 结果是否来自缓存
	 */
	private final boolean resultFormCache;

	/**
	 * <p>
	 * Title:ResponseInfo
	 * </p>
	 * <p>
	 * Description:构造方法
	 * </p>
	 * 
	 * @param code
	 * @param response
	 * @param resultFormCache
	 */
	public ResponseInfo(int code, T response, boolean resultFormCache) {
		this.code = code;
		this.response = response;
		this.resultFormCache = resultFormCache;
	}

	/**
	 * 
	 * <p>
	 * Title:ResponseInfo
	 * </p>
	 * <p>
	 * Description:构造方法
	 * </p>
	 * 
	 * @param resultFormCache
	 */

	public ResponseInfo(boolean resultFormCache) {
		super();
		this.resultFormCache = resultFormCache;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the response
	 */
	public T getResponse() {
		return response;
	}

	/**
	 * @param response
	 *            the response to set
	 */
	public void setResponse(T response) {
		this.response = response;
	}

	/**
	 * @return the resultFormCache
	 */
	public boolean isResultFormCache() {
		return resultFormCache;
	}

	@Override
	public String toString() {
		return "{code:" + code + ", response:" + response
				+ ", resultFormCache:" + resultFormCache + "}";
	}
}
