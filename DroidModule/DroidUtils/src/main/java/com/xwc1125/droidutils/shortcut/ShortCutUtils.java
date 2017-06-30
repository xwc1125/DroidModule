package com.xwc1125.droidutils.shortcut;

import java.util.List;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.image.ImageUtils;
import com.xwc1125.droidutils.LogUtils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;

/**
 * 
 * <p>
 * Title: ShortCutUtils
 * </p>
 * <p>
 * Description: 桌面快捷方式的工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年9月5日 下午2:14:10
 *
 */
public class ShortCutUtils {
	private static final boolean isDebug = UtilsConfig.isDebug;
	private static final String TAG = ShortCutUtils.class.getName();

	/**
	 * 
	 * <p>
	 * Title: createShortCut
	 * </p>
	 * <p>
	 * Description: 创建桌面快捷方式
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * 
	 * @author xwc1125
	 * @date 2016年9月5日 下午2:14:35
	 */
	public static void createShortCutChange(Context context, String shortcutName, Bitmap shortcutIcon, Bitmap watermark,
			Intent shortcutIntent) {
		watermark = ImageUtils.scaleWithWH(watermark, shortcutIcon.getWidth(), shortcutIcon.getHeight());
		shortcutIcon = ImageUtils.getInstance(context).createWaterMaskImage(context, shortcutIcon, watermark);
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra("duplicate", false);// 只允许一个快捷图标
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * Title: createShortCut
	 * </p>
	 * <p>
	 * Description: 创建桌面快捷方式
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * 
	 * @author xwc1125
	 * @date 2016年9月5日 下午2:14:35
	 */
	public static void createShortCut(Context context, String shortcutName, Bitmap shortcutIcon,
			Intent shortcutIntent) {
		Intent intent = new Intent();
		intent.setAction("com.android.launcher.action.INSTALL_SHORTCUT");
		intent.putExtra("duplicate", false);// 只允许一个快捷图标
		// Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(),
		// MResource.getIdByName(context, "drawable",
		// ConfigResources.zzxsdk_logo));
		// Intent shortcutIntent = new Intent();
		// shortcutIntent.setClass(context,
		// com.zzx.sdk.all.activity.SplashActivity.class);
		// shortcutIntent.setAction("zzx.sdk.app");
		// shortcutIntent.addCategory(Intent.CATEGORY_DEFAULT);
		intent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, shortcutIntent);
		intent.putExtra(Intent.EXTRA_SHORTCUT_NAME, shortcutName);
		intent.putExtra(Intent.EXTRA_SHORTCUT_ICON, shortcutIcon);
		context.sendBroadcast(intent);
	}

	/**
	 * 
	 * <p>
	 * Title: hasShortcut
	 * </p>
	 * <p>
	 * Description: 判断快捷方式是否已经存在
	 * </p>
	 * <p>
	 * 因为不同的手机厂商可能对手机系统进行了修改使用原生的“content://com.android.launcher.settings/
	 * favorites?notify=true"
	 * 或者"content://com.android.launcher2.settings/favorites?notify=true"
	 * 并不能准确判断需要通过权限去获取当前手机provider.authority
	 * </p>
	 * 
	 * @tags @param activity
	 * @tags @param shortcutName
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月5日 下午2:19:07
	 */
	public static boolean hasShortcut(Activity activity, String shortcutName) {
		String url1 = "content://"
				+ getAuthorityFromPermission(activity, "com.android.launcher.permission.READ_SETTINGS")
				+ "/favorites?notify=true";
		String url2 = "content://"
				+ getAuthorityFromPermission(activity, "com.android.launcher2.permission.READ_SETTINGS")
				+ "/favorites?notify=true";
		String[] urls = new String[] { url1, url2 };
		ContentResolver resolver = activity.getContentResolver();

		for (int i = 0; i < urls.length; i++) {
			try {
				Cursor cursor = resolver.query(Uri.parse(urls[i]), new String[] { "title", "iconResource" }, "title=?",
						new String[] { shortcutName }, null);

				if (cursor != null && cursor.moveToFirst()) {
					cursor.close();
					return true;
				}
			} catch (Exception e) {
				LogUtils.e(TAG, "hasShortcut:" + e.getMessage(), isDebug);
				return false;
			}
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: getAuthorityFromPermission
	 * </p>
	 * <p>
	 * Description: 判断是否有权限
	 * </p>
	 * <p>
	 * 权限 <uses-permission android:name=
	 * "com.android.launcher.permission.READ_SETTINGS" />
	 * 
	 * 因为不同的手机厂商可能对手机系统进行了修改使用原生的“content://com.android.launcher.settings/
	 * favorites?notify=true"
	 * 或者"content://com.android.launcher2.settings/favorites?notify=true"
	 * 并不能准确判断需要通过权限去获取当前手机provider.authority
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param permission
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月5日 下午2:19:53
	 */
	private static String getAuthorityFromPermission(Context context, String permission) {
		if (permission == null) {
			return null;
		}
		List<PackageInfo> packs = context.getPackageManager().getInstalledPackages(PackageManager.GET_PROVIDERS);
		if (packs != null) {
			for (PackageInfo pack : packs) {
				ProviderInfo[] providers = pack.providers;
				if (providers != null) {
					for (ProviderInfo provider : providers) {
						if (permission.equals(provider.readPermission))
							return provider.authority;
						if (permission.equals(provider.writePermission))
							return provider.authority;
					}
				}
			}
		}
		return null;
	}
}
