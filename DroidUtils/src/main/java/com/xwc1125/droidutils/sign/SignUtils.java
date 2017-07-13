package com.xwc1125.droidutils.sign;

import java.util.Map;
import java.util.TreeMap;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.security.MD5Utils;

/**
 * 
 * <p>
 * Title: SignUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xwc1125
 * @date 2015-5-30上午10:26:46
 */
public class SignUtils {
	private static final String TAG = SignUtils.class.getName();
	private static boolean isDebug = UtilsConfig.isDebug;

	public static String sign(String uri, TreeMap<String, Object> paramMap,
			String md5Key) {
		if (paramMap == null) {
			return null;
		}
		String sign = null;
		try {
			StringBuffer buf = new StringBuffer(md5Key);
			buf.append(uri).append('?');
			for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
				String name = entry.getKey();
				String value = entry.getValue() + "";
				if (entry.getValue() != null && value.length() > 0
						&& !"null".equals(value)) {
					if (!"sign".equals(name) && !name.startsWith("_")
							&& !"file".equals(name)) {
						buf.append(name).append('=').append(entry.getValue())
								.append('&');
					}
				}
			}
			if (buf.charAt(buf.length() - 1) == '&') {
				buf.deleteCharAt(buf.length() - 1);
			}
			sign = MD5Utils.encrypt(buf.toString());
		} catch (Exception e) {
			LogUtils.e(TAG, e.getMessage(), isDebug);
		}
		return sign;
	}
}
