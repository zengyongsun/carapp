package com.dimine.cardcar.utils;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/27 10:53
 * desc   :
 * version: 1.0
 */
public class StringUtils {

    public static boolean isEmpty(String str) {
        return str == null || str.length() == 0;
    }

    public static int length(String s) {
        return isEmpty(s) ? 0 : s.length();
    }

}
