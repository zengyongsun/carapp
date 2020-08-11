package com.dimine.cardcar.domain.login;


import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.data.bean.UserBean_;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.MyLog;

import java.util.List;

import io.objectbox.Box;
import io.objectbox.query.QueryBuilder;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/30 15:21
 * desc   :
 * version: 1.0
 */
public class LoginModel {

    private Box<UserBean> userBeanBox;

    public LoginModel(Box<UserBean> box) {
        this.userBeanBox = box;
    }

    public void putUser(UserBean userBean) {
        UserBean findBean = queryUser(userBean.UserId);
        MyLog.d("LoginModel", "putUser: " + findBean);
        if (findBean == null) {
            userBeanBox.put(userBean);
        } else {
            findBean.lastLoginTime = userBean.lastLoginTime;
            userBeanBox.put(findBean);
        }
    }

    public List<UserBean> getAll() {
        return userBeanBox.getAll();
    }

    public List<UserBean> getCountUser(int count) {
        return userBeanBox.query()
                .equal(UserBean_.type, LocalArguments.getInstance().carTypeId())
                .order(UserBean_.lastLoginTime, QueryBuilder.DESCENDING)
                .build()
                .find(0, count);
    }

    public UserBean queryUser(String account) {
        return userBeanBox.query().equal(UserBean_.UserId, account).build().findFirst();
    }

    public int getCarTypeId() {
        return LocalArguments.getInstance().carTypeId();
    }

    public String getDeviceTitle() {
        return LocalArguments.getInstance().deviceTitle();
    }

    public String getLoginUserName() {
        return LocalArguments.getInstance().userName();
    }

    public String getAdvancePwd() {
        return LocalArguments.getInstance().advanceSettingsPwd();
    }

    public String getFactoryPwd() {
        return LocalArguments.getInstance().factorySettingsPwd();
    }


    public void saveUserId(String userId) {
        LocalArguments.getInstance().saveUserId(userId);
    }

    public void saveUserName(String userName) {
        LocalArguments.getInstance().saveUserName(userName);
    }

    public String getUserId() {
        return LocalArguments.getInstance().userId();
    }

    public String getDeviceNumber() {
        return LocalArguments.getInstance().deviceNumber();
    }

    public int getGpsUpload() {
        return Integer.parseInt(LocalArguments.getInstance().gpsUploadSpeed());
    }

    public void saveGpsUploadSpeed(String speed) {
        LocalArguments.getInstance().saveGpsUploadSpeed(speed);
    }

    public void saveMaxSpeed(String maxSpeed) {
        LocalArguments.getInstance().saveMaxSpeed(maxSpeed);
    }

    public void saveWriteLog(boolean log) {
        LocalArguments.getInstance().saveWriteLog(log);
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
