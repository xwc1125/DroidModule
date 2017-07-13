package com.xwc1125.droidutils.handler;

import android.content.Context;
import android.os.Handler;
import android.os.Message;

import java.lang.ref.WeakReference;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/3  15:34 <br>
 */
public class WeakHandler extends Handler {

    // 弱引用 ，防止内存泄露
    private WeakReference<Context> weakReference;
    private HandlerListener listener;

    public WeakHandler(Context context, HandlerListener listener) {
        weakReference = new WeakReference<Context>(context);
        this.listener = listener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        // 通过  软引用  看能否得到activity示例
        Context context = weakReference.get();
        // 防止内存泄露
        if (context != null) {
            // 如果当前Activity，进行UI的更新
            int msgWhat = msg.what;
            Object msgObj = (Object) msg.obj;
            if (listener != null) {
                listener.onHandler(msg);
                listener.onHandler(msgWhat, msgObj);
            }
        } else {
            // 没有实例不进行操作
        }
    }

}

