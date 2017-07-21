package com.xwc1125.droidui.webview.interfaces;

/**
 * Class: WebViewJavascriptBridge<br>
 * Description: <br>
 *
 * @author xwc1125<br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017/7/19<br>
 * @date 2017/7/19 16:50<br>
 */
public interface Html5JavascriptBridge {

    public void send(String data);

    public void send(String data, ResponseCallback responseCallback);

}
