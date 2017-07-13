/**
 * <p>
 * Title: FileDownloadHandler.java
 * </p>
 * <p>
 * Description: 文件下载处理类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.xwc1125.droidutils.http.handler;

import android.text.TextUtils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;

/**
 * 
 * <p>
 * Title: FileDownloadHandler
 * </p>
 * <p>
 * Description:文件下载处理
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年5月20日下午2:00:42
 * 
 */
public class FileDownloadHandler {

	/**
	 * 
	 * <p>
	 * Title: handleEntity
	 * </p>
	 * <p>
	 * Description: 文件操作
	 * </p>
	 * <p>
	 * 完成： 1、将下载的文件内容存入指定的文件中。 2、将下载文件进度数据同步回传出去。
	 * </p>
	 * 
	 * @param connection
	 *            ：网络访问连接对象
	 * @param callBackHandler
	 *            ：文件下载进度回传对象
	 * @param target
	 *            ：文件保存路径
	 * @param isResume
	 *            ：是否支持断点下载
	 * 
	 * @param responseFileName
	 *            ：支持断点下载时的文件保存路径（直接从coonection中获取）
	 * 
	 * @return
	 * @throws IOException
	 * 
	 * @author zhangqy
	 * @date 2016年8月16日 下午5:30:46
	 */
	public File handleEntity(HttpURLConnection connection,
			RequestCallBackHandler callBackHandler, String target,
			boolean isResume, String responseFileName) throws IOException {
		if (connection == null || TextUtils.isEmpty(target)) {
			return null;
		}

		// 创建文件对象
		File targetFile = new File(target);
		if (!targetFile.exists()) {
			File dir = targetFile.getParentFile();
			if (dir.exists() || dir.mkdirs()) {
				targetFile.createNewFile();
			}
		}
		// 文件内容读取、存储操作
		long current = 0;
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			FileOutputStream fileOutputStream = null;
			if (isResume) {
				current = targetFile.length();
				fileOutputStream = new FileOutputStream(target, true);
			} else {
				fileOutputStream = new FileOutputStream(target);
			}
			long total = connection.getContentLength() + current;
			bis = new BufferedInputStream(connection.getInputStream());
			bos = new BufferedOutputStream(fileOutputStream);

			if (callBackHandler != null
					&& !callBackHandler.updateProgress(total, current, true)) {
				return targetFile;
			}

			byte[] tmp = new byte[4096];
			int len;
			while ((len = bis.read(tmp)) != -1) {
				bos.write(tmp, 0, len);
				current += len;
				if (callBackHandler != null) {// 更新文件下载进度
					boolean updateProgress = callBackHandler.updateProgress(total, current, false);
					if (!updateProgress) {
						return targetFile;
					}
				}
			}
			bos.flush();
			if (callBackHandler != null) {
				callBackHandler.updateProgress(total, current, true);
			}
		} catch (Throwable e) {
			e.printStackTrace();
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (Throwable e) {
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (Throwable e) {
				}
			}
		}
		// 文件重命名（限支持断点下载）
		if (targetFile.exists() && !TextUtils.isEmpty(responseFileName)) {
			File newFile = new File(targetFile.getParent(), responseFileName);
			while (newFile.exists()) {
				newFile = new File(targetFile.getParent(),
						System.currentTimeMillis() + responseFileName);
			}
			return targetFile.renameTo(newFile) ? newFile : targetFile;
		} else {
			return targetFile;
		}
	}

}
