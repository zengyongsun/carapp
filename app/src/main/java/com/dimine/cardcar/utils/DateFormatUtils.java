package com.dimine.cardcar.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/10 14:12
 * desc   :
 * version: 1.0
 */
public class DateFormatUtils {

    public static String timeHHmmss() {
        SimpleDateFormat format = new SimpleDateFormat("HHmmss");
        Date date = new Date();
        return format.format(date);
    }

    public static String timeddMMyy() {
        SimpleDateFormat format = new SimpleDateFormat("ddMMyy");
        Date date = new Date();
        return format.format(date);
    }

    /**
     * 把分钟转换成小时的小数形式
     */
    public static String minuteToHour(String minute) {
        int m = Integer.parseInt(minute);
        double re = m / 60.0;
        int last = m % 60;
        String returnStr;
        if (last == 0) {
            DecimalFormat df = new DecimalFormat("0");
            returnStr = df.format(re);
        } else {
            BigDecimal bg = new BigDecimal(re);
            returnStr = bg.setScale(1, BigDecimal.ROUND_HALF_UP).doubleValue() + "";
        }
        return returnStr;
    }

    /**
     * 判断给定时间是否在一定范围内
     *
     * @param shortDate 2019/11/8
     * @param shortTime 11:56
     * @return
     */
    public static boolean withinTheLegalRange(String shortDate, String shortTime) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            Date old = format.parse(shortDate + " " + shortTime);
            Date now = new Date();
            //得到相差的分钟数
            long difference = (now.getTime() - old.getTime()) / (1000 * 60);
            if (difference < 5) {
                return true;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * 判断给定的两个日期是否是同一天
     *
     * @param one 日期1
     * @param two 日期2
     * @return true为同一个，false不是同一天
     */
    public static boolean isSameDay(Date one, Date two) {
        Calendar oneDay = Calendar.getInstance();
        Calendar twoDay = Calendar.getInstance();
        oneDay.setTime(one);
        twoDay.setTime(two);
        if (oneDay.get(Calendar.YEAR) == twoDay.get(Calendar.YEAR)
                && oneDay.get(Calendar.DAY_OF_YEAR) == twoDay.get(Calendar.DAY_OF_YEAR)) {
            return true;
        }
        return false;
    }

    public static SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HHmmss");

    public static String[] splitGpsTime(long time) {
        return sdf.format(new Date(time)).split(" ");
    }

}
