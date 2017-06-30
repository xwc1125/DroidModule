package com.yuancy.framework.utils.string;

import android.text.TextUtils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 
 * <p>
 * Title: StringUtils
 * </p>
 * <p>
 * Description:
 * </p>
 * <p>
 * Company:
 * </p>
 * 
 * @author xwc1125
 * @date 2015-5-30下午6:33:09
 */
public class StringUtils {
	/**
	 * @Title isEmpty
	 * @Description TODO(describe the methods)
	 * @param @param ss
	 * @param @return
	 * @return boolean
	 * @author xwc1125
	 * @date 2016年1月22日 下午2:36:09
	 */
	public static boolean isEmpty(String... ss) {
		if (ss == null)
			return true;
		for (String s : ss) {
			if (s == null || s.length() < 1)
				if (isEmpty(s))
					return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: isEmpty
	 * </p>
	 * <p>
	 * Description: 判断是否为空字符
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param str
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-13下午2:08:37
	 */
	public static Boolean isEmpty(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0
				|| str.equals("null") || str.equals("")) {
			return true;
		}
		return false;
	}

	/**
	 * 
	 * <p>
	 * Title: isNotEmpty
	 * </p>
	 * <p>
	 * Description: 判断是否不为空字符
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @param str
	 * @return
	 * 
	 * @author xwc1125
	 * @date 2015-7-13下午2:09:08
	 */
	public static Boolean isNotEmpty(String str) {
		if (str == null || str.length() == 0 || str.trim().length() == 0
				|| str.equals("null") || str.equals("")) {
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @Title isNotEmpty
	 * @Description 是否全部不为空
	 * @param @param str
	 * @param @return
	 * @return Boolean
	 * @author xwc1125
	 * @date 2016年4月1日 上午9:25:28
	 */
	public static Boolean isNotEmpty(String... str) {
		if (str == null)
			return false;
		for (String s : str) {
			if (s == null || s.length() < 1)
				return false;
		}
		return true;
	}

	/**
	 * 
	 * <p>
	 * Title: isContain
	 * </p>
	 * <p>
	 * Description: 判断对象是否在数组中
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param str
	 * @tags @param filter
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016-3-28上午11:52:31
	 */
	public static Boolean isContain(Object str, Object... filter) {
		if (filter == null || filter.length == 0 || str == null) {
			return false;
		}
		List<Object> l = Arrays.asList(filter);
		if (l.contains(str)) {
			return true;
		} else
			return false;
	}

	/**
	 * 
	 * <p>
	 * Title: isListContain
	 * </p>
	 * <p>
	 * Description: list中是否存在该元素
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * 
	 * @tags @param str
	 * @tags @param filter
	 * @tags @return
	 * 
	 * @author xwc1125
	 * @date 2016-3-28下午12:02:19
	 */
	public static Boolean isListContain(String str, List<String> filter) {
		if (filter == null || filter.size() == 0 || str == null) {
			return false;
		}
		if (filter.contains(str)) {
			return true;
		} else
			return false;
	}

	private static final int STRING_BUFFER_LENGTH = 100;

	/**
	 * 
	 * <p>
	 * Title: sizeOfString
	 * </p>
	 * <p>
	 * Description:
	 * </p>
	 * <p>
	 * </p>
	 * 
	 * @param str
	 * @param charset
	 * @return
	 * @throws UnsupportedEncodingException
	 * 
	 * @author zhangqy
	 * @date 2016年5月12日下午2:02:46
	 */
	public static long sizeOfString(final String str, String charset)
			throws UnsupportedEncodingException {
		if (TextUtils.isEmpty(str)) {
			return 0;
		}
		int len = str.length();
		if (len < STRING_BUFFER_LENGTH) {
			return str.getBytes(charset).length;
		}
		long size = 0;
		for (int i = 0; i < len; i += STRING_BUFFER_LENGTH) {
			int end = i + STRING_BUFFER_LENGTH;
			end = end < len ? end : len;
			String temp = getSubString(str, i, end);
			size += temp.getBytes(charset).length;
		}
		return size;
	}

	public static String getSubString(final String str, int start, int end) {
		return new String(str.substring(start, end));
	}

	/**
	 * 
	 * @Title getListFromListStr
	 * @Description 将list的字符串转换为list
	 * @param @param listStr
	 * @param @return
	 * @return List<String>
	 * @author xwc1125
	 * @date 2016年2月23日 上午9:26:37
	 */
	public static List<String> getListFromListStr(String listStr) {
		List<String> receiversList = new ArrayList<String>();
		if (StringUtils.isEmpty(listStr)) {
			return receiversList;
		}
		try {
			String[] arr = listStr.replaceAll("\\[", "").replaceAll("\\]", "")
					.replaceAll(" ", "").split(",");
			receiversList = Arrays.asList(arr);
		} catch (Exception e) {
			receiversList.add(listStr);
		}
		return receiversList;
	}

	/**
	 * 
	 * <p>
	 * Title: isLowerCase
	 * </p>
	 * <p>
	 * Description: 判断字符串是否全部为小写
	 * </p>
	 * <p>
	 * 
	 * </p>
	 * @param content
	 * @return
	 * 
	 * @author zhangqy
	 * @date 2017年2月16日 上午11:04:53
	 */
	public static boolean isLowerCase(String content) {
		if (isEmpty(content)) {
			return false;
		}
		char[] charArray = content.trim().toCharArray();
		if (charArray != null && charArray.length > 0) {
			for (int i = 0; i < charArray.length; i++) {
				char c = charArray[i];
				boolean lowerCase = Character.isLowerCase(c);
				if (!lowerCase) {
					return false;
				}
			}
		}
		return true;
	}

	public static String strJoin(CharSequence delimiter, Iterable<? extends CharSequence> elements) throws Exception {
		if (delimiter == null || elements == null) {
			throw new Exception("参数不能为空");
		}

		StringBuffer joiner = new StringBuffer();

		for (CharSequence cs : elements) {
			joiner.append(cs).append(delimiter);
		}
		int aa=joiner.lastIndexOf(delimiter+"");
		return joiner.substring(0,aa);
	}
}
