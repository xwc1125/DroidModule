package com.xwc1125.droidui.webview.interfaces;


import com.xwc1125.droidui.webview.interfaces.ResponseCallback;

/**
 * Class description
 *
 * @author YEZHENNAN220
 * @date 2016-07-08 16:24
 */
public interface JsHandler {

    public void OnHandler(String handlerName, String responseData, ResponseCallback function);

}
