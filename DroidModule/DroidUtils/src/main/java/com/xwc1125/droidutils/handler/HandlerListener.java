package com.xwc1125.droidutils.handler;

import android.os.Message;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/24  16:26 <br>
 */
public interface HandlerListener {
    void onHandler(Message msg);

    void onHandler(int what, Object obj);
}
