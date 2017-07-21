package com.xwc1125.droidui.webview.config;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/19  17:10 <br>
 */
public class Html5Config {

    public final static String DROID_OVERRIDE_SCHEMA_NAME = "droid";
    public final static String DROID_OVERRIDE_SCHEMA = "droid://";//url的schema
    public final static String DROID_RETURN_DATA = DROID_OVERRIDE_SCHEMA + "return/";//数据返回的格式为   droid://return/{function}/returncontent

    public final static String DROID_FETCH_QUEUE = DROID_RETURN_DATA + "_fetchQueue/";
    public final static String EMPTY_STR = "";
    public final static String UNDERLINE_STR = "_";
    public final static String SPLIT_MARK = "/";

    public final static String CALLBACK_ID_FORMAT = "JAVA_CB_%s";//java调用
    public final static String JS_HANDLE_MESSAGE_FROM_JAVA = "javascript:WebViewJavascriptBridge._handleMessageFromNative('%s');";
    public final static String JS_FETCH_QUEUE_FROM_JAVA = "javascript:WebViewJavascriptBridge._fetchQueue();";
    public final static String JAVASCRIPT_STR = "javascript:";
    public final static String JAVASCRIPT_BRIDGE="WebViewJavascriptBridge";

}
