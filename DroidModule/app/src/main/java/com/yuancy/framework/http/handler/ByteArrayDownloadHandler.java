/**
 * <p>
 * Title: ByteArrayDownloadHandler.java
 * </p>
 * <p>
 * Description: 
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月16日 上午10:16:14
 * @version V1.0
 */
package com.yuancy.framework.http.handler;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;

/**
 * 
 * <p>
 * Title: ByteArrayDownloadHandler
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年5月20日下午2:00:42
 * 
 */
public class ByteArrayDownloadHandler {

	public byte[] handleEntity(HttpURLConnection connection) throws IOException {
		if (connection == null) {
			return null;
		}
		InputStream is = connection.getInputStream();
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] tmp = new byte[4096];
		int len;
		while ((len = is.read(tmp)) != -1) {
			baos.write(tmp, 0, len);
		}
		baos.flush();
		return baos.toByteArray();
	}

}
