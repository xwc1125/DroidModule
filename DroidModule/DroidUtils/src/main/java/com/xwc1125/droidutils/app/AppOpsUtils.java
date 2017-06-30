/**
 * <p>
 * Title: AppOpsUtils.java
 * </p>
 * <p>
 * Description: 应用程序权限管理
 * </p>
 * <p>
 * 1、针对4.2版本以上系统；
 * 2、4.4版本以上系统添加了默认短信应用功能，只有默认的短信应用才有权限操作短信箱；
 * 3、使用此类应用程序可获取到短信箱操作权限
 * </p>
 *
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午4:38:36
 * @version V1.0
 */
package com.xwc1125.droidutils.app;

import java.lang.reflect.Method;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.AppOpsManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;

/**
 *
 * <p>
 * Title: AppOpUtils
 * </p>
 * <p>
 * Description: 权限管理
 * </p>
 * <p>
 *
 * </p>
 *
 * @author xwc1125
 * @date 2016年4月19日上午11:10:35
 *
 */
public final class AppOpsUtils {
    private static final String TAG = AppOpsUtils.class.getName();
    private static boolean isDebug = UtilsConfig.isDebug;

    public static final int OP_NONE = -1;
    /*
     * android.Manifest.permission.ACCESS_COARSE_LOCATION
     */
    public static final int OP_COARSE_LOCATION = 0;
    /*
     * android.Manifest.permission.ACCESS_FINE_LOCATION
     */
    public static final int OP_FINE_LOCATION = 1;
    /*
     * null
     */
    public static final int OP_GPS = 2;
    /*
     * android.Manifest.permission.VIBRATE
     */
    public static final int OP_VIBRATE = 3;
    /*
     * android.Manifest.permission.READ_CONTACTS
     */
    public static final int OP_READ_CONTACTS = 4;
    /*
     * android.Manifest.permission.WRITE_CONTACTS
     */
    public static final int OP_WRITE_CONTACTS = 5;
    /*
     * android.Manifest.permission.READ_CALL_LOG
     */
    public static final int OP_READ_CALL_LOG = 6;
    /*
     * android.Manifest.permission.WRITE_CALL_LOG
     */
    public static final int OP_WRITE_CALL_LOG = 7;
    /*
     * android.Manifest.permission.READ_CALENDAR
     */
    public static final int OP_READ_CALENDAR = 8;
    /*
     * android.Manifest.permission.WRITE_CALENDAR
     */
    public static final int OP_WRITE_CALENDAR = 9;
    /*
     * android.Manifest.permission.ACCESS_WIFI_STATE
     */
    public static final int OP_WIFI_SCAN = 10;
    /*
     * null:no permission required for notifications
     */
    public static final int OP_POST_NOTIFICATION = 11;
    /*
     * null: neighboring cells shares the coarse location perm
     */
    public static final int OP_NEIGHBORING_CELLS = 12;
    /*
     * android.Manifest.permission.CALL_PHONE
     */
    public static final int OP_CALL_PHONE = 13;
    /*
     * android.Manifest.permission.READ_SMS
     */
    public static final int OP_READ_SMS = 14;
    /*
     * android.Manifest.permission.WRITE_SMS
     */
    public static final int OP_WRITE_SMS = 15;
    /*
     * android.Manifest.permission.RECEIVE_SMS
     */
    public static final int OP_RECEIVE_SMS = 16;
    /*
     * android.Manifest.permission.RECEIVE_EMERGENCY_BROADCAST
     */
    public static final int OP_RECEIVE_EMERGECY_SMS = 17;
    /*
     * android.Manifest.permission.RECEIVE_MMS
     */
    public static final int OP_RECEIVE_MMS = 18;
    /*
     * android.Manifest.permission.RECEIVE_WAP_PUSH
     */
    public static final int OP_RECEIVE_WAP_PUSH = 19;
    /*
     * android.Manifest.permission.SEND_SMS
     */
    public static final int OP_SEND_SMS = 20;
    /*
     * android.Manifest.permission.READ_SMS
     */
    public static final int OP_READ_ICC_SMS = 21;
    /*
     * android.Manifest.permission.WRITE_SMS
     */
    public static final int OP_WRITE_ICC_SMS = 22;
    /*
     * android.Manifest.permission.WRITE_SETTINGS
     */
    public static final int OP_WRITE_SETTINGS = 23;
    /*
     * android.Manifest.permission.SYSTEM_ALERT_WINDOW
     */
    public static final int OP_SYSTEM_ALERT_WINDOW = 24;
    /*
     * android.Manifest.permission.ACCESS_NOTIFICATIONS
     */
    public static final int OP_ACCESS_NOTIFICATIONS = 25;
    /*
     * android.Manifest.permission.CAMERA
     */
    public static final int OP_CAMERA = 26;
    /*
     * android.Manifest.permission.RECORD_AUDIO
     */
    public static final int OP_RECORD_AUDIO = 27;
    /*
     * no permission for playing audio
     */
    public static final int OP_PLAY_AUDIO = 28;
    /*
     * no permission for reading clipboard
     */
    public static final int OP_READ_CLIPBOARD = 29;
    /*
     * no permission for writing clipboard
     */
    public static final int OP_WRITE_CLIPBOARD = 30;
    /*
     * no permission for taking media buttons
     */
    public static final int OP_TAKE_MEDIA_BUTTONS = 31;
    /*
     * no permission for taking audio focus
     */
    public static final int OP_TAKE_AUDIO_FOCUS = 32;
    /*
     * no permission for changing master volume
     */
    public static final int OP_AUDIO_MASTER_VOLUME = 33;
    /*
     * no permission for changing voice volume
     */
    public static final int OP_AUDIO_VOICE_VOLUME = 34;
    /*
     * no permission for changing ring volume
     */
    public static final int OP_AUDIO_RING_VOLUME = 35;
    /*
     * no permission for changing media volume
     */
    public static final int OP_AUDIO_MEDIA_VOLUME = 36;
    /*
     * no permission for changing alarm volume
     */
    public static final int OP_AUDIO_ALARM_VOLUME = 37;
    /*
     * no permission for changing notification volume
     */
    public static final int OP_AUDIO_NOTIFICATION_VOLUME = 38;
    /*
     * no permission for changing bluetooth volume
     */
    public static final int OP_AUDIO_BLUETOOTH_VOLUME = 39;
    /*
     * android.Manifest.permission.WAKE_LOCK
     */
    public static final int OP_WAKE_LOCK = 40;
    /*
     * no permission for generic location monitoring
     */
    public static final int OP_MONITOR_LOCATION = 41;
    /*
     * no permission for high power location monitoring
     */
    public static final int OP_MONITOR_HIGH_POWER_LOCATION = 42;
    /*
     * android.Manifest.permission.PACKAGE_USAGE_STATS
     */
    public static final int OP_GET_USAGE_STATS = 43;
    /*
     * no permission for muting/unmuting microphone
     */
    public static final int OP_MUTE_MICROPHONE = 44;
    /*
     * no permission for displaying toasts
     */
    public static final int OP_TOAST_WINDOW = 45;
    /*
     * no permission for projecting media
     */
    public static final int OP_PROJECT_MEDIA = 46;
    /*
     * no permission for activating vpn
     */
    public static final int OP_ACTIVATE_VPN = 47;

    /**
     *
     * <p>
     * Title: isEnabled
     * </p>
     * <p>
     * Description: 判断应用程序是否有指定权限
     * </p>
     * <p>
     *
     * </p>
     *
     * @param context
     * @param code
     *            权限代码
     * @param isSet
     *            如果无指定权限，是否去获取权限
     * @return
     *
     * @author xwc1125
     * @date 2016年4月19日上午11:35:47
     */
    @SuppressLint("InlinedApi")
    public static boolean isEnabled(Context context, int code, boolean isSet) {
        boolean enabled = false;
        if (context == null) {
            return enabled;
        }
        // 4.3（不包括4.3）以下系统无AppOpsManager类，4.4以下系统有权限操作数据库。JELLY_BEAN_MR2 = 18
        if (Build.VERSION.SDK_INT < 19) {
            return true;
        }
        try {
            int uid = getUid(context);
            Object opRes = checkOp(context, code, uid);
            if (opRes instanceof Integer) {
                enabled = (Integer) opRes == AppOpsManager.MODE_ALLOWED;
            }
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        if (isSet && !enabled) {
            enabled = setEnabled(context, code, true);
        }
        return enabled;
    }

    /**
     *
     * <p>
     * Title: setEnabled
     * </p>
     * <p>
     * Description: 应用程序指定权限的管理
     * </p>
     * <p>
     *
     * </p>
     *
     * @param context
     * @param enabled
     * @return
     *
     * @author xwc1125
     * @date 2016年4月19日上午11:39:18
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static boolean setEnabled(Context context, int code, boolean enabled) {
        boolean result = false;
        if (context == null) {
            return result;
        }
        try {
            int uid = getUid(context);
            int mode = enabled ? AppOpsManager.MODE_ALLOWED
                    : AppOpsManager.MODE_IGNORED;
            result = setMode(context, code, uid, mode);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return result;
    }

    /**
     *
     * <p>
     * Title: checkOp
     * </p>
     * <p>
     * Description: 判断应用程序是否拥有指定的权限
     * </p>
     * <p>
     * 系统checkOp方法描述<br>
     * Do a quick check for whether an application might be able to perform an
     * operation. This is not a security check; you must use noteOp(int, int,
     * String) or startOp(int, int, String) for your actual security checks,
     * which also ensure that the given uid and package name are consistent.
     * This function can just be used for a quick check to see if an operation
     * has been disabled for the application, as an early reject of some work.
     * This does not modify the time stamp or other data about the operation.
     *
     * Parameters: <br>
     * op The operation to check. One of the OP_* constants.<br>
     * uid The user id of the application attempting to perform the operation.<br>
     * packageName The name of the application attempting to perform the
     * operation.<br>
     *
     * Returns:<br>
     * Returns MODE_ALLOWED if the operation is allowed, or MODE_IGNORED if it
     * is not allowed and should be silently ignored (without causing the app to
     * crash).
     * </p>
     *
     * @param context
     * @param op
     * @param uid
     * @return
     *
     * @author xwc1125
     * @date 2016年4月19日上午11:23:21
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static Object checkOp(Context context, int op, int uid) {
        Object result = null;
        if (context == null) {
            return result;
        }
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context
                    .getSystemService(Context.APP_OPS_SERVICE);
            Class<?> appOpsManagerClass = appOpsManager.getClass();
            Class<?>[] types = new Class[3];
            types[0] = Integer.TYPE;
            types[1] = Integer.TYPE;
            types[2] = String.class;
            Method checkOp = appOpsManagerClass.getMethod("checkOp", types);
            Object[] args = new Object[3];
            args[0] = op;
            args[1] = uid;
            args[2] = context.getPackageName();
            result = checkOp.invoke(appOpsManager, args);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return result;
    }

    /**
     *
     * <p>
     * Title: setMode
     * </p>
     * <p>
     * Description: 为应用程序申请指定权限
     * </p>
     * <p>
     *
     * </p>
     *
     * @param context
     * @param code
     *            要申请的权限代码
     * @param uid
     *            userId
     * @param mode
     *            AppOpsManager.MODE_ALLOWED <br>
     *            AppOpsManager.MODE_IGNORED
     * @return
     *
     * @author xwc1125
     * @date 2016年4月19日上午11:18:43
     */
    @TargetApi(Build.VERSION_CODES.KITKAT)
    private static boolean setMode(Context context, int code, int uid, int mode) {
        try {
            AppOpsManager appOpsManager = (AppOpsManager) context
                    .getSystemService(Context.APP_OPS_SERVICE);
            Class<?> appOpsManagerClass = appOpsManager.getClass();
            Class<?>[] types = new Class[4];
            types[0] = Integer.TYPE;
            types[1] = Integer.TYPE;
            types[2] = String.class;
            types[3] = Integer.TYPE;
            Method setMode = appOpsManagerClass.getMethod("setMode", types);
            Object[] args = new Object[4];
            args[0] = Integer.valueOf(code);
            args[1] = Integer.valueOf(uid);
            args[2] = context.getPackageName();
            args[3] = Integer.valueOf(mode);
            setMode.invoke(appOpsManager, args);
            return true;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return false;
    }

    /**
     *
     * <p>
     * Title: getUid
     * </p>
     * <p>
     * Description: 获取userID
     * </p>
     * <p>
     * The kernel user-ID that has been assigned to this application; currently
     * this is not a unique ID (multiple applications can have the same uid).
     * </p>
     *
     * @param context
     * @return
     *
     * @author xwc1125
     * @date 2016年4月19日上午11:13:31
     */
    private static int getUid(Context context) {
        int uid = -1;
        if (context == null) {
            return uid;
        }
        try {
            uid = context.getPackageManager().getApplicationInfo(
                    context.getPackageName(), PackageManager.GET_ACTIVITIES).uid;
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return uid;
    }
}
