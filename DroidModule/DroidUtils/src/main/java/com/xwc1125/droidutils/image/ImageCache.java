/**
 * <p>
 * Title: Image.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) Aug 1, 2016
 * @author xwc1125
 * @date Aug 1, 20163:12:04 PM
 * @version V1.0
 */
package com.xwc1125.droidutils.image;

import android.media.Image;

/**
 * <p>
 * Title: Image
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date Aug 1, 20163:12:04 PM
 * 
 */
public class ImageCache {
	private String id;
	private Image image;

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 */
	public ImageCache(String id) {
		this.id = id;
	}

	/**
	 * <p>
	 * Title:
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * 
	 * @param id
	 * @param image
	 */
	public ImageCache(String id, Image image) {
		this.id = id;
		this.image = image;
	}

	public String getId() {
		return null;
	}

	public Image getImage() {
		return image;
	}

	public void setImage(Image image) {
		this.image = image;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Override
	public String toString() {
		return "ImageCache [id=" + id + ", image=" + image + "]";
	}

}
