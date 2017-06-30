package com.xwc1125.droidutils.uuid;

import java.util.UUID;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

import android.content.Context;
import android.telephony.TelephonyManager;

/**
 * <p>
 * Title: UuidUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016-3-28下午1:01:43
 * 
 */
public class UuidUtils {
	private static final String TAG = UuidUtils.class.getName();
	private static final Boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: getUuid
	 * </p>
	 * <p>
	 * Description: 获取UUID
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param flag 如果true，则使用随时变化的Uuid，否则却唯一的。
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016-3-28下午1:13:26
	 */
	public static String getUuid(Context context, boolean flag) {
		String uuidString = "";
		try {
			final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
			String tmDevice = "", tmSerial = "", androidId = "";
			try {
				tmDevice = "" + tm.getDeviceId();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				tmSerial = "" + tm.getSimSerialNumber();
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(),
						android.provider.Settings.Secure.ANDROID_ID);
			} catch (Exception e) {
				// TODO: handle exception
			}
			String rankString = "";
			if (flag) {
				rankString = System.currentTimeMillis() + "";
			}
			String uuString = tmDevice + tmSerial + androidId + rankString;
			uuidString = UUID.nameUUIDFromBytes(uuString.getBytes()).toString().replaceAll("-", "");
		} catch (Throwable e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);

		}
		return uuidString;
	}

}
