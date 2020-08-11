package com.dimine.cardcar.utils;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/7/9 9:22
 * desc   : GPS数据的转换工具
 * version: 1.0
 */
public class GPSConversionUtil {


    public static double degreesToMapCoordinate(String tude) {
        if ("".equals(tude)) {
            return 0.0;
        }
        double data = Double.parseDouble(tude);
        int integer = (int) data / 100;
        double decimal = (data - integer * 100) / 60;
        return integer + decimal;
    }

    public static double mapCoordinateToDegrees(String d) {
        if ("".equals(d)) {
            return 0.0;
        }
        double data = Double.parseDouble(d);
        int one = (int) data * 100;
        double two = (data * 100 - one)/100 * 60;
        return one + two;
    }

}
