package com.xwc1125.droidmodule.Splash;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.xwc1125.droidmodule.R;
import com.xwc1125.droidmodule.Splash.config.SplashConfig;
import com.xwc1125.droidmodule.Welcome.ModuleWelcomeActivity;

/**
 * An full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public abstract class ModuleSplashActivity extends Activity {
    private boolean first; // 判断是否第一次打开软件
    private View view;
    private static Activity activity;
    private Animation animation;
    private SharedPreferences shared;
    private static int TIME = 1000; // 进入主程序的延迟时间

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activity = this; // 得到上下文
        int layoutResId = setContentView();
        if (layoutResId == 0) {
            //默认的UI
            layoutResId = R.layout.splash_activity;
        }
        view = View.inflate(this, layoutResId, null);
        setContentView(view);
        shared = new SplashConfig(activity).getSharedPreferences(); // 得到配置文件
    }
    protected abstract int setContentView();

    @Override
    protected void onResume() {
        into();
        super.onResume();
    }

    public void onPause() {
        super.onPause();
    }

    /**
     * Method:  into<br>
     * Description:  进入主程序的方法
     *
     * @author xwc1125 <br>
     * @date 2017/5/23 16:58
     */
    private void into() {
        // 如果网络可用则判断是否第一次进入，如果是第一次则进入欢迎界面
        first = shared.getBoolean("First", true);
        // 如果第一次，则进入引导页WelcomeActivity
        first = false;
        if (first) {
            Intent intent = new Intent(activity, ModuleWelcomeActivity.class);
            startActivity(intent);
            // 设置Activity的切换效果
            overridePendingTransition(R.anim.in_from_right, R.anim.out_to_left);
            activity.finish();
            return;
        }
        // 设置动画效果是alpha，在anim目录下的alpha.xml文件中定义动画效果
        animation = AnimationUtils.loadAnimation(this, R.anim.alpha);
        // 给view设置动画效果
        view.startAnimation(animation);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation arg0) {
            }

            @Override
            public void onAnimationRepeat(Animation arg0) {
            }

            // 这里监听动画结束的动作，在动画结束的时候开启一个线程，这个线程中绑定一个Handler,并
            // 在这个Handler中调用goHome方法，而通过postDelayed方法使这个方法延迟500毫秒执行，达到
            // 达到持续显示第一屏500毫秒的效果
            @Override
            public void onAnimationEnd(Animation arg0) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        setEvent(activity);
                    }
                }, TIME);
            }
        });
    }

    protected abstract void setEvent(Activity activity);
}
