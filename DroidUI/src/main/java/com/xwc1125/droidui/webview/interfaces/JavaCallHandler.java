package com.xwc1125.droidui.webview.interfaces;

/**
 * Class: JavaCallHandler<br>
 * Description: java调用js后的回调
 *
 * @author xwc1125<br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017/7/20<br>
 * @date 2017/7/20 15:50<br>
 */
public interface JavaCallHandler {

    public void OnHandler(String handlerName, String jsResponseData);
}
