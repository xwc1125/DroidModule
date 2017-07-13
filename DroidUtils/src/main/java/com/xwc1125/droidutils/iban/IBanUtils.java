package com.xwc1125.droidutils.iban;

import java.math.BigInteger;

/**
 * Created by fanjl on 2017/6/28.
 */
public class IBanUtils extends BaseFunc {

    private static final String HEX_PREFIX = "0x";
    /**
     * 地址转成iban
     *
     * @param address
     * @return
     */
    public static String fromAddress(String address) {
        address = cleanHexPrefix(address);
        BigInteger asBn = new BigInteger(address, 16);
        String base36 = asBn.toString(36);
        String padded = padLeft(base36, 15);
        return fromBban(padded.toUpperCase());
    }

    private static String fromBban(String bban) {
        String countryCode = "XE";
        int remainder = Modulo97.checksum((countryCode + "00" + bban) + "");
        String checkDigit = 98 - remainder + "";
        return countryCode + checkDigit + bban;
    }

    /**
     * 判断是否是iban值
     *
     * @param iban
     * @return
     */
    private static boolean isValid(String iban) {
        return Modulo97.verifyCheckDigits(iban);
    }

    /**
     * iban转回地址
     *
     * @param iban
     * @return
     */
    public static String toAddress(String iban) throws Exception {
        if (isValid(iban)) {
            String base36 = iban.substring(4);
            BigInteger asBn = new BigInteger(base36, 36);
            String address = padLeft(asBn.toString(16), 20);
            if (address != null && !address.startsWith("0x") && !address.startsWith("0X")) {
                address = "0x" + address;
            }
            return address;
        } else {
            throw new Exception("invalid iban address:" + iban);
        }
    }

    public static String cleanHexPrefix(String input) {
        if (containsHexPrefix(input)) {
            return input.substring(2);
        } else {
            return input;
        }
    }

    public static String prependHexPrefix(String input) {
        if (!containsHexPrefix(input)) {
            return HEX_PREFIX + input;
        } else {
            return input;
        }
    }

    public static boolean containsHexPrefix(String input) {
        return input.length() > 1 && input.charAt(0) == '0' && input.charAt(1) == 'x';
    }

    public static void main(String[] args) {
        System.out.println(fromAddress("ac17961144fcf098f8d7ee37de9f16a57c5189ea"));
        try {
            System.out.println(toAddress("XE21K3OK0LQEZQNBED6KAYUXXPX1EEXC7HM"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
