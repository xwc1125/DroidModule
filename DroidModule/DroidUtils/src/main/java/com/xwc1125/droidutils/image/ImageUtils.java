/**
 * <p>
 * Title: ImageUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016年9月5日
 * @author xwc1125
 * @date 2016年9月5日 下午4:58:59
 * @version V1.0
 */
package com.xwc1125.droidutils.image;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;

/**
 * <p>
 * Title: ImageUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年9月5日 下午4:58:59
 * 
 */
public class ImageUtils {

	private static final String TAG = ImageUtils.class.getName();
	private static final boolean isDebug = UtilsConfig.isDebug;
	private static ImageUtils ins;
	private static Context mContext;

	private ImageUtils(Context context) {
		mContext = context;
	}

	public static ImageUtils getInstance(Context context) {
		if (ins == null) {
			ins = new ImageUtils(mContext);
		}
		return ins;
	}

	/**
	 * 
	 * <p>
	 * Title: drawable2Bitmap
	 * </p>
	 * <p>
	 * Description: drawable转换为Bitmap
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param drawable
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月5日 下午5:03:26
	 */
	public Bitmap drawable2Bitmap(Drawable drawable) {
		try {
			if (drawable instanceof BitmapDrawable) {
				return ((BitmapDrawable) drawable).getBitmap();
			} else if (drawable instanceof NinePatchDrawable) {
				Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(),
						drawable.getOpacity() != PixelFormat.OPAQUE ? Config.ARGB_8888 : Config.RGB_565);
				Canvas canvas = new Canvas(bitmap);
				drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
				drawable.draw(canvas);
				return bitmap;
			} else {
				return null;
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: createWaterMaskImage
	 * </p>
	 * <p>
	 * Description: 添加水印图片
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param gContext
	 * @tags @param src
	 * @tags @param watermark
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月9日 上午11:04:53
	 */
	public Bitmap createWaterMaskImage(Context gContext, Bitmap src, Bitmap watermark) {
		if (src == null) {
			return null;
		}
		// 【视频卡】
		// 1、修复：如果水印图标为空时，导致生成图标失败的问题。
		if (watermark == null) {
			return src;
		}
		int w = src.getWidth();
		int h = src.getHeight();
		int ww = watermark.getWidth();
		int wh = watermark.getHeight();
		// create the new blank bitmap
		Bitmap newb = Bitmap.createBitmap(w, h, Config.ARGB_8888);// 创建一个新的和SRC长度宽度一样的位图
		Canvas cv = new Canvas(newb);
		// draw src into
		cv.drawBitmap(src, 0, 0, null);// 在 0，0坐标开始画入src
		// draw watermark into
		cv.drawBitmap(watermark, (w - ww) / 2, h - wh, null);// 在src的右下角画入水印
		// save all clip
		cv.save(Canvas.ALL_SAVE_FLAG);// 保存
		// store
		cv.restore();// 存储
		return newb;
	}

	/**
	 * 
	 * <p>
	 * Title: scaleWithWH
	 * </p>
	 * <p>
	 * Description: 缩放图片
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param src
	 * @tags @param w
	 * @tags @param h
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月9日 下午3:35:52
	 */
	public static Bitmap scaleWithWH(Bitmap src, double w, double h) {
		if (w == 0 || h == 0 || src == null) {
			return src;
		} else {
			// 记录src的宽高
			int width = src.getWidth();
			int height = src.getHeight();
			// 创建一个matrix容器
			Matrix matrix = new Matrix();
			// 计算缩放比例
			float scaleWidth = (float) (w / width);
			float scaleHeight = (float) (h / height);
			// 开始缩放
			matrix.postScale(scaleWidth, scaleHeight);
			// 创建缩放后的图片
			return Bitmap.createBitmap(src, 0, 0, width, height, matrix, true);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: drawTextToBitmap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param gContext
	 * @tags @param gResId
	 * @tags @param gText
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月9日 下午3:36:33
	 */
	public Bitmap drawTextToBitmap(Context gContext, int gResId, String gText) {
		Resources resources = gContext.getResources();
		float scale = resources.getDisplayMetrics().density;
		Bitmap bitmap = BitmapFactory.decodeResource(resources, gResId);

		bitmap = scaleWithWH(bitmap, 300 * scale, 300 * scale);

		Config bitmapConfig = bitmap.getConfig();

		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.RED);
		paint.setTextSize((int) (18 * scale));
		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		Rect bounds = new Rect();
		paint.getTextBounds(gText, 0, gText.length(), bounds);
		int x = 30;
		int y = 30;
		canvas.drawText(gText, x * scale, y * scale, paint);
		return bitmap;
	}

	/**
	 * 
	 * <p>
	 * Title: drawTextToBitmap
	 * </p>
	 * <p>
	 * Description: TODO(describe the methods)
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param gContext
	 * @tags @param bitmap
	 * @tags @param gText
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016年9月9日 下午3:36:47
	 */
	public Bitmap drawTextToBitmap(Context gContext, Bitmap bitmap, String gText) {
		int bitMapWidth = bitmap.getWidth();
		int bitMapHeight = bitmap.getHeight();
		Config bitmapConfig = bitmap.getConfig();

		// set default bitmap config if none
		if (bitmapConfig == null) {
			bitmapConfig = Config.ARGB_8888;
		}
		// resource bitmaps are imutable,
		// so we need to convert it to mutable one
		bitmap = bitmap.copy(bitmapConfig, true);

		Canvas canvas = new Canvas(bitmap);
		// new antialised Paint
		Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
		// text color - #3D3D3D
		paint.setColor(Color.RED);
		int textSize = 32;
		paint.setTextSize(textSize);
		paint.setDither(true); // 获取跟清晰的图像采样
		paint.setFilterBitmap(true);// 过滤一些
		Rect bounds = new Rect();
		paint.getTextBounds(gText, 0, gText.length(), bounds);
		int x = bitMapWidth / 2 - textSize;
		int y = bitMapHeight - 10;// 30
		canvas.drawText(gText, x, y, paint);
		return bitmap;
	}

}
