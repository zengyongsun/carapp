package com.dimine.cardcar.domain.settings;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 11:17
 * desc   :
 * version: 1.0
 */
public class SettingsPresenter implements SettingsContract.Presenter {

    private final SettingsContract.View mSettingView;
    private final SettingModel mModel;

    public SettingsPresenter(SettingModel settingModel, SettingsContract.View settingsView) {
        mModel = settingModel;
        mSettingView = settingsView;
        mSettingView.setPresenter(this);
    }

    @Override
    public void loadLocalIp() {
        mSettingView.showLocalIp(mModel.getLocalIp());
    }

    @Override
    public void loadLocalPort() {
        mSettingView.showLocalPort(mModel.getLocalPort());
    }


    @Override
    public void loadDeviceNumber() {
        mSettingView.showDeviceNumber(mModel.getDeviceNumber());
    }

    @Override
    public void saveCardId(String cardId) {
        mModel.saveCardId(cardId);
        loadCardId();
    }

    @Override
    public void loadCardId() {
        mSettingView.showCardId(mModel.getCardId());
    }

    @Override
    public void loadServiceIp() {
        mSettingView.showServerIp(mModel.getServiceIp());
    }

    @Override
    public void loadServicePort() {
        mSettingView.showServerPort(mModel.getServicePort());
    }

    @Override
    public void loadMask() {
        mSettingView.showMask(mModel.getMask());
    }

    @Override
    public void loadGateWay() {
        mSettingView.showGateWay(mModel.getGateWay());
    }


    @Override
    public void start() {
        loadDeviceNumber();
        loadCardId();

        loadLocalIp();
        loadLocalPort();
        loadMask();
        loadGateWay();

        loadServiceIp();
        loadServicePort();
    }
}
