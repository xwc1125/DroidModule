/**
 * <p>
 * Title: PhoneUtils.java
 * </p>
 * <p>
 * Description: 拨号、呼转
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午9:29:45
 * @version V1.0
 */
package com.xwc1125.droidutils.device;

import java.lang.reflect.Method;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.telephony.TelephonyManager;
import android.widget.Toast;

import com.xwc1125.droidutils.StringUtils;

/**
 * 
 * <p>
 * Title: PhoneUtils
 * </p>
 * <p>
 * Description: 拨号工具
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年4月11日上午9:27:15
 * 
 */
public class PhoneUtils {
	/**
	 * 
	 * <p>
	 * Title: phoneCall
	 * </p>
	 * <p>
	 * Description: 调用系统 ，给指定号码打电话
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param phoneNumber
	 * @param isDirectCall
	 * 
	 * @author zhangqy
	 * @date 2016年4月11日上午9:27:49
	 */
	public static void phoneCall(Context context, String phoneNumber,
			Boolean isDirectCall) {
		if (StringUtils.isEmpty(phoneNumber)) {
			Toast.makeText(context, "号码不能为空！！！", Toast.LENGTH_SHORT).show();
		} else {
			Intent phoneIntent = new Intent();
			if (isDirectCall) {
				phoneIntent.setAction(Intent.ACTION_CALL);
			} else {
				phoneIntent.setAction(Intent.ACTION_DIAL);
			}
			phoneIntent.setData(Uri.parse("tel:" + phoneNumber));
			context.startActivity(phoneIntent);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: quiesceCall
	 * </p>
	 * <p>
	 * Description: 静默拨打电话指定电话
	 * </p>
	 * <p>
	 * call 的实现方法中会调用Uri.parse("tel:" + phoneNumber)
	 * </p>
	 * 
	 * @param context
	 * 
	 * @author zhangqy
	 * @date 2016年2月15日上午9:19:42
	 */
	public static void quiesceCall(Context context, String callNumber) {
		TelephonyManager telephonyManager = (TelephonyManager) context
				.getSystemService(Service.TELEPHONY_SERVICE);
		try {
			Class<?> managerClass = Class.forName(telephonyManager.getClass()
					.getName());
			Method getITelephony = managerClass
					.getDeclaredMethod("getITelephony");
			getITelephony.setAccessible(true);
			Object iTelephony = getITelephony.invoke(telephonyManager);
			Class<?> telephonyClass = Class
					.forName("com.android.internal.telephony.ITelephony");
			Method callMethod = telephonyClass.getDeclaredMethod("call",
					String.class, String.class);
			callMethod.setAccessible(true);
			callMethod.invoke(iTelephony, context.getPackageName(), callNumber);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
