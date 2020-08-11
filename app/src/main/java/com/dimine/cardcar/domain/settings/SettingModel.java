package com.dimine.cardcar.domain.settings;


import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.utils.IPUtils;
import com.dimine.cardcar.utils.MyLog;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 15:21
 * desc   :
 * version: 1.0
 */
public class SettingModel {


    public String getLocalIp() {
        String ip = IPUtils.getLocalIpAddress();
        if (ip.isEmpty()) {
            ip = LocalArguments.getInstance().localIp();
        }
        MyLog.d("settings_ip", "getLocalIp" + ip);
        return ip;
    }

    public String getDeviceNumber() {
        return LocalArguments.getInstance().deviceNumber();
    }

    public void saveCardId(String cardId) {
        LocalArguments.getInstance().saveCardId(cardId);
    }

    public String getCardId() {
        return LocalArguments.getInstance().getCardId();
    }

    public String getLocalPort() {
        return LocalArguments.getInstance().localPort();
    }

    public String getServiceIp() {
        return LocalArguments.getInstance().serviceIp();
    }

    public String getServicePort() {
        return LocalArguments.getInstance().servicePort();
    }

    public String getGateWay() {
        String gateway = IPUtils.getGateWay();
        if ("dev".equals(gateway)) {
            gateway = LocalArguments.getInstance().localGateway();
        }
        MyLog.d("settings_ip", "getGateWay" + gateway);
        return gateway;
    }

    public String getMask() {
        String mask = IPUtils.getIpAddrMaskForInterfaces("eth0");
        if ("255.0.0.0".equals(mask)) {
            mask = LocalArguments.getInstance().localMask();
        }
        MyLog.d("settings_ip", "getMask" + mask);
        return mask;
    }
}
