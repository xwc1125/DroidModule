package com.xwc1125.droidui.webview.core;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.GeolocationPermissions;
import android.webkit.JsPromptResult;
import android.webkit.JsResult;
import android.webkit.PermissionRequest;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.xwc1125.droidui.webview.interfaces.Html5Listener;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  15:04 <br>
 */
public class Html5WebChromeClient extends WebChromeClient {
    private Context mContext;
    private Html5Listener listener;

    public Html5WebChromeClient(Context context, WebView webView) {
        this.mContext = context;
    }

    public void setListener(Html5Listener listener) {
        this.listener = listener;
    }

    /**
     * 作用：获得网页的加载进度并显示
     *
     * @param view
     * @param newProgress
     */
    @Override
    public void onProgressChanged(WebView view, int newProgress) {
        super.onProgressChanged(view, newProgress);
        if (listener != null) {
            listener.onProgressChanged(newProgress);
        }
    }

    /**
     * 作用：获取Web页中的标题
     * <p>
     * 每个网页的页面都有一个标题，比如www.baidu.com这个页面的标题即“百度一下，你就知道”，那么如何知道当前webview正在加载的页面的title并进行设置呢？
     *
     * @param view
     * @param title
     */
    @Override
    public void onReceivedTitle(WebView view, String title) {
        super.onReceivedTitle(view, title);
        if (listener != null) {
            listener.onReceivedTitle(view, title);
        }
    }

    @Override
    public void onShowCustomView(View view, int requestedOrientation, CustomViewCallback callback) {
        super.onShowCustomView(view, requestedOrientation, callback);
    }

    @Override
    public void onShowCustomView(View view,
                                 CustomViewCallback callback) {
        if (listener != null) {
            listener.onShowCustomView(view,
                    callback);
        }
    }

    @Override
    public void onHideCustomView() {
        if (listener != null) {
            listener.onHideCustomView();
        }
    }

    @Override
    public boolean onCreateWindow(WebView view, boolean isDialog, boolean isUserGesture, Message resultMsg) {
        return super.onCreateWindow(view, isDialog, isUserGesture, resultMsg);
    }

    @Override
    public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
        return super.onJsAlert(view, url, message, result);
    }

    @Override
    public boolean onJsBeforeUnload(WebView view, String url, String message, JsResult result) {
        return super.onJsBeforeUnload(view, url, message, result);
    }

    @Override
    public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
        return super.onConsoleMessage(consoleMessage);
    }

    @Override
    public boolean onJsConfirm(WebView view, String url, String message, JsResult result) {
        return super.onJsConfirm(view, url, message, result);
    }

    @Override
    public boolean onJsPrompt(WebView view, String url, String message, String defaultValue, JsPromptResult result) {
        return super.onJsPrompt(view, url, message, defaultValue, result);
    }

    @Override
    public void onGeolocationPermissionsHidePrompt() {
        super.onGeolocationPermissionsHidePrompt();
    }

    @Override
    public void onPermissionRequest(PermissionRequest request) {
        super.onPermissionRequest(request);
    }

    @Override
    public void onPermissionRequestCanceled(PermissionRequest request) {
        super.onPermissionRequestCanceled(request);
    }

    /**
     * 设置视频的默认图
     *
     * @return
     */
    @Override
    public Bitmap getDefaultVideoPoster() {
        if (listener != null) {
            return listener.onSetDefaultVideoPoster();
        }
        return null;
    }

    /**
     * 视频加载时进程loading
     *
     * @return
     */
    @SuppressLint("InflateParams")
    @Override
    public View getVideoLoadingProgressView() {
        if (listener != null) {
            return listener.onSetVideoLoadingProgressView(mContext);
        }
        return null;
    }


    @Override
    public void onGeolocationPermissionsShowPrompt(String origin,
                                                   GeolocationPermissions.Callback callback) {
        callback.invoke(origin, true, false);
    }

    @Override
    public void onCloseWindow(WebView window) {
        window.onPause();
    }

    /**
     * 打开文件
     *
     * @param webView
     * @param uploadMsg
     * @param fileChooserParams
     * @return
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
        if (listener != null) {
            return listener.onOpenFileChooser(webView, uploadMsg, fileChooserParams);
        }
        return true;
    }

}