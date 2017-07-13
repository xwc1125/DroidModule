package com.xwc1125.droidui.webview;

import android.content.Context;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

/**
 * Class: com.ugchain.wallet.ui.webView <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  16:41 <br>
 */
public abstract class Html5Linstener {
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

    /**
     * 隐藏进度条
     */
    public void hindProgressBar() {

    }

    /**
     * 显示webview
     */
    public void showWebView() {

    }

    /**
     * 隐藏webview
     */
    public void hindWebView() {

    }

    /**
     * 进度条先加载到90%,然后再加载到100%
     */
    public void startProgress() {

    }


    /**
     * 进度条变化时调用
     */
    public void onProgressChanged(int newProgress) {

    }

    /**
     * 播放网络视频全屏调用
     */
    public void fullViewAddView(View view) {

    }

    /**
     * 显示全屏视频
     */
    public void showVideoFullView() {

    }

    /**
     * 隐藏全屏视频
     */
    public void hindVideoFullView() {

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
     * 设置video的加载进度view
     *
     * @param context
     * @return
     */
    public View setVideoLoadingProgressView(Context context) {
        return null;
    }

    /**
     * 图片点击
     *
     * @param imgUrl
     * @param hasLink
     */
    @JavascriptInterface
    public void onImageClick(String imgUrl, String hasLink) {
    }

    /**
     * 文本点击
     *
     * @param type
     * @param item_pk
     */
    @JavascriptInterface
    public void onTextClick(String type, String item_pk) {
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
     * 打开网页时调用
     *
     * @param view
     * @param url
     */
    public void loadingUrl(WebView view, String url) {

    }

    /**
     * 返回title
     *
     * @param view
     * @param title
     */
    public void onReceivedTitle(WebView view, String title) {
    }
}
