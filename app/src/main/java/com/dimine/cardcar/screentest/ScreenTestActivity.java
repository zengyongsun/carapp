package com.dimine.cardcar.screentest;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.mvpBase.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ScreenTestActivity extends BaseActivity {

    @BindView(R.id.color)
    TextView color;
    @BindView(R.id.touch)
    TextView touch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_test);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.color)
    public void onColorClicked() {
        startActivity(new Intent(this, ColorTestActivity.class));
    }

    @OnClick(R.id.touch)
    public void onTouchClicked() {
        startActivity(new Intent(this, TouchTestActivity.class));
    }

    @OnClick(R.id.tvName)
    public void onBackClicked() {
        finish();
    }
}
