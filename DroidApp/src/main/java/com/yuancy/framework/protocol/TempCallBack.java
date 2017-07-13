/**
 * <p>
 * Title: RequestCallback.java
 * </p>
 * <p>
 * Description: 内部回调
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
 * Title: TempCallBack
 * </p>
 * <p>
 * Description: 内部回调
 * </p>
 * <p>
 * 使用场景： 当用户选择使用手机号认证＋验证码认证方式进行认证登录时，如果出现密钥出错等任何认证方式都不可能认证通过的错误，则不进行后续的认证操作。<br>
 * 此时，在内部使用TempCallBack进行数据处理，如果onFailure中code＝＝2则不进行后面的操作，直接计为认证失败
 * </p>
 * 
 * @author xwc1125
 * @param <T>
 * @date 2016年5月19日上午8:58:50
 * 
 */
public abstract class TempCallBack<T> implements BaseCallBack<T> {

	@Override
	public void onFailure(int status, String msg) {
	}

	public abstract void onFailure(int code, int status, String msg);

}
