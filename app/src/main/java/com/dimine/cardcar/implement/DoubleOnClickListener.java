package com.dimine.cardcar.implement;

import android.view.View;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/19 9:05
 * desc   :
 * version: 1.0
 */
public abstract class DoubleOnClickListener implements View.OnClickListener {

    static boolean enable = true;

    private static final Runnable ENABLE_AGAIN = new Runnable() {
        @Override
        public void run() {
            enable = true;
        }
    };

    @Override
    public void onClick(View v) {
        if (enable) {
            enable = false;
            v.post(ENABLE_AGAIN);
            doClick(v);
        }
    }

    public abstract void doClick(View v);
}
