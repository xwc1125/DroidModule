package com.xwc1125.droidmodule.ToolBar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;

import com.xwc1125.droidutils.view.FindViewUtils;

public class ToolbarHelper {

    private Toolbar mToolbar = null;

    public ToolbarHelper(Activity activity, int resId) {
        mToolbar = FindViewUtils.findViewById(activity, resId);
    }

    public Toolbar getToolbar() {
        return mToolbar;
    }

    public void setVisibility(int flag) {
        mToolbar.setVisibility(flag);
    }

    public void setNavigationIcon(int resId) {
        mToolbar.setNavigationIcon(resId);
    }

    public void setNavigationIcon(Drawable drawable) {
        mToolbar.setNavigationIcon(drawable);
    }

    public void setTitle(String title) {
        mToolbar.setTitle(title);
    }

}