package com.xwc1125.droidutils.security;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import android.annotation.SuppressLint;

import com.xwc1125.droidutils.UtilsConfig;
import com.xwc1125.droidutils.LogUtils;
import com.xwc1125.droidutils.StringUtils;

/**
 * <p>
 * Title: AESUtils
 * </p>
 * <p>
 * Description: TODO(describe the types)
 * </p>
 * <p>
 * <p>
 * </p>
 *
 * @author xwc1125
 * @date 2015-7-1上午11:43:31
 */
public class AESUtils {
    private static final String charset = "utf-8";
    private static final String TAG = AESUtils.class.getName();
    private static boolean isDebug = UtilsConfig.isDebug;

    /**
     * <p>
     * Title: Encrypt
     * </p>
     * <p>
     * Description: AES加密
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param plaintext 要加密的字串
     * @param sKey      加密的密钥
     * @return
     * @throws Exception
     * @author xwc1125
     * @date 2015-7-1下午1:38:05
     */
    @SuppressLint({"TrulyRandom", "DefaultLocale"})
    public static String encrypt(String plaintext, String sKey) {
        String encryptResult = null;
        if (StringUtils.isEmpty(plaintext)) {
            LogUtils.e(TAG, "encrypt content is null", isDebug);
            return null;
        }
        if (StringUtils.isEmpty(sKey)) {
            LogUtils.e(TAG, "encrypt key is null", isDebug);
            return null;
        }
        if (sKey.length() != 16) {
            LogUtils.e(TAG, "encrypt key length error", isDebug);
            return null;
        }
        try {
            byte[] raw = sKey.getBytes(charset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
            byte[] encrypted = cipher.doFinal(plaintext.getBytes(charset));
            encryptResult = byte2hex(encrypted).toLowerCase();
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return encryptResult;
    }

    /**
     * <p>
     * Title: Decrypt
     * </p>
     * <p>
     * Description: AES解密
     * </p>
     * <p>
     * <p>
     * </p>
     *
     * @param ciphertext 要解密的加密后字符串
     * @param sKey       解密秘钥
     * @return
     * @throws Exception
     * @author xwc1125
     * @date 2015-7-1下午1:37:42
     */
    public static String decrypt(String ciphertext, String sKey) {
        String decryptResult = null;
        if (StringUtils.isEmpty(ciphertext)) {
            LogUtils.e(TAG, "decrypt content is null", isDebug);
            return decryptResult;
        }
        if (StringUtils.isEmpty(sKey)) {
            LogUtils.e(TAG, "decrypt key is null", isDebug);
            return decryptResult;
        }
        if (sKey.length() != 16) {
            LogUtils.e(TAG, "decrypt key length error", isDebug);
            return decryptResult;
        }
        try {
            byte[] raw = sKey.getBytes(charset);
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, skeySpec);
            byte[] encrypted1 = hex2byte(ciphertext);
            byte[] original = cipher.doFinal(encrypted1);
            decryptResult = new String(original, charset);
        } catch (Exception e) {
            LogUtils.e(TAG, e.getMessage(), isDebug);
        }
        return decryptResult;
    }

    private static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    @SuppressLint("DefaultLocale")
    private static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }
}
