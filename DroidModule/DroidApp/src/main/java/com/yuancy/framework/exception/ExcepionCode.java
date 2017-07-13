/**
 * <p>
 * Title: ExcepionCode.java
 * </p>
 * <p>
 * Description: 异常码
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
 * Title: ExcepionCode
 * </p>
 * <p>
 * Description: 错误信息代码
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年4月19日下午2:23:15
 * 
 */
public interface ExcepionCode {
	/**
	 * 通用:100000
	 */
	public static final int ERROR_NOMAL = 100000;
	/**
	 * 网络出错:100001
	 */
	public static final int ERROR_NET = 100001;
	/**
	 * 参数出错：100002
	 */
	public static final int ERROR_PARAM = 100002;

	/**
	 * 数据格式出错:100003
	 */
	public static final int ERROR_DATA_FORMAT = 100003;
	/**
	 * 数据为空:100004
	 */
	public static final int ERROR_DATA_NULL = 100004;
	/**
	 * 数据不匹配：100005
	 */
	public static final int ERROR_DATA = 100005;
	/**
	 * 包名不匹配:100006
	 */
	public static final int ERROR_PAKEAGE = 100006;
	/**
	 * 无权限 apiKey为空:100007
	 */
	public static final int ERROR_NO_APIKEY = 100007;
	/**
	 * 接受超时
	 */
	public static final int ERROR_TIMEOUT = 100008;

	/**
	 * 用户取消操作
	 */
	public static final int ERROR_CANCLE = 100009;

	/********************** 短信插入、删除状态码 *****************/
	/**
	 * 参数出错
	 */
	public static final int ERROR_SMS_PARAM = 200000;
	/**
	 * 无数据库操作权限
	 */
	public static final int ERROR_SMS_PERMISSION = 200001;

	/**
	 * 短信存储出错或删除出错
	 */
	public static final int ERROR_SMS_STORAGE = 200002;

	/**
	 * 没有与条件匹配的内容
	 */
	public static final int ERROR_SMS_DATA = 200003;

	/**
	 * 短信入库返回的uri为空
	 */
	public static final int ERROR_SMS_URI = 200004;

	/**
	 * 彩信文件路径为空
	 */
	public static final int ERROR_SMS_PATH = 200005;

	/********************** 短信插入、删除状态码 *****************/

}
