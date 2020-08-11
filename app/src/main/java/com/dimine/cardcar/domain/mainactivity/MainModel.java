package com.dimine.cardcar.domain.mainactivity;


import com.dimine.cardcar.data.local.LocalArguments;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 15:21
 * desc   :
 * version: 1.0
 */
public class MainModel {

    public int getCarTypeId() {
        return LocalArguments.getInstance().carTypeId();
    }

    public String getDeviceTitle() {
        return LocalArguments.getInstance().deviceTitle();
    }

    public boolean getNightModel() {
        return LocalArguments.getInstance().isNight();
    }

    public void saveUserName(String userName) {
        LocalArguments.getInstance().saveUserName(userName);
    }

    public String getUserName() {
        return LocalArguments.getInstance().userName();
    }

    public void saveNightModel(boolean isModel) {
        LocalArguments.getInstance().saveNight(isModel);
    }

    public String getSipDomain() {
        return LocalArguments.getInstance().sipDomain();
    }

    public String getSipNumber() {
        return LocalArguments.getInstance().sipCallNumber();
    }

    public double getCarMaxSpeed() {
        return Double.parseDouble(LocalArguments.getInstance().maxSpeed());
    }

    public String getAdvancePwd() {
        return LocalArguments.getInstance().advanceSettingsPwd();
    }

    public String getUserId() {
        return LocalArguments.getInstance().userId();
    }

    public void saveUserId(String userId) {
        LocalArguments.getInstance().saveUserId(userId);
    }

    public int getGpsUpload() {
        return Integer.parseInt(LocalArguments.getInstance().gpsUploadSpeed());
    }

    public String getDeviceNumber() {
        return LocalArguments.getInstance().deviceNumber();
    }
    /**
     * D ,铲车，T 卡车
     *
     * @return
     */
    public String getCarType() {
        if (getCarTypeId() == 1) {
            return "D";
        }
        if (getCarTypeId() == 2) {
            return "T";
        }
        return "";
    }
}
