package com.xwc1125.droidui.webview.core;

import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.webkit.ClientCertRequest;
import android.webkit.HttpAuthHandler;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.xwc1125.droidui.webview.interfaces.Html5Listener;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidui.webview.config.Html5Config;
import com.xwc1125.droidui.webview.config.JsConfig;
import com.xwc1125.droidui.webview.entity.Message;
import com.xwc1125.droidui.webview.interfaces.ImageClickListener;
import com.xwc1125.droidui.webview.interfaces.JavaScriptLinstener;
import com.xwc1125.droidui.webview.interfaces.TextClickListener;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLDecoder;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/5/27  15:09 <br>
 */

public class Html5WebViewClient extends WebViewClient {
    private static final String TAG = Html5WebViewClient.class.getName();
    private Html5WebView html5WebView;
    //==============监听==============
    private Html5Listener listener;
    private ImageClickListener imageClickListener;//图片的点击监听
    private TextClickListener textClickListener;//文本的点击监听
    private JavaScriptLinstener javaScriptLinstener;//注入js

    private SSLContext sslContext;

    public Html5WebViewClient() {
    }

    public void setListener(Html5WebView html5WebView, Html5Listener listener) {
        this.html5WebView = html5WebView;
        this.listener = listener;
    }

    /**
     * 设置图片的点击监听
     *
     * @param imageClickListener
     */
    public void setImageClickListener(ImageClickListener imageClickListener) {
        this.imageClickListener = imageClickListener;
    }

    /**
     * 设计文本的点击监听
     *
     * @param textClickListener
     */
    public void setTextClickListener(TextClickListener textClickListener) {
        this.textClickListener = textClickListener;
    }

    /**
     * 注入js
     *
     * @param javaScriptLinstener
     */
    public void setJavaScriptLinstener(JavaScriptLinstener javaScriptLinstener) {
        this.javaScriptLinstener = javaScriptLinstener;
    }

    /**
     * 作用：开始载入页面调用的，我们可以设定一个loading的页面，告诉用户程序在等待网络响应。
     * <p>
     * 在开始加载网页时会回调
     *
     * @param view
     * @param url
     * @param favicon
     */
    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        super.onPageStarted(view, url, favicon);
        if (listener != null) {
            listener.startProgress();
        }
        if (listener != null) {
            listener.onPageStarted(view, url, favicon);
        }
    }

    /**
     * 作用：在页面加载结束时调用。我们可以关闭loading 条，切换程序动作。
     *
     * @param view
     * @param url
     */
    @Override
    public void onPageFinished(WebView view, String url) {
        if (listener != null) {
            listener.onPageFinished(view, url);
        }

        if (imageClickListener != null) {
            String aa=Html5Config.JAVASCRIPT_STR + JsConfig.WEB_JAVASCRIPT_IMAGECLICK;
            view.loadUrl(Html5Config.JAVASCRIPT_STR + JsConfig.WEB_JAVASCRIPT_IMAGECLICK);
        }

        if (textClickListener != null) {
            view.loadUrl(Html5Config.JAVASCRIPT_STR + JsConfig.WEB_JAVASCRIPT_TEXTCLICK);
        }

        //注入js
        view.loadUrl(Html5Config.JAVASCRIPT_STR + JsConfig.WEB_JAVASCRIPT_BRIDGE);

        //注入自定义的js
        if (javaScriptLinstener != null) {
            javaScriptLinstener.onInjectJavaScript(html5WebView);
        }

        if (html5WebView.getStartupMessage() != null) {
            for (Message m : html5WebView.getStartupMessage()) {
                html5WebView.dispatchMessage(m);
            }
            html5WebView.setStartupMessage(null);
        }

        super.onPageFinished(view, url);
    }


    /**
     * 作用：打开网页时不调用系统浏览器， 而是在本WebView中显示；在网页上的所有加载都经过这个方法,这个函数我们可以做很多操作。
     * <p>
     * 拦截 url 跳转,在里边添加点击链接跳转或者操作
     */
    //复写shouldOverrideUrlLoading()方法，
    //使得打开网页时不调用系统浏览器， 而是在本WebView中显示
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        if (TextUtils.isEmpty(url)) {
            return true;
        }
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (url.startsWith(Html5Config.DROID_RETURN_DATA)) { // 如果是返回数据
            //调用js并有数据返回
            html5WebView.handlerResponseData(url);
            return true;
        } else if (url.startsWith(Html5Config.DROID_OVERRIDE_SCHEMA)) { //
            html5WebView.flushMessageQueue();
            return true;
        }
        if (listener != null) {
            boolean flag = listener.onInterceptUrl(view, url, listener.onPageHeaders(url));
            if (flag) {
                return flag;
            }
        }
        if (listener != null && listener.onPageHeaders(url) != null) {
            view.loadUrl(url, listener.onPageHeaders(url));
        } else {
            view.loadUrl(url);
        }
        return true;
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
        return super.shouldOverrideUrlLoading(view, request);
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
        if (listener != null) {
            listener.onReceivedError(view, request, error);
        }
    }

    /**
     * 网页加载出错
     *
     * @param view
     * @param errorCode
     * @param description
     * @param failingUrl
     */
    @Override
    public void onReceivedError(WebView view, int errorCode,
                                String description, String failingUrl) {
        if (listener != null) {
            listener.onReceivedError(view, errorCode, description, failingUrl);
        }
    }

    /**
     * 作用：处理https请求
     * <p>
     * webView默认是不处理https请求的，页面显示空白，需要进行如下设置：
     * 当接收到https错误时，会回调此函数，在其中可以做错误处理
     *
     * @param view
     * @param handler
     * @param error
     */
    @Override
    public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
        super.onReceivedSslError(view, handler, error);
        if (listener != null) {
            listener.onReceivedSslError(view, handler, error);
        }
    }

    /**
     * 在每一次请求资源时，都会通过这个函数来回调
     *
     * @param view
     * @param url
     * @return
     */
    public WebResourceResponse shouldInterceptRequest(WebView view,
                                                      String url) {
        return null;
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


    @Override
    public void onScaleChanged(WebView view, float oldScale, float newScale) {
        super.onScaleChanged(view, oldScale, newScale);
        if (newScale - oldScale > 7) {
            view.setInitialScale((int) (oldScale / newScale * 100)); //异常放大，缩回去。
        }
    }

    /**
     * 页面可见：此时隐藏进度条
     * @param view
     * @param url
     */
    @Override
    public void onPageCommitVisible(WebView view, String url) {
        super.onPageCommitVisible(view, url);
        if (listener != null) {
            listener.onStopProgress();
        }
    }

    @Override
    public void onFormResubmission(WebView view, android.os.Message dontResend, android.os.Message resend) {
        super.onFormResubmission(view, dontResend, resend);
    }

    @Override
    public void onReceivedClientCertRequest(WebView view, ClientCertRequest request) {
        super.onReceivedClientCertRequest(view, request);
    }

    @Override
    public void onReceivedHttpAuthRequest(WebView view, HttpAuthHandler handler, String host, String realm) {
        super.onReceivedHttpAuthRequest(view, handler, host, realm);
    }

    @Override
    public void onReceivedHttpError(WebView view, WebResourceRequest request, WebResourceResponse errorResponse) {
        super.onReceivedHttpError(view, request, errorResponse);
    }

    @Override
    public void onReceivedLoginRequest(WebView view, String realm, String account, String args) {
        super.onReceivedLoginRequest(view, realm, account, args);
    }

    @Override
    public void onUnhandledKeyEvent(WebView view, KeyEvent event) {
        super.onUnhandledKeyEvent(view, event);
    }

    @Override
    public void onTooManyRedirects(WebView view, android.os.Message cancelMsg, android.os.Message continueMsg) {
        super.onTooManyRedirects(view, cancelMsg, continueMsg);
    }

    //=======================SSL======================
    private WebResourceResponse processRequest(Uri uri) {

        try {
            // Setup connection
            URL url = new URL(uri.toString());
            HttpsURLConnection urlConnection = (HttpsURLConnection) url.openConnection();

            // Set SSL Socket Factory for this request
            urlConnection.setSSLSocketFactory(sslContext.getSocketFactory());

            // Get content, contentType and encoding
            InputStream is = urlConnection.getInputStream();
            String contentType = urlConnection.getContentType();
            String encoding = urlConnection.getContentEncoding();

            // If got a contentType header
            if (contentType != null) {

                String mimeType = contentType;

                // Parse mime type from contenttype string
                if (contentType.contains(";")) {
                    mimeType = contentType.split(";")[0].trim();
                }


//                listener.Loaded(uri.toString());

                // Return the response
                return new WebResourceResponse(mimeType, encoding, is);
            }

        } catch (SSLHandshakeException e) {
            if (isCause(CertPathValidatorException.class, e)) {
//                listener.PinningPreventedLoading(uri.getHost());
            }
            Log.d("SSL_PINNING_WEBVIEWS", e.getLocalizedMessage());
        } catch (Exception e) {
            Log.d("SSL_PINNING_WEBVIEWS", e.getLocalizedMessage());
        }

        // Return empty response for this request
        return new WebResourceResponse(null, null, null);
    }

    private void prepareSslPinning(InputStream... certificates) throws IOException {
        try {
            InputStream inputStream = html5WebView.getContext().getResources().getAssets().open("client.p12");

            TrustManager[] trustManagers = prepareTrustManager(certificates);
            KeyManager[] keyManagers = prepareKeyManager(inputStream, "your client.p12 password");
            sslContext = SSLContext.getInstance("TLS");
            sslContext.init(keyManagers, new TrustManager[]{new MyTrustManager(chooseTrustManager(trustManagers))}, new SecureRandom());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (KeyManagementException e) {
            e.printStackTrace();
        }
    }

    private static TrustManager[] prepareTrustManager(InputStream... certificates) {
        if (certificates == null || certificates.length <= 0) return null;
        try {
            CertificateFactory certificateFactory = CertificateFactory.getInstance("X.509");
            KeyStore keyStore = KeyStore.getInstance(KeyStore.getDefaultType());
            keyStore.load(null);
            int index = 0;
            for (InputStream certificate : certificates) {
                String certificateAlias = Integer.toString(index++);
                keyStore.setCertificateEntry(certificateAlias, certificateFactory.generateCertificate(certificate));
                try {
                    if (certificate != null)
                        certificate.close();
                } catch (IOException e) {
                }
            }
            TrustManagerFactory trustManagerFactory = null;

            trustManagerFactory = TrustManagerFactory.
                    getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init(keyStore);

            TrustManager[] trustManagers = trustManagerFactory.getTrustManagers();

            return trustManagers;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    private static KeyManager[] prepareKeyManager(InputStream bksFile, String password) {
        try {
            if (bksFile == null || password == null) return null;

            KeyStore clientKeyStore = KeyStore.getInstance("PKCS12");
            clientKeyStore.load(bksFile, password.toCharArray());
            KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
            keyManagerFactory.init(clientKeyStore, password.toCharArray());
            return keyManagerFactory.getKeyManagers();

        } catch (KeyStoreException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (UnrecoverableKeyException e) {
            e.printStackTrace();
        } catch (CertificateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static X509TrustManager chooseTrustManager(TrustManager[] trustManagers) {
        for (TrustManager trustManager : trustManagers) {
            if (trustManager instanceof X509TrustManager) {
                return (X509TrustManager) trustManager;
            }
        }
        return null;
    }


    private static class MyTrustManager implements X509TrustManager {
        private X509TrustManager defaultTrustManager;
        private X509TrustManager localTrustManager;

        public MyTrustManager(X509TrustManager localTrustManager) throws NoSuchAlgorithmException, KeyStoreException {
            TrustManagerFactory var4 = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            var4.init((KeyStore) null);
            defaultTrustManager = chooseTrustManager(var4.getTrustManagers());
            this.localTrustManager = localTrustManager;
        }


        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {

        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                defaultTrustManager.checkServerTrusted(chain, authType);
            } catch (CertificateException ce) {
                localTrustManager.checkServerTrusted(chain, authType);
            }
        }


        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[0];
        }
    }

    public static boolean isCause(
            Class<? extends Throwable> expected,
            Throwable exc
    ) {
        return expected.isInstance(exc) || (
                exc != null && isCause(expected, exc.getCause())
        );
    }

}
