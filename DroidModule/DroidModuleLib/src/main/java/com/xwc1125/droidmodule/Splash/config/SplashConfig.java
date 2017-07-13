package com.xwc1125.droidmodule.Splash.config;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Class: com.xwc1125.safewallet.module.Splash.config <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/28  10:44 <br>
 */
public class SplashConfig {
    private Context mContext;
    private SharedPreferences sharedPreferences;

    public SplashConfig(Context context) {
        this.mContext = context;
        sharedPreferences = context.getSharedPreferences("splash_config", Context.MODE_PRIVATE);
    }

    public SharedPreferences getSharedPreferences() {
        return sharedPreferences;
    }
}
