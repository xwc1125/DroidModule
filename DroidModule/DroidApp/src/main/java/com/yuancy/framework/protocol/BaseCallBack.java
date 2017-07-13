/**
 * <p>
 * Title: BaseCallBack.java
 * </p>
 * <p>
 * Description: 基础回调对象
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午2:11:10
 * @version V1.0
 */
package com.yuancy.framework.protocol;

/**
 * 
 * <p>
 * Title: BaseCallBack
 * </p>
 * <p>
 * Description: 操作回调
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年3月23日下午4:37:22
 * 
 * @param <T>
 */
public interface BaseCallBack<T> {
	/**
	 * 
	 * <p>
	 * Title: onSuccess
	 * </p>
	 * <p>
	 * Description: 操作成功
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param status：状态码
	 * @param response：结果
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:12:49
	 */
	public void onSuccess(int status, T response);

	/**
	 * 
	 * <p>
	 * Title: onFailure
	 * </p>
	 * <p>
	 * Description:操作失败
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param status：状态吗
	 * @param msg：失败原因描述
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午2:12:57
	 */
	public void onFailure(int status, String msg);
}
