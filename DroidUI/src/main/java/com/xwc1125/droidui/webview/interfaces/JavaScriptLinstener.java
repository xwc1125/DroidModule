package com.xwc1125.droidui.webview.interfaces;

import com.xwc1125.droidui.webview.Html5WebView;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/21  09:01 <br>
 */
public interface JavaScriptLinstener {
    /**
     * 注入js
     * <p>
     * webView.addJavascriptInterface(类对象,类的别名);
     */
    void onInjectJavaScript(Html5WebView webView);
}
