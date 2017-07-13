package com.xwc1125.droidmodule.Welcome;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.widget.LinearLayout;

import com.xwc1125.droidmodule.Base.adapter.BasePagerAdapter;
import com.xwc1125.droidmodule.Base.interfaces.PagerCallback;
import com.xwc1125.droidmodule.R;
import com.xwc1125.droidmodule.Splash.config.SplashConfig;

import java.util.ArrayList;
import java.util.List;

/**
 * Class: WelcomeActivity.java<br>
 * Description: The first loading will start this activity<br>
 *
 * @author xwc1125<br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017/5/23<br>
 * @date 2017/5/23 15:50<br>
 */
public abstract class ModuleWelcomeActivity extends Activity implements OnPageChangeListener, View.OnClickListener {

    protected static Activity activity;
    private ViewPager viewPager;
    private PagerAdapter pagerAdapter;
    private LinearLayout indicatorLayout;
    private ArrayList<View> views;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this; // 得到上下文
        int layoutResId = setContentView();
        if (layoutResId == 0) {
            //默认的UI
            layoutResId = R.layout.welcome_activity;
        }
        View view = View.inflate(this, layoutResId, null);
        setContentView(view);

        initView();
    }

    protected abstract int setContentView();

    /**
     * Method:  <br>
     * Description:  initialize the view.<br>
     *
     * @author xwc1125 <br>
     * @date 2017/5/23 15:48
     */
    private void initView() {
        // 实例化视图控件
        viewPager = (ViewPager) findViewById(R.id.viewpage);
        indicatorLayout = (LinearLayout) findViewById(R.id.indicator);
        views = setPagerView(activity);
        pagerAdapter = new BasePagerAdapter(this, views, new PagerCallback() {

            @Override
            public void onItemClick(List<View> views, int position) {
                if (position == views.size() - 1) {
                    views.get(position).setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            toHome();
                        }
                    });
                }
            }
        });
        viewPager.setAdapter(pagerAdapter); // 设置适配器
        viewPager.setOnPageChangeListener(this);
    }

    protected abstract ArrayList<View> setPagerView(Activity activity);

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    /**
     * toHome
     */
    private void toHome() {
        SharedPreferences shared = new SplashConfig(activity).getSharedPreferences();
        SharedPreferences.Editor editor = shared.edit();
        editor.putBoolean("First", false);
        editor.commit();
        setEvent(activity);
    }
    protected abstract void setEvent(Activity activity);
}
