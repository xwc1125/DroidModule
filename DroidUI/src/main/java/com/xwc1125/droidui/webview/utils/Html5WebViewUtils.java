package com.xwc1125.droidui.webview.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.webkit.ValueCallback;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.xwc1125.droidui.webview.config.Html5Config;
import com.xwc1125.droidui.webview.config.JsConfig;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static android.app.Activity.RESULT_OK;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/31  09:47 <br>
 */
public class Html5WebViewUtils {
    /**
     * 获取默认的配置信息
     *
     * @param webView
     * @return
     */
    public static WebSettings getWebSettings(WebView webView) {
        // Configure the webview
        WebSettings ws = webView.getSettings();

        //设置自适应屏幕
        ws.setLoadWithOverviewMode(true);// 网页内容的宽度是否可大于WebView控件的宽度,缩放至屏幕的大小
        ws.setUseWideViewPort(true);// 设置此属性，可任意比例缩放。将图片调整到适合webview的大小

        // 保存表单数据
        ws.setSaveFormData(true);

        //缩放操作
        ws.setSupportZoom(true);// 是否应该支持使用其屏幕缩放控件和手势缩放
        ws.setBuiltInZoomControls(true);//设置内置的缩放控件。若为false，则该WebView不可缩放
        ws.setDisplayZoomControls(false);//隐藏原生的缩放控件

        //缓存模式如下：
        //LOAD_CACHE_ONLY: 不使用网络，只读取本地缓存数据
        //LOAD_DEFAULT: （默认）根据cache-control决定是否从网络上取数据。
        //LOAD_NO_CACHE: 不使用缓存，只从网络获取数据.
        //LOAD_CACHE_ELSE_NETWORK，只要本地有，无论是否过期，或者no-cache，都使用缓存中的数据。
        ws.setCacheMode(WebSettings.LOAD_DEFAULT);// 设置缓存模式
        ws.setAllowFileAccess(true); //设置可以访问文件
        // setDefaultZoom  api19被弃用
        // ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // ws.setSavePassword(true);
        // ws.setSaveFormData(true);
        // enable navigator.geolocation
        // ws.setGeolocationEnabled(true);
        // ws.setGeolocationDatabasePath("/data/data/com.zzxapp.miscall/databases/");

        // 缩放比例 1
        webView.setInitialScale(1);
        // 告诉WebView启用JavaScript执行。默认的是false。
        ws.setJavaScriptEnabled(true);
        //  页面加载好以后，再放开图片
        ws.setBlockNetworkImage(false);

        // 排版适应屏幕
        ws.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        // WebView是否支持多个窗口。
        ws.setSupportMultipleWindows(true);
        ws.setJavaScriptCanOpenWindowsAutomatically(true);//支持通过JS打开新窗口
        ws.setLoadsImagesAutomatically(true); //支持自动加载图片
        ws.setDefaultTextEncodingName("utf-8");//设置编码格式

        // 使用localStorage则必须打开,不使用缓存
        ws.setDomStorageEnabled(true); // 开启 DOM storage API 功能
        ws.setDatabaseEnabled(true);   //开启 database storage API 功能
        ws.setAppCacheEnabled(true);//开启 Application Caches 功能
        //String cacheDirPath = getFilesDir().getAbsolutePath() + APP_CACAHE_DIRNAME;
        //webSettings.setAppCachePath(cacheDirPath); //设置  Application Caches 缓存目录

        // webview从5.0开始默认不允许混合模式,https中不能加载http资源,需要设置开启。
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ws.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        /** 设置字体默认缩放大小(改变网页字体大小,setTextSize  api14被弃用)*/
        ws.setTextZoom(100);

        return ws;
    }

    /**
     * 从url中获取函数名
     */
    public static String parseFunctionName(String jsUrl) {
        return jsUrl.replace("javascript:" + Html5Config.JAVASCRIPT_BRIDGE + ".", "").replaceAll("\\(.*\\);", "");
    }

    /**
     * 数据返回url中获取数据
     */
    public static String getDataFromReturnUrl(String url) {
        if (url.startsWith(Html5Config.DROID_FETCH_QUEUE)) {
            return url.replace(Html5Config.DROID_FETCH_QUEUE, Html5Config.EMPTY_STR);
        }

        String temp = url.replace(Html5Config.DROID_RETURN_DATA, Html5Config.EMPTY_STR);
        String[] functionAndData = temp.split(Html5Config.SPLIT_MARK);

        if (functionAndData.length >= 2) {
            StringBuilder sb = new StringBuilder();
            for (int i = 1; i < functionAndData.length; i++) {
                sb.append(functionAndData[i]);
            }
            return sb.toString();
        }
        return null;
    }

    /**
     * 数据返回url中获取函数名
     */
    public static String getFunctionFromReturnUrl(String url) {
        String temp = url.replace(Html5Config.DROID_RETURN_DATA, Html5Config.EMPTY_STR);
        String[] functionAndData = temp.split(Html5Config.SPLIT_MARK);
        if (functionAndData.length >= 1) {
            return functionAndData[0];
        }
        return null;
    }


    /**
     * js 文件将注入为第一个script引用
     *
     * @param view
     * @param url
     */
    public static void webViewLoadJs(WebView view, String url) {
        String js = "var newscript = document.createElement(\"script\");";
        js += "newscript.src=\"" + url + "\";";
        js += "document.scripts[0].parentNode.insertBefore(newscript,document.scripts[0]);";
        view.loadUrl("javascript:" + js);
    }

    /**
     * 向webview中注入js
     */
    public static void webViewLoadLocalJs(WebView view) {
        String jsContent = JsConfig.WEB_JAVASCRIPT_BRIDGE;
        view.loadUrl("javascript:" + jsContent);
    }

    /**
     * 向webview中注入js
     */
    public static void injectJsFromAsset(WebView view, String path) {
        String jsContent = assetFile2Str(view.getContext(), path);
        view.loadUrl("javascript:" + jsContent);
    }


    /**
     * 将asset内的js转换成String
     *
     * @param context
     * @param urlStr
     * @return
     */
    private static String assetFile2Str(Context context, String urlStr) {
        InputStream in = null;
        try {
            in = context.getAssets().open(urlStr);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line = null;
            StringBuilder sb = new StringBuilder();
            do {
                line = bufferedReader.readLine();
                if (line != null && !line.matches("^\\s*\\/\\/.*")) {
                    sb.append(line);
                }
            } while (line != null);

            bufferedReader.close();
            in.close();

            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                }
            }
        }
        return null;
    }

    //=======================扩展浏览器上传文件===========================


    /**
     * 打开文件
     * 3.0++版本
     */
    public void openFileChooser(Activity activity, ValueCallback<Uri> uploadMsg, String acceptType) {
        openFileChooserImpl(activity, uploadMsg, acceptType);
    }

    /**
     * 打开文件
     * 3.0--版本
     */
    public void openImageChooser(Activity activity, ValueCallback<Uri> uploadMsg) {
        openFileChooserImpl(activity, uploadMsg, "image/*");
    }

    private ValueCallback<Uri> mUploadMessage;
    private ValueCallback<Uri[]> mUploadMessageForAndroid5;

    public static int FILECHOOSER_RESULTCODE = 1;
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;

    /**
     * 图片选择 Android 3.0++
     *
     * @param uploadMsg
     */
    private void openFileChooserImpl(Activity activity, ValueCallback<Uri> uploadMsg, String acceptType) {
        mUploadMessage = uploadMsg;
        Intent i = new Intent(Intent.ACTION_GET_CONTENT);
        i.addCategory(Intent.CATEGORY_OPENABLE);
        i.setType(acceptType);
        activity.startActivityForResult(Intent.createChooser(i, "文件选择"), FILECHOOSER_RESULTCODE);
    }

    /**
     * 图片选择 Android > 5.0
     *
     * @param uploadMsg
     */
    private void openFileChooserImplForAndroid5(Activity activity, ValueCallback<Uri[]> uploadMsg) {
        mUploadMessageForAndroid5 = uploadMsg;
        Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
        contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
        contentSelectionIntent.setType("image/*");

        Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
        chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
        chooserIntent.putExtra(Intent.EXTRA_TITLE, "图片选择");
        activity.startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
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
