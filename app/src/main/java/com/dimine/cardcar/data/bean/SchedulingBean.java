package com.dimine.cardcar.data.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/11 9:40
 * desc   : 卡车调度指令和信息
 * version: 1.0
 */

@Entity
public class SchedulingBean {

    /*
     * "*RS," + TID + ",K1," + DateTime.Now.ToShortTimeString() + ",S," + Latitude +",D," +
     * Longitude + ",G," + DateTime.Now.ToShortDateString() + "," + _Dispatch_Info.Dispatch_Type
     * + "," + _Dispatch_Info.Status + "#";
     * *RS,目的地终端号（反铲号）,k1,调度发出时间,S,目的地纬度,D,目的地经度,G,发出日期,物料种类，调度信息#
     * *RS,采矿1号,K1,15:22,S,30.2800939877828,D,114.298644002279,G,2019/10/14,白云石,调度信息#
     *
     * *RS,目的地终端号（反铲号）,k1,调度发出时间,目的地名称（反铲名称）,发出日期,物料种类，调度信息#

     */

    public SchedulingBean() {
    }

    public SchedulingBean(String[] data) {
        this.TID = data[1];
        this.shortTime = data[3];
        this.destination = data[4];
        this.shortDate = data[5];
        this.dispatchType = data[6];
        this.message = data[7].replace("#", "");
    }

    /**
     * 1：信息
     * 2：调度指令
     * 只存储最新的一条
     */
    @Id(assignable = true)
    public long id;

    /**
     * 目的地 铲车号
     */
    public String TID;

    /**
     * 调度发出时间
     */
    public String shortTime;


    public String destination;

    /**
     * 发出日期
     */
    public String shortDate;

    /**
     * 物料种类
     */
    public String dispatchType;

    /**
     * 调度信息
     */
    public String message;

    @Override
    public String toString() {
        return "SchedulingBean{" +
                "id=" + id +
                ", TID='" + TID + '\'' +
                ", shortTime='" + shortTime + '\'' +
                ", destination='" + destination + '\'' +
                ", shortDate='" + shortDate + '\'' +
                ", dispatchType='" + dispatchType + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
