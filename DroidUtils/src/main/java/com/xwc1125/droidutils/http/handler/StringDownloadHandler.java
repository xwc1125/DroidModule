/**
 * <p>
 * Title: StringDownloadHandler.java
 * </p>
 * <p>
 * Description: 网络访问请求结果处理类
 * </p>
 * <p>
 * 
 * </p>
 * @Copyright: Copyright (c) 2016
 * @author zhangqy
 * @date 2016年8月17日 上午9:19:16
 * @version V1.0
 */
package com.xwc1125.droidutils.http.handler;

import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.http.HttpConfig;
import com.xwc1125.droidutils.http.utils.InnerHttpUtil;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

/**
 * 
 * <p>
 * Title: StringDownloadHandler
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * 
 * </p>
 * 
 * @author zhangqy
 * @date 2016年5月20日下午2:00:17
 * 
 */
public class StringDownloadHandler {

	private static final String TAG = "StringDownloadHandler";
	private static final Boolean isDebug = HttpConfig.IS_SHOW_LOG;

	/**
	 * 
	 * <p>
	 * Title: handleEntity
	 * </p>
	 * <p>
	 * Description:处理网络访问请求的结果
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param connection：网络访问请求连接对象
	 * @param handler:进度回传对象
	 * @param charset:编码方式
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午12:54:05
	 */
	public String handleEntity(HttpURLConnection connection,
			RequestCallBackHandler handler, String charset) {
		StringBuilder sb = null;
		try {
			if (connection != null) {
				long current = 0;
				long total = connection.getContentLength();

				if (handler != null
						&& !handler.updateProgress(total, current, true)) {
					return null;
				}
				InputStream inputStream = null;
				sb = new StringBuilder();
				inputStream = connection.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(inputStream, charset));
				String line = "";
				while ((line = reader.readLine()) != null) {
					sb.append(line).append('\n');
					current += InnerHttpUtil.sizeOfString(line, charset);
					if (handler != null) {
						if (!handler.updateProgress(total, current, false)) {//进度回传
							break;
						}
					}
				}
				if (handler != null) {
					handler.updateProgress(total, current, true);
				}
			}
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return sb == null ? null : sb.toString().trim();
	}
}
