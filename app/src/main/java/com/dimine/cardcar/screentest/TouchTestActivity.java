package com.dimine.cardcar.screentest;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.widget.ImageView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.utils.MyLog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class TouchTestActivity extends BaseActivity {

    @BindView(R.id.image)
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_touch_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.image)
    public void onTvNameClicked() {
        finish();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float x = event.getX();
            float y = event.getY();
        }

        if (event.getAction() == MotionEvent.ACTION_UP) {
            float x = event.getX();
            float y = event.getY();
            MyLog.d("imageView", "[x]= " + x + "   [y] =" + y);
            MyLog.d("imageView", "[x_left]= " + imageView.getLeft() +
                    "[x_right]= " + imageView.getRight() + "   [y_top] =" + imageView.getTop()
                    + "   [y_bottom] =" + imageView.getBottom());

            float curTranslationX = imageView.getTranslationX();
            float curTranslationY = imageView.getTranslationY();
            MyLog.d("imageView", "[TranslationX]= " + imageView.getTranslationX() +
                    "   [TranslationY] =" + imageView.getTranslationY());
            ObjectAnimator.ofFloat(imageView, "translationX",
                    curTranslationX, x - 30).start();
            ObjectAnimator.ofFloat(imageView, "translationY",
                    curTranslationY, y - 30).start();
            MyLog.d("imageView", "[TranslationX]= " + imageView.getTranslationX() +
                    "   [TranslationY] =" + imageView.getTranslationY());
        }

        return super.onTouchEvent(event);
    }
}
