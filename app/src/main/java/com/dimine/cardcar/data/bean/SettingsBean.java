package com.dimine.cardcar.data.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/12/23 15:52
 * desc   :
 * version: 1.0
 */
public class SettingsBean {

    public String deviceNumber;

    /**
     * 出厂后设备唯一，不能修改
     */
    public String UUID;

    public String localIp;

    public String localPort;

    public String localMask;

    public String localGetWay;

    public String sipNumber;

    public String sipPwd;

    public String sipServerIp;

    public String serverIp;

    public String serverPort;

    public String remark;

    public String title;

    public String uhf;

    public String version;

    public String sipCallPhone;

    @Override
    public String toString() {
        return "SettingsBean{" +
                "deviceNumber='" + deviceNumber + '\'' +
                ", UUID='" + UUID + '\'' +
                ", localIp='" + localIp + '\'' +
                ", localPort='" + localPort + '\'' +
                ", localMask='" + localMask + '\'' +
                ", localGetWay='" + localGetWay + '\'' +
                ", sipNumber='" + sipNumber + '\'' +
                ", sipPwd='" + sipPwd + '\'' +
                ", sipServerIp='" + sipServerIp + '\'' +
                ", serverIp='" + serverIp + '\'' +
                ", serverPort='" + serverPort + '\'' +
                ", remark='" + remark + '\'' +
                ", title='" + title + '\'' +
                ", uhf='" + uhf + '\'' +
                ", version='" + version + '\'' +
                ", sipCallPhone='" + sipCallPhone + '\'' +
                '}';
    }
}
