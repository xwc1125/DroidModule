/**
 * <p>
 * Title: DrawableUtils.java
 * </p>
 * <p>
 * Description: 图片处理类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author xwc1125
 * @date 2016年8月18日 上午9:29:45
 * @version V1.0
 */
package com.xwc1125.droidutils.drawable;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.net.Uri;
import android.util.Log;

/**
 * 
 * <p>
 * Title: DrawableUtils
 * </p>
 * <p>
 * Description: 图片相关工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date 2016年4月8日上午9:33:04
 * 
 */
public class DrawableUtils {
	private static final String TAG = DrawableUtils.class.getName();
	public static boolean isDebug = UtilsConfig.isDebug;

	/**
	 * 
	 * <p>
	 * Title: getHttpBitmap
	 * </p>
	 * <p>
	 * Description: 根据URL获取网络图片
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param url
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年1月4日下午1:30:44
	 */
	public static Bitmap getHttpBitmap(final String url) {
		URL imgUrl;
		Bitmap bitmap = null;
		try {
			imgUrl = new URL(url);
			HttpURLConnection conn = (HttpURLConnection) imgUrl
					.openConnection();
			// conn.setConnectionTiem(0);表示没有时间限制
			conn.setConnectTimeout(0);
			// 连接设置获得数据流
			conn.setDoInput(true);
			// 不使用缓存
			conn.setUseCaches(false);
			conn.connect();
			// 得到数据流
			InputStream is = conn.getInputStream();
			// 解析得到图片
			bitmap = BitmapFactory.decodeStream(is);
			// 关闭数据流
			is.close();
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}

		return bitmap;
	}

	/**
	 * 
	 * <p>
	 * Title: getShapeDrawable
	 * </p>
	 * <p>
	 * Description: 获取shape定义的背景
	 * </p>
	 * <p>
	 * roundRadius: Specify radii for each of the 4 corners. For each corner,
	 * the array contains 2 values, <code>[X_radius, Y_radius]</code>. The
	 * corners are ordered top-left, top-right, bottom-right, bottom-left. This
	 * property is honored only when the shape is of type .
	 * </p>
	 * 
	 * @param strokeWidth
	 *            边框宽度
	 * @param strokeColor
	 *            边框颜色
	 * @param fillColor
	 *            填充颜色
	 * @param roundRadius
	 *            圆角半径
	 * 
	 * 
	 * @author xwc1125
	 * @date 2016年1月5日上午11:27:36
	 */
	public static Drawable getShapeDrawable(int strokeWidth, int strokeColor,
			int fillColor, float[] roundRadius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(fillColor);
		drawable.setCornerRadii(roundRadius);
		drawable.setStroke(strokeWidth, strokeColor);
		return drawable;
	}

	/**
	 * 
	 * <p>
	 * Title: getShapeDrawable
	 * </p>
	 * <p>
	 * Description: 获取shape定义的背景
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param strokeWidth
	 *            边框宽度
	 * @param strokeColor
	 *            边框颜色
	 * @param fillColor
	 *            填充颜色
	 * @param roundRadius
	 *            圆角半径
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年1月5日下午2:32:20
	 */
	public static Drawable getShapeDrawable(int strokeWidth, int strokeColor,
			int fillColor, int roundRadius) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setColor(fillColor);
		drawable.setCornerRadius(roundRadius);
		drawable.setStroke(strokeWidth, strokeColor);
		return drawable;
	}

	/**
	 * 
	 * <p>
	 * Title: getImageFromAssetsFile
	 * </p>
	 * <p>
	 * Description:获取assets文件夹中的图片文件
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param fileName
	 *            图片文件名称
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2016年1月5日下午12:09:33
	 */
	public static Bitmap getImageFromAssetsFile(Context context, String fileName) {
		Bitmap image = null;
		AssetManager am = context.getResources().getAssets();
		try {
			InputStream is = am.open(fileName);
			image = BitmapFactory.decodeStream(is);
			is.close();
		} catch (IOException e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return image;
	}

	/**
	 * 
	 * <p>
	 * Title: getDividerDrawable
	 * </p>
	 * <p>
	 * Description: 分界线
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param fillColor
	 * @param size
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2017年3月9日 下午5:09:17
	 */
	public static Drawable getDividerDrawable(Context context, int fillColor,
			int[] size) {
		GradientDrawable drawable = new GradientDrawable();
		drawable.setSize(size[0], size[1]);
		drawable.setColor(fillColor);
		return drawable;
	}

	/**
	 * 
	 * <p>
	 * Title: getDrawableByUri
	 * </p>
	 * <p>
	 * Description: 根据uri创建drawable
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @param mUri
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2017年3月10日 上午10:38:00
	 */
	public static Drawable getDrawableByUri(Context mContext, Uri mUri) {
		if (mContext == null || mUri == null) {
			return null;
		}
		Drawable d = null;
		try {
			String scheme = mUri.getScheme();
			if (ContentResolver.SCHEME_CONTENT.equals(scheme)
					|| ContentResolver.SCHEME_FILE.equals(scheme)) {
				InputStream stream = null;
				try {
					stream = mContext.getContentResolver()
							.openInputStream(mUri);
					d = Drawable.createFromStream(stream, null);
				} catch (Exception e) {
					Log.w("ImageView", "Unable to open content: " + mUri, e);
				} finally {
					if (stream != null) {
						try {
							stream.close();
						} catch (IOException e) {
							Log.w("ImageView", "Unable to close content: "
									+ mUri, e);
						}
					}
				}
			} else {
				d = Drawable.createFromPath(mUri.toString());
			}

			if (d == null) {
				System.out.println("resolveUri failed on bad bitmap uri: "
						+ mUri);
				// Don't try again.
				mUri = null;
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d;
	}

	/**
	 * 
	 * <p>
	 * Title: getDrawableByAssetName
	 * </p>
	 * <p>
	 * Description: 根据asset目录下的文件名称获取图片
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param mContext
	 * @param mName
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2017年3月10日 上午10:42:46
	 */
	public static Drawable getDrawableByAssetName(Context mContext, String mName) {
		if (mContext == null || mName == null) {
			return null;
		}
		Drawable d = null;
		try {
			AssetManager am = mContext.getResources().getAssets();
			InputStream stream = null;
			try {
				stream = am.open(mName);
				d = Drawable.createFromStream(stream, null);
			} catch (Exception e) {
				Log.w("ImageView", "Unable to open content: " + mName, e);
			} finally {
				if (stream != null) {
					try {
						stream.close();
					} catch (IOException e) {
						Log.w("ImageView", "Unable to close content: " + mName,
								e);
					}
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return d;
	}

	/**
	 * 
	 * <p>
	 * Title: getPressedDrawable
	 * </p>
	 * <p>
	 * Description:控件按下的效果
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param defalutImg
	 * @param pressedImg
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2017年3月14日 下午4:05:14
	 */
	public static StateListDrawable getPressedDrawable(Context context,
			String defalutImg, String pressedImg) {
		if (context == null) {
			return null;
		}
		try {
			StateListDrawable selector = new StateListDrawable();
			selector.addState(new int[] { android.R.attr.state_pressed },
					DrawableUtils.getDrawableByAssetName(context, pressedImg));
			selector.addState(new int[] {},
					DrawableUtils.getDrawableByAssetName(context, defalutImg));

			return selector;
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return null;
	}

	/**
	 * 
	 * <p>
	 * Title: getAnimationDrawable
	 * </p>
	 * <p>
	 * Description: 获取帧动画
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param context
	 * @param imgList
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2017年3月14日 下午4:13:04
	 */
	public static AnimationDrawable getAnimationDrawable(Context context,
			String[] imgList) {
		if (context == null && imgList == null) {
			return null;
		}
		try {
			AnimationDrawable drawable = new AnimationDrawable();
			for (int i = 0; i < imgList.length; i++) {
				drawable.addFrame(getDrawableByAssetName(context, imgList[i]),
						200);

			}
			drawable.setOneShot(false);
			return drawable;
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return null;
	}
}
