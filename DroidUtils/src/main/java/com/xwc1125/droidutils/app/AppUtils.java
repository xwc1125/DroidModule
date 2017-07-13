/**
 * <p>
 * Title: AppUtils.java
 * </p>
 * <p>
 * Description: 应用程序信息、管理工具类 
 * </p>
 * <p>
 * 1、用于获取应用程序相关信息；
 * 2、应用管理应用程序中的activity；
 * 3、判断应用程序中的服务是否启动
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月17日 下午4:38:36
 * @version V1.0
 */
package com.xwc1125.droidutils.app;

import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

/**
 * 
 * <p>
 * Title: AppUtils
 * </p>
 * <p>
 * Description: 应用程序信息、管理工具
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月17日 下午4:39:55
 * 
 */
public class AppUtils {
	private static final String TAG = AppUtils.class.getName();
	private static boolean isDebug = UtilsConfig.isDebug;
	/**
	 * activity栈
	 */
	private static Stack<Activity> activityStack = new Stack<Activity>();
	private static Application APP;
	static {
		try {
			Class<?> ActivityThreadClass = Class
					.forName("android.app.ActivityThread");
			Method currentAppicationMethod = ActivityThreadClass
					.getDeclaredMethod("currentApplication");
			currentAppicationMethod.setAccessible(true);
			APP = (Application) currentAppicationMethod.invoke(null);
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getVersionName
	 * </p>
	 * <p>
	 * Description: 获取应用程序的版本名称
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-1上午10:48:46
	 */
	public static String getVersionName(Context context) {
		String versionName = null;
		if (context == null) {
			return versionName;
		}
		PackageManager pm = context.getPackageManager();
		try {
			// 获取清单文件的对象，packageInfo能获取清单文件中的所有配置
			PackageInfo packageInfo = pm.getPackageInfo(
					context.getPackageName(), 0);
			if (packageInfo != null) {
				versionName = packageInfo.versionName;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return versionName;
	}

	/**
	 * 
	 * <p>
	 * Title: getMetaData
	 * </p>
	 * <p>
	 * Description: 获取应用程序清单中配置的MetaData
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @param context
	 * @param name
	 *            ：MetaData的name
	 * @return：MetaData的value
	 * 
	 * @author xwc1125
	 * @date 2015年7月14日下午4:01:58
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getMetaData(Context context, String name) {
		T metaValue = null;
		if (context == null || StringUtils.isEmpty(name)) {
			LogUtils.e("getMetaData", "context 或 name为空", isDebug);
			return metaValue;
		}
		try {
			final ApplicationInfo appInfo = context.getPackageManager()
					.getApplicationInfo(getPackageName(context),
							PackageManager.GET_META_DATA);
			if (appInfo != null) {
				Bundle bundle = appInfo.metaData;
				if (bundle != null) {
					metaValue = (T) bundle.get(name);
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return metaValue;
	}

	/**
	 * 
	 * <p>
	 * Title: getVersionCode
	 * </p>
	 * <p>
	 * Description: 获取应用程序版本号
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-1上午10:49:04
	 */
	public static int getVersionCode(Context context) {
		int versionCode = -1;
		if (context == null) {
			return versionCode;
		}
		try {
			PackageInfo packageInfo = context.getPackageManager()
					.getPackageInfo(getPackageName(context), 1);
			if (packageInfo != null) {
				versionCode = packageInfo.versionCode;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return versionCode;
	}

	/**
	 * 
	 * <p>
	 * Title: getPackageName
	 * </p>
	 * <p>
	 * Description: 取得应用程序的包名
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-1上午10:53:00
	 */
	public static String getPackageName(Context context) {
		if (context == null) {
			LogUtils.w(TAG, "context 为空", isDebug);
			return null;
		}
		try {
			return context.getPackageName();
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: getPackageName
	 * </p>
	 * <p>
	 * Description: 获取当前应用包名
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年12月9日 下午4:00:25
	 */
	public static String getPackageName() {
		try {
			Class<?> ActivityThreadClass = Class
					.forName("android.app.ActivityThread");
			Method currentPackageNameMethod = ActivityThreadClass
					.getDeclaredMethod("currentPackageName");
			currentPackageNameMethod.setAccessible(true);
			return (String) currentPackageNameMethod.invoke(null);
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: getAndroidSDKVersion
	 * </p>
	 * <p>
	 * Description: 获取当前系统的AndroidSDK版本
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015年7月10日 下午5:12:30
	 */
	public static int getAndroidSDKVersion(Context context) {
		int version = -1;
		try {
			version = Integer.valueOf(android.os.Build.VERSION.SDK_INT);
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return version;
	}

	/**
	 * 
	 * <p>
	 * Title: getAppLable
	 * </p>
	 * <p>
	 * Description: 获得应用程序名字
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-1上午10:48:33
	 */
	public static String getAppLable(Context context) {
		String appLable = null;
		if (context == null) {
			LogUtils.e(TAG,  "context 为空", isDebug);
			return appLable;
		}
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			if (packageInfo != null) {
				int labelRes = packageInfo.applicationInfo.labelRes;
				appLable = context.getResources().getString(labelRes);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return appLable;
	}

	/**
	 * 
	 * <p>
	 * Title: getAppIcon
	 * </p>
	 * <p>
	 * Description: 获取应用程序图标
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-1上午10:54:31
	 */
	public static Drawable getAppIcon(Context context) {
		Drawable drawable = null;
		if (context == null) {
			LogUtils.e(TAG, "context 为空", isDebug);
			return drawable;
		}
		try {
			PackageManager packageManager = context.getPackageManager();
			PackageInfo packageInfo = packageManager.getPackageInfo(
					context.getPackageName(), 0);
			if (packageInfo != null) {
				int icon = packageInfo.applicationInfo.icon;
				drawable = context.getResources().getDrawable(icon);
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return drawable;
	}

	/**
	 * 
	 * <p>
	 * Title: isServiceRunning
	 * </p>
	 * <p>
	 * Description: 服务是否在运行
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param className
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年4月14日下午4:28:17
	 */
	public static boolean isServiceRunning(Context context, String className) {
		if (context == null) {
			LogUtils.w(TAG, "isServiceRunning: context 为空", isDebug);
			return false;
		}
		boolean isRunning = false;
		try {
			ActivityManager activityManager = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			List<RunningServiceInfo> servicesList = activityManager
					.getRunningServices(Integer.MAX_VALUE);
			Iterator<RunningServiceInfo> l = servicesList.iterator();
			while (l.hasNext()) {
				RunningServiceInfo si = (RunningServiceInfo) l.next();
				if (className.equals(si.service.getClassName())) {
					isRunning = true;
				}
			}
			return isRunning;
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return isRunning;
	}

	/**
	 * 
	 * <p>
	 * Title: addActivity
	 * </p>
	 * <p>
	 * Description: 添加Activity到堆栈
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param activity
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:55:24
	 */
	public void addActivity(Activity activity) {
		activityStack.add(activity);
	}

	/**
	 * 
	 * <p>
	 * Title: currentActivity
	 * </p>
	 * <p>
	 * Description: 获取当前Activity（堆栈中最后一个压入的）
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:55:48
	 */
	public Activity currentActivity() {
		Activity activity = activityStack.lastElement();
		return activity;
	}

	/**
	 * 
	 * <p>
	 * Title: finishActivity
	 * </p>
	 * <p>
	 * Description: 结束当前Activity（堆栈中最后一个压入的）
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:56:23
	 */
	public void finishActivity() {
		Activity activity = activityStack.lastElement();
		finishActivity(activity);
	}

	/**
	 * 
	 * <p>
	 * Title: finishActivity
	 * </p>
	 * <p>
	 * Description: 结束指定的Activity
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param activity
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:56:45
	 */
	public void finishActivity(Activity activity) {
		if (activity != null) {
			activityStack.remove(activity);
			activity.finish();
			activity = null;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: finishActivity
	 * </p>
	 * <p>
	 * Description: 结束指定类名的Activity
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param cls
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:57:20
	 */
	public void finishActivity(Class<?> cls) {
		for (Activity activity : activityStack) {
			if (activity.getClass().equals(cls)) {
				finishActivity(activity);
			}
		}
	}

	/**
	 * 
	 * <p>
	 * Title: finishAllActivity
	 * </p>
	 * <p>
	 * Description:结束所有Activity
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:57:41
	 */
	public void finishAllActivity() {
		for (int i = 0; i < activityStack.size(); i++) {
			if (null != activityStack.get(i)) {
				activityStack.get(i).finish();
			}
		}
		activityStack.clear();
	}

	/**
	 * 
	 * <p>
	 * Title: exit
	 * </p>
	 * <p>
	 * Description: 退出应用程序
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午4:58:00
	 */
	public void exit(Context context) {
		try {
			finishAllActivity();
			ActivityManager activityMgr = (ActivityManager) context
					.getSystemService(Context.ACTIVITY_SERVICE);
			activityMgr.killBackgroundProcesses(context.getPackageName());
			System.exit(0);
		} catch (Exception e) {
		}
	}

	/**
	 * 
	 * <p>
	 * Title: getContext
	 * </p>
	 * <p>
	 * Description: 获取应用的上下文
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年12月9日 下午3:52:49
	 */
	public static Context getContext() {
		return APP == null ? null : APP.getApplicationContext();
	}

	/**
	 * <p>
	 * Title: getTargetSdkVersion
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @tags @param context
	 * @tags @return 
	 * 
	 * @author xwc1125
	 * @date 2017年2月14日 下午5:20:31
	 */
	private static int targetSdkVersion = -1;
	public static int getTargetSdkVersion(Context context) {		
		if(context == null) {
			return targetSdkVersion;
		}
		if(targetSdkVersion==-1){
			try {
				PackageInfo packageInfo = context.getPackageManager().getPackageInfo(getPackageName(context), 1);
				if (packageInfo != null) {
					targetSdkVersion = packageInfo.applicationInfo.targetSdkVersion;
				}
			} catch (Exception e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
		}		
		return targetSdkVersion;
	}
}
