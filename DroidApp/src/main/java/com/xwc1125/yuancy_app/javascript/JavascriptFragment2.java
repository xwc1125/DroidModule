package com.xwc1125.yuancy_app.javascript;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.xwc1125.droidapp.R;
import com.xwc1125.droidmodule.Base.fragment.BaseFragment;
import com.xwc1125.droidui.webview.interfaces.Html5Listener;
import com.xwc1125.droidui.webview.Html5WebView;
import com.xwc1125.droidui.webview.interfaces.ImageClickListener;
import com.xwc1125.droidui.webview.interfaces.ResponseCallback;
import com.xwc1125.droidui.webview.interfaces.TextClickListener;
import com.xwc1125.droidui.webview.interfaces.JavaCallHandler;
import com.xwc1125.droidui.webview.interfaces.JsHandler;
import com.xwc1125.droidutils.statubar.StatusBarUtils;
import com.xwc1125.droidutils.view.FindViewUtils;

import java.util.ArrayList;
import java.util.Map;

public class JavascriptFragment2 extends BaseFragment {

    private static Html5WebView webView;
    // UI references.
    private ArrayList<String> mHandlers = new ArrayList<>();

    ValueCallback<Uri> mUploadMessage;//上传的内容
    private static ResponseCallback mfunction;//回调内容

    int RESULT_CODE = 0;

    public JavascriptFragment2() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment WebViewFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static JavascriptFragment2 newInstance() {
        JavascriptFragment2 fragment = new JavascriptFragment2();
        return fragment;
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
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
                webView.loadUrl("file:///android_asset/javascript_demo.html");
            }
        });
        refreshLayout.autoRefresh();

        //状态栏透明和间距处理
        StatusBarUtils.setTranslucentForImageView(activity, 0, webView);
        StatusBarUtils.setPaddingSmart(activity, webView);

        mHandlers.add("login");
        mHandlers.add("callNative");
        mHandlers.add("callJs");
        mHandlers.add("open");

        //回调js的方法
        webView.registerHandlers(mHandlers, new JsHandler() {
            @Override
            public void OnHandler(String handlerName, String responseData, ResponseCallback responseCallback) {

                if (handlerName.equals("login")) {
                    Toast.makeText(getActivity(), responseData, Toast.LENGTH_SHORT).show();
                } else if (handlerName.equals("callNative")) {
                    Toast.makeText(getActivity(), responseData, Toast.LENGTH_SHORT).show();
                    responseCallback.onCallBack("我在上海");
                } else if (handlerName.equals("callJs")) {
                    Toast.makeText(getActivity(), responseData, Toast.LENGTH_SHORT).show();
                    // 想调用你的方法：
                    responseCallback.onCallBack("好的 这是图片地址 ：xxxxxxx");
                }
                if (handlerName.equals("open")) {
                    mfunction = responseCallback;
                    pickFile();
                }

            }
        });

        // 调用js

        webView.callJsHandler("callNative", "hello H5, 我是java", new JavaCallHandler() {
            @Override
            public void OnHandler(String handlerName, String jsResponseData) {
                Toast.makeText(getActivity(), "h5返回的数据：" + jsResponseData, Toast.LENGTH_SHORT).show();
            }
        });
        //发送消息给js
        webView.send("哈喽", new ResponseCallback() {
            @Override
            public void onCallBack(String data) {
                Toast.makeText(getActivity(), data, Toast.LENGTH_SHORT).show();
            }
        });
        final EditText editText = FindViewUtils.findViewById(view, R.id.et_url);
        FindViewUtils.findViewById(view, R.id.btn_go).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.loadUrl(editText.getText().toString());
            }
        });
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

        @Override
        public boolean onInterceptUrl(WebView view, String url, Map<String, String> additionalHttpHeaders) {
            // 电话、短信、邮箱
            if (url.startsWith(WebView.SCHEME_TEL) || url.startsWith("sms:") || url.startsWith(WebView.SCHEME_MAILTO)) {
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(url));
                    activity.startActivity(intent);
                } catch (ActivityNotFoundException ignored) {
                }
            }
            return super.onInterceptUrl(view, url, additionalHttpHeaders);
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

    public void pickFile() {
        Intent chooserIntent = new Intent(Intent.ACTION_GET_CONTENT);
        chooserIntent.setType("image/*");
        startActivityForResult(chooserIntent, RESULT_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        if (requestCode == RESULT_CODE) {
            if (null == mUploadMessage) {
                return;
            }
            Uri result = intent == null || resultCode != Activity.RESULT_OK ? null : intent.getData();
            mUploadMessage.onReceiveValue(result);
            mUploadMessage = null;

            mfunction.onCallBack(intent.getData().toString());

        }
    }

}
