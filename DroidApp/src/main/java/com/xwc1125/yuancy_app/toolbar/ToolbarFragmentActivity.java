package com.xwc1125.yuancy_app.toolbar;

import android.os.Bundle;

import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidapp.R;

public class ToolbarFragmentActivity extends BaseAppCompatActivity {

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
