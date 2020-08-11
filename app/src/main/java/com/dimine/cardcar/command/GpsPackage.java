package com.dimine.cardcar.command;

import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.remote.CommandCodeConstant;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/25 16:10
 * desc   : 车辆GPS信息  去GPS传感器的 $GNRMC 数据
 *  * $GPRMC,083559.00,A,4717.11437,N,00833.91522,E,0.004,77.52,091202,,,A,V*57
 * version: 1.0
 */
public class GpsPackage extends BaseProtocol {

    public GpsPackage(RMCBean rmcBean) {
        this.time = rmcBean.time;
        this.s = rmcBean.status;
        this.latitude = rmcBean.Latitude;
        this.longitude = rmcBean.Longitude;
        this.spd = rmcBean.spd;
        this.cog = rmcBean.cog;
        this.date = rmcBean.date;
        this.EquipemntType = rmcBean.EquipemntType;
    }

    public String deviceNumber;

    public String command = CommandCodeConstant.gpsCommand;

    public String time;

    public String s = "S";

    /**
     * 纬度
     */
    public String latitude;

    public String d = "D";

    /**
     * 经度
     */
    public String longitude;

    public String g = "G";

    /**
     * 速度
     */
    public String spd;

    public String cog;

    public String date;

    public String vehicle_status = "0";

    /**
     * 设备类型
     */
    public String EquipemntType;

    public String toPackage() {
        return header + "," + deviceNumber + "," + command + "," +
                time + "," + s + "," + latitude + "," + d + "," + longitude + "," + g + ","
                + spd + "," + cog + "," + date + "," + vehicle_status + "," + EquipemntType + end;
    }
}
