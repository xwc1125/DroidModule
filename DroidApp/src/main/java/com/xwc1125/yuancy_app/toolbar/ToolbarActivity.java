package com.xwc1125.yuancy_app.toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.xwc1125.droidmodule.Base.activity.BaseAppCompatActivity;
import com.xwc1125.droidapp.R;
import com.xwc1125.droidui.ToolBar.ToolbarHelper;

/**
 * Class: com.xwc1125.yuancy_app.toolbar <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/6  21:15 <br>
 */
public class ToolbarActivity extends BaseAppCompatActivity {

    private ToolbarHelper toolbarHelper;

    @Override
    protected void setContentView(Bundle savedInstanceState) {
        setContentView(R.layout.activity_toolbar);
    }

    @Override
    protected void getBundleExtra() {

    }

    @Override
    protected void initViews() {
        ToolbarHelper toolbarHelper = new ToolbarHelper(activity);
        toolbarHelper.getToolbar().inflateMenu(R.menu.menu_toolbar);
        AppCompatActivity appCompatActivity = (AppCompatActivity) activity;
        ActionBar actionBar = appCompatActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowTitleEnabled(false);
        }
        //加入菜单响应
        toolbarHelper.getToolbar().setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.action_switch:
                        startActivity(activity,ToolbarFragmentActivity.class);
                        break;
                    case R.id.action_get:
                        Toast.makeText(activity, "收款码", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.action_new:
                        Toast.makeText(activity, "创建账户", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });

        toolbarHelper.setTitle("Toolbar");
        toolbarHelper.setNavigationIcon(null);
    }

    @Override
    protected void initListeners() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return super.onCreateOptionsMenu(menu);
    }
}
