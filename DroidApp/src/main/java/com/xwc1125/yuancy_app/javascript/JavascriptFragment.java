package com.xwc1125.yuancy_app.javascript;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.fragment.BaseFragment;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidutils.statubar.StatusBarUtils;
import com.xwc1125.droidutils.view.FindViewUtils;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/7/19  10:30 <br>
 */
public class JavascriptFragment extends BaseFragment {

    private static Html5WebView webView;
    private TextView msgView = null;

    public static JavascriptFragment newInstance() {
        JavascriptFragment fragment = new JavascriptFragment();
        return fragment;
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_webview_javascript, container, false);
    }

    @SuppressLint("JavascriptInterface")
    @Override
    protected void initView(View view) {
        LinearLayout mLayout = FindViewUtils.findViewById(view, R.id.ll_webview);
        //不在xml中定义 Webview ，而是在需要的时候在Activity中创建，并且Context使用 getApplicationgContext()
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        webView = new Html5WebView(getContext());
        webView.setLayoutParams(params);
        mLayout.addView(webView);

        webView.loadData("", "text/html", null);
        webView.loadUrl("file:///android_asset/javascript.html");

        Button button = (Button) view.findViewById(R.id.button);
        button.setOnClickListener(btnClickListener);
        msgView = (TextView) view.findViewById(R.id.msg);

        webView.addJavascriptInterface(new JsObject(), "android");
        //状态栏透明和间距处理
        StatusBarUtils.setTranslucentForImageView(activity,0,webView);
        StatusBarUtils.setPaddingSmart(activity, webView);
    }

    View.OnClickListener btnClickListener = new Button.OnClickListener() {
        public void onClick(View v) {
            // 无参数调用
            webView.loadUrl("javascript:javacalljs()");
            // 传递参数调用
            webView.loadUrl("javascript:javacalljswithargs(" + "'hello world'" + ")");
        }
    };

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



    class JsObject{

        @JavascriptInterface
        public void startFunction() {
            Toast.makeText(getActivity(), "js调用了java函数", Toast.LENGTH_SHORT).show();
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    msgView.setText(msgView.getText() + "\njs调用了java函数");

                }
            });
        }

        @JavascriptInterface
        public void startFunction(final String str) {
            Toast.makeText(getActivity(), str, Toast.LENGTH_SHORT).show();
            getActivity().runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    msgView.setText(msgView.getText() + "\njs调用了java函数传递参数：" + str);

                }
            });
        }
    }

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
