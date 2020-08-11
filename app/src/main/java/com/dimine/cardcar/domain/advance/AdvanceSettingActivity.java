package com.dimine.cardcar.domain.advance;

import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.widget.Button;
import android.widget.TextView;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.domain.SettingsStatusActivity;
import com.dimine.cardcar.domain.login.LoginActivity;
import com.dimine.cardcar.domain.settings.SettingsActivity;
import com.dimine.cardcar.screentest.ScreenTestActivity;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.view.dialog.InputCarIdDialog;
import com.dimine.cardcar.view.dialog.InputDialog;
import com.dimine.cardcar.view.dialog.InputTitleDialog;
import com.dimine.cardcar.view.dialog.ModifyStateDialog;
import com.dimine.cardcar.view.dialog.SettingModifyDialog;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/10 11:41
 * desc   :
 * version: 1.0
 */
public class AdvanceSettingActivity extends BaseActivity implements AdvanceContract.View {

    private static final String TAG = AdvanceSettingActivity.class.getSimpleName();

    @BindView(R.id.topTitle)
    TextView topTitle;

    @BindView(R.id.tvTerminalNumber)
    TextView tvTerminalNumber;
    @BindView(R.id.tvTerminalName)
    TextView tvTerminalName;
    @BindView(R.id.tvDeviceId)
    TextView tvDeviceId;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvMessage)
    TextView tvMessage;
    @BindView(R.id.tvCount)
    TextView tvCount;

    @BindView(R.id.sipAutoSetting)
    Button sipAutoSetting;

    private SettingModifyDialog settingModifyDialog;

    private AdvanceContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_advance_setting_layout);
        ButterKnife.bind(this);
        new AdvancePresenter(new AdvanceSettingModel(), this);
    }


    @Override
    public void showToastMessage(String message) {
        MyToast.showShort(this, message);
    }

    @Override
    public void showDeviceType(int typeId) {
        if (typeId == CarType.Forklift.getTypeId()) {
            tvTerminalName.setText("铲车");
        } else if (typeId == CarType.Truck.getTypeId()) {
            tvTerminalName.setText("卡车");
        }
    }

    @Override
    public void showVoicePlayCount(String count) {
        tvCount.setText(count);
    }


    @OnClick(R.id.tvName)
    public void onIvBackClicked() {
        finish();
    }

    @OnClick(R.id.mbGoSystem)
    public void onMbGoSystemClicked() {
        if (settingModifyDialog == null) {
            settingModifyDialog = new SettingModifyDialog(this);
        }
        settingModifyDialog.show();
    }

    @OnClick(R.id.terminalNumberLayout)
    public void onTerminalNumberLayoutClicked() {
        InputCarIdDialog inputDialog = new InputCarIdDialog(this,
                InputCarIdDialog.PASSWORD_TYPE);
        inputDialog.setInputName(getString(R.string.device_number_desc));
        inputDialog.setListener(content -> {
            mPresenter.deviceIdChange(content);
            mPresenter.loadDeviceNumber();
            mPresenter.loadDeviceType();
            mPresenter.loadDeviceTitle();
            inputDialog.dismiss();
        });
        inputDialog.show();
    }

    @OnClick(R.id.deviceTitle)
    public void onViewDeviceTitleClicked() {
        InputTitleDialog inputDialog = new InputTitleDialog(this,
                InputTitleDialog.title_type);
        inputDialog.setinputName(getString(R.string.settings_device_title));
        inputDialog.setListener(content -> {
            mPresenter.deviceTitleChange(content);
            mPresenter.loadDeviceTitle();
            inputDialog.dismiss();
        });
        inputDialog.show();
    }

    @OnClick(R.id.layoutMessage)
    public void onMessageLayoutClicked() {
        InputTitleDialog inputDialog = new InputTitleDialog(this,
                InputTitleDialog.desc_type);
        inputDialog.setinputName(getString(R.string.settings_device_remark));
        inputDialog.setListener(content -> {
            mPresenter.deviceDesc(content);
            mPresenter.loadRemark();
            inputDialog.dismiss();
        });
        inputDialog.show();
    }

    @OnClick(R.id.mDeviceState)
    public void onDeviceStateClicked() {
        ModifyStateDialog inputDialog = new ModifyStateDialog(this);
        inputDialog.show();
    }


    @OnClick(R.id.sipAutoSetting)
    public void onSipAutoClicked() {
        mPresenter.sipAutoChange();
    }

    @OnClick(R.id.restart)
    public void onRestartAppClicked() {
        restartApp();
    }


    /**
     * 重新启动App -> 杀进程,会短暂黑屏,启动慢
     */
    public void restartApp() {
        //启动页
        Intent intent = new Intent(Helper.getContext(), LoginActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        Helper.getContext().startActivity(intent);
        Process.killProcess(Process.myPid());
    }


    @OnClick(R.id.sipSetting)
    public void onViewClicked() {

    }


    @OnClick(R.id.btCamera)
    public void onOpenCameraClicked() {
        Intent captureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivity(captureIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    public void showTitle(int title) {
        topTitle.setText(title);
    }

    @Override
    public void showDeviceTitle(String title) {
        tvTitle.setText(title);
    }

    @Override
    public void showDeviceNumber(String number) {
        tvTerminalNumber.setText(number);
    }

    @Override
    public void showDeviceId(String id) {
        tvDeviceId.setText(id);
    }

    @Override
    public void showRemark(String remark) {
        tvMessage.setText(remark);
    }


    @Override
    public void setPresenter(AdvanceContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @OnClick(R.id.voiceLayout)
    public void onMPlayVoiceClicked() {
        voice("欢迎使用智能调度车载终端");
    }

    @OnClick(R.id.mImplementation)
    public void onMImplementationClicked() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @OnClick(R.id.mUserSetting)
    public void onMUserSettingClicked() {
        startActivity(new Intent(this, SettingsStatusActivity.class));
    }

    @OnClick(R.id.btDel)
    public void onDelClicked() {
        boolean result = mPresenter.delData();
        MyToast.showShort(this, "清除数据" + (result ? "成功" : "失败"));
    }

    @OnClick(R.id.voiceNumber)
    public void onVoiceNumberClicked() {
        InputDialog inputDialog = new InputDialog(this, InputDialog.NUMBER_TYPE);
        inputDialog.setInputName(getString(R.string.dialog_voic_count));
        inputDialog.setListener(content -> {
            mPresenter.voicePlay(content);
            mPresenter.loadVoicePlay();
            inputDialog.dismiss();
        });
        inputDialog.show();
    }


    @OnClick(R.id.screenTest)
    public void onScreenTestClicked() {
        startActivity(new Intent(this, ScreenTestActivity.class));
    }

    public void voice(String str) {
        Helper.getHelper().getApp().voice(str);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
