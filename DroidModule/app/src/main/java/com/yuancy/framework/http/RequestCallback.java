/**
 * <p>
 * Title: RequestCallback.java
 * </p>
 * <p>
 * Description: 请求状态信息回调类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午2:11:10
 * @version V1.0
 */
package com.yuancy.framework.http;

import com.yuancy.framework.http.entity.ResponseInfo;

/**
 * 
 * <p>
 * Title: RequestCallback
 * </p>
 * <p>
 * Description: 请求状态回调对象
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年3月25日上午10:56:17
 * 
 * @param <T>
 */
public abstract class RequestCallback<T> {
	/**
	 * 请求进度回传默认频率
	 */
	private static final int DEFAULT_RATE = 1000;
	/**
	 * 请求进度回传最小频率
	 */
	private static final int MIN_RATE = 200;
	/**
	 * 求进度回传最小频率
	 */
	private int rate;

	/**
	 * 
	 * <p>
	 * Title: RequestCallback
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 */
	public RequestCallback() {
		this.rate = DEFAULT_RATE;
	}

	/**
	 * 
	 * <p>
	 * Title: RequestCallback
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * 
	 * @param rate
	 *            请求进度回传频率
	 */
	public RequestCallback(int rate) {
		this.rate = rate;
	}

	/**
	 * 
	 * <p>
	 * Title: getRate
	 * </p>
	 * <p>
	 * Description: 返回请求进度回传频率
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:19:08
	 */
	public final int getRate() {
		if (rate < MIN_RATE) {
			return MIN_RATE;
		}
		return rate;
	}

	/**
	 * 
	 * <p>
	 * Title: setRate
	 * </p>
	 * <p>
	 * Description: 设置请求进度回传频率
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param rate
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:19:47
	 */
	public final void setRate(int rate) {
		this.rate = rate;
	}

	/**
	 * 
	 * <p>
	 * Title: onStart
	 * </p>
	 * <p>
	 * Description: 开始请求
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:21:20
	 */
	public void onStart() {
	}

	/**
	 * 
	 * <p>
	 * Title: onCancelled
	 * </p>
	 * <p>
	 * Description: 请求取消
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:21:36
	 */
	public void onCancelled() {
	}

	/**
	 * 
	 * <p>
	 * Title: onLoading
	 * </p>
	 * <p>
	 * Description: 请求中
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param total
	 * @param current
	 * @param isUploading
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:21:54
	 */
	public void onLoading(long total, long current, boolean isUploading) {
	}

	/**
	 * 
	 * <p>
	 * Title: onSuccess
	 * </p>
	 * <p>
	 * Description:请求成功
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param responseInfo
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:22:11
	 */
	public abstract void onSuccess(ResponseInfo<T> responseInfo);

	/**
	 * 
	 * <p>
	 * Title: onFailure
	 * </p>
	 * <p>
	 * Description: 请求失败
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param status
	 * @param msg
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:22:28
	 */
	public abstract void onFailure(int status, Object msg);
}
