package com.xwc1125.droidmodule.WebView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;

import com.xwc1125.droidmodule.Base.activity.BaseActivity;
import com.xwc1125.droidmodule.R;
import com.xwc1125.droidui.webview.interfaces.Html5Listener;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidutils.StringUtils;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/14  15:16 <br>
 */
public abstract class ModuleWebViewActivity extends BaseActivity {
    private static final String BUNDLE_KEY_URL = "bundle_key_url";
    private static final String BUNDLE_KEY_TITLE = "bundle_key_title";
    private String url;
    private String title;
    private static Html5WebView webView;

    public static void openActivity(Activity activity, String title, String url) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putString(BUNDLE_KEY_URL, url);
        bundle.putString(BUNDLE_KEY_TITLE, title);
        intent.setClass(activity, ModuleWebViewActivity.class);
        intent.putExtras(bundle);
        activity.startActivity(intent);
    }

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        int layoutResId = setContentView();
        if (layoutResId == 0) {
            //默认的UI
            layoutResId = R.layout.webview_activity;
        }
        View view = View.inflate(this, layoutResId, null);
        setContentView(view);
    }

    protected abstract int setContentView();

    @Override
    protected void getBundleExtra() {
        url = getIntent().getStringExtra(BUNDLE_KEY_URL);
        title = getIntent().getStringExtra(BUNDLE_KEY_TITLE);
    }

    @Override
    protected void initViews() {
        setViews(webView, title, url);
        if (url.startsWith("file:///android_asset")) {
            webView.getSettings().setTextZoom(240);
        }
    }

    protected abstract void setViews(WebView webView, String title, String url);

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {
        Html5Listener myHtml5Linstener = setHtml5Linstener(activity, webView);
        if (myHtml5Linstener == null) {
            myHtml5Linstener = MyHtml5Linstener;
        }
        if (StringUtils.isNotEmpty(url)) {
            webView.loadUrl(url);
            webView.setListener(myHtml5Linstener);
        } else {
            activity.finish();
        }
    }

    protected abstract Html5Listener setHtml5Linstener(Activity activity, WebView webView);

    @Override
    protected void onDestroy() {
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
        super.onDestroy();
    }

    private static Html5Listener MyHtml5Linstener = new Html5Listener() {
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (webView.canGoBack()) {
                    //返回网页上一页
                    webView.goBack();
                    return true;
                } else {
                    //退出网页
                    webView.loadUrl("about:blank");
                    activity.finish();
                }
            }
            return false;
        }


        @Override
        public void startProgress() {
            super.startProgress();
        }

    };
}
