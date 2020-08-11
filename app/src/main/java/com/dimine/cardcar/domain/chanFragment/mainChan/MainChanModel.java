package com.dimine.cardcar.domain.chanFragment.mainChan;


import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.data.bean.UserBean_;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.ObjectBox;

import io.objectbox.Box;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 15:21
 * desc   :
 * version: 1.0
 */
public class MainChanModel {

    private Box<UserBean> userBox;
    private Box<SchedulingBean> boxSchedul;

    public MainChanModel() {
        boxSchedul = ObjectBox.get().boxFor(SchedulingBean.class);
        userBox = ObjectBox.get().boxFor(UserBean.class);
    }

    public String getUserName() {
        return LocalArguments.getInstance().userName();
    }

    public String getDeviceNumber() {
        return LocalArguments.getInstance().deviceNumber();
    }

    public boolean getNightModel() {
        return LocalArguments.getInstance().isNight();
    }

    public UserBean currentUser() {
        String userId = LocalArguments.getInstance().userId();
        return userBox.query().startsWith(UserBean_.UserId, userId).build().findFirst();
    }

    public SchedulingBean getSchedulingBean() {
        return boxSchedul.get(2);
    }

    public SchedulingBean getSchedulingMessage() {
        return boxSchedul.get(1);
    }
}
