/**
 * <p>
 * Title: BaseException.java
 * </p>
 * <p>
 * Description: 基础异常类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.yuancy.framework.exception;

/**
 * 
 * <p>
 * Title: BaseException
 * </p>
 * <p>
 * Description: 错误信息基类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年4月19日下午2:44:30
 * 
 */
public class BaseException extends Exception {

	/**
	 * serialVersionUID
	 * 
	 * @author zhangqy
	 * @date 2016年4月19日下午1:58:03
	 */
	private static final long serialVersionUID = 5118374485720700016L;

	/**
	 * 异常信息对象
	 */
	private ExceptionInfo exceptionInfo;
	/**
	 * 异常信息描述
	 */
	private String errMsg;

	/**
	 * 
	 * <p>
	 * Title: BaseException
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * @param errMsg 异常信息描述
	 */
	public BaseException(String errMsg) {
		this.errMsg = errMsg;
	}

	/**
	 * 
	 * <p>
	 * Title: BaseException
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * @param exceptionInfo 异常信息对象
	 */
	public BaseException(ExceptionInfo exceptionInfo) {
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: BaseException
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * @param exceptionInfo
	 * @param s
	 */
	public BaseException(ExceptionInfo exceptionInfo, String s) {
		super(s);
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: BaseException
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * @param exceptionInfo
	 * @param t
	 */
	public BaseException(ExceptionInfo exceptionInfo, Throwable t) {
		super(t);
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * 
	 * <p>
	 * Title: BaseException
	 * </p>
	 * <p>
	 * Description: 构造方法
	 * </p>
	 * @param exceptionInfo
	 * @param s
	 * @param t
	 */
	public BaseException(ExceptionInfo exceptionInfo, String s, Throwable t) {
		super(s, t);
		this.exceptionInfo = exceptionInfo;
	}

	/**
	 * @return the exceptionInfo
	 */
	
	public ExceptionInfo getExceptionInfo() {
		return exceptionInfo;
	}

	/**
	 * @return the errMsg
	 */
	public String getErrMsg() {
		return errMsg;
	}

	/**
	 * 
	 * <p>
	 * Title: getExceptionInfo
	 * </p>
	 * <p>
	 * Description: 获取异常信息
	 * </p>
	 * <p>
	 * isJson：true 返回json格式的异常信息
	 * isJson：false 返回异常信息
	 * </p>
	 * @param isJson
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月16日 下午3:49:34
	 */
	public String getExceptionInfo(Boolean isJson) {
		if (isJson) {
			return exceptionInfo == null ? null : exceptionInfo.toJsonString();
		} else {
			return exceptionInfo == null ? null : exceptionInfo.toString();
		}
	}

}
