package com.dimine.cardcar.command;

import android.support.annotation.NonNull;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/10 14:15
 * desc   : 故障上报的信息实体
 * version: 1.0
 */
public class FaultPackage extends BaseProtocol {

    /**
     * 终端编号
     */
    public String terminalID;

    public String command = "I9";

    public String timeHHssmm;

    /**
     * 登录id
     */
    public String userId;

    /**
     * 设备编号
     */
    public String equipmentNo;

    public String number = "2";

    public String faultMsg;

    public String latitude = "0";

    public String longitude = "0";

    public String effect;

    public String type;

    @NonNull
    @Override
    public String toString() {
        return header + "," + terminalID + "," + command + "," + timeHHssmm + ","
                + userId + "," + equipmentNo + "," + number + "," + faultMsg + ","
                + latitude + "," + longitude + "," + effect+","+type + end;
    }


}
