package com.xwc1125.droidui.webview.interfaces;

import android.webkit.JavascriptInterface;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/21  08:51 <br>
 */
public interface TextClickListener {
    /**
     * 文本点击
     *
     * @param type
     * @param item_pk
     */
    @JavascriptInterface
    public void onTextClick(String type, String item_pk);
}
