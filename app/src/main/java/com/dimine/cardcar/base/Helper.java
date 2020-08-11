package com.dimine.cardcar.base;

import android.content.Context;
import android.os.Handler;

import com.dimine.cardcar.MyApplication;

/**
 * 全局helper
 */
public class Helper {

    public static final String INIT_TAG = "初始化 ==> ";

    private static Helper helper;
    private MyApplication app;
    private Context context;
    private Handler handler;

    public static void init(MyApplication app) {
        if (helper != null) {
            return;
        }
        helper = new Helper();
        helper.app = app;
        helper.context = helper.app.getApplicationContext();
        helper.handler = new Handler(helper.context.getMainLooper());
    }

    public static Handler getHandler() {
        return helper.handler;
    }

    public static Context getContext() {
        return helper.context;
    }

    public static Helper getHelper() {
        return helper;
    }

    public MyApplication getApp() {
        return app;
    }
}
