package com.xwc1125.yuancy_app.webview;


import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.xwc1125.droidapp.R;
import com.xwc1125.droidui.webview.Html5Linstener;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidmodule.Base.fragment.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WebViewFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WebViewFragment extends BaseFragment {
    private static Html5WebView webView;

    public WebViewFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WebViewFragment newInstance() {
        WebViewFragment fragment = new WebViewFragment();
        return fragment;
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.example_fragment_web_view, container, false);
    }

    @Override
    protected void initView(View view) {
        LinearLayout mLayout = (LinearLayout) view.findViewById(R.id.ll_webview);
        //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new Html5WebView(getContext());
        webView.setLayoutParams(params);
        mLayout.addView(webView);
    }

    @Override
    protected void initListeners() {
        webView.loadUrl("http://www.baidu.com", DemoHtml5Linstener);
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (webView != null) {
            webView.loadDataWithBaseURL(null, "", "text/html", "utf-8", null);
            webView.clearHistory();

            ((ViewGroup) webView.getParent()).removeView(webView);
            webView.destroy();
            webView = null;
        }
    }

    /**
     * Html5webview的监听
     */

    private static Html5Linstener DemoHtml5Linstener = new Html5Linstener() {
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (goBack()) return true;
            }
            return false;
        }

        @Override
        public void hindProgressBar() {
            super.hindProgressBar();
        }

        @Override
        public void showWebView() {
            super.showWebView();
        }

        @Override
        public void hindWebView() {
            super.hindWebView();
        }

        @Override
        public void startProgress() {
            super.startProgress();
        }

        @Override
        public void onProgressChanged(int newProgress) {
            super.onProgressChanged(newProgress);
        }

        @Override
        public void fullViewAddView(View view) {
            super.fullViewAddView(view);
        }

        @Override
        public void showVideoFullView() {
            super.showVideoFullView();
        }

        @Override
        public void hindVideoFullView() {
            super.hindVideoFullView();
        }

        @Override
        public void loadingUrl(WebView view, String url) {
            // 电话、短信、邮箱
            if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(WebView.SCHEME_MAILTO)) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
        }

        @Override
        public void onImageClick(String imgUrl, String hasLink) {
            Toast.makeText(activity, "imgUrl被点击了：" + imgUrl, Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onTextClick(String type, String item_pk) {
            Toast.makeText(activity, "item_pk被点击了：" + item_pk, Toast.LENGTH_SHORT).show();
        }
    };

    private static boolean goBack() {
        if (webView.canGoBack()) {
            //返回网页上一页
            webView.goBack();
            return true;
        } else {
            //退出网页
            webView.loadUrl("about:blank");
            activity.finish();
        }
        return false;
    }
}