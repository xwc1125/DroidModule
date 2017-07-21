package com.xwc1125.droidui.webview.interfaces;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;

import java.util.Map;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  16:41 <br>
 */
public abstract class Html5Listener {
    /**
     * 按键事件
     *
     * @param keyCode
     * @param event
     * @return
     */
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        return false;
    }

    //===============WebViewClient===================

    /**
     * 进度条先加载到90%,然后再加载到100%
     */
    public void startProgress() {

    }

    /**
     * 在开始加载网页时会回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    public void onPageStarted(WebView view, String url, Bitmap favicon) {

    }

    /**
     * 在页面加载结束时调用
     *
     * @param view
     * @param url
     */
    public void onPageFinished(WebView view, String url) {

    }

    /**
     * header参数
     *
     * @param url
     * @return
     */
    public Map<String, String> onPageHeaders(String url) {
        return null;
    }

    /**
     * 拦截访问的url
     *
     * @param view
     * @param url
     * @param additionalHttpHeaders
     * @return
     */
    public boolean onInterceptUrl(WebView view, String url, Map<String, String> additionalHttpHeaders) {
        return false;
    }

    /**
     * 加载页面的服务器出现错误时（如404）调用
     *
     * @param view
     * @param request
     * @param error
     */
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {

    }

    /**
     * 加载页面的服务器出现错误时（如404）调用
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {

    }

    /**
     * 当接收到https错误时，会回调此函数，在其中可以做错误处理
     *
     * @param view
     * @param handler
     * @param error
     */
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {

    }

    /**
     * 隐藏进度条
     */
    public void onStopProgress() {

    }
    //===============WebViewClient[end]===================

    //===============WebChromeClient===================

    /**
     * 进度条变化时调用
     */
    public void onProgressChanged(int newProgress) {

    }

    /**
     * 返回title
     *
     * @param view
     * @param title
     */
    public void onReceivedTitle(WebView view, String title) {
    }

    public void onShowCustomView(View view, int requestedOrientation, WebChromeClient.CustomViewCallback callback) {

    }

    /**
     * 显示自定义的view
     *
     * @param view
     * @param callback
     */
    public void onShowCustomView(View view, WebChromeClient.CustomViewCallback callback) {
    }

    /**
     * 隐藏自定义view
     */
    public void onHideCustomView() {
    }

    /**
     * 设置视频的默认图
     *
     * @return
     */
    public Bitmap onSetDefaultVideoPoster() {
        return null;
    }

    /**
     * 设置video的加载进度view
     *
     * @param context
     * @return
     */
    public View onSetVideoLoadingProgressView(Context context) {
        return null;
    }

    /**
     * 打开文件
     * <p>
     * 可以调用Html5Utils
     *
     * @param webView
     * @param uploadMsg
     * @param fileChooserParams
     * @return
     */
    public boolean onOpenFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, WebChromeClient.FileChooserParams fileChooserParams) {
        return true;
    }
    //===============WebChromeClient[end]===================

}
