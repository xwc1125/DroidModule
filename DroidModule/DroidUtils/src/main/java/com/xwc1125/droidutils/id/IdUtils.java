/**
 * <p>
 * Title: IdUtils.java
 * </p>
 * <p>
 * Description: 资源id获取工具类
 * </p>
 * <p>
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月18日 上午11:45:45
 * @version V1.0
 */
package com.xwc1125.droidutils.id;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

import android.content.Context;
import android.content.res.Resources;

/**
 * 
 * <p>
 * Title: IdUtils
 * </p>
 * <p>
 * Description: 与ID相关工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2015-3-23 上午11:29:52
 * 
 */
public class IdUtils {

	/**
	 * 
	 * <p>
	 * Title: getIdByName
	 * </p>
	 * <p>
	 * Description: 根据资源名称获取资源ID R.className.name;
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param className
	 * @param name
	 * @return:资源id
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:46:03
	 */
	public static int getIdByName(Context context, String className, String name) {
		try {
			String packageName = context.getPackageName();
			return mGetIdByName(packageName, className, name);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return 0;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 0;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}

	/**
	 * 
	 * @param pkname
	 * @param className
	 * @param name
	 * @return
	 */
	public static int getIdByName(String pkname, String className, String name) {
		try {
			return mGetIdByName(pkname, className, name);
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return 0;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return 0;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return 0;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return 0;
		}
	}


	/**
	 * 
	 * <p>
	 * Title: mGetIdByName
	 * </p>
	 * <p>
	 * Description: 反射获取id
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param className
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:46:33
	 */
	private static int mGetIdByName(String packageName, String className,
			String name) throws ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {
		Class<?> r = Class.forName(packageName + ".R$" + className);
		int id = r.getField(name).getInt(r);
		return id;
	}

	/**
	 * 
	 * <p>
	 * Title: getIdsByName
	 * </p>
	 * <p>
	 * Description: 根据资源名称获取资源ID组
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param className
	 * @param name
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:46:52
	 */
	public static int[] getIdsByName(Context context, String className,
			String name) {
		try {
			return mGetIdsByName(context, className, name);

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			return null;
		} catch (SecurityException e) {
			e.printStackTrace();
			return null;
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 
	 * <p>
	 * Title: mGetIdsByName
	 * </p>
	 * <p>
	 * Description:反射获取
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param className
	 * @param name
	 * @return
	 * @throws ClassNotFoundException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws NoSuchFieldException
	 * 
	 * @author xwc1125
	 * @date 2016年8月18日 上午11:47:17
	 */
	private static int[] mGetIdsByName(Context context, String className,
			String name) throws ClassNotFoundException, IllegalAccessException,
			IllegalArgumentException, NoSuchFieldException {

		String packageName = context.getPackageName();
		Class<?> r = Class.forName(packageName + ".R$" + className);
		Field field = r.getField(name);
		field.setAccessible(true);
		int[] ids = (int[]) field.get(r);
		return ids;
	}

	/**
	 * 
	 * <p>
	 * Title: getResources
	 * </p>
	 * <p>
	 * Description: 获取resources
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param apkPath
	 * @tags @return
	 * @tags @throws Exception
	 * 
	 * @author xwc1125
	 * @date 2016年8月17日 下午5:48:37
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static Resources getResources(Context context, String apkPath) throws Exception {
		String PATH_AssetManager = "android.content.res.AssetManager";
		Class assetMagCls = Class.forName(PATH_AssetManager);
		Constructor assetMagCt = assetMagCls.getConstructor((Class[]) null);
		Object assetMag = assetMagCt.newInstance((Object[]) null);
		Class[] typeArgs = new Class[1];
		typeArgs[0] = String.class;
		Method assetMag_addAssetPathMtd = assetMagCls.getDeclaredMethod("addAssetPath", typeArgs);
		Object[] valueArgs = new Object[1];
		valueArgs[0] = apkPath;
		assetMag_addAssetPathMtd.invoke(assetMag, valueArgs);
		Resources res = context.getResources();
		typeArgs = new Class[3];
		typeArgs[0] = assetMag.getClass();
		typeArgs[1] = res.getDisplayMetrics().getClass();
		typeArgs[2] = res.getConfiguration().getClass();
		Constructor resCt = Resources.class.getConstructor(typeArgs);
		valueArgs = new Object[3];
		valueArgs[0] = assetMag;
		valueArgs[1] = res.getDisplayMetrics();
		valueArgs[2] = res.getConfiguration();
		res = (Resources) resCt.newInstance(valueArgs);
		return res;
	}
	
}
