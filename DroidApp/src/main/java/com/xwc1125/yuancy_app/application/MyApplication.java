package com.xwc1125.yuancy_app.application;

import com.yuancy.framework.db.DBApplication;

/**
 * Class: com.xwc1125.yuancy_app.application <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/3  14:41 <br>
 */
public class MyApplication extends com.xwc1125.droidmodule.MyApplication {
    @Override
    public void onCreate() {
        super.onCreate();
        //DB的初始化
        DBApplication.init(this);
    }
}
