package com.dimine.cardcar.command;

import android.support.annotation.NonNull;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/15 16:40
 * desc   :
 * version: 1.0
 */
public class ForkiftUpPackage extends BaseProtocol {

    /**
     * 电铲上传装载信息
     * *RS,terminal,Z1,diggerNum,truckNum,V1,hhmmss,0,1# 其中digger_num和truck_num分别是反铲编号以及卡车编号
     * "*RS," + TerminalID + ",Z1," + diggerNum + "," + truckNum + "," + DateTime.Now.ToString("HHmmss") + ",0,1#";
     */

    public String terminalID;

    public String command = "Z1";

    /**
     * 铲车编号
     */
    public String diggerNum;

    /**
     * 卡车编号
     */
    public String truckNum;

    public String timeHHmmss;

    public String one = "0";

    public String two = "1";

    @NonNull
    @Override
    public String toString() {
        return header + "," + terminalID + "," + command + "," + diggerNum + "," + truckNum + ","
                + timeHHmmss + "," + one + "," + two + end;
    }
}
