package com.dimine.cardcar.domain.settingFragment;

import android.graphics.LinearGradient;
import android.graphics.Shader;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.domain.mainactivity.MainActivity;
import com.dimine.cardcar.implement.MyOnSeekBarChangeListener;
import com.dimine.cardcar.utils.AppUtils;
import com.dimine.cardcar.utils.AudioMngHelper;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.StorageUtils;
import com.dimine.cardcar.utils.SystemMemoryUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/20 8:59
 * desc   :
 * version: 1.0
 */
public class SettingFragment extends Fragment {

    private static final String TAG = SettingFragment.class.getSimpleName();

    @BindView(R.id.tvVersion)
    TextView tvVersion;
    @BindView(R.id.tvStorage)
    TextView tvStorage;
    @BindView(R.id.tvMemory)
    TextView tvMemory;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    @BindView(R.id.tvLess)
    TextView tvLess;
    @BindView(R.id.tvAdd)
    TextView tvAdd;
    @BindView(R.id.tvVoiceNumber)
    TextView tvVoiceNumber;

    private Unbinder unbinder;
    private AudioMngHelper audioMngHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings_status, container, false);
        unbinder = ButterKnife.bind(this, view);
        init();
        MyLog.d("life_cycle", "SettingFragment ====> onCreateView");
        return view;
    }

    protected void init() {
        if (getContext() != null) {
            audioMngHelper = new AudioMngHelper(getContext());
            seekBar.setMax(audioMngHelper.getSystemMaxVolume());
            seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
            seekBar.setOnSeekBarChangeListener(new MyOnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    MyLog.d(TAG, "onProgressChanged: " + "progress = "
                            + progress + " fromUser" + fromUser);
                    modifyBySystem(progress);
                    showVoiceNumber();
                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {
                    super.onStopTrackingTouch(seekBar);
                    //调节音量后，播放一段，以便感受音量的调节是否合适
                    if (getActivity() != null) {
                        ((MainActivity) getActivity()).showVoice(
                                getContext().getString(R.string.test_voice_message));
                    }
                }
            });
        }

        LinearGradient mLinearGradientLess = new LinearGradient(0, 0, 0,
                tvLess.getPaint().getTextSize(), getResources().getColor(R.color.voice_less_start_color)
                , getResources().getColor(R.color.voice_less_end_color), Shader.TileMode.CLAMP);
        tvLess.getPaint().setShader(mLinearGradientLess);
        LinearGradient mLinearGradientAdd = new LinearGradient(0, 0, 0,
                tvAdd.getPaint().getTextSize(), getResources().getColor(R.color.voice_add_start_color)
                , getResources().getColor(R.color.voice_add_end_color), Shader.TileMode.CLAMP);
        tvAdd.getPaint().setShader(mLinearGradientAdd);

        MyLog.d(TAG, "【SystemMaxVolume】" + audioMngHelper.getSystemMaxVolume() +
                "【SystemCurrentVolume】" + audioMngHelper.getSystemCurrentVolume() +
                "【100】" + audioMngHelper.get100CurrentVolume());
        showVoiceNumber();
    }

    private void showVoiceNumber() {
        tvVoiceNumber.setText(audioMngHelper.get100CurrentVolume() + "");
    }

    @Override
    public void onResume() {
        super.onResume();
        String ram = LocalArguments.getInstance().getRam() + "GB";
        String rom = LocalArguments.getInstance().getRom() + "GB";
        if ("0GB".equals(ram)) {
            tvMemory.setText(SystemMemoryUtils.getAvailMemory(getContext()) + "/" +
                    SystemMemoryUtils.getTotalMemory(getContext()));
        } else {
            tvMemory.setText(SystemMemoryUtils.getAvailMemory(getContext()) + "/" +
                    ram);
        }

        if ("0GB".equals(rom)) {
            tvStorage.setText(StorageUtils.getAvailableInternalMemorySize(getContext()) + "/" +
                    StorageUtils.getInternalMemorySize(getContext()));
        } else {
            tvStorage.setText(StorageUtils.getAvailableInternalMemorySize(getContext()) + "/" +
                    rom);
        }

        tvVersion.setText(AppUtils.getVersionName(getContext()));
    }

    private void modifyBySystem(int progress) {
        audioMngHelper.addVoiceSystem().setVoice(progress);
        LocalArguments.getInstance().saveVolumeNumber(progress);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        MyLog.d("life_cycle", "SettingFragment ====> onDestroyView");
    }

    private int step = 10;

    @OnClick(R.id.llVoiceLess)
    public void onLlVoiceLessClicked() {
        int current = Integer.parseInt(tvVoiceNumber.getText().toString());
        audioMngHelper.setVoice100((current - step) > 0 ? (current - step) : 0);
        seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
        MyLog.d("SettingFragment", "onLlVoiceLessClicked");
    }

    @OnClick(R.id.llVoiceAdd)
    public void onLlVoiceAddClicked() {
        int current = Integer.parseInt(tvVoiceNumber.getText().toString());
        audioMngHelper.setVoice100((current + step) <= 100 ? (current + step) : 100);
        seekBar.setProgress(audioMngHelper.getSystemCurrentVolume());
        MyLog.d("SettingFragment", "onLlVoiceAddClicked");
    }

    @OnClick(R.id.versionDesc)
    public void versionDescClicked() {
        /*
         * 提示：已经接入Bugly用户改用上面的初始化方法,不影响原有的crash上报功能; init方法会自动检测更新，
         * 不需要再手动调用Beta.checkUpgrade(), 如需增加自动检查时机可以使用Beta.checkUpgrade(false,false);
         *
         * 参数1：isManual 用户手动点击检查，非用户点击操作请传false
         *
         * 参数2：isSilence 是否显示弹窗等交互，[true:没有弹窗和toast] [false:有弹窗或toast]
         */
//        Beta.checkUpgrade();
    }
}
