package com.dimine.cardcar.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.text.format.Formatter;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigDecimal;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/6 16:39
 * desc   : 获取手机的可用内存和总内存的工具类
 * version: 1.0
 */
public class SystemMemoryUtils {

    /**
     *   * 获取android当前可用运行内存大小
     *   * @param context
     *   *
     */
    public static String getAvailMemory(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo mi = new ActivityManager.MemoryInfo();
        am.getMemoryInfo(mi);
        // mi.availMem; 当前系统的可用内存
        // 将获取的内存大小规格化
        return Formatter.formatFileSize(context, mi.availMem);
    }


    /**
     *   * 获取android总运行内存大小
     *   * @param context
     *   *
     */
    public static String getTotalMemory(Context context) {
        // 系统内存信息文件
        String str1 = "/proc/meminfo";
        String str2;
        String[] arrayOfString;
        long initial_memory = 0;
        try {
            FileReader localFileReader = new FileReader(str1);
            BufferedReader localBufferedReader = new BufferedReader(localFileReader, 8192);
            // 读取meminfo第一行，系统总内存大小
            str2 = localBufferedReader.readLine();
            arrayOfString = str2.split("\\s+");
            for (String num : arrayOfString) {
                Log.i(str2, num + "\t");
            }
            // 获得系统总内存，单位是KB
            //为了是系统的开起来是整数 的处理
            double i = Integer.valueOf(arrayOfString[1]).doubleValue();
            i = i / (1000 * 1000);
            BigDecimal bd = new BigDecimal(i);
            // 向上取整
            i = bd.setScale(0, BigDecimal.ROUND_UP).intValue();
            i = i * 1024 * 1024;
            Log.d("MainActivity", arrayOfString[1]);
            //int值乘以1024转换为long类型
            initial_memory = Long.valueOf((long) i * 1024);
            localBufferedReader.close();
        } catch (IOException e) {
        }
        // Byte转换为KB或者MB，内存大小规格化
        return Formatter.formatFileSize(context, initial_memory);
    }

}
