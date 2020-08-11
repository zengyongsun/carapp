package com.dimine.cardcar.domain;

import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.implement.MyOnSeekBarChangeListener;
import com.dimine.cardcar.utils.AppUtils;
import com.dimine.cardcar.utils.AudioMngHelper;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.StorageUtils;
import com.dimine.cardcar.utils.SystemMemoryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SettingsStatusActivity extends AppCompatActivity {

    private static final String TAG = SettingsStatusActivity.class.getSimpleName();

    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvStorage)
    TextView tvStorage;
    @BindView(R.id.tvMemory)
    TextView tvMemory;
    @BindView(R.id.seekBar)
    SeekBar seekBar;
    @BindView(R.id.topLayout)
    RelativeLayout topLayout;

    @BindView(R.id.tvLess)
    TextView tvLess;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.tvVoiceNumber)
    TextView tvVoiceNumber;

    private AudioMngHelper audioMngHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_status);
        fullscreen();
        ButterKnife.bind(this);
        init();
    }

    protected void init() {
        audioMngHelper = new AudioMngHelper(this);
        seekBar.setMax(audioMngHelper.getSystemMaxVolume());
        seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
        seekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                MyLog.d(TAG, "onProgressChanged: " + "progress = " + progress +
                        " fromUser" + fromUser);
                modifyBySystem(progress);
                showVoiceNumber();
            }
        });

        LinearGradient mLinearGradientLess = new LinearGradient(0, 0, 0,
                tvLess.getPaint().getTextSize(), getResources().getColor(R.color.voice_less_start_color)
                , getResources().getColor(R.color.voice_less_end_color), Shader.TileMode.CLAMP);
        tvLess.getPaint().setShader(mLinearGradientLess);
        LinearGradient mLinearGradientAdd = new LinearGradient(0, 0, 0,
                tvAdd.getPaint().getTextSize(), getResources().getColor(R.color.voice_add_start_color)
                , getResources().getColor(R.color.voice_add_end_color), Shader.TileMode.CLAMP);
        tvAdd.getPaint().setShader(mLinearGradientAdd);

        showVoiceNumber();
    }

    private void showVoiceNumber() {
        tvVoiceNumber.setText(audioMngHelper.get100CurrentVolume() + "");
    }


    private void modifyBySystem(int progress) {
        audioMngHelper.addVoiceSystem().setVoice(progress);
        LocalArguments.getInstance().saveVolumeNumber(progress);
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvStorage.setText(StorageUtils.getAvailableInternalMemorySize(this) + "/" +
                StorageUtils.getInternalMemorySize(this));
        tvMemory.setText(SystemMemoryUtils.getAvailMemory(this) + "/" +
                SystemMemoryUtils.getTotalMemory(this));
        tvVersion.setText(AppUtils.getVersionName(this));
    }

    @OnClick(R.id.ivBack)
    public void onViewClicked() {
        finish();
    }

    private void fullscreen() {
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus && Build.VERSION.SDK_INT >= 19) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }

    private int step = 10;

    @OnClick(R.id.llVoiceLess)
    public void onLlVoiceLessClicked() {
        int current = Integer.parseInt(tvVoiceNumber.getText().toString());
        audioMngHelper.setVoice100((current - step) > 0 ? (current - step) : 0);
        seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
    }

    @OnClick(R.id.llVoiceAdd)
    public void onLlVoiceAddClicked() {
        int current = Integer.parseInt(tvVoiceNumber.getText().toString());
        audioMngHelper.setVoice100((current + step) <= 100 ? (current + step) : 100);
        seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
    }

    @OnClick({R.id.versionDesc, R.id.tvVersion})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.versionDesc:
            case R.id.tvVersion:
                checkUpdate();
                break;
        }
    }

    private void checkUpdate() {

    }
}
