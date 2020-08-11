package com.dimine.cardcar.data.local;

import android.content.Context;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.utils.SPUtils;
import com.dimine.cardcar.utils.VersionUtil;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/26 17:32
 * desc   :
 * version: 1.0
 */
public class LocalArguments {

    private String update_port = "8003";
    private String update_ip = "192.168.4.101";

    private String service_port = "8090";
    private String service_ip = "106.54.93.215";

    private String rabbit_username = "wlqk";
    private String rabbit_password = "123456";

    private String local_port = "5672";

    private String max_speed = "30";
    private String gps_upload_speed = "5";


    private String device_id = "-1";

    private String serial_port_device = "/dev/ttySAC2";
    private String serial_port_baudrate = "115200";

    private String sip_domain = "172.16.100.243";
    private String sip_call_number = "8888";
    private String sip_username = "8020";
    private String sip_password = "Abc123456";

    private boolean is_night = false;

    private String advance_settings_pwd = "666";
    private String factory_settings_pwd = "369";

    private String settings_remark = "无";

    private String device_ram = "0";
    private String device_rom = "0";
    private Object play_voice = "3";

    private boolean write_log = true;


    private Context mContext;

    private static final LocalArguments ourInstance = new LocalArguments();

    public static LocalArguments getInstance() {
        return ourInstance;
    }

    private LocalArguments() {
    }

    public void init(Context context) {
        mContext = context;
    }

    public int carTypeId() {
        return Integer.parseInt(deviceNumber().charAt(0) + "");
    }

    public boolean isNight() {
        return (boolean) SPUtils.get(mContext, SPUtils.SETTING_NIGHT_MODE, is_night);
    }

    public void saveNight(boolean isNight) {
        SPUtils.put(mContext, SPUtils.SETTING_NIGHT_MODE, isNight);
    }

    public String advanceSettingsPwd() {
        return (String) SPUtils.get(mContext, SPUtils.ADVANCE_SETTINGS_PWD, advance_settings_pwd);
    }

    public void saveSettingsPwd(String pwd) {
        SPUtils.put(mContext, SPUtils.ADVANCE_SETTINGS_PWD, pwd);
    }

    public String factorySettingsPwd() {
        return (String) SPUtils.get(mContext, SPUtils.FACTORY_SETTINGS_PSD, factory_settings_pwd);
    }

    public void saveFactorySettingsPwd(String pwd) {
        SPUtils.put(mContext, SPUtils.FACTORY_SETTINGS_PSD, pwd);
    }


    /*
     *------------------------------------------------------------------
     *-------------------------- 用户信息 ------------------------------
     *------------------------------------------------------------------
     */

    public String userId() {
        return (String) SPUtils.get(mContext, SPUtils.USER_ID, "0");
    }

    public void saveUserId(String userId) {
        SPUtils.put(mContext, SPUtils.USER_ID, userId);
    }


    public String userName() {
        return (String) SPUtils.get(mContext, SPUtils.USER_NAME, "");
    }

    public void saveUserName(String userName) {
        SPUtils.put(mContext, SPUtils.USER_NAME, userName);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- sip 账号信息 start --------------------------
     *------------------------------------------------------------------
     */

    public String sipUserName() {
        return (String) SPUtils.get(mContext, SPUtils.SIP_USRNAME, sip_username);
    }

    public void saveSipUserName(String userName) {
        SPUtils.put(mContext, SPUtils.SIP_USRNAME, userName);
    }

    public String sipPassword() {
        return (String) SPUtils.get(mContext, SPUtils.SIP_PASSWORD, sip_password);
    }

    public void saveSipPassword(String password) {
        SPUtils.put(mContext, SPUtils.SIP_PASSWORD, password);
    }

    public String sipDomain() {
        return (String) SPUtils.get(mContext, SPUtils.SIP_DOMAIN, sip_domain);
    }

    public void saveSipDomain(String domain) {
        SPUtils.put(mContext, SPUtils.SIP_DOMAIN, domain);
    }

    public String sipCallNumber() {
        return (String) SPUtils.get(mContext, SPUtils.SIP_CALL_NUMBER, sip_call_number);
    }

    public void saveSipNumber(String number) {
        SPUtils.put(mContext, SPUtils.SIP_CALL_NUMBER, number);
    }


    /*
     *------------------------------------------------------------------
     *--------------------- 串口波特率 start --------------------------
     *------------------------------------------------------------------
     */

    public String portPath() {
        return (String) SPUtils.get(mContext, SPUtils.SERIAL_PORT_DEVICE, serial_port_device);
    }

    public void savePortPath(String path) {
        SPUtils.put(mContext, SPUtils.SERIAL_PORT_DEVICE, path);
    }

    public String portBaudrate() {
        return (String) SPUtils.get(mContext, SPUtils.SERIAL_PORT_BAUDRATE, serial_port_baudrate);
    }

    public void savePortBaudrate(String baudrate) {
        SPUtils.put(mContext, SPUtils.SERIAL_PORT_BAUDRATE, baudrate);
    }


    public String appVersion() {
        return VersionUtil.getVersionName(mContext);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- 设备编号 start --------------------------
     *------------------------------------------------------------------
     */

    public String deviceNumber() {
        return String.valueOf(SPUtils.get(mContext, SPUtils.DEVICE_ID, device_id));
    }

    public void saveDeviceNumber(String str) {
        SPUtils.put(mContext, SPUtils.DEVICE_ID, str);
    }

    public String maxSpeed() {
        return String.valueOf(SPUtils.get(mContext, SPUtils.CAR_MAX_SPEED, max_speed));
    }

    public void saveMaxSpeed(String maxSpeed) {
        SPUtils.put(mContext, SPUtils.CAR_MAX_SPEED, maxSpeed);
    }

    public String gpsUploadSpeed() {
        return (String) SPUtils.get(mContext, SPUtils.GPS_UPLOAD_SPEED, gps_upload_speed);
    }

    public void saveGpsUploadSpeed(String gpsSpeed) {
        SPUtils.put(mContext, SPUtils.GPS_UPLOAD_SPEED, gpsSpeed);
    }

    public boolean writeLog() {
        return (boolean) SPUtils.get(mContext, SPUtils.WRITE_LOG, write_log);
    }

    public void saveWriteLog(boolean log) {
        SPUtils.put(mContext, SPUtils.WRITE_LOG, log);
    }


    public String deviceTitle() {
        if (carTypeId() == CarType.Truck.getTypeId()) {
            return String.valueOf(SPUtils.get(mContext, SPUtils.DEVICE_TITLE,
                    mContext.getString(R.string.app_top_title)));
        } else if (carTypeId() == CarType.Forklift.getTypeId()) {
            return String.valueOf(SPUtils.get(mContext, SPUtils.DEVICE_TITLE,
                    mContext.getString(R.string.chan_app_top_title)));
        }
        return String.valueOf(SPUtils.get(mContext, SPUtils.DEVICE_TITLE,
                mContext.getString(R.string.app_top_title)));
    }

    public void saveDeviceTitle(String title) {
        SPUtils.put(mContext, SPUtils.DEVICE_TITLE, title);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- ip和端口 start --------------------------
     *------------------------------------------------------------------
     */

    public String localIp() {
        return (String) SPUtils.get(mContext, SPUtils.LOCAL_IP, "");
    }

    public void saveLocalIp(String ip) {
        SPUtils.put(mContext, SPUtils.LOCAL_IP, ip);
    }

    public String localMask() {
        return (String) SPUtils.get(mContext, SPUtils.LOCAL_MASK, "");
    }

    public void saveLocalMask(String mask) {
        SPUtils.put(mContext, SPUtils.LOCAL_MASK, mask);
    }

    public String localGateway() {
        return (String) SPUtils.get(mContext, SPUtils.LOCAL_GETWAY, "");
    }

    public void saveLocalGetWay(String getway) {
        SPUtils.put(mContext, SPUtils.LOCAL_GETWAY, getway);
    }


    public String localPort() {
        return (String) SPUtils.get(mContext, SPUtils.LOCAL_PORT, local_port);
    }

    public void modifyLocalPort(String port) {
        SPUtils.put(mContext, SPUtils.LOCAL_PORT, port);
    }

    public String serviceIp() {
        return (String) SPUtils.get(mContext, SPUtils.SERVICE_IP, service_ip);
    }

    public void modifyServiceIp(String ip) {
        SPUtils.put(mContext, SPUtils.SERVICE_IP, ip);
    }

    public String servicePort() {
        return (String) SPUtils.get(mContext, SPUtils.SERVICE_PORT, service_port);
    }

    public void modifyServicePort(String port) {
        SPUtils.put(mContext, SPUtils.SERVICE_PORT, port);
    }

    public String updateIp() {
        return (String) SPUtils.get(mContext, SPUtils.UPDATE_IP, update_ip);
    }

    public void modifyUpdateIp(String ip) {
        SPUtils.put(mContext, SPUtils.UPDATE_IP, ip);
    }

    public String updatePort() {
        return (String) SPUtils.get(mContext, SPUtils.UPDATE_PORT, update_port);
    }

    public void modifyUpdatePort(String port) {
        SPUtils.put(mContext, SPUtils.UPDATE_PORT, port);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- 绑定的卡id start --------------------------
     *------------------------------------------------------------------
     */

    public void saveCardId(String cardId) {
        SPUtils.put(mContext, SPUtils.SAVE_CARD_ID, cardId);
    }

    public String getCardId() {
        return (String) SPUtils.get(mContext, SPUtils.SAVE_CARD_ID, "");
    }


    /*
     *------------------------------------------------------------------
     *--------------------- 设备唯一id start --------------------------
     *------------------------------------------------------------------
     */

    public void saveMac(String id) {
        SPUtils.put(mContext, SPUtils.MAC_ID, id);
    }

    public String getMac() {
        return (String) SPUtils.get(mContext, SPUtils.MAC_ID, "");
    }


    public String getSettingsDesc() {
        return (String) SPUtils.get(mContext, SPUtils.SETTINGS_DESC,
                mContext.getString(R.string.settings_default_value));
    }

    public void saveSettingsDesc(String desc) {
        SPUtils.put(mContext, SPUtils.SETTINGS_DESC, desc);
    }

    public void saveVoicePlay(String count) {
        SPUtils.put(mContext, SPUtils.SETTINGS_VOICE_COUNT, count);
    }

    public String getVoiceCount() {
        return (String) SPUtils.get(mContext, SPUtils.SETTINGS_VOICE_COUNT, play_voice);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- 系统音量的设置 start --------------------------
     *------------------------------------------------------------------
     */

    public void saveVolumeNumber(int progress) {
        SPUtils.put(mContext, SPUtils.VOLUME_NUMBER, progress);
    }

    public int volumeNumber() {
        return (int) SPUtils.get(mContext, SPUtils.VOLUME_NUMBER, -1);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- rabbitMQ的帐号密码 start --------------------------
     *------------------------------------------------------------------
     */

    public void saveRabbitMqUserName(String userName) {
        SPUtils.put(mContext, SPUtils.RABBIT_MQ_USERNAME, userName);
    }

    public String rabbitMqUserName() {
        return (String) SPUtils.get(mContext, SPUtils.RABBIT_MQ_USERNAME, rabbit_username);
    }

    public void saveRabbitMqPassword(String password) {
        SPUtils.put(mContext, SPUtils.RABBIT_MQ_PASSWORD, password);
    }

    public String rabbitMqPassword() {
        return (String) SPUtils.get(mContext, SPUtils.RABBIT_MQ_PASSWORD, rabbit_password);
    }


    public String settingsRemark() {
        return (String) SPUtils.get(mContext, SPUtils.SETTINGS_REMARK, settings_remark);
    }

    public void saveRemark(String remark) {
        SPUtils.put(mContext, SPUtils.SETTINGS_REMARK, remark);
    }

    /*
     *------------------------------------------------------------------
     *--------------------- rabbitMQ的帐号密码 start --------------------------
     *------------------------------------------------------------------
     */

    public String getRom() {
        return (String) SPUtils.get(mContext, SPUtils.SETTINGS_ROM, device_rom);
    }

    public void saveRom(String rom) {
        SPUtils.put(mContext, SPUtils.SETTINGS_ROM, rom);
    }

    public String getRam() {
        return (String) SPUtils.get(mContext, SPUtils.SETTINGS_RAM, device_ram);
    }

    public void saveRam(String ram) {
        SPUtils.put(mContext, SPUtils.SETTINGS_RAM, ram);
    }


    public boolean isRecive() {
        return (boolean) SPUtils.get(mContext, SPUtils.ACK_FAULT, false);
    }

    public void changeRecive(boolean is) {
        SPUtils.put(mContext, SPUtils.ACK_FAULT, is);
    }

    public boolean getAutoAnswer() {
        return (boolean) SPUtils.get(mContext, SPUtils.AUTO_ANSWER, false);
    }

    public void setAutoAnswer(boolean auto) {
        SPUtils.put(mContext, SPUtils.AUTO_ANSWER, auto);
    }

}
