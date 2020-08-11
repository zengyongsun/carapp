package com.dimine.cardcar.utils;

import android.content.Context;

import com.dimine.cardcar.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/27 14:00
 * desc   :
 * version: 1.0
 */
public class TimeUtils {

    public static final int HOUR = 60 * 60;
    public static final int MINUTE = 60;

    public static String secondsToHMS(int seconds) {
        StringBuffer strb = new StringBuffer();
        int hour = seconds / HOUR;
        int minute = 0;
        if (seconds > HOUR) {
            minute = (seconds - hour * HOUR) / MINUTE;
        } else {
            minute = seconds / MINUTE;
        }
        int s = seconds % 60;
        if (hour > 0) {
            strb.append(hour + "小时");
        }
        if (minute > 0) {
            strb.append(minute + "分");
        }
        if (s > 0) {
            strb.append(s + "秒");
        }
        return strb.toString();
    }

       public static String secondsToHHmmss(int seconds) {
        StringBuffer strb = new StringBuffer();
        int hour = seconds / HOUR;
        int minute = 0;
        if (seconds > HOUR) {
            minute = (seconds - hour * HOUR) / MINUTE;
        } else {
            minute = seconds / MINUTE;
        }
        int s = seconds % 60;
        if (hour > 0) {
            strb.append(hour+":");
        }else {
            strb.append("00:");
        }
        if (minute > 0) {
            strb.append(minute +":");
        }else {
            strb.append("00:");
        }
        if (s > 0) {
            strb.append(s);
        }
        return strb.toString();
    }

    /**
     * 日期格式字符串转换成时间戳
     *
     * @param date_str 字符串日期
     * @param format   如：yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String date2TimeStamp(String date_str, String format) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return String.valueOf(sdf.parse(date_str).getTime() / 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getMorningAfternoon(Context context) {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 6) {
            return context.getString(R.string.good_in_the_morning);
        }
        if (a > 6 && a <= 12) {
            return context.getString(R.string.good_morning);
        }
        if (a > 12 && a <= 13) {
            return context.getString(R.string.good_afternoon);
        }
        if (a > 13 && a <= 18) {
            return context.getString(R.string.int_the_afternoon);
        }
        if (a > 18 && a <= 24) {
            return context.getString(R.string.good_evening);
        }
        return "";
    }


}

