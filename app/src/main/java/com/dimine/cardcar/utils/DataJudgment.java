package com.dimine.cardcar.utils;

import android.content.Context;
import android.text.TextUtils;

import com.dimine.cardcar.R;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/17 8:58
 * desc   :
 * version: 1.0
 */
public class DataJudgment {

    public static String judgeDeviceData(String data, Context context) {
        if (TextUtils.isEmpty(data)) {
            return context.getString(R.string.hint_unable_empty);
        }
        if (data.length() != 6) {
            return context.getString(R.string.hint_six_count);
        }
        int first = Integer.parseInt(data.charAt(0) + "");
        if (first != 1 && first != 2) {
            return context.getString(R.string.hint_first_no_match);
        }
        return "";
    }


}
