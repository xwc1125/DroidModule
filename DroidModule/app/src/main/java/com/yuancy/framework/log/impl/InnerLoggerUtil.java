package com.yuancy.framework.log.impl;

/**
 * Class: com.yuancy.framework.log.impl <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/22  18:10 <br>
 */
public class InnerLoggerUtil {
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
}
