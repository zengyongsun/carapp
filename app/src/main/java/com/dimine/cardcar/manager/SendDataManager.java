package com.dimine.cardcar.manager;

import com.dimine.cardcar.data.bean.ErrorBean;
import com.dimine.cardcar.data.bean.ErrorBean_;
import com.dimine.cardcar.data.bean.UpMessageBean;
import com.dimine.cardcar.data.bean.UpMessageBean_;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.implement.NetWork;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.ObjectBox;

import java.util.Date;
import java.util.List;

import io.objectbox.Box;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/25 15:45
 * desc   :
 * version: 1.0
 */
public class SendDataManager {

    private static final SendDataManager ourInstance = new SendDataManager();

    public static SendDataManager getInstance() {
        return ourInstance;
    }

    private Box<UpMessageBean> upBox;
    private Box<ErrorBean> errorBeanBox;
    private NetWork netWork;

    public void setNetWorkManager(NetWork netWork) {
        this.netWork = netWork;
        this.upBox = ObjectBox.get().boxFor(UpMessageBean.class);
        this.errorBeanBox = ObjectBox.get().boxFor(ErrorBean.class);
    }

    private SendDataManager() {

    }

    public void sendData(String message) {
        if (netWork.connection()) {
            SendRabbitMqManager.getInstance().publishMessage(message);
            List<UpMessageBean> list = getLocalMessage(20);
            if (list.size() > 0) {
                MyLog.d("saveLocal", "发送历史数据：" + list.size());
                for (UpMessageBean bean : list) {
                    SendRabbitMqManager.getInstance().publishMessage(bean.message);
                }
                upBox.remove(list);
            }
            List<ErrorBean> errorBeanList = getLocalError(20);
            if (errorBeanList.size() > 0) {
                for (ErrorBean bean : errorBeanList) {
                    MyLog.d("errorBeanList", "发送崩溃数据：" + bean.errorMessage);
                    WriteLogManager.getInstance().writeLog(bean.errorMessage);
                }
                errorBeanBox.remove(errorBeanList);
            }

        } else {
            // 保存本地 目前不需要这个功能
            MyLog.d("saveLocal", "调用了");
            upBox.put(new UpMessageBean(message, new Date()));
        }
    }

    public List<UpMessageBean> getLocalMessage(int count) {
        return upBox.query()
                .order(UpMessageBean_.createTime)
                .build()
                .find(0, count);
    }

    public List<ErrorBean> getLocalError(int count) {
        return errorBeanBox.query()
                .order(ErrorBean_.createTime)
                .build()
                .find(0, count);
    }


    public void sendDataAck(String message) {
        if (netWork.connection()) {
            SendRabbitMqManager.getInstance().publishMessageAck(message);
            if (LocalArguments.getInstance().writeLog()) {
                WriteLogManager.getInstance().writeLog("【send ack to service】" + message);
            }
        }
    }


}
