package com.xwc1125.droidui.webview.core;

import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Class: com.ugchain.wallet.ui.webView <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/31  09:47 <br>
 */
public class Html5WebViewUtils {
    public static void getWebSettings(WebView webView) {
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
    }
}
