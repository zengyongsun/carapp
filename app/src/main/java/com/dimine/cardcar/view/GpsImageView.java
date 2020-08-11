package com.dimine.cardcar.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.dimine.cardcar.R;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/7/12 10:01
 * desc   :
 * version: 1.0
 */
public class GpsImageView extends AppCompatImageView {

    public GpsImageView(Context context) {
        super(context);
    }

    public GpsImageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void setGpsImg(int signal) {
        if (signal == 0) {
            setImageResource(R.mipmap.icon_gps_no);
            return;
        }
        if (signal > 7) {
            setImageResource(R.mipmap.icon_gps);
        } else if (signal > 5) {
            setImageResource(R.mipmap.icon_gps_1);
        }else if (signal>3){
            setImageResource(R.mipmap.icon_gps_2);
        }
    }


}
