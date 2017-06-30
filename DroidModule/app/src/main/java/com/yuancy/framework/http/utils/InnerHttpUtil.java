/**
 *  @Project WuyiTouClick-sdk 
 *  @Package com.ugchain.sdk.base.framework.http.utils
 *
 * @Title HttpUtils.java
 * @Description
 * @Copyright Copyright (c) 2017
 * @author xwc1125
 * @date 2017年5月19日 下午2:21:53 
 * @version V1.0
 */
package com.yuancy.framework.http.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.text.TextUtils;


import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.util.Locale;

/**
 * @ClassName HttpUtils
 * @Description TODO(describe the types)
 * @author xwc1125
 * @date 2017年5月19日 下午2:21:53
 */
public class InnerHttpUtil {
    /**
     *
     * @Title isSupportRange
     * @Description 是否支持断点续传
     * @param @param
     *            connection
     * @param @return
     * @return boolean
     * @author xwc1125
     * @date 2017年5月19日 下午2:22:10
     */
    public static boolean isSupportRange(final HttpURLConnection connection) {
        if (connection == null)
            return false;
        String value = connection.getHeaderField("Accept-Ranges");
        if (isNotEmpty(value)) {
            return "bytes".equals(value);
        }
        String rangeValue = connection.getHeaderField("Content-Range");
        if (isNotEmpty(rangeValue)) {
            return value.startsWith("bytes");
        }
        return false;
    }

    /**
     *
     * @Title getFileNameFromHttpResponse
     * @Description 根据http响应结果获取文件名称
     * @param @param
     *            connection
     * @param @return
     * @return String
     * @author xwc1125
     * @date 2017年5月19日 下午2:22:54
     */
    public static String getFileNameFromHttpResponse(final HttpURLConnection connection) {
        String fileName = null;
        try {
            if (connection != null) {
                String headerField = connection.getHeaderField("Content-Disposition");
                if (isNotEmpty(headerField)) {
                    String contentDisposition = new String(headerField.getBytes("ISO-8859-1"), "GBK");
                    if (isNotEmpty(contentDisposition)) {
                        fileName = contentDisposition.substring(contentDisposition.indexOf('\"') + 1,
                                contentDisposition.lastIndexOf("\""));
                    }
                }
            }
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return fileName;
    }

    @SuppressLint("DefaultLocale")
    public static String getUserAgent(Context context) {
        String webUserAgent = null;
        if (context != null) {
            try {
                Class<?> sysResCls = Class
                        .forName("com.android.internal.R$string");
                Field webUserAgentField = sysResCls
                        .getDeclaredField("web_user_agent");
                Integer resId = (Integer) webUserAgentField.get(null);
                webUserAgent = context.getString(resId);
            } catch (Throwable ignored) {
                ignored.printStackTrace();
            }
        }
        if (TextUtils.isEmpty(webUserAgent)) {
            webUserAgent = "Mozilla/5.0 (Linux; U; Android %s) AppleWebKit/533.1 (KHTML, like Gecko) Version/4.0 %sSafari/533.1";
        }
        try {
            Locale locale = Locale.getDefault();
            StringBuffer buffer = new StringBuffer();
            // Add version
            final String version = Build.VERSION.RELEASE;
            if (version.length() > 0) {
                buffer.append(version);
            } else {
                // default to "1.0"
                buffer.append("1.0");
            }
            buffer.append("; ");
            final String language = locale.getLanguage();
            if (language != null) {
                buffer.append(language.toLowerCase());
                final String country = locale.getCountry();
                if (country != null) {
                    buffer.append("-");
                    buffer.append(country.toLowerCase());
                }
            } else {
                // default to "en"
                buffer.append("en");
            }
            // add the model for the release build
            if ("REL".equals(Build.VERSION.CODENAME)) {
                final String model = Build.MODEL;
                if (model.length() > 0) {
                    buffer.append("; ");
                    buffer.append(model);
                }
            }
            final String id = Build.ID;
            if (id.length() > 0) {
                buffer.append(" Build/");
                buffer.append(id);
            }
            webUserAgent = String.format(webUserAgent, buffer, "Mobile ");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return webUserAgent;
    }

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

    public static Boolean isNotEmpty(String str) {
        if (str == null || str.length() == 0 || str.trim().length() == 0
                || str.equals("null") || str.equals("")) {
            return false;
        }
        return true;
    }
    private static final int STRING_BUFFER_LENGTH = 100;
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
}
