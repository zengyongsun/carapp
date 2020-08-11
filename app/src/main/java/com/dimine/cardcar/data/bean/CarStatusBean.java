package com.dimine.cardcar.data.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/11 9:55
 * desc   : 车辆的状态，参数
 * version: 1.0
 */
public class CarStatusBean {

    /*
     * *EquipmentInfo，终端号，时间，状态，当日产量，空运时长，重运时长#
     * *EquipmentInfo,25712,103322,空运,0,45,0#
     *
     *“*EquipmentInfo," + status.Terminal_Num + "," + DateTime.Now.ToString("HHmmss") +
     *  "," + equipmentStatus + "," + equipmentWeight + "," + 空运时长，+重运时长 + "#";
     */
    //*EquipmentInfo,终端号，时间，当日产量，状态，品种#"
    //*EquipmentInfo,终端号，时间，当日产量,计划产量，状态，品种#"

    public CarStatusBean(String[] data) {
        this.Terminal_Num = data[1];
        this.timeHHmmss = data[2];
        this.equipmentWeight = data[3];
        this.planWeight = data[4];
        this.equipmentStatus = data[5];
        this.breed = data[6].replace("#", "");
    }

    public CarStatusBean() {
    }

    /**
     * 终端编号
     */
    public String Terminal_Num;

    public String timeHHmmss;

    /**
     * 状态
     */
    public String equipmentStatus;

    /**
     * 当日产量
     */
    public String equipmentWeight;


    public String planWeight;

    /**
     * 品种
     */
    public String breed;

    /**
     * 空运时间
     */
    public int airTime;

    /**
     * 重运时间
     */
    public int reloadTime;

    public int loadTime;

    public int unloadTime;

    public int idleTime;

    public int spareTime;

    @Override
    public String toString() {
        return "CarStatusBean{" +
                "Terminal_Num='" + Terminal_Num + '\'' +
                ", timeHHmmss='" + timeHHmmss + '\'' +
                ", equipmentStatus='" + equipmentStatus + '\'' +
                ", equipmentWeight='" + equipmentWeight + '\'' +
                ", planWeight='" + planWeight + '\'' +
                ", breed='" + breed + '\'' +
                ", airTime=" + airTime +
                ", reloadTime=" + reloadTime +
                ", loadTime=" + loadTime +
                ", unloadTime=" + unloadTime +
                ", idleTime=" + idleTime +
                ", spareTime=" + spareTime +
                '}';
    }
}
