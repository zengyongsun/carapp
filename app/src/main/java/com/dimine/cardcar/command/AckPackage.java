package com.dimine.cardcar.command;

import android.support.annotation.NonNull;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/12/13 13:53
 * desc   : 消息确认指令对象
 * version: 1.0
 */
public class AckPackage extends BaseProtocol {
    /**
     * *RS,TerminalID,k2,调度发出时间,,发出日期#
     */

    public String terminalID;

    public String command = "k2";

    public String schedulingTime;

    public String schedulingDay;

    @NonNull
    @Override
    public String toString() {
        return header + "," + terminalID + "," + command + "," + schedulingTime + ","
                + schedulingDay + end;
    }

}
