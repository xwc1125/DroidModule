package com.xwc1125.droidutils.app.entity;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.graphics.drawable.Drawable;

import com.xwc1125.droidutils.json.JsonUtils;

import java.io.File;

/**
 * 
 * <p>
 * Title: ApkItem
 * </p>
 * <p>
 * Description: apk的内容
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年8月17日 下午5:45:58
 *
 */
public class ApkItemInfo {
	/**
	 * App的图标
	 */
	private Drawable icon;
	/**
	 * App的包名
	 */
	private String pkName;
	/**
	 * App名称
	 */
	private CharSequence lable;
	/**
	 * App版本
	 */
	private String versionName;
	/**
	 * App版本号
	 */
	private int versionCode;
	/**
	 * App的位置
	 */
	private String apkfile;
	/**
	 * android.content.pm.PackageInfo
	 */
	private PackageInfo packageInfo;
	/**
	 * -1:未知，0：非系统App，1：系统App
	 */
	private int isSystemApp = -1;

	/**
	 * 
	 * <p>
	 * Title: getApkItemInfo
	 * </p>
	 * <p>
	 * Description: 通过PM，packageInfo，appInfo获取安装包的内容
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param pm
	 * @tags @param packageInfo
	 * @tags @param appInfo
	 * 
	 * @author xwc1125
	 * @date 2016年8月22日 上午9:42:18
	 */
	public void getApkItemInfo(PackageManager pm, PackageInfo packageInfo, ApplicationInfo appInfo) {
		try {
			this.icon = pm.getApplicationIcon(packageInfo.applicationInfo);
		} catch (Exception e) {
			this.icon = pm.getDefaultActivityIcon();
		}
		try {
			lable = pm.getApplicationLabel(packageInfo.applicationInfo);
		} catch (Exception e) {
		}
		try {
			if ((packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 0) {
				// 非系统应用
				this.isSystemApp = 0;
			} else {
				// 系统应用
				this.isSystemApp = 1;
			}
		} catch (Exception e) {
		}

		// 名称
		this.pkName = appInfo.packageName; // 得到包名
		this.versionName = packageInfo.versionName;
		this.versionCode = packageInfo.versionCode;
		this.apkfile = appInfo.sourceDir;
		this.packageInfo = packageInfo;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:根据上下文和安装包的文件进行获取安装包的信息
	 * </p>
	 * 
	 * @param context
	 * @param apkFile
	 *            安装包的file
	 */
	public ApkItemInfo(Context context, File apkFile) {
		if (apkFile != null) {
			PackageManager pm = context.getPackageManager();
			PackageInfo packinfo = pm.getPackageArchiveInfo(apkFile.getPath(), 0);
			ApplicationInfo appInfo = packinfo.applicationInfo;
			/* 必须加这两句，不然下面icon获取是default icon而不是应用包的icon */
			appInfo.sourceDir = apkFile.getPath();
			appInfo.publicSourceDir = apkFile.getPath();
			getApkItemInfo(pm, packinfo, appInfo);
		}
	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 根据包名获取指定包名的应用信息
	 * </p>
	 * 
	 * @param context
	 * @param packageName
	 *            包名
	 * @throws NameNotFoundException
	 */
	public ApkItemInfo(Context context, String packageName) throws NameNotFoundException {
		PackageManager pm = context.getPackageManager();
		ApplicationInfo appInfo = pm.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
		PackageInfo packinfo = pm.getPackageInfo(packageName, 0);
		getApkItemInfo(pm, packinfo, appInfo);
	}

	public Drawable getIcon() {
		return icon;
	}

	public void setIcon(Drawable icon) {
		this.icon = icon;
	}

	public String getPkName() {
		return pkName;
	}

	public void setPkName(String pkName) {
		this.pkName = pkName;
	}

	public CharSequence getLable() {
		return lable;
	}

	public void setLable(CharSequence lable) {
		this.lable = lable;
	}

	public String getVersionName() {
		return versionName;
	}

	public void setVersionName(String versionName) {
		this.versionName = versionName;
	}

	public int getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(int versionCode) {
		this.versionCode = versionCode;
	}

	public String getApkfile() {
		return apkfile;
	}

	public void setApkfile(String apkfile) {
		this.apkfile = apkfile;
	}

	public PackageInfo getPackageInfo() {
		return packageInfo;
	}

	public void setPackageInfo(PackageInfo packageInfo) {
		this.packageInfo = packageInfo;
	}

	public int getIsSystemApp() {
		return isSystemApp;
	}

	public void setIsSystemApp(int isSystemApp) {
		this.isSystemApp = isSystemApp;
	}

	public String toJsonString() {
		return JsonUtils.toJsonString(this);
	}

	@Override
	public String toString() {
		return "ApkItemInfo [icon=" + icon + ", pkName=" + pkName + ", lable=" + lable + ", versionName=" + versionName
				+ ", versionCode=" + versionCode + ", apkfile=" + apkfile + ", packageInfo=" + packageInfo + "]";
	}

}