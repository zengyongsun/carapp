package com.dimine.cardcar.utils;

import com.dimine.cardcar.data.local.LocalArguments;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/22 13:31
 * desc   :
 * version: 1.0
 */
public class PlayVoiceUtil {

    public static String playCount(String readStr) {
        int count = Integer.parseInt(LocalArguments.getInstance().getVoiceCount());
        StringBuffer sBuffer = new StringBuffer();
        for (int i = 0; i < count; i++) {
            sBuffer.append(readStr);
            sBuffer.append("。");
        }
        return sBuffer.toString();
    }


}
