package com.dimine.cardcar.command;

import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.local.LocalArguments;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/20 10:50
 * desc   :
 * version: 1.0
 */
public class StatusTimePackage extends BaseProtocol {

    public StatusTimePackage(CarStatusBean carStatusBean) {
        this.terminalID = LocalArguments.getInstance().deviceNumber();
        this.airTime = carStatusBean.airTime / 60;
        this.reloadTime = carStatusBean.reloadTime / 60;
        this.loadTime = carStatusBean.loadTime / 60;
        this.unloadTime = carStatusBean.unloadTime / 60;
        this.idleTime = carStatusBean.idleTime / 60;
        this.spareTime = carStatusBean.spareTime / 60;
        this.time = carStatusBean.timeHHmmss;
        if (LocalArguments.getInstance().carTypeId() == CarType.Forklift.getTypeId()) {
            this.type = "D";
        }
        if (LocalArguments.getInstance().carTypeId() == CarType.Truck.getTypeId()) {
            this.type = "T";
        }
    }

    /**
     * 终端编号
     */
    public String terminalID;

    public String command = "S1";

    /**
     * 空运时间
     */
    public int airTime;

    /**
     * 重运时间
     */
    public int reloadTime;

    /**
     * 装载时间
     */
    public int loadTime;

    /**
     * 卸载时间
     */
    public int unloadTime;

    /**
     * 空闲时间
     */
    public int idleTime;

    /**
     * 备用时间
     */
    public int spareTime;

    public String time;

    /**
     * 设备类型
     */
    public String type;


    /**
     * 卡车状态的时间包
     */
    public String toKaPackage() {
        return header + "," + terminalID + "," + command + "," + airTime + "," + reloadTime + ","
                + loadTime + "," + unloadTime + "," + idleTime + "," + time + "," + type + end;
    }

    /**
     * 铲车状态的时间包    *RS,100016,S1,0,0,1761,2020/6/7 11:12:18,D#
     */
    public String toChanPackage() {
        return header + "," + terminalID + "," + command + "," + spareTime + "," + loadTime + ","
                + idleTime + "," + time + "," + type + end;
    }
}
