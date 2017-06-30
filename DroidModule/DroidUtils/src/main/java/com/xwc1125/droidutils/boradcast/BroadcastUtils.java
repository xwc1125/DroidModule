/**
 * <p>
 * Title: BroadcastUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016年8月18日
 * @author xwc1125
 * @date 2016年8月18日 下午2:21:47
 * @version V1.0
 */
package com.xwc1125.droidutils.boradcast;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

/**
 * <p>
 * Title: BroadcastUtils
 * </p>
 * <p>
 * Description: 广播类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月18日 下午2:21:47
 * 
 */
public class BroadcastUtils {
	/**
	 * 
	 * <p>
	 * Title: sendBroadcast
	 * </p>
	 * <p>
	 * Description: 发送广播
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param actionName
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 下午2:23:20
	 */
	public static void sendBroadcast(Context context, String actionName) {
		Intent intent = new Intent();
		intent.setAction(actionName);
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * Title: sendBroadcast
	 * </p>
	 * <p>
	 * Description: 发送广播
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param actionName
	 * @tags @param bundle 数据
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 下午2:32:09
	 */
	public static void sendBroadcast(Context context, String actionName, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(actionName);
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * Title: sendOrderedBroadcast
	 * </p>
	 * <p>
	 * Description: 发送有序广播
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param actionName
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 下午2:23:29
	 */
	public static void sendOrderedBroadcast(Context context, String actionName) {
		Intent intent = new Intent();
		intent.setAction(actionName);
		context.sendOrderedBroadcast(intent, null);
	}

	/**
	 * 
	 * <p>
	 * Title: sendOrderedBroadcast
	 * </p>
	 * <p>
	 * Description: 发送有序广播
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param actionName
	 * @tags @param bundle 数据保存
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 下午2:31:48
	 */
	public static void sendOrderedBroadcast(Context context, String actionName, Bundle bundle) {
		Intent intent = new Intent();
		intent.setAction(actionName);
		intent.putExtras(bundle);
		context.sendOrderedBroadcast(intent, null);
	}
}
