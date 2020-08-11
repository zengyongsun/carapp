package com.dimine.cardcar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dimine.cardcar.R;
import com.dimine.cardcar.utils.MyLog;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/7/12 10:01
 * desc   :
 * version: 1.0
 */
public class SignalImageView extends AppCompatImageView {

    public SignalImageView(Context context) {
        super(context);
    }

    public SignalImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setSignalImg(int signal) {
        MyLog.d("TelnetManager#status", "setSignalImg: " + signal);
        if (signal == 0) {
            setImageResource(R.mipmap.icon_4g_no);
            return;
        }
        if (signal > -64) {
            setImageResource(R.mipmap.icon_4g);
        } else if (signal > -72) {
            setImageResource(R.mipmap.icon_4g_3);
        } else if (signal > -80) {
            setImageResource(R.mipmap.icon_4g_2);
        } else if (signal > -88) {
            setImageResource(R.mipmap.icon_4g_1);
        } else {
            setImageResource(R.mipmap.icon_4g_no);
        }
    }
}
