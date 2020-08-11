package com.dimine.cardcar.domain.advance;

import com.dimine.cardcar.R;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 14:00
 * desc   :出厂设置
 * version: 1.0
 */
public class AdvancePresenter implements AdvanceContract.Presenter {

    private AdvanceSettingModel mModel;
    private AdvanceContract.View mAdvanceView;

    public AdvancePresenter(AdvanceSettingModel mModel, AdvanceContract.View mAdvanceView) {
        this.mModel = mModel;
        this.mAdvanceView = mAdvanceView;
        this.mAdvanceView.setPresenter(this);
    }

    @Override
    public void loadTitle() {
        mAdvanceView.showTitle(R.string.setting_first);
    }

    @Override
    public void loadDeviceTitle() {
        mAdvanceView.showDeviceTitle(mModel.getDeviceTitle());
    }

    @Override
    public void loadDeviceType() {
        mAdvanceView.showDeviceType(mModel.getDeviceTypeId());
    }

    @Override
    public void loadDeviceNumber() {
        mAdvanceView.showDeviceNumber(mModel.getDeviceNumber());
    }

    @Override
    public void loadDeviceId() {
        mAdvanceView.showDeviceId(mModel.getDeviceId());
    }


    @Override
    public void loadRemark() {
        mAdvanceView.showRemark(mModel.getSettingsRemark());
    }

    @Override
    public void deviceIdChange(String number) {
        mModel.saveDeviceIdChange(number);
    }

    @Override
    public void voicePlay(String count) {
        mModel.saveVoicePlay(count);
    }

    @Override
    public void deviceTitleChange(String title) {
        mModel.saveDeviceTitleChange(title);
    }

    @Override
    public void deviceDesc(String desc) {
        mModel.saveDeviceDesc(desc);
    }

    @Override
    public void loadVoicePlay() {
        mAdvanceView.showVoicePlayCount(mModel.getVoiceCount());
    }


    @Override
    public void start() {
        loadTitle();
        loadDeviceTitle();
        loadDeviceNumber();
        loadDeviceType();
        loadDeviceId();
        loadRemark();
        loadVoicePlay();

    }

    @Override
    public void sipAutoChange() {
        boolean old = mModel.getSipAuto();
        mModel.saveSipAuto(!old);

    }

    @Override
    public boolean delData() {
        return mModel.delData();
    }
}
