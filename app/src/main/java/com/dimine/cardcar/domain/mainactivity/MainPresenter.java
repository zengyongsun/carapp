package com.dimine.cardcar.domain.mainactivity;

import android.os.SystemClock;
import android.support.v7.app.AppCompatDelegate;
import android.util.Log;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.command.GpsPackage;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.bean.ResponseBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.data.remote.Urls;
import com.dimine.cardcar.manager.GpsManager;
import com.dimine.cardcar.manager.MyNetWorkManager;
import com.dimine.cardcar.manager.SendDataManager;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.TimeUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/27 11:11
 * desc   :
 * version: 1.0
 */
public class MainPresenter implements MainContract.Presenter, GpsManager.GpsSensorDataListener,
        MyNetWorkManager.OnNetWorkManagerListener {

    private static final String TAG = MainPresenter.class.getSimpleName();

    private final MainContract.View mMainView;
    private final MainModel mModel;
    private GpsManager gpsManager;
    private MyNetWorkManager netWorkManager;

    public MainPresenter(MainModel mainModel, MainContract.View mainView, GpsManager gpsManager,
                         MyNetWorkManager netWorkManager) {
        mModel = mainModel;
        mMainView = mainView;
        this.gpsManager = gpsManager;
        this.netWorkManager = netWorkManager;
        setSensorListener();
        mMainView.setPresenter(this);
    }

    private void setSensorListener() {
        Log.d("SensorListener", "注册 main setSensorListener");
        gpsManager.setGpsSensorDataListener(this);
        netWorkManager.setNetWorkListener(this);
    }

    private void removeListener() {
        Log.d("SensorListener", "移除 main setSensorListener");
        gpsManager.setGpsSensorDataListener(null);
        netWorkManager.setNetWorkListener(null);
    }

    UploadRunnable runnable;

    @Override
    public void start() {
        loadUserName();
        mMainView.showTitle(mModel.getDeviceTitle());
        if (runnable == null) {
            runnable = new UploadRunnable();
            Helper.getHandler().postDelayed(runnable, mModel.getGpsUpload() * 1000);
        }
    }


    /**
     * GPS的信息 经纬度
     */
    @Override
    public void onLatitudeAndLongitude(double latitude, double longitude) {

    }

    private RMCBean currentRMCBean = null;

    @Override
    public void onUpDate(RMCBean rmcBean) {
        this.currentRMCBean = rmcBean;
    }

    /**
     * GPS的信息，行驶速度
     */
    @Override
    public void onSpeed(double speed) {
        String showSpeed = speed == 0 ? "0" : speed + "";
        MyLog.d("Speed", showSpeed);
        speedAlarm(speed);
        carSpeed(showSpeed);
    }

    /**
     * 卫星数
     */
    @Override
    public void onSatelliteNumber(int number) {
        gpsAmounts(number);
    }

    private boolean checkTime = false;

    @Override
    public void onTimestamp(long timestamp) {
        if (!checkTime) {
            MyLog.d("timestamp", timestamp + "");
            SystemClock.setCurrentTimeMillis(timestamp * 1000L);
            checkTime = true;
        }
    }

    @Override
    public void carSpeed(String speed) {

    }

    @Override
    public void gpsAmounts(int number) {
        mMainView.showGpsAmount(number);
    }

    @Override
    public void netStrength(int net) {
        mMainView.showNetStrength(net);
    }

    @Override
    public void nightModelChange(boolean isNight) {
        mModel.saveNightModel(isNight);
        MyLog.d(TAG, "onCheckedChanged: " + isNight);
        if (isNight) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }
        recreate();
    }

    @Override
    public void recreate() {
        mMainView.myRecreate();
    }

    @Override
    public void loadFragment(BottomType bottomType) {
        mMainView.showFragment(bottomType);
    }

    @Override
    public void exitLogin() {
        mMainView.showLoading(true);
        //网络请求操作
        MyLog.d("LoginPresenter" + mModel.getDeviceNumber() + " " + mModel.getCarType());
        OkGo.<String>get(Urls.host_ip + Urls.url_out)
                .params("terminalNo", mModel.getDeviceNumber())
                .params("Type", mModel.getCarType())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLog.d("LoginPresenter" + Thread.currentThread().getName(), response.body());
                        Gson gson = new Gson();
                        ResponseBean responseBean = gson.fromJson(response.body(), ResponseBean.class);
                        if ("0".equals(responseBean.code)) {
                            mModel.saveUserName("");
                            mModel.saveUserId("0");
                            mMainView.showLoginActivity();
                        } else {
                            mMainView.showToastMessage(responseBean.msg);
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        MyLog.e("LoginPresenter", response.code() + "");
                    }

                    @Override
                    public void onFinish() {
                        mMainView.showLoading(false);
                    }
                });
    }

    @Override
    public void exitDialog() {
        mMainView.showExitDialog();
    }

    @Override
    public void callControlRoom() {

    }

    private int counter = 0;
    private int duration = 15;

    @Override
    public void speedAlarm(double speed) {
        /*
         *1.提示司机超速，如果超速，15秒提醒一次
         *2.生成一条记录
         */
        if (speed > mModel.getCarMaxSpeed() && counter > duration) {
            generateAlarmLog(speed);
            mMainView.showVoice(mMainView.getContext().getString(R.string.hint_over_speed));
            counter = 0;
        }
        counter++;
    }

    @Override
    public void loadUserName() {
        mMainView.showUserName(TimeUtils.getMorningAfternoon(mMainView.getContext())
                + " " + mModel.getUserName());
    }

    @Override
    public void verifySettingPassword(String password) {
        if (mModel.getAdvancePwd().equals(password)) {
            goSettings();
        } else {
            mMainView.showToastMessage(mMainView.getContext().getString(R.string.error_password_no_match));
        }
    }

    public void goSettings() {
        mMainView.showImplementionSettings();
    }


    private void generateAlarmLog(double speed) {
        // TODO: 2019/9/3  生成的超速报警
    }

    private void uploadGps() {
        if (currentRMCBean != null) {
            MyLog.d("Main", "uploadGps");
            // currentRMCBean = new RMCBean("095243.00", "B", "3529.81759",
            //        "11351.28316", "2.492", "", "030520");
            GpsPackage gpsPackage = new GpsPackage(currentRMCBean);
            if (mModel.getCarTypeId() == CarType.Forklift.getTypeId()) {
                gpsPackage.EquipemntType = "D";
            } else {
                gpsPackage.EquipemntType = "T";
            }
            gpsPackage.deviceNumber = LocalArguments.getInstance().deviceNumber();
            gpsPackage.vehicle_status = Helper.getHelper().getApp().timingManager.getOutType();
            SendDataManager.getInstance().sendData(gpsPackage.toPackage());
        }
    }

    @Override
    public void destroy() {
        if (runnable != null) {
            Helper.getHandler().removeCallbacks(runnable);
        }
    }

    @Override
    public void onNetWork(boolean ok) {
        //网络状态
        mMainView.showNetWorkConnect(ok);
    }

    class UploadRunnable implements Runnable {

        @Override
        public void run() {
            uploadGps();
            Helper.getHandler().postDelayed(this, mModel.getGpsUpload() * 1000);
        }
    }

}
