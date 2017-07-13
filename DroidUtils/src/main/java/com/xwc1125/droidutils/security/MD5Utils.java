package com.xwc1125.droidutils.security;

import java.security.MessageDigest;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

/**
 * <p>
 * Title: Md5Utils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-1下午1:39:02
 */
public class MD5Utils {
    private static final String TAG = MD5Utils.class.getName();
    private static boolean isDebug = UtilsConfig.isDebug;

    /**
     * <p>
     * Title: encrypt
     * </p>
     * <p>
     * Description: Md5加密
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param str
     * @return
     * @author xwc1125
     * @date 2015-7-1下午1:40:24
     */
    public static String encrypt(String str) {
        String encryptResult = null;
        if (StringUtils.isEmpty(str)) {
            return encryptResult;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(str.getBytes());
            encryptResult = new String(encodeHex(messageDigest.digest()));
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return encryptResult;
    }

    /**
     * Used to build output as Hex
     */
    private static final char[] DIGITS_LOWER = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    /**
     * Converts an array of bytes into an array of characters representing the
     * hexadecimal values of each byte in order. The returned array will be
     * double the length of the passed array, as it takes two characters to
     * represent any given byte.
     *
     * @param data a byte[] to convert to Hex characters
     * @return A char[] containing hexadecimal characters
     */
    private static char[] encodeHex(final byte[] data) {
        final int l = data.length;
        final char[] out = new char[l << 1];
        // two characters form the hex value.
        for (int i = 0, j = 0; i < l; i++) {
            out[j++] = DIGITS_LOWER[(0xF0 & data[i]) >>> 4];
            out[j++] = DIGITS_LOWER[0x0F & data[i]];
        }
        return out;
    }
}
