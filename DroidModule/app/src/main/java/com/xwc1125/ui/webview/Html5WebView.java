package com.xwc1125.ui.webview;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.webkit.WebView;

import java.util.Map;

/**
 * Class: com.ugchain.wallet.ui.webView <br>
 * Description: TODO <br>
 * <p>
 * 使用方法：
 * 防止内存泄漏，webview的使用方法<br>
 * //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()<br>
 * LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);<br>
 * webView = new Html5WebView(getApplicationContext());<br>
 * webView.setLayoutParams(params);<br>
 * mLayout.addView(webView);<br>
 * webView.loadUrl("http://www.baidu.com", new Html5Linstener());<br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  14:54 <br>
 */
public class Html5WebView extends WebView {
    private Context mContext;
    private Html5WebChromeClient html5WebChromeClient;
    private Html5WebViewClient html5WebViewClient;
    private Html5Linstener linstener;

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

    private void init(Context context) {
        this.mContext = context;
        Html5WebViewUtils.getWebSettings(this);

        // 与js交互
        this.addJavascriptInterface(linstener, "injectedObject");

        html5WebChromeClient = new Html5WebChromeClient(mContext, this, linstener);
        this.setWebChromeClient(html5WebChromeClient);
        html5WebViewClient = new Html5WebViewClient(linstener);
        this.setWebViewClient(html5WebViewClient);
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (linstener != null) {
            return linstener.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    public Html5Linstener getLinstener() {
        return linstener;
    }

    public void setLinstener(Html5Linstener linstener) {
        this.linstener = linstener;
    }

    public void loadUrl(String url, Map<String, String> additionalHttpHeaders, Html5Linstener linstener) {
        super.loadUrl(url, additionalHttpHeaders);
        this.linstener = linstener;
    }

    public void loadUrl(String url, Html5Linstener linstener) {
        super.loadUrl(url);
        this.linstener = linstener;
    }


    public Html5WebChromeClient getHtml5WebChromeClient() {
        return html5WebChromeClient;
    }

    public void setHtml5WebChromeClient(Html5WebChromeClient html5WebChromeClient) {
        this.html5WebChromeClient = html5WebChromeClient;
    }

    public Html5WebViewClient getHtml5WebViewClient() {
        return html5WebViewClient;
    }

    public void setHtml5WebViewClient(Html5WebViewClient html5WebViewClient) {
        this.html5WebViewClient = html5WebViewClient;
    }
}
