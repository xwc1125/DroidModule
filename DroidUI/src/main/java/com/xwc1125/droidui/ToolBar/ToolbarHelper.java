package com.xwc1125.droidui.ToolBar;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.xwc1125.droidui.R;
import com.xwc1125.droidui.language.AppTextView;
import com.xwc1125.droidutils.view.FindViewUtils;

public class ToolbarHelper {

    private Toolbar mToolbar = null;
    private RelativeLayout top_layout;

    private LinearLayout left_layout;
    private ImageView iv_left_icon;
    private ImageView iv_left_icon_sub;

    private View view_temp;

    private LinearLayout left_text_layout;
    private AppTextView tv_left_title;
    private AppTextView tv_left_title_sub;

    private LinearLayout center_layout;
    private ProgressBar center_progressbar;
    private LinearLayout center_title_layout;
    private AppTextView tv_center_title;
    private AppTextView tv_center_title_sub;

    private LinearLayout right_layout;
    private LinearLayout right_text_layout;
    private AppTextView tv_right_title;
    private AppTextView tv_right_title_sub;
    private ImageView iv_right_icon;

    public ToolbarHelper(Activity activity) {
        mToolbar = FindViewUtils.findViewById(activity, R.id.toolbar);
        top_layout = FindViewUtils.findViewById(activity, R.id.top_layout);

        //======left image
        left_layout = FindViewUtils.findViewById(activity, R.id.left_layout);
        iv_left_icon = FindViewUtils.findViewById(activity, R.id.iv_left_icon);
        iv_left_icon_sub = FindViewUtils.findViewById(activity, R.id.iv_left_icon_sub);

        //======left hind
        view_temp = FindViewUtils.findViewById(activity, R.id.view_temp);

        //======left text
        left_text_layout = FindViewUtils.findViewById(activity, R.id.left_text_layout);
        tv_left_title = FindViewUtils.findViewById(activity, R.id.tv_left_title);
        tv_left_title_sub = FindViewUtils.findViewById(activity, R.id.tv_left_title_sub);

        //======center
        center_layout = FindViewUtils.findViewById(activity, R.id.center_layout);
        center_progressbar = FindViewUtils.findViewById(activity, R.id.center_progressbar);
        center_title_layout = FindViewUtils.findViewById(activity, R.id.center_title_layout);
        tv_center_title = FindViewUtils.findViewById(activity, R.id.tv_center_title);
        tv_center_title_sub = FindViewUtils.findViewById(activity, R.id.tv_center_title_sub);

        //======right
        right_layout = FindViewUtils.findViewById(activity, R.id.right_layout);
        right_text_layout = FindViewUtils.findViewById(activity, R.id.right_text_layout);
        tv_right_title = FindViewUtils.findViewById(activity, R.id.tv_right_title);
        tv_right_title_sub = FindViewUtils.findViewById(activity, R.id.tv_right_title_sub);
        iv_right_icon = FindViewUtils.findViewById(activity, R.id.iv_right_icon);
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

    //==============left image
    public ImageView getLeftIcon() {
        top_layout.setVisibility(View.VISIBLE);
        left_layout.setVisibility(View.VISIBLE);
        iv_left_icon.setVisibility(View.VISIBLE);
        return iv_left_icon;
    }

    public ImageView getLeftIconSub() {
        top_layout.setVisibility(View.VISIBLE);
        left_layout.setVisibility(View.VISIBLE);
        iv_left_icon_sub.setVisibility(View.VISIBLE);
        return iv_left_icon_sub;
    }

    //==============left hind
    public View getViewTemp() {
        top_layout.setVisibility(View.VISIBLE);
        view_temp.setVisibility(View.VISIBLE);
        return view_temp;
    }

    //==============left text
    public AppTextView getLeftTitle() {
        top_layout.setVisibility(View.VISIBLE);
        left_text_layout.setVisibility(View.VISIBLE);
        tv_left_title.setVisibility(View.VISIBLE);
        return tv_left_title;
    }

    public AppTextView getLeftTitleSub() {
        top_layout.setVisibility(View.VISIBLE);
        left_text_layout.setVisibility(View.VISIBLE);
        tv_left_title_sub.setVisibility(View.VISIBLE);
        return tv_left_title_sub;
    }

    //==============center
    public ProgressBar getCenterProgressBar() {
        top_layout.setVisibility(View.VISIBLE);
        center_layout.setVisibility(View.VISIBLE);
        center_progressbar.setVisibility(View.VISIBLE);
        return center_progressbar;
    }

    public AppTextView getCenterTitle() {
        top_layout.setVisibility(View.VISIBLE);
        center_title_layout.setVisibility(View.VISIBLE);
        tv_center_title.setVisibility(View.VISIBLE);
        return tv_center_title;
    }

    public AppTextView getCenterTitleSub() {
        top_layout.setVisibility(View.VISIBLE);
        center_title_layout.setVisibility(View.VISIBLE);
        tv_center_title_sub.setVisibility(View.VISIBLE);
        return tv_center_title_sub;
    }

    //==============Right
    public AppTextView getRightTitle() {
        top_layout.setVisibility(View.VISIBLE);
        right_layout.setVisibility(View.VISIBLE);
        right_text_layout.setVisibility(View.VISIBLE);
        tv_right_title.setVisibility(View.VISIBLE);
        return tv_right_title;
    }

    public AppTextView getRightTitleSub() {
        top_layout.setVisibility(View.VISIBLE);
        right_layout.setVisibility(View.VISIBLE);
        right_text_layout.setVisibility(View.VISIBLE);
        tv_right_title_sub.setVisibility(View.VISIBLE);
        return tv_right_title_sub;
    }

    public ImageView getRightIcon() {
        top_layout.setVisibility(View.VISIBLE);
        right_layout.setVisibility(View.VISIBLE);
        iv_right_icon.setVisibility(View.VISIBLE);
        return iv_right_icon;
    }
}
