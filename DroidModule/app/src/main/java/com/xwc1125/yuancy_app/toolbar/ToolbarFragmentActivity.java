package com.xwc1125.yuancy_app.toolbar;

import android.os.Bundle;

import com.xwc1125.droidmodule.R;
import com.xwc1125.yuancy_app.base.BaseActivity;

public class ToolbarFragmentActivity extends BaseActivity {

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_toolbar_fragment);
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected void initViews() {
    }

    @Override
    protected void initListeners() {
        replaceFragment(R.id.fragment_content, ToolbarFragment.newInstance("111", "222"));
    }

    @Override
    protected void initData() {

    }
}
