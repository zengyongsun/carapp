package com.dimine.cardcar.data.bean;

import android.text.TextUtils;

import com.dimine.cardcar.utils.GPSConversionUtil;
import com.dimine.cardcar.utils.TimeUtils;

import java.text.DecimalFormat;

/**
 * 参阅手册 page 115 of 351
 * <p>
 * $GPRMC,083559.00,A,4717.11437,N,00833.91522,E,0.004,77.52,091202,,,A,V*57
 * <p>
 * 0 xxRMC - string $GPRMC RMC Message ID (xx = current Talker ID)
 * <p>
 * 1 time - hhmmss.ss 083559.00 UTC time, see note on UTC representation
 * <p>
 * 2 status - character A Status, V = Navigation receiver warning, A = Data
 * valid
 * <p>
 * 3 lat - ddmm.mmmmm  4717.11437 Latitude (degrees & minutes)
 * <p>
 * 4 NS - character N North/South indicator
 * <p>
 * 5 long - dddmm.mmmmm  00833.91522 Longitude (degrees & minutes),
 * <p>
 * 6 EW - character E East/West indicator
 * <p>
 * 7 spd knots numeric 0.004 Speed over ground
 * <p>
 * 9 date - ddmmyy 091202 Date in day, month, year format
 * <p>
 * 14 cs - hexadecimal *57 Checksum
 */
public class RMCBean {

    public RMCBean(String time, String status, String latitude, String longitude, String spd,
                   String cog, String date) {
        this.time = time;
        this.status = status;
        Latitude = latitude;
        Longitude = longitude;
        this.spd = spd;
        this.cog = cog;
        this.date = date;
    }

    /**
     * 车辆类型，是卡车还是铲车
     * D ,铲车，T 卡车
     */
    public String EquipemntType;

    /**
     * hhmmss.ss
     */
    public String time;

    /**
     * A 数据有效
     * V 有问题的数据
     */
    public String status;

    /**
     * 要转成double
     */
    public String Latitude;

    /**
     * 要转成double
     */
    public String Longitude;

    public double getLatitude() {
        if (TextUtils.isEmpty(Latitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(df7.format(GPSConversionUtil.degreesToMapCoordinate(Latitude)));
        }
    }

    public double getLongitude() {
        if (TextUtils.isEmpty(Longitude)) {
            return 0.0;
        } else {
            return Double.parseDouble(df7.format(GPSConversionUtil.degreesToMapCoordinate(Longitude)));
        }
    }

    /**
     * 1节等于每小时 1海里，也就是每小时行驶1.852千米（公里）
     */
    public String spd;

    private DecimalFormat df2 = new DecimalFormat("#0.00");
    private DecimalFormat df7 = new DecimalFormat("#.0000000");

    public String getSpd() {
        if ("".equals(spd)) {
            return 0 + "";
        }
        double speed = Double.parseDouble(spd);
        speed = speed * 1.852;
        return df2.format(speed);
    }

    /**
     * date='090919'
     *
     * @return yyyy-MM-dd
     */
    public String getDate() {
        if (date.length() >= 6) {
            String dd = date.substring(0, 2);
            String mm = date.substring(2, 4);
            String yy = date.substring(4, 6);
            return "20" + yy + "-" + mm + "-" + dd;
        }
        return "";
    }

    public long timestamp() {
        String dataStr = getDate() + " " + getTime();
        if (TextUtils.isEmpty(getDate())||TextUtils.isEmpty(getTime())) {
            return -1;
        }
        if (dataStr.trim().length() > 0) {
            //差8时区，所以增加8小时的秒数，让系统计算
            return Long.parseLong(
                    TimeUtils.date2TimeStamp(dataStr, "yyyy-MM-dd HH:mm:ss")) + 8 * 60 * 60;
        }
        return -1;
    }

    /**
     * time='091121.80'
     *
     * @return hh:mm:ss
     */
    public String getTime() {
        if (time.length() >= 6) {
            //小时差时区
            String hh = time.substring(0, 2);
            String mu = time.substring(2, 4);
            String ss = time.substring(4, 6);
            return hh + ":" + mu + ":" + ss;
        }
        return "";
    }

    /**
     * 地面角度
     */
    public String cog;

    /**
     * 091202 日月年的格式
     */
    public String date;

    @Override
    public String toString() {
        return "RMCBean{" +
                "time='" + time + '\'' +
                ", status='" + status + '\'' +
                ", Latitude='" + getLatitude() + '\'' +
                ", Longitude='" + getLongitude() + '\'' +
                ", spd='" + spd + '\'' +
                ", cog='" + cog + '\'' +
                ", date='" + date + '\'' +
                '}';
    }
}
