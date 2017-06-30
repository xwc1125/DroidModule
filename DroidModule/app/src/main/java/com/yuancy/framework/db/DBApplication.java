package com.yuancy.framework.db;

import android.app.Application;
import android.content.Context;

import java.util.InputMismatchException;
import java.util.Vector;

/**
 * Class: com.yuancy.framework.db.db <br>
 * Description: TODO <br>
 *
 * @author xwc1125 <br>
 * @version V1.0
 * @Copyright: Copyright (c) 2017 <br>
 * @date 2017/6/3  14:44 <br>
 */
public class DBApplication {
    private static DBApplication instance = null;
    private static Application application = null;

    private DBApplication() {

    }

    public static DBApplication init(Application application) {
        if (instance == null) {
            syncInit(application);
        }
        return instance;
    }

    private static synchronized void syncInit(Application application) {
        if (instance == null) {
            instance = new DBApplication();
            DBApplication.application = application;
        }
    }

    public static Application getApp() {
        if (application == null) {
            throw new InputMismatchException("DBApplication is not initialed");
        }
        return application;
    }

}
