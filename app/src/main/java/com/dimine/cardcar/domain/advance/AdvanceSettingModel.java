package com.dimine.cardcar.domain.advance;


import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.bean.TimeRecordBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.ObjectBox;

import io.objectbox.Box;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/10 11:41
 * desc   :
 * version: 1.0
 */
public class AdvanceSettingModel {


    private Box<UserBean> userBeanBox;
    private Box<TimeRecordBean> timeRecordBeanBox;
    private Box<SchedulingBean> schedulingBeanBox;

    public AdvanceSettingModel() {
        userBeanBox = ObjectBox.get().boxFor(UserBean.class);
        timeRecordBeanBox = ObjectBox.get().boxFor(TimeRecordBean.class);
        schedulingBeanBox = ObjectBox.get().boxFor(SchedulingBean.class);
    }

    public int getDeviceTypeId() {
        return LocalArguments.getInstance().carTypeId();
    }

    public String getDeviceNumber() {
        return LocalArguments.getInstance().deviceNumber();
    }


    public void saveDeviceIdChange(String number) {
        LocalArguments.getInstance().saveDeviceNumber(number);
    }

    public String getDeviceTitle() {
        return LocalArguments.getInstance().deviceTitle();
    }

    public void saveDeviceTitleChange(String str) {
        LocalArguments.getInstance().saveDeviceTitle(str);
    }

    public String getDeviceId() {
        return LocalArguments.getInstance().getMac();
    }

    public String getSettingsRemark() {
        return LocalArguments.getInstance().settingsRemark();
    }

    public void saveDeviceDesc(String desc) {
        LocalArguments.getInstance().saveSettingsDesc(desc);
    }

    public void saveVoicePlay(String count) {
        LocalArguments.getInstance().saveVoicePlay(count);
    }

    public String getVoiceCount() {
        return LocalArguments.getInstance().getVoiceCount();
    }

    public boolean getSipAuto() {
        return LocalArguments.getInstance().getAutoAnswer();
    }

    public void saveSipAuto(boolean b) {
        LocalArguments.getInstance().setAutoAnswer(b);
    }

    public boolean delData() {
        LocalArguments.getInstance().saveUserId("");
        LocalArguments.getInstance().saveUserName("");
        userBeanBox.removeAll();
        timeRecordBeanBox.removeAll();
        schedulingBeanBox.removeAll();
        return true;
    }
}
