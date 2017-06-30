package com.xwc1125.yuancy_app.base;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;

import com.xwc1125.ui.toolbar.ToolbarHelper;
import com.yuancy.framework.helper.WeakHandler;

/**
 * Class: com.xwc1125.yuancy_app.base <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/3  15:29 <br>
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected WeakHandler handler;
    protected static Activity activity;
    protected static ToolbarHelper toolbarHelper;
    protected FragmentManager fm;
    protected FragmentTransaction ft;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this;
        setContentView(savedInstanceState);
        getBundleExtra();
        initViews();
        initListeners();
        initData();
    }

    /**
     * Set the windows having no title
     *
     * @author xwc1125 2015-5-30上午8:41:09
     */
    protected void setWindowsNoTitle() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);

    }

    /**
     * Set the orientation
     *
     * @param flag : 0:UNSPECIFIED,1:PORTRAIT,2:LANDSCAPE,3:USER,4:BEHIND,5:
     *             SENSOR,6:NOSENSOR,other:UNSPECIFIED
     * @author xwc1125 2015-5-30上午8:40:58
     */
    protected void PortraitOrientation(int flag) {
        if (flag == 0) {
            // 默认值 由系统来判断显示方向.判定的策略是和设备相关的，所以不同的设备会有不同的显示方向.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        } else if (flag == 1) {
            // // 竖屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        } else if (flag == 2) {
            // 横屏
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        } else if (flag == 3) {
            // 用户当前首选的方向
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_USER);
        } else if (flag == 4) {
            // 和该Activity下面的那个Activity的方向一致(在Activity堆栈中的)
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_BEHIND);
        } else if (flag == 5) {
            // 有物理的感应器来决定。如果用户旋转设备这屏幕会横竖屏切换
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR);
        } else if (flag == 6) {
            // 忽略物理感应器，这样就不会随着用户旋转设备而更改了（"unspecified"设置除外）
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_NOSENSOR);
        } else {
            // 默认值 由系统来判断显示方向.判定的策略是和设备相关的，所以不同的设备会有不同的显示方向.
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
        }
    }

    protected abstract void setContentView(Bundle savedInstanceState);

    protected abstract void getBundleExtra();

    protected void setToolbarView(int toolbarResId) {
        toolbarHelper = new ToolbarHelper(activity, toolbarResId);
        /**
         * 获取toolbar的组建，然后通过setSupportActionBar方法将组建设置进activity中
         */
        setSupportActionBar(toolbarHelper.getToolbar());
    }

    protected abstract void initViews();

    protected abstract void initListeners();

    protected abstract void initData();

    /**
     * 替换fragment
     *
     * @param fragmetId fragment id
     * @param fragment
     */
    protected void replaceFragment(int fragmetId,Fragment fragment) {
        fm = getSupportFragmentManager();
        ft = fm.beginTransaction();
        ft.replace(fragmetId, fragment);
        ft.commit();
    }

    /**
     * <p>
     * Title: startActivity
     * </p>
     * <p>
     * Description:
     * </p>
     *
     * @param cla
     * @author xwc1125
     * @date 2015-6-2下午4:53:40
     */
    protected void startActivity(Class<?> cla) {
        Intent intent = new Intent();
        intent.setClass(activity, cla);
        activity.startActivity(intent);
    }
}
