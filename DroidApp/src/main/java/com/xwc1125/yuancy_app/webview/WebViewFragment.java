package com.xwc1125.yuancy_app.webview;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xwc1125.droidapp.R;
import com.xwc1125.droidui.webview.interfaces.Html5Listener;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidmodule.Base.fragment.BaseFragment;
import com.xwc1125.droidui.webview.interfaces.ImageClickListener;
import com.xwc1125.droidui.webview.interfaces.TextClickListener;
import com.xwc1125.droidutils.statubar.StatusBarUtils;
import com.xwc1125.droidutils.view.FindViewUtils;

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

    static RefreshLayout refreshLayout;

    @Override
    protected void initView(View view) {
        LinearLayout mLayout = FindViewUtils.findViewById(view, R.id.ll_webview);
        //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new Html5WebView(getContext());
        webView.setLayoutParams(params);
        mLayout.addView(webView);
        webView.setListener(DemoHtml5Linstener);
        webView.setImageClickListener(new ImageClickListener() {
            @Override
            public void onImageClick(String imgUrl, String hasLink) {
                Toast.makeText(activity, "imgUrl=" + imgUrl + ",hasLink=" + hasLink, Toast.LENGTH_LONG).show();
            }
        });
        webView.setTextClickListener(new TextClickListener() {
            @Override
            public void onTextClick(String type, String item_pk) {
                Toast.makeText(activity, "type=" + type + ",item_pk=" + item_pk, Toast.LENGTH_LONG).show();
            }
        });

        refreshLayout = FindViewUtils.findViewById(view, R.id.smartLayout);
        refreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                webView.loadUrl("http://www.baidu.com");
            }
        });
        refreshLayout.autoRefresh();

        //状态栏透明和间距处理
        StatusBarUtils.setTranslucentForImageView(activity, 0, webView);
        StatusBarUtils.setPaddingSmart(activity, webView);
    }

    @Override
    protected void initListeners() {

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

    private static Html5Listener DemoHtml5Linstener = new Html5Listener() {
        @Override
        public boolean onKeyDown(int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK) {
                if (goBack()) return true;
            }
            return false;
        }

        @Override
        public void onStopProgress() {
            super.onStopProgress();
            refreshLayout.finishRefresh();
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
