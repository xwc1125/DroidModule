package com.xwc1125.droidui.webview.interfaces;

public class DefaultHandler implements BridgeHandler {

    @Override
    public void handler(String data, ResponseCallback function) {
        if (function != null) {
            function.onCallBack("DefaultHandler response data");
        }
    }

}
