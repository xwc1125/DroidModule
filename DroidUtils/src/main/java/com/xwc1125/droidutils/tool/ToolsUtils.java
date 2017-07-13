package com.xwc1125.droidutils.tool;

/**
 * 
 * <p>
 * Title: ToolsUtils
 * </p>
 * <p>
 * Description: 工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月18日 上午10:04:59
 *
 */
public class ToolsUtils {

	/**
	 * 
	 * <p>
	 * Title: getMethodName
	 * </p>
	 * <p>
	 * Description: 获取当前的函数名称
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-14下午4:12:52
	 */
	public static String getMethodName(int index) {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e;
		try {
			if (index>0) {
				e = stacktrace[index];
			}else{
				e = stacktrace[2];
			}
		} catch (Exception e2) {
			e = stacktrace[2];
		}
		String methodName = e.getMethodName();
		return methodName;
	}

	/**
	 * 
	 * <p>
	 * Title: getMethodPath
	 * </p>
	 * <p>
	 * Description: 获取当前的方法路径
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午10:04:25
	 */
	public static StackTraceElement getMethodPath(int index) {
		StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
		StackTraceElement e;
		try {
			if (index>0) {
				e = stacktrace[index];
			}else{
				e = stacktrace[2];
			}
		} catch (Exception e2) {
			e = stacktrace[2];
		}
		return e;
	}
}
