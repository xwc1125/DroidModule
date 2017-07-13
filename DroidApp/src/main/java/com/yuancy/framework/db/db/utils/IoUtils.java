package com.yuancy.framework.db.db.utils;

import android.database.Cursor;

import org.xutils.common.util.LogUtil;

import java.io.Closeable;

/**
 * Class: com.yuancy.framework.db.db.utils <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/22  18:50 <br>
 */
public class IoUtils {
    private IoUtils() {
    }

    public static void closeQuietly(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Throwable ignored) {
                LogUtil.d(ignored.getMessage(), ignored);
            }
        }
    }

    public static void closeQuietly(Cursor cursor) {
        if (cursor != null) {
            try {
                cursor.close();
            } catch (Throwable ignored) {
                LogUtil.d(ignored.getMessage(), ignored);
            }
        }
    }
}
