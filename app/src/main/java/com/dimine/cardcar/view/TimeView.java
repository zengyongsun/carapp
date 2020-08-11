package com.dimine.cardcar.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;

import com.dimine.cardcar.R;
import com.dimine.cardcar.utils.MyLog;

import java.lang.ref.WeakReference;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/30 16:43
 * desc   : 时间显示的自定义View
 * version: 1.0
 */
public class TimeView extends AppCompatTextView {

    private Handler mHandler;

    private int hasType;

    private final int has_all = 1;
    private final int no_has_second = 2;
    private final int no_has_yymmdd = 3;

    //采用弱引用防止内存泄漏
    private static final class TimerHandler extends Handler {
        private WeakReference<TimeView> viewWeakReference;

        private TimerHandler(TimeView timeView) {
            viewWeakReference = new WeakReference<>(timeView);
        }

        @Override
        public void handleMessage(Message msg) {
            TimeView view = viewWeakReference.get();
            if (view != null) {
                if (view.hasType != view.no_has_second) {
                    view.refreshTime();
                } else {
                    view.refreshTimeNoSecond();
                }
                view.invalidate();
                sendEmptyMessageDelayed(1, 1000);
            }
        }
    }

    public TimeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //获取自定义属性的集合
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TimeView);

        //获取单个属性，R.styleable+属性集合名称+下划线+属性名称
        hasType = typedArray.getInt(R.styleable.TimeView_haveSecond, has_all);
        //回收 TypedArray对象
        typedArray.recycle();

        initView();

        mHandler = new TimerHandler(this);
        if (hasType != no_has_second) {
            refreshTime();
        } else {
            refreshTimeNoSecond();
        }
        mHandler.sendEmptyMessageDelayed(1, 1000);
    }

    private String dtf;
    SimpleDateFormat fmt;

    private void initView() {
        if (hasType == has_all) {
            dtf = "HH:mm:ss  yyyy/MM/dd";
        } else if (hasType == no_has_second) {
            dtf = "hh:mm  yyyy-MM-dd";
        } else if (hasType == no_has_yymmdd) {
            dtf = "h:mm:ss";
        }
        fmt = new SimpleDateFormat(dtf);
    }

    private void refreshTime() {
        setText(fmt.format(new Date()));
    }

    private int count = 1;

    private void refreshTimeNoSecond() {
        if ((count & 1) == 0) {
            dtf = "yyyy-MM-dd  hh:mm";
        } else {
            dtf = "yyyy-MM-dd  hh mm";
        }
        count++;
        fmt = new SimpleDateFormat(dtf);
        setText(fmt.format(new Date()));
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        MyLog.d("调用了 onFinishInflate");
    }


}
