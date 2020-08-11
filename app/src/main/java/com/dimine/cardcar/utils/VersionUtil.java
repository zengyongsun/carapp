package com.dimine.cardcar.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2018/11/28 10:10
 * desc   : 获取应用版本信息
 * version: 1.0
 */
public class VersionUtil {

    /**
     * 获取版本名
     *
     * @param context
     * @return 获取失败则返回null
     */
    public static String getVersionName(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取版本号
     *
     * @param context
     * @return 获取失败则返回0
     */
    public static int getVersionCode(Context context) {
        // 包管理者
        PackageManager mg = context.getPackageManager();
        try {
            // getPackageInfo(packageName 包名, flags 标志位（表示要获取什么数据）);
            // 0表示获取基本数据
            PackageInfo info = mg.getPackageInfo(context.getPackageName(), 0);
            return info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 生成一个5位的随机数作为id
     *
     * @return
     */
    public static int generateDeviceId() {
        double random = Math.random();
        while (random == 0) {
            random = Math.random();
        }
        while (random < 0.1) {
            random = Math.random();
        }
        return (int) (random * 100000);
    }

}
