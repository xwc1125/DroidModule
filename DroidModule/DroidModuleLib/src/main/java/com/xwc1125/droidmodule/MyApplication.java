package com.xwc1125.droidmodule;

import android.app.Application;

import org.xutils.x;

/**
 * Class: com.xwc1125.yuancy_app.application <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/3  14:41 <br>
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
    }
}
