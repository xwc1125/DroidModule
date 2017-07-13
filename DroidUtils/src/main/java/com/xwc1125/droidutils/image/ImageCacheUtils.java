/**
 * <p>
 * Title: ImageCacheUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) Aug 1, 2016
 * @author xwc1125
 * @date Aug 1, 20163:02:41 PM
 * @version V1.0
 */
package com.xwc1125.droidutils.image;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.util.Hashtable;

/**
 * <p>
 * Title: ImageCacheUtils
 * </p>
 * <p>
 * Description: 图片缓存工具类
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date Aug 1, 20163:02:41 PM
 * 
 */
public class ImageCacheUtils {
	static private ImageCacheUtils cache;// 一个Cache实例
	private Hashtable<String, ImageRef> ImageRefs;// 用于Chche内容的存储
	private ReferenceQueue<ImageCache> q;// 垃圾Reference的队列

	/**
	 * 
	 * <p>
	 * Title: ImageRef
	 * </p>
	 * <p>
	 * Description: 继承SoftReference，使得每一个实例都具有可识别的标识。
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20163:07:44 PM
	 *
	 */
	private class ImageRef extends SoftReference<ImageCache> {
		private String key = "";

		public ImageRef(ImageCache im, ReferenceQueue<ImageCache> q) {
			super(im, q);
			key = im.getId();
		}
	}

	/**
	 * 
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description: 构建一个缓存器实例
	 * </p>
	 */
	private ImageCacheUtils() {
		ImageRefs = new Hashtable<String, ImageRef>();
		q = new ReferenceQueue<ImageCache>();

	}

	/**
	 * 
	 * <p>
	 * Title: getInstance
	 * </p>
	 * <p>
	 * Description: 取得缓存器实例
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20163:08:06 PM
	 */
	public static ImageCacheUtils getInstance() {
		if (cache == null) {
			cache = new ImageCacheUtils();
		}
		return cache;

	}

	/**
	 * 
	 * <p>
	 * Title: cacheImage
	 * </p>
	 * <p>
	 * Description: 以软引用的方式对一个Image对象的实例进行引用并保存该引用
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param im
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20163:08:14 PM
	 */
	public void cacheImage(ImageCache im) {
		cleanCache();// 清除垃圾引用
		ImageRef ref = new ImageRef(im, q);
		ImageRefs.put(im.getId(), ref);
	}

	/**
	 * 
	 * <p>
	 * Title: getImage
	 * </p>
	 * <p>
	 * Description: 依据所指定的ID号，重新获取相应Image对象的实例
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param id
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20163:08:25 PM
	 */
	public ImageCache getImage(String id) {
		ImageCache im = null;
		// 缓存中是否有该Image实例的软引用，如果有，从软引用中取得。
		if (ImageRefs.containsKey(id)) {
			ImageRef ref = (ImageRef) ImageRefs.get(id);
			im = (ImageCache) ref.get();
		}

		// 如果没有软引用，或者从软引用中得到的实例是null，重新构建一个实例，
		// 并保存对这个新建实例的软引用

		if (im == null) {
			im = new ImageCache(id);
			this.cacheImage(im);
		}
		return im;
	}

	private void cleanCache() {
		ImageRef ref = null;
		while ((ref = (ImageRef) q.poll()) != null) {
			ImageRefs.remove(ref.key);
		}
	}

	/**
	 * 
	 * <p>
	 * Title: clearCache
	 * </p>
	 * <p>
	 * Description: 清除Cache内的全部内容
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags
	 * 
	 * @author xwc1125
	 * @date Aug 1, 20163:08:35 PM
	 */
	public void clearCache() {
		cleanCache();
		ImageRefs.clear();
		System.gc();
		System.runFinalization();
	}
}
