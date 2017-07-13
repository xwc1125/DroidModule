package com.xwc1125.droidui.webview.core;

import android.graphics.Bitmap;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xwc1125.droidui.webview.Html5Linstener;

/**
 * Class: com.ugchain.wallet.ui.webView <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  15:09 <br>
 */

public class Html5WebViewClient extends WebViewClient {
    private static final String TAG = Html5WebViewClient.class.getName();
    private Html5Linstener linstener;

    public Html5WebViewClient(Html5Linstener linstener) {
        this.linstener = linstener;
    }

    /**
     * 作用：打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
     */
    //复写shouldOverrideUrlLoading()方法，
    //使得打开网页时不调用系统浏览器， 而是在本WebView中显示
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {

        if (linstener != null) {
            linstener.loadingUrl(view, url);
            linstener.startProgress();
        }
        view.loadUrl(url);
        return true;
    }

    /**
     * 作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
    }

    /**
     * 作用：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        if (linstener != null) {
            addImageClickListener(view);
            linstener.onPageFinished(view, url);
        }
        super.onPageFinished(view, url);
    }

    /**
     * 作用：在加载页面资源时会调用，每一个资源（比如图片）的加载都会调用一次。
     *
     * @param view
     * @param url
     */

    @Override
    public void onLoadResource(WebView view, String url) {
        super.onLoadResource(view, url);
    }

    /**
     * 作用：加载页面的服务器出现错误时（如404）调用。
     * <p>
     * App里面使用webview控件的时候遇到了诸如404这类的错误的时候，若也显示浏览器里面的那种错误提示页面就显得很丑陋了，那么这个时候我们的app就需要加载一个本地的错误提示页面，即webview如何加载一个本地的页面
     *
     * @param view
     * @param request
     * @param error
     */

    @Override
    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
        super.onReceivedError(view, request, error);
    }

    /**
     * 作用：处理https请求
     * <p>
     * webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
     *
     * @param view
     * @param handler
     * @param error
     */

    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
    }

    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    public void addImageClickListener(WebView webView) {
        // 这段js函数的功能就是，遍历所有的img节点，并添加onclick函数，函数的功能是在图片点击的时候调用本地java接口并传递url过去
        // 如要点击一张图片在弹出的页面查看所有的图片集合,则获取的值应该是个图片数组
        webView.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                //图片点击的回调
                "objs[i].onclick=function(){window.injectedObject.onImageClick(this.getAttribute(\"src\"),this.getAttribute(\"has_link\"));}" +
                "}" +
                "})()");

        // 遍历所有的a节点,将节点里的属性传递过去(属性自定义,用于页面跳转)
        webView.loadUrl("javascript:(function(){" +
                "var objs =document.getElementsByTagName(\"a\");" +
                "for(var i=0;i<objs.length;i++)" +
                "{" +
                "objs[i].onclick=function(){" +
                //内容点击的回调
                "window.injectedObject.onTextClick(this.getAttribute(\"type\"),this.getAttribute(\"item_pk\"));}" +
                "}" +
                "})()");
    }
}
