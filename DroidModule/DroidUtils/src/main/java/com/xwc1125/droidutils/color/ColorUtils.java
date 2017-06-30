/**
 * <p>
 * Title: ColorUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) Aug 1, 2016
 * @author xwc1125
 * @date Aug 1, 20162:57:52 PM
 * @version V1.0
 */
package com.xwc1125.droidutils.color;

import java.util.regex.Pattern;

import android.content.Context;
import android.graphics.Color;

/**
 * <p>
 * Title: ColorUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date Aug 1, 20162:57:52 PM
 * 
 */
public class ColorUtils {
	/**
	 * 
	 * <p>
	 * Title: getResourcesColor
	 * </p>
	 * <p>
	 * Description:获取资源中的颜色
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param context
	 * @tags @param color
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20162:58:53 PM
	 */
	public static int getResourcesColor(Context context, int color) {
		int ret = 0x00ffffff;
		try {
			ret = context.getResources().getColor(color);
		} catch (Exception e) {
		}
		return ret;
	}

	/**
	 * 
	 * <p>
	 * Title: HextoColor
	 * </p>
	 * <p>
	 * Description: 将十六进制 颜色代码 转换为 int
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param color
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20162:59:07 PM
	 */
	public static int HextoColor(String color) {
		// #ff00CCFF
		String reg = "#[a-f0-9A-F]{8}";
		if (!Pattern.matches(reg, color)) {
			color = "#00ffffff";
		}
		return Color.parseColor(color);
	}

	/**
	 * 
	 * <p>
	 * Title: changeAlpha
	 * </p>
	 * <p>
	 * Description: 修改颜色透明度
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param color
	 * @tags @param alpha
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20162:59:27 PM
	 */
	public static int changeAlpha(int color, int alpha) {
		int red = Color.red(color);
		int green = Color.green(color);
		int blue = Color.blue(color);

		return Color.argb(alpha, red, green, blue);
	}
}
