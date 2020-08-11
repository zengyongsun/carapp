package com.dimine.cardcar.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
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
public class CustomerCarProgressBar extends ProgressBar {



    public CustomerCarProgressBar(Context context) {
        super(context);
        init(context);
    }

    public CustomerCarProgressBar(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    private Paint mPaint;

    private void init(Context context) {
        setIndeterminate(false);
        setIndeterminate(false);
        setIndeterminateDrawable(ContextCompat.getDrawable(context,
                android.R.drawable.progress_indeterminate_horizontal));
        mPaint = new Paint();
        mPaint.setDither(true);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setTypeface(Typeface.MONOSPACE);

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);

        // TODO: 2019/9/27 需要优化，目前只实现效果，应该自定义在一个里面
        Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.icon_progress_bar_car);
        float iconX = getProgress() * (getWidth() * 1.0f / getMax()) - icon.getWidth() / 2+ DensityUtils.px2dp(getContext(), 16);
        float iconY = getHeight() / 2 - 10;
        canvas.drawBitmap(icon, iconX, iconY, mPaint);

        if (!icon.isRecycled()) {
            icon.isRecycled();
        }

    }

}
