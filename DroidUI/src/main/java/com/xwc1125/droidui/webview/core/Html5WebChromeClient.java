package com.xwc1125.droidui.webview.core;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.view.View;
import android.webkit.GeolocationPermissions;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

import com.xwc1125.droidui.webview.Html5Linstener;

import static android.app.Activity.RESULT_OK;

/**
 * Class: com.ugchain.wallet.ui.webView <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  15:04 <br>
 */
public class Html5WebChromeClient extends WebChromeClient {
    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;
    public static int FILECHOOSER_RESULTCODE = 1;
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private Bitmap mDefaultVideoPoster;//视频的默认图
    private Context mContext;
    private Html5Linstener linstener;

    public Html5WebChromeClient(Context context, WebView webView) {
        this.mContext = context;
    }

    public void setLinstener(Html5Linstener linstener) {
        this.linstener = linstener;
    }

    @Override
    public void onShowCustomView(View view,
                                 CustomViewCallback callback) {
        if (linstener != null) {
            linstener.onShowCustomView(view,
                    callback);
        }
    }

    @Override
    public void onHideCustomView() {
        if (linstener != null) {
            linstener.onHideCustomView();
        }
    }

    @Override
    public Bitmap getDefaultVideoPoster() {
        if (mDefaultVideoPoster != null) {
            return mDefaultVideoPoster;
        } else {
            return null;
        }
    }

    /**
     * 设置视频的默认图
     *
     * @param DefaultVideoPoster
     */
    public void setDefaultVideoPoster(Bitmap DefaultVideoPoster) {
        this.mDefaultVideoPoster = DefaultVideoPoster;
    }

    // 视频加载时进程loading
    @SuppressLint("InflateParams")
    @Override
    public View getVideoLoadingProgressView() {
        if (linstener != null) {
            return linstener.setVideoLoadingProgressView(mContext);
        }
        return null;
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
        if (linstener != null) {
            linstener.onReceivedTitle(view, title);
        }
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
        if (linstener != null) {
            linstener.onProgressChanged(newProgress);
        }
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

    //=======================扩展浏览器上传文件===========================

    /**
     * 打开文件
     * 3.0++版本
     */
    public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooserImpl(uploadMsg, acceptType);
    }

    /**
     * 打开文件
     * 3.0--版本
     */
    public void openImageChooser(ValueCallback<Uri> uploadMsg) {
        openFileChooserImpl(uploadMsg, "image/*");
    }

    /**
     * 打开文件
     * For Android > 5.0
     */
    @Override
    public boolean onShowFileChooser(WebView webView, ValueCallback<Uri[]> uploadMsg, FileChooserParams fileChooserParams) {
        openFileChooserImplForAndroid5(uploadMsg);
        return true;
    }

    /**
     * 图片选择 Android 3.0++
     *
     * @param uploadMsg
     */
    private void openFileChooserImpl(ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(acceptType);
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
        }
    }

    /**
     * 图片选择 Android > 5.0
     *
     * @param uploadMsg
     */
    private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");
        if (mContext instanceof Activity) {
            ((Activity) mContext).startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
        }
    }

    /**
     * 5.0以下 上传图片成功后的回调
     */

    public void mUploadMessage(Intent intent, int resultCode) {
        if (null == mUploadMessage) {
            return;
        }
        Uri result = intent == null || resultCode != RESULT_OK ? null : intent.getData();
        mUploadMessage.onReceiveValue(result);
        mUploadMessage = null;
    }

    /**
     * 5.0以上 上传图片成功后的回调
     */
    public void mUploadMessageForAndroid5(Intent intent, int resultCode) {
        if (null == mUploadMessageForAndroid5) {
            return;
        }
        Uri result = (intent == null || resultCode != RESULT_OK) ? null : intent.getData();
        if (result != null) {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{result});
        } else {
            mUploadMessageForAndroid5.onReceiveValue(new Uri[]{});
        }
        mUploadMessageForAndroid5 = null;
    }
}