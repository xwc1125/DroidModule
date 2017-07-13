/**
 * @Project ZzxSDK-3.0 
 * @Package com.zzx.framework.util.security 
 * @Title SecurityConfig.java 
 * @Description 
 * @Copyright Copyright (c) 2016 
 * @author xwc1125 
 * @date 2016年3月29日 上午8:58:54 
 * @version V1.0 
 */
package com.xwc1125.droidutils.security;

import com.xwc1125.droidutils.StringUtils;

import java.util.ArrayList;

/**
 * @ClassName SecurityKeyUtils
 * @Description TODO(describe the types)
 * @author xwc1125
 * @date 2016年3月29日 上午8:58:54
 */
public class SecurityKeyUtils {

	/**
	 * 
	 * <p>
	 * Title: getKeyValue
	 * </p>
	 * <p>
	 * Description:密钥处理
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param key
	 *            密钥
	 * @param tag
	 *            位移量
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2016年3月28日下午6:23:54
	 */
	public static String getKeyValue(String key, int tag) {
		if (StringUtils.isEmpty(key)) {
			return null;
		}
		char[] charKey = key.toCharArray();
		ArrayList<Character> aeskey = new ArrayList<Character>();
		for (int i = 0; i < charKey.length; i++) {
			aeskey.add(charKey[i]);
		}
		char[] result = new char[charKey.length];
		int k = 0;
		ArrayList<Integer> aesRemoveIndexs = new ArrayList<Integer>();
		for (int i = tag + 1; i > 0; i--) {
			int size = aeskey.size();
			aesRemoveIndexs.clear();
			for (int j = 0; j < size; j++) {
				if (j % i == 0) {
					if (k < charKey.length) {
						result[k++] = aeskey.get(j);
						aesRemoveIndexs.add(j);
					} else {
						break;
					}
				}
			}
			for (int l = 0; l < aesRemoveIndexs.size(); l++) {
				int removeIndex = aesRemoveIndexs.get(l);
				aeskey.remove(removeIndex - l);
			}
		}
		return new String(result);
	}
}
