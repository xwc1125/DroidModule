package com.xwc1125.ui.qr.common;

import android.os.AsyncTask;
import android.os.Build;

/**
 * Class: com.xwc1125.ui.qr.common <br>
 * Description: 兼容低版本的子线程开启任务
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/10  17:44 <br>
 */
public class Runnable {
    public static void execAsync(AsyncTask<?, ?, ?> task) {
        if (Build.VERSION.SDK_INT >= 11) {
            task.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
        } else {
            task.execute();
        }

    }
}
