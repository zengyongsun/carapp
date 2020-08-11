package com.dimine.cardcar.utils;

import com.dimine.cardcar.R;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/26 16:16
 * desc   :
 * version: 1.0
 */
public class FaultImgUtils {

    public static int loadImageByDesc(int loadId, boolean yesOrNo) {
        switch (loadId) {
            case 0:
                if (yesOrNo) {
                    return R.mipmap.stop_yes;
                } else {
                    return R.mipmap.stop_no;
                }
            case 1:
                if (yesOrNo) {
                    return R.mipmap.illumination_yes;
                } else {
                    return R.mipmap.illumination_no;
                }
            case 2:
                if (yesOrNo) {
                    return R.mipmap.motor_oil_yes;
                } else {
                    return R.mipmap.motor_oil_no;
                }
            case 3:
                if (yesOrNo) {
                    return R.mipmap.fuel_yes;
                } else {
                    return R.mipmap.fuel_no;
                }
            case 4:
                if (yesOrNo) {
                    return R.mipmap.temperature_yes;
                } else {
                    return R.mipmap.temperature_no;
                }
            case 5:
                if (yesOrNo) {
                    return R.mipmap.battery_yes;
                } else {
                    return R.mipmap.battery_no;
                }
            case 6:
                if (yesOrNo) {
                    return R.mipmap.tire_pressure_yes;
                } else {
                    return R.mipmap.tire_pressure_no;
                }
            case 7:
                if (yesOrNo) {
                    return R.mipmap.engine_yes;
                } else {
                    return R.mipmap.engine_no;
                }
            case 8:
                if (yesOrNo) {
                    return R.mipmap.other_yes;
                } else {
                    return R.mipmap.other_no;
                }
            default:
                return 0;
        }
    }

}
