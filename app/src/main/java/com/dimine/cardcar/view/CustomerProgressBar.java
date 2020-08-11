package com.dimine.cardcar.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.ProgressBar;

import com.dimine.cardcar.R;
import com.dimine.cardcar.utils.DensityUtils;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/27 10:36
 * desc   :
 * version: 1.0
 */
public class CustomerProgressBar extends ProgressBar {


    public CustomerProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CustomerProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Paint mPaint;

    private void init(Context context) {
        setIndeterminate(false);
        setIndeterminate(false);
        setIndeterminateDrawable(ContextCompat.getDrawable(context,
                android.R.drawable.progress_indeterminate_horizontal));
        setProgressDrawable(ContextCompat.getDrawable(context,
                R.drawable.progress_bar_bg));
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTypeface(Typeface.MONOSPACE);
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //进度
        mPaint.setColor(getResources().getColor(R.color.progress_bar_text_color));
        mPaint.setStrokeWidth(DensityUtils.dp2px(getContext(), 5));
        float x = getProgress() * (getWidth() * 1.0f / getMax());
        Log.d("CustomerProgressBar", "draw: " + getProgress());
        for (int i = 26; i < x; i += 15) {
            canvas.drawLine(i, 0, i + 15, getHeight() + 10, mPaint);
        }
    }
}
