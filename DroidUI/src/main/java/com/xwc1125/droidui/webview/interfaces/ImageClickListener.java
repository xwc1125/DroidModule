package com.xwc1125.droidui.webview.interfaces;

import android.webkit.JavascriptInterface;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/21  08:50 <br>
 */
public interface ImageClickListener {
    /**
     * 图片点击
     *
     * @param imgUrl
     * @param hasLink
     */
    @JavascriptInterface
    public void onImageClick(String imgUrl, String hasLink);
}
