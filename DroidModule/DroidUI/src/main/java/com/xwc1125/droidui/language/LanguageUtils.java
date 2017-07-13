package com.xwc1125.droidui.language;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;

import com.xwc1125.droidui.language.LanguageChangableView;

import java.util.Locale;

/**
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/12  18:41 <br>
 */
public class LanguageUtils {
    /**
     * 更新UI
     * @param view
     */
    public static void updateView(View view) {
        if (view instanceof ViewGroup) {
            ViewGroup vg = (ViewGroup) view;
            int count = vg.getChildCount();
            for (int i = 0; i < count; i++) {
                updateView(vg.getChildAt(i));
            }
        } else if (view instanceof LanguageChangableView) {
            LanguageChangableView tv = (LanguageChangableView) view;
            tv.reLoadLanguage();
        }
    }

    /**
     * 刷新语言
     */
    public static void switchLanguage(Context context, String language) {
        // 本地语言设置
        Locale myLocale = new Locale(language);
        //应用内配置语言
        Resources resources = context.getResources();//获得res资源对象
        Configuration config = resources.getConfiguration();//获得设置对象
        DisplayMetrics dm = resources.getDisplayMetrics();//获得屏幕参数：主要是分辨率，像素等。
        config.locale = myLocale;
        resources.updateConfiguration(config, dm);
    }

    public static String getLocaleLanguage(Context context) {
        Locale locale = context.getResources().getConfiguration().locale;
        String language = locale.getLanguage();
        return language;
    }

    public static String getLanguage(Context context) {
        String language = context.getSharedPreferences("language_config",
                Context.MODE_PRIVATE).getString("language", "zh");
        return language;
    }

    public static void setLanguage(Context context, String language) {
        context.getSharedPreferences("language_config", Context.MODE_PRIVATE).edit()
                .putString("language", language).commit();
    }
}
