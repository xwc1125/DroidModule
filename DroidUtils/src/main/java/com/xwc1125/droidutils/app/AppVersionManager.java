/**
 * <p>
 * Title: AppVersionManagerUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) Aug 1, 2016
 * @author xwc1125
 * @date Aug 1, 20163:32:48 PM
 * @version V1.0
 */
package com.xwc1125.droidutils.app;

/**
 * <p>
 * Title: AppVersionManagerUtils
 * </p>
 * <p>
 * Description:  APK版本管理器
 *  版本检查，版本更新等
 * </p>
 * <p>
 * 
 * </p>
 * @author xwc1125
 * @date Aug 1, 20163:32:48 PM
 *  
 */
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;

import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

public class AppVersionManager {

	private static final String TAG = AppVersionManager.class.getName();
	private static boolean isDebug = UtilsConfig.isDebug;

	private Context context;

	public AppVersionManager(Context context) {
		this.context = context;
	}

	/**
	 * 检查版本号是否相同
	 * 
	 * @param versionCode
	 * @return
	 */
	public boolean isSameVersion(int versionCode) {
		return getCurrentVersion() != versionCode ? Boolean.FALSE : Boolean.TRUE;
	}

	/**
	 * 
	 * <p>
	 * Title: silenceInstall
	 * </p>
	 * <p>
	 * Description: 静默安装，安装之前必须要获取到ROOT权限 原理:1.先获取到ROOT权限 2.在通过命令的方式直接安装APK
	 * </p>
	 * <p>
	 * 返回0：失败，1：成功
	 * </p>
	 * 
	 * @tags @param file
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月1日 下午6:10:28
	 */
	public int silenceInstall(File file) {
		OutputStream out = null;
		Process process = null;
		DataOutputStream dataOutputStream = null;
		int flag = 1;
		if (file.exists()) {
			try {
				final String command = "pm install -r " + file.getAbsolutePath();
				Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
				flag = proc.waitFor();
			} catch (Exception e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
			if (flag != 0) {
				try {
					Process proc = Runtime.getRuntime().exec("/system/bin/busybox install " + file.getAbsolutePath());
					flag = proc.waitFor();
				} catch (Exception e) {
					LogUtils.e(TAG, e.getMessage(), isDebug);
				}
			}
		}
		if (flag == 0) {
			return 1;
		}
		try {
			process = Runtime.getRuntime().exec("su");
			out = process.getOutputStream();
			dataOutputStream = new DataOutputStream(out);
			dataOutputStream.writeBytes("chmod 777 " + file.getPath() + "\n");
			dataOutputStream
					.writeBytes("LD_LIBRARY_PATH=/vendor/lib:/system/lib pm install -r " + file.getAbsolutePath());
			// 提交命令
			dataOutputStream.flush();
			int value = process.waitFor();
			if (value == 0) {
				return 1;
			}
			return 0;

		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			return 0;
		} finally {
			try {
				if (dataOutputStream != null) {
					dataOutputStream.close();
				}
				if (out != null) {
					out.close();
				}
			} catch (IOException e) {
				LogUtils.e(TAG, e.getMessage(), isDebug);
			}
		}
	}

	/**
	 * 普通的安装应用方式
	 * 
	 * @param file
	 *            安装包文件
	 */
	public void installApk(File file) {
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setDataAndType(Uri.parse("file://" + file.toString()), "application/vnd.android.package-archive");
		i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		this.context.startActivity(i);
	}

	/**
	 * 获取服务端中的版本号 这个自行完成
	 * 
	 * @return
	 */
	public int getHttpVersion() {
		return 0;
	}

	/**
	 * 获取当前APK的版本号
	 * 
	 * @return 当前APK的版本号
	 */
	public int getCurrentVersion() {
		try {
			return this.context.getPackageManager().getPackageInfo(this.context.getPackageName(), 0).versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
			return 0;
		}
	}

	/**
	 * 下载APK
	 */
	public void downApk() {
		new Thread(new DownApk()).start();
	}

	/**
	 * 显示下载进度提示框
	 */
	public void showDownloadDialog() {

	}

	/**
	 * 显示软件更新提示对话框
	 */
	public void showNoticeDialog() {

	}

	/**
	 * 下载APk的类
	 */
	class DownApk implements Runnable {

		@Override
		public void run() {

		}
	}

}