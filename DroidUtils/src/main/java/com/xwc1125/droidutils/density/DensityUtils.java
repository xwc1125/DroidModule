/**
 * <p>
 * Title: DensityUtils.java
 * </p>
 * <p>
 * Description: 适配，单位转换
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月18日 上午9:29:45
 * @version V1.0
 */
package com.xwc1125.droidutils.density;

import android.content.Context;

/**
 * 
 * <p>
 * Title: DensityUtils
 * </p>
 * <p>
 * Description: 单位转换
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年8月18日 上午11:05:46
 * 
 */
public class DensityUtils {
	/**
	 * 
	 * <p>
	 * Title: dip2px
	 * </p>
	 * <p>
	 * Description:根据手机的分辨率从 dp 的单位 转成为 px(像素)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param dpValue
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月18日 上午11:05:41
	 */
	public static int dip2px(Context context, float dpValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dpValue * scale + 0.5f);
	}

	/**
	 * 
	 * <p>
	 * Title: px2dip
	 * </p>
	 * <p>
	 * Description: 根据手机的分辨率从 px(像素) 的单位 转成为 dp
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月18日 上午11:06:16
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 
	 * <p>
	 * Title: px2sp
	 * </p>
	 * <p>
	 * Description: 将px值转换为sp值，保证文字大小不变
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param pxValue
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月18日 上午11:06:28
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 
	 * <p>
	 * Title: sp2px
	 * </p>
	 * <p>
	 * Description: 将sp值转换为px值，保证文字大小不变
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param spValue
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年8月18日 上午11:06:43
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}
}
