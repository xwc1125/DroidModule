/**
 * <p>
 * Title: SDCardUtils.java
 * </p>
 * <p>
 * Description: TODO(describe the file) 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) May 3, 2016
 * @author xwc1125
 * @date May 3, 20169:37:29 AM
 * @version V1.0
 */
package com.xwc1125.droidutils.device;

import android.os.Environment;
import android.os.StatFs;

/**
 * <p>
 * Title: SDCardUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author xwc1125
 * @date May 3, 20169:37:29 AM
 * 
 */
public class SDCardUtils {

	/**
	 * 检查是否安装了sd卡
	 * 
	 * @return false 未安装
	 */
	public static boolean isExitsSdcard() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)
				&& !state.equals(Environment.MEDIA_MOUNTED_READ_ONLY)) {
			return true;
		}
		return false;
	}

	/**
	 * 获取SD卡剩余空间的大小
	 * 
	 * @return long SD卡剩余空间的大小（单位：byte）
	 */
	@SuppressWarnings("deprecation")
	public static long getSDSize() {
		final String str = Environment.getExternalStorageDirectory().getPath();
		final StatFs localStatFs = new StatFs(str);
		final long blockSize = localStatFs.getBlockSize();
		return localStatFs.getAvailableBlocks() * blockSize;
	}
}
