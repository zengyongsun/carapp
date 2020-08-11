package com.dimine.cardcar.utils;

import android.content.Context;
import android.util.Log;


import com.dimine.cardcar.BuildConfig;
import com.dimine.cardcar.data.bean.MyObjectBox;

import io.objectbox.BoxStore;
import io.objectbox.android.AndroidObjectBrowser;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/4/11 13:29
 * desc   : 参考｛https://docs.objectbox.io/getting-started｝
 * version: 1.0
 */
public class ObjectBox {

    private static BoxStore boxStore;

    public static void init(Context context) {
        boxStore = MyObjectBox.builder()
                .androidContext(context.getApplicationContext())
                .build();
        //在浏览器调试用
        if (BuildConfig.DEBUG) {
            boolean started = new AndroidObjectBrowser(ObjectBox.get()).start(context);
            Log.i("ObjectBrowser", "Started: " + started);
        }
    }

    public static BoxStore get() {
        return boxStore;
    }

}
