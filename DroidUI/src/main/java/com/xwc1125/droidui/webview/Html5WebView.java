package com.xwc1125.droidui.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Looper;
import android.os.SystemClock;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xwc1125.droidui.webview.config.Html5Config;
import com.xwc1125.droidui.webview.core.Html5WebChromeClient;
import com.xwc1125.droidui.webview.core.Html5WebViewClient;
import com.xwc1125.droidui.webview.interfaces.Html5Listener;
import com.xwc1125.droidui.webview.interfaces.ImageClickListener;
import com.xwc1125.droidui.webview.interfaces.JavaScriptLinstener;
import com.xwc1125.droidui.webview.interfaces.TextClickListener;
import com.xwc1125.droidui.webview.interfaces.JavaCallHandler;
import com.xwc1125.droidui.webview.interfaces.JsHandler;
import com.xwc1125.droidui.webview.utils.Html5WebViewUtils;
import com.xwc1125.droidui.webview.entity.Message;
import com.xwc1125.droidui.webview.interfaces.BridgeHandler;
import com.xwc1125.droidui.webview.interfaces.ResponseCallback;
import com.xwc1125.droidui.webview.interfaces.DefaultHandler;
import com.xwc1125.droidui.webview.interfaces.Html5JavascriptBridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description: TODO <br>
 * <p>
 * 使用方法：
 * 防止内存泄漏，webview的使用方法<br>
 * //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()<br>
 * LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);<br>
 * webView = new Html5WebView(getApplicationContext());<br>
 * webView.setLayoutParams(params);<br>
 * mLayout.addView(webView);<br>
 * webView.loadUrl("http://www.baidu.com");<br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  14:54 <br>
 */
public class Html5WebView extends WebView implements Html5JavascriptBridge {
    private Context mContext;
    private Html5WebChromeClient html5WebChromeClient;
    private Html5WebViewClient html5WebViewClient;
    private WebSettings webSettings;//webSettings


    private Html5Listener listener;//监听
    private ImageClickListener imageClickListener;//图片的点击监听
    private TextClickListener textClickListener;//文本的点击监听
    private JavaScriptLinstener javaScriptLinstener;//注入js

    //====================添加javascript==============
    private long uniqueId = 0;//唯一ID
    Map<String, ResponseCallback> responseCallbacks = new HashMap<String, ResponseCallback>();
    /**
     * 被注册的java方法集合，以至于可以让js调用
     */
    Map<String, BridgeHandler> messageHandlers = new HashMap<String, BridgeHandler>();
    BridgeHandler defaultHandler = new DefaultHandler();
    private List<Message> startupMessage = new ArrayList<Message>();

    public List<Message> getStartupMessage() {
        return startupMessage;
    }

    public void setStartupMessage(List<Message> startupMessage) {
        this.startupMessage = startupMessage;
    }

    public Html5WebView(Context context) {
        super(context);
        init(context);
    }

    public Html5WebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    public Html5WebView(Context context, AttributeSet attrs, int defStyleAttr, boolean privateBrowsing) {
        super(context, attrs, defStyleAttr, privateBrowsing);
        init(context);
    }


    @SuppressLint("JavascriptInterface")
    private void init(Context context) {
        this.mContext = context;
        webSettings = Html5WebViewUtils.getWebSettings(this);
        html5WebChromeClient = new Html5WebChromeClient(mContext, this);
        this.setWebChromeClient(html5WebChromeClient);
        html5WebViewClient = new Html5WebViewClient();
        this.setWebViewClient(html5WebViewClient);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (listener != null) {
            return listener.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public WebSettings getHtml5Settings() {
        return webSettings;
    }


    //==========================监听=======================

    /**
     * 设置监听
     *
     * @param listener
     */
    public void setListener(Html5Listener listener) {
        this.listener = listener;
        html5WebChromeClient.setListener(listener);
        html5WebViewClient.setListener(this, listener);
    }

    /**
     * 设置图片的点击监听
     *
     * @param imageClickListener
     */
    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
        html5WebViewClient.setImageClickListener(imageClickListener);
    }

    /**
     * 设计文本的点击监听
     *
     * @param textClickListener
     */
    public void setTextClickListener(TextClickListener textClickListener) {
        this.textClickListener = textClickListener;
        html5WebViewClient.setTextClickListener(textClickListener);
    }

    /**
     * 获取WebChromeClient
     *
     * @return
     */
    public Html5WebChromeClient getHtml5WebChromeClient() {
        return html5WebChromeClient;
    }

    /**
     * 设置WebChromeClient
     *
     * @param html5WebChromeClient
     */
    public void setHtml5WebChromeClient(Html5WebChromeClient html5WebChromeClient) {
        this.html5WebChromeClient = html5WebChromeClient;
    }

    /**
     * 获取WebViewClient
     *
     * @return
     */
    public Html5WebViewClient getHtml5WebViewClient() {
        return html5WebViewClient;
    }

    /**
     * 设置WebViewClient
     *
     * @param html5WebViewClient
     */
    public void setHtml5WebViewClient(Html5WebViewClient html5WebViewClient) {
        this.html5WebViewClient = html5WebViewClient;
    }

    /**
     * 打开URL
     *
     * @param url
     */
    public void loadUrl(String url) {
        super.loadUrl(url);
        html5WebChromeClient.setListener(listener);
        html5WebViewClient.setListener(this, listener);
    }

    /**
     * 打开URL
     *
     * @param url
     * @param additionalHttpHeaders
     */
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders) {
        super.loadUrl(url, additionalHttpHeaders);
        html5WebChromeClient.setListener(listener);
        html5WebViewClient.setListener(this, listener);
    }

    /**
     * 打开URL
     *
     * @param url
     * @param responseCallback
     */
    public void loadUrl(String url, ResponseCallback responseCallback) {
        loadUrl(url, null, responseCallback);
    }

    /**
     * 打开URL
     *
     * @param url
     * @param additionalHttpHeaders
     * @param responseCallback
     */
    public void loadUrl(String url, Map<String, String> additionalHttpHeaders, ResponseCallback responseCallback) {
        if (url.startsWith("file:") || additionalHttpHeaders == null) {
            loadUrl(url);
        } else {
            loadUrl(url, additionalHttpHeaders);
        }
        if (responseCallback == null) {
            return;
        }
        responseCallbacks.put(Html5WebViewUtils.parseFunctionName(url), responseCallback);
    }

    /**
     * 注册handler，让js能够调用
     *
     * @param handlerName 方法名称
     * @param handler     回调接口
     */
    public void registerHandler(String handlerName, BridgeHandler handler) {
        if (handler != null) {
            messageHandlers.put(handlerName, handler);
        }
    }

    /**
     * 批量注册本地java方法，以供js端调用
     *
     * @param handlerNames 方法名称数组
     * @param handler      回调接口
     */
    public void registerHandlers(final ArrayList<String> handlerNames, final JsHandler handler) {
        if (handler != null) {
            for (final String handlerName : handlerNames) {
                this.registerHandler(handlerName, new BridgeHandler() {
                    @Override
                    public void handler(String data, ResponseCallback responseCallback) {
                        handler.OnHandler(handlerName, data, responseCallback);
                    }
                });
            }
        }
    }

    /**
     * 唤起注册好的方法
     *
     * @param handlerName 已经注册好的方法名称
     * @param javaData    本地端传递给js端的参数，json字符串
     * @param callBack
     */
    public void callJsHandler(String handlerName, String javaData, ResponseCallback callBack) {
        doSend(handlerName, javaData, callBack);
    }

    /**
     * 调用js端已经注册好的方法
     *
     * @param handlerName 已经注册好的方法名称
     * @param javaData    本地端传递给js端的参数，json字符串
     * @param handler     回调接口
     */
    public void callJsHandler(final String handlerName, String javaData, final JavaCallHandler handler) {
        this.callJsHandler(handlerName, javaData, new ResponseCallback() {
            @Override
            public void onCallBack(String data) {
                if (handler != null) {
                    handler.OnHandler(handlerName, data);
                }
            }
        });
    }

    /**
     * 批量调用js端已经注册好的方法
     *
     * @param handlerInfos 方法名称与参数的map，方法名为key
     * @param handler      回调接口
     */
    public void callJsHandler(final Map<String, String> handlerInfos, final JavaCallHandler handler) {
        if (handler != null) {
            for (final Map.Entry<String, String> entry : handlerInfos.entrySet()) {
                this.callJsHandler(entry.getKey(), entry.getValue(), new ResponseCallback() {
                    @Override
                    public void onCallBack(String data) {
                        handler.OnHandler(entry.getKey(), data);
                    }
                });
            }
        }
    }

    /**
     * 发送java信息到js
     *
     * @param javaData
     */
    @Override
    public void send(String javaData) {
        send(javaData, null);
    }

    /**
     * 发送java信息到js
     *
     * @param javaData
     * @param responseCallback
     */
    @Override
    public void send(String javaData, ResponseCallback responseCallback) {
        doSend(null, javaData, responseCallback);
    }

    /**
     * 发送java信息到js
     *
     * @param handlerName      已经注册好的方法名称
     * @param javaData         本地端传递给js端的参数，json字符串
     * @param responseCallback
     */
    private void doSend(String handlerName, String javaData, ResponseCallback responseCallback) {
        Message m = new Message();
        if (!TextUtils.isEmpty(javaData)) {
            m.setData(javaData);
        }
        if (responseCallback != null) {
            String callbackStr = String.format(Html5Config.CALLBACK_ID_FORMAT,
                    ++uniqueId + (Html5Config.UNDERLINE_STR + SystemClock.currentThreadTimeMillis()));
            responseCallbacks.put(callbackStr, responseCallback);
            m.setRequestId(callbackStr);
        }
        if (!TextUtils.isEmpty(handlerName)) {
            m.setHandlerName(handlerName);
        }
        queueMessage(m);
    }

    /**
     * 队列信息
     *
     * @param m
     */
    private void queueMessage(Message m) {
        if (startupMessage != null) {
            startupMessage.add(m);
        } else {
            dispatchMessage(m);
        }
    }

    /**
     * 网页返回数据
     *
     * @param url
     */
    public void handlerResponseData(String url) {
        String functionName = Html5WebViewUtils.getFunctionFromReturnUrl(url);
        ResponseCallback f = responseCallbacks.get(functionName);
        String data = Html5WebViewUtils.getDataFromReturnUrl(url);
        if (f != null) {
            f.onCallBack(data);
            responseCallbacks.remove(functionName);
            return;
        }
    }

    /**
     * 分发信息到JS上
     *
     * @param m
     */
    public void dispatchMessage(Message m) {
        String messageJson = m.toJson();
        //escape special characters for json string
        messageJson = messageJson.replaceAll("(\\\\)([^utrn])", "\\\\\\\\$1$2");
        messageJson = messageJson.replaceAll("(?<=[^\\\\])(\")", "\\\\\"");
        String javascriptCommand = String.format(Html5Config.JS_HANDLE_MESSAGE_FROM_JAVA, messageJson);
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            this.loadUrl(javascriptCommand);
        }
    }

    /**
     * 清空信息队列
     */
    public void flushMessageQueue() {
        if (Thread.currentThread() == Looper.getMainLooper().getThread()) {
            loadUrl(Html5Config.JS_FETCH_QUEUE_FROM_JAVA, new ResponseCallback() {

                @Override
                public void onCallBack(String data) {
                    // deserializeMessage
                    List<Message> list = null;
                    try {
                        list = Message.toArrayList(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                    if (list == null || list.size() == 0) {
                        return;
                    }
                    for (int i = 0; i < list.size(); i++) {
                        Message m = list.get(i);
                        String responseId = m.getResponseId();
                        // 是否是response
                        if (!TextUtils.isEmpty(responseId)) {
                            ResponseCallback function = responseCallbacks.get(responseId);
                            String responseData = m.getResponseData();
                            function.onCallBack(responseData);
                            responseCallbacks.remove(responseId);
                        } else {
                            ResponseCallback responseFunction = null;
                            // if had callbackId
                            final String callbackId = m.getRequestId();
                            if (!TextUtils.isEmpty(callbackId)) {
                                responseFunction = new ResponseCallback() {
                                    @Override
                                    public void onCallBack(String data) {
                                        Message responseMsg = new Message();
                                        responseMsg.setResponseId(callbackId);
                                        responseMsg.setResponseData(data);
                                        queueMessage(responseMsg);
                                    }
                                };
                            } else {
                                responseFunction = new ResponseCallback() {
                                    @Override
                                    public void onCallBack(String data) {
                                        // do nothing
                                    }
                                };
                            }
                            BridgeHandler handler;
                            if (!TextUtils.isEmpty(m.getHandlerName())) {
                                handler = messageHandlers.get(m.getHandlerName());
                            } else {
                                handler = defaultHandler;
                            }
                            if (handler != null) {
                                handler.handler(m.getData(), responseFunction);
                            }
                        }
                    }
                }
            });
        }
    }

}
