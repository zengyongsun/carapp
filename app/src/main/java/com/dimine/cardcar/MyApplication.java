package com.dimine.cardcar;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.provider.Settings;
import android.speech.tts.TextToSpeech;
import android.support.multidex.MultiDex;
import android.support.v7.app.AppCompatDelegate;
import android.text.TextUtils;
import android.widget.Toast;

import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.command.AckPackage;
import com.dimine.cardcar.command.StatusTimePackage;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.bean.TimeRecordBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.data.remote.CommandCodeConstant;
import com.dimine.cardcar.manager.MyNetWorkManager;
import com.dimine.cardcar.manager.SendDataManager;
import com.dimine.cardcar.manager.SendRabbitMqManager;
import com.dimine.cardcar.manager.TimingManager;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.ObjectBox;
import com.dimine.cardcar.utils.SPUtils;
import com.dimine.cardcar.utils.VersionUtil;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.interceptor.HttpLoggingInterceptor;
import com.tencent.bugly.Bugly;

import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;

import io.objectbox.Box;
import okhttp3.OkHttpClient;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/7 17:21
 * desc   : 应用中用到的服务都是这里开启的
 * version: 1.0
 */
public class MyApplication extends Application implements
        SendRabbitMqManager.ReceiveMessageListener, TextToSpeech.OnInitListener {

    public TimingManager timingManager;
    public MyNetWorkManager networkManager = new MyNetWorkManager();
    private TextToSpeech textToSpeech;

    /**
     * 打印日志的开关
     */
    private boolean debug = true;

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        ObjectBox.init(this);
        initApp();
    }

    public void initApp() {
        Bugly.init(getApplicationContext(), "3a18e6c210", false);
        initOkGo();
        LocalArguments.getInstance().init(this);
        setIdName();
        initMac();
        SendDataManager.getInstance().setNetWorkManager(networkManager);
        initNightMode();
        Helper.init(this);
        initManager();
        initSpeech();
        MyLog.isDebug = debug;
    }

    /**
     * 设置mac地址
     */
    private void initMac() {
        if (TextUtils.isEmpty(LocalArguments.getInstance().getMac())) {
            //恢复出厂设置会变
            String ANDROID_ID = Settings.System.getString(getContentResolver(), Settings.System.ANDROID_ID);
            LocalArguments.getInstance().saveMac(ANDROID_ID);
        }
    }

    public void initSpeech() {
        textToSpeech = new TextToSpeech(getApplicationContext(), this);
        // 设置音调，值越大声音越尖（女生），值越小则变成男声,1.0是常规
        textToSpeech.setPitch(0.5f);
        //设定语速 ，默认1.0正常语速
        textToSpeech.setSpeechRate(1.0f);
    }

    /**
     * 设置设备的唯一id
     */
    private void setIdName() {
        int deviceId = Integer.parseInt(LocalArguments.getInstance().deviceNumber());
        if (deviceId == -1) {
            //默认生成是卡车的id,第一位表示设备类型，共6位
            String id = "2" + VersionUtil.generateDeviceId();
            LocalArguments.getInstance().saveDeviceNumber(id);
        }
        MyLog.d("deviceId", "setIdName: " + deviceId);
    }

    /**
     * 启动SendRabbitMqManager
     */
    public void initRabbit() {
        SendRabbitMqManager.getInstance().setReceiveMessageListener(this);
    }


    private void initOkGo() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (debug) {
            HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor("OkGo");
            //log打印级别，决定了log显示的详细程度
            loggingInterceptor.setPrintLevel(HttpLoggingInterceptor.Level.BODY);
            //log颜色级别，决定了log在控制台显示的颜色
            loggingInterceptor.setColorLevel(Level.INFO);
            builder.addInterceptor(loggingInterceptor);
        }
        //全局的读取超时时间
        builder.readTimeout(4000, TimeUnit.MILLISECONDS);
        //全局的写入超时时间
        builder.writeTimeout(4000, TimeUnit.MILLISECONDS);
        //全局的连接超时时间
        builder.connectTimeout(4000, TimeUnit.MILLISECONDS);
        OkGo.getInstance().init(this)
                .setOkHttpClient(builder.build())
                .setRetryCount(2);
    }

    private void initManager() {
        timingManager = new TimingManager(new Handler(getMainLooper()));
        networkManager.init();
    }

    public void initNightMode() {
        boolean isNight = (boolean) SPUtils.get(this, SPUtils.SETTING_NIGHT_MODE, false);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
    }

    @Override
    public void onMessage(String msg) {
        String[] data = msg.split(",");
        String command = data[CommandCodeConstant.command_index];
        switch (command) {
            case CommandCodeConstant.schedulingCommand:
                SchedulingBean schedulingBean = new SchedulingBean(data);
                //延时0.6秒运行
                Helper.getHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (receiveDataListener != null) {
                            receiveDataListener.onTruckScheduling(schedulingBean);
                            MyLog.d("MyApplication#ReceiveRabbitMqManager", "接口回调onTruckScheduling");
                        }
                    }
                }, 600);
                if ("".equals(schedulingBean.TID)) {
                    //信息
                    schedulingBean.id = 1;
                } else {
                    //调度指令
                    schedulingBean.id = 2;
                }
                Box<SchedulingBean> boxSch = ObjectBox.get().boxFor(SchedulingBean.class);
                boxSch.put(schedulingBean);
                MyLog.d("MyApplication#ReceiveRabbitMqManager", receiveDataListener + "[data]" + schedulingBean.toString());
                AckPackage ackPackage = new AckPackage();
                ackPackage.terminalID = LocalArguments.getInstance().deviceNumber();
                ackPackage.schedulingDay = schedulingBean.shortDate;
                ackPackage.schedulingTime = schedulingBean.shortTime;
                SendDataManager.getInstance().sendDataAck(ackPackage.toString());
                MyLog.d("MyApplication#ReceiveRabbitMqManager", "[ack data]" + ackPackage.toString());
                break;
            case CommandCodeConstant.faultCommand:
                LocalArguments.getInstance().changeRecive(true);
                break;
            default:
                if ("*EquipmentInfo".equals(data[0])) {
                    CarStatusBean carStatusBean = new CarStatusBean(data);
                    timingManager.setRunnableType(carStatusBean.equipmentStatus);
                    TimeRecordBean timeRecordBean = timingManager.getTimeRecordBean();
                    carStatusBean.airTime = timeRecordBean.emptyTime;
                    carStatusBean.reloadTime = timeRecordBean.heavyTime;
                    carStatusBean.loadTime = timeRecordBean.loadingTime;
                    carStatusBean.unloadTime = timeRecordBean.unloadingTime;
                    carStatusBean.idleTime = timeRecordBean.idleTime;
                    carStatusBean.spareTime = timeRecordBean.spareTime;

                    if (receiveDataListener != null) {
                        receiveDataListener.onCarStatus(carStatusBean);
                    }
                    //发送状态时间给服务器
                    StatusTimePackage timePackage = new StatusTimePackage(carStatusBean);
                    if (LocalArguments.getInstance().carTypeId() == CarType.Truck.getTypeId()) {
                        SendDataManager.getInstance().sendData(timePackage.toKaPackage());
                    } else {
                        SendDataManager.getInstance().sendData(timePackage.toChanPackage());
                    }
                    MyLog.d("MyApplication#ReceiveRabbitMqManager", "[data]" + carStatusBean.toString());
                }
                break;
        }
        MyLog.d("ProtocolData", "命令：" + command);
    }

    @Override
    public void onInit(int status) {
        MyLog.d("SpeechSpeak", status + "");
        if (status == TextToSpeech.SUCCESS) {
            int result = textToSpeech.setLanguage(Locale.CHINA);
            if (result == TextToSpeech.LANG_MISSING_DATA
                    || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Toast.makeText(this, "数据丢失或不支持", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void voice(String str) {
        //QUEUE_FLUSH方式表示清除当前队列中的内容而直接播放新的内容，
        // QUEUE_ADD方式表示将新的内容添加到队列尾部进行播放
        textToSpeech.speak(str, TextToSpeech.QUEUE_ADD, null);
    }

    public interface ReceiveDataListener {

        void onCarStatus(CarStatusBean truck);

        void onTruckScheduling(SchedulingBean schedulingBean);

    }

    private ReceiveDataListener receiveDataListener;

    public void setReceiveDataListener(ReceiveDataListener receiveDataListener) {
        this.receiveDataListener = receiveDataListener;
    }

}
