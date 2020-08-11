package com.dimine.cardcar.domain.kaFragment.main;


import com.dimine.cardcar.data.bean.OilBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
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
public class MainKaModel {


    private Box<OilBean> oilBeanBox;
    private Box<SchedulingBean> boxSchedul;

    public MainKaModel() {
        oilBeanBox = ObjectBox.get().boxFor(OilBean.class);
        boxSchedul = ObjectBox.get().boxFor(SchedulingBean.class);
    }

    public void putOidBean(OilBean oilBean) {
        oilBeanBox.put(oilBean);
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

    public SchedulingBean getSchedulingBean() {
        return boxSchedul.get(2);
    }

    public SchedulingBean getSchedulingMessage() {
        return boxSchedul.get(1);
    }
}
