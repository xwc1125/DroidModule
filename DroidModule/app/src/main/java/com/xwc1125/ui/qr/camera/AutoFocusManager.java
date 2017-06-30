package com.xwc1125.ui.qr.camera;

import android.hardware.Camera;
import android.os.AsyncTask;
import android.util.Log;

import com.xwc1125.ui.qr.common.Runnable;

import com.xwc1125.ui.qr.config.QRConfig;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Class: com.xwc1125.ui.qr.camera <br>
 * Description: 由于对焦不是一次性完成的任务（手抖），而系统提供的对焦仅有Camera.autoFocus()方法，
 * 因此需要一个线程来不断调用Camera.autoFocus()直到用户满意按下快门为止
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/10  17:21 <br>
 */
public class AutoFocusManager implements Camera.AutoFocusCallback {
    private final static String TAG = AutoFocusManager.class.getName();
    private static boolean isDebug = QRConfig.isDebug;

    //===========配置信息============
    private static final long AUTO_FOCUS_INTERVAL_MS = QRConfig.AUTO_FOCUS_INTERVAL_MS;//自动对焦的时间间隔，毫秒
    private final Camera camera;
    private final boolean isUseAutoFocus;//是否使用自动对焦

    private static final Collection<String> FOCUS_MODES_CALLING_AF;

    static {
        FOCUS_MODES_CALLING_AF = new ArrayList<String>(2);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_AUTO);
        FOCUS_MODES_CALLING_AF.add(Camera.Parameters.FOCUS_MODE_MACRO);
    }

    private AsyncTask<?, ?, ?> outstandingTask;//自动对焦的任务
    private boolean isTaskActive;//是否任务正在进行中

    /**
     * 构造函数
     *
     * @param camera
     */
    public AutoFocusManager(Camera camera) {
        this.camera = camera;
        String currentFocusMode = camera.getParameters().getFocusMode();
        // 自动对焦
        isUseAutoFocus = true && FOCUS_MODES_CALLING_AF.contains(currentFocusMode);
        start();
    }

    @Override
    public synchronized void onAutoFocus(boolean success, Camera camera) {
        if (isTaskActive) {
            outstandingTask = new AutoFocusTask();
            try {
                //此处需要进行低版本的兼容
                Runnable.execAsync(outstandingTask);
            } catch (RuntimeException re) {
                if (isDebug) {
                    Log.w(TAG, "Can not use the AutoFocus:", re);
                }
            }
        }
    }

    /**
     * 启动对焦
     */
    synchronized void start() {
        if (isUseAutoFocus) {
            isTaskActive = true;
            camera.autoFocus(this);
        }
    }

    /**
     * 停止对焦
     */
    synchronized void stop() {
        if (isUseAutoFocus) {
            try {
                camera.cancelAutoFocus();
            } catch (RuntimeException re) {
                if (isDebug) {
                    Log.w(TAG, "cancel the autoFocus", re);
                }
            }
        }
        if (outstandingTask != null) {
            outstandingTask.cancel(true);
            outstandingTask = null;
        }
        isTaskActive = false;
    }

    /**
     * 自动对焦的任务
     */
    private final class AutoFocusTask extends AsyncTask<Object, Object, Object> {
        @Override
        protected Object doInBackground(Object... voids) {
            try {
                //睡眠
                Thread.sleep(AUTO_FOCUS_INTERVAL_MS);
            } catch (InterruptedException e) {
                // continue
            }
            synchronized (AutoFocusManager.this) {
                if (isTaskActive) {
                    start();
                }
            }
            return null;
        }
    }
}
