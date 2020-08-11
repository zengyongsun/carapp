package com.dimine.cardcar.screentest;

import android.graphics.Color;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.mvpBase.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ColorTestActivity extends BaseActivity {


    /**
     * 红、绿、蓝、黄、白、黑
     */
    private int[] colors = new int[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.WHITE, Color.BLACK};
    @BindView(R.id.root)
    ConstraintLayout root;

    private int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_test);
        ButterKnife.bind(this);
        root.setBackgroundColor(colors[index]);
        index++;
    }

    @OnClick(R.id.root)
    public void onViewClicked() {
        if (index >= colors.length) {
            finish();
        }else {
            root.setBackgroundColor(colors[index]);
            index++;
        }
    }
}
