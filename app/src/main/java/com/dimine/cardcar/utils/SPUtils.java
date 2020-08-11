package com.dimine.cardcar.utils;

import android.content.Context;
import android.content.SharedPreferences;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

/**
 * @author : 张鸿洋
 * desc   :SharedPreferences封装类
 * version: 1.0
 */
public class SPUtils {

    /**
     * 保存在手机里面的文件名
     */
    public static final String FILE_NAME = "share_data";


    /**
     * 保存数据的方法，我们需要拿到保存数据的具体类型，然后根据类型调用不同的保存方法
     */
    public static void put(Context context, String key, Object object) {

        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();

        if (object instanceof String) {
            editor.putString(key, (String) object);
        } else if (object instanceof Integer) {
            editor.putInt(key, (Integer) object);
        } else if (object instanceof Boolean) {
            editor.putBoolean(key, (Boolean) object);
        } else if (object instanceof Float) {
            editor.putFloat(key, (Float) object);
        } else if (object instanceof Long) {
            editor.putLong(key, (Long) object);
        } else {
            editor.putString(key, object.toString());
        }
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 得到保存数据的方法，我们根据默认值得到保存的数据的具体类型，然后调用相对于的方法获取值
     */
    public static Object get(Context context, String key, Object defaultObject) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);

        if (defaultObject instanceof String) {
            return sp.getString(key, (String) defaultObject);
        } else if (defaultObject instanceof Integer) {
            return sp.getInt(key, (Integer) defaultObject);
        } else if (defaultObject instanceof Boolean) {
            return sp.getBoolean(key, (Boolean) defaultObject);
        } else if (defaultObject instanceof Float) {
            return sp.getFloat(key, (Float) defaultObject);
        } else if (defaultObject instanceof Long) {
            return sp.getLong(key, (Long) defaultObject);
        }
        return null;
    }

    /**
     * 移除某个key值已经对应的值
     */
    public static void remove(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.remove(key);
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 清除所有数据
     */
    public static void clear(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sp.edit();
        editor.clear();
        SharedPreferencesCompat.apply(editor);
    }

    /**
     * 查询某个key是否已经存在
     */
    public static boolean contains(Context context, String key) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.contains(key);
    }

    /**
     * 返回所有的键值对
     */
    public static Map<String, ?> getAll(Context context) {
        SharedPreferences sp = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE);
        return sp.getAll();
    }

    /**
     * 创建一个解决SharedPreferencesCompat.apply方法的一个兼容类
     */
    private static class SharedPreferencesCompat {
        private static final Method sApplyMethod = findApplyMethod();

        /**
         * 反射查找apply的方法
         */
        @SuppressWarnings({"unchecked", "rawtypes"})
        private static Method findApplyMethod() {
            try {
                Class clz = SharedPreferences.Editor.class;
                return clz.getMethod("apply");
            } catch (NoSuchMethodException e) {
            }

            return null;
        }

        /**
         * 如果找到则使用apply执行，否则使用commit
         */
        public static void apply(SharedPreferences.Editor editor) {
            try {
                if (sApplyMethod != null) {
                    sApplyMethod.invoke(editor);
                    return;
                }
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
            editor.commit();
        }
    }


    public static final String SETTING_NIGHT_MODE = "night_mode";
    public static final String ADVANCE_SETTINGS_PWD = "advance_setting";
    public static final String FACTORY_SETTINGS_PSD = "factory_setting";
    public static final String DEVICE_TYPE = "device_type";


    public static final String USER_ID = "user_id";
    public static final String ACCOUNT = "account";
    public static final String USER_NAME = "user_name";


    public static final String RUN_TIME_TOTAL = "time_total";

    /**
     * SerialPort
     */
    public static final String SERIAL_PORT_DEVICE = "device";
    public static final String SERIAL_PORT_BAUDRATE = "baudrate";

    public static final String DEVICE_ID = "device_id";
    public static final String DEVICE_TITLE = "device_title";

    public static final String SIP_USRNAME = "sip_username";
    public static final String SIP_PASSWORD = "sip_password";
    public static final String SIP_DOMAIN = "sip_domain";
    public static final String SIP_CALL_NUMBER = "sip_call_number";


    public static final String CAR_MAX_SPEED = "max_speed";
    public static final String GPS_UPLOAD_SPEED = "gps_upload_speed";
    public static final String WRITE_LOG = "write_log";


    /**
     * 设置的卡号id
     */
    public static final String SAVE_CARD_ID = "card_id";

    public static final String LOCAL_IP = "local_ip";
    public static final String LOCAL_MASK = "local_mask";
    public static final String LOCAL_GETWAY = "local_getway";

    public static final String LOCAL_PORT = "local_port";
    public static final String SERVICE_IP = "service_ip";
    public static final String SERVICE_PORT = "service_port";
    public static final String UPDATE_IP = "update_ip";
    public static final String UPDATE_PORT = "update_port";

    public static final String MAC_ID = "mac_id";

    public static final String SETTINGS_DESC = "settings_desc";
    public static final String SETTINGS_VOICE_COUNT = "settings_voice_count";

    public static final String VOLUME_NUMBER = "volume_number";

    public static final String RABBIT_MQ_USERNAME = "rabbit_mq_username";

    public static final String RABBIT_MQ_PASSWORD = "rabbit_mq_password";

    public static final String SETTINGS_REMARK = "settings_remark";

    public static final String SETTINGS_RAM = "settings_ram";
    public static final String SETTINGS_ROM = "settings_rom";


    public static final String ACK_FAULT = "ack_fault";


    public static final String AUTO_ANSWER = "auto_answer";

}

