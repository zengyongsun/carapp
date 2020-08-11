package com.dimine.cardcar.domain.login;

import android.support.annotation.NonNull;
import android.util.Log;

import com.dimine.cardcar.R;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.command.FaultPackage;
import com.dimine.cardcar.command.GpsPackage;
import com.dimine.cardcar.data.bean.ConfigBean;
import com.dimine.cardcar.data.bean.LoginBean;
import com.dimine.cardcar.data.bean.ProductionTaskBean;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.bean.ResponseBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.data.remote.Urls;
import com.dimine.cardcar.manager.MyNetWorkManager;
import com.dimine.cardcar.manager.SendDataManager;
import com.dimine.cardcar.utils.DateFormatUtils;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.PlayVoiceUtil;
import com.dimine.cardcar.utils.StringUtils;
import com.google.gson.Gson;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/26 14:30
 * desc   :
 * version: 1.0
 */
public class LoginPresenter implements LoginContract.Presenter, MyNetWorkManager.OnNetWorkManagerListener {

    private LoginContract.View mLoginView;
    private LoginModel mModel;
    private MyNetWorkManager netWorkManager;

    public LoginPresenter(@NonNull LoginModel model, @NonNull LoginContract.View loginView,
                          MyNetWorkManager netWorkManager, String from) {
        mModel = model;
        mLoginView = loginView;

        this.netWorkManager = netWorkManager;
        netWorkManager.setNetWorkListener(this);
        mLoginView.setPresenter(this);
        //不是来自主界面的启动，需要获取调度任务
        if (!"main".equals(from)) {
            getProductionTask = new GetProductionTask();
            Helper.getHandler().postDelayed(getProductionTask, 60 * 1000);
        }
    }


    @Override
    public void login(String userId, String userName) {
        if ("".equals(userId) || null == userId) {
            mLoginView.showToastMessage(mLoginView.getContext().getString(R.string.error_user_id_not_empty));
            return;
        }
        mLoginView.showLoading(true);
        //网络请求操作
        OkGo.<String>get(Urls.host_ip + Urls.url_login)
                .params("userName", userId)
                .params("terminalNo", mModel.getDeviceNumber())
                .params("Type", mModel.getCarType())
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLog.d("LoginPresenter" + Thread.currentThread().getName(), response.body());
                        Gson gson = new Gson();
                        try {
                            ResponseBean responseBean = gson.fromJson(response.body(), ResponseBean.class);
                            if ("0".equals(responseBean.code)) {
                                LoginBean userBean = gson.fromJson(response.body(), LoginBean.class);
                                userBean.data.lastLoginTime = new Date();
                                userBean.data.type = LocalArguments.getInstance().carTypeId();
                                mModel.putUser(userBean.data);
                                mModel.saveUserId(userBean.data.UserId);
                                mModel.saveUserName(userBean.data.UserName);
                                mLoginView.voice(userBean.data.UserName +
                                        mLoginView.getContext().getString(R.string.hint_login_ok));
                                Helper.getHelper().getApp().timingManager.checkAccount(userBean.data.UserId);
                                mLoginView.showMainActivity();
                            } else {
                                mLoginView.showToastMessage(responseBean.msg);
                            }
                        } catch (Exception e) {
                            mLoginView.showToastMessage("解析数据出错！" + e.getMessage());
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        mLoginView.voice(mLoginView.getContext().getString(R.string.error_login_failure));
                        MyLog.e("LoginPresenter#requestConfig", response.code() + " " + response.message());
                        mLoginView.showToastMessage(mLoginView.getContext().getString(R.string.error_network));
                    }

                    @Override
                    public void onFinish() {
                        mLoginView.showLoading(false);
                    }
                });
    }


    @Override
    public void loadUserById(String account) {
        //根据账号查找用户
        mLoginView.showUserName(mModel.queryUser(account));
    }

    @Override
    public void selectUserInfo(UserBean userBean) {
        mLoginView.showSelectUserInfo(userBean);
    }

    @Override
    public void verifySettingPassword(String password) {
        if (mModel.getAdvancePwd().equals(password)) {
            goSettings();
        } else {
            mLoginView.showToastMessage(mLoginView.getContext().getString(R.string.error_password_no_match));
        }
    }

    @Override
    public void verifyFactorySettingsPassword(String password) {
        if (mModel.getFactoryPwd().equals(password)) {
            mLoginView.showFactorySettings();
        } else {
            mLoginView.showToastMessage(mLoginView.getContext()
                    .getString(R.string.error_password_no_match));
        }
    }

    public void goSettings() {
        mLoginView.showImplementionSettings();
    }

    UploadRunnable runnable;

    @Override
    public void start() {
        loadTitle(mModel.getDeviceTitle());
        isLogin();
        mLoginView.showLoginHistory(mModel.getCountUser(5));
        requestConfig();
        if (runnable == null) {
            Log.d("LoginPresenter", "启用runnable");
            runnable = new UploadRunnable();
            Helper.getHandler().postDelayed(runnable,
                    mModel.getGpsUpload() * 1000);
        }
    }

    public void loadTitle(String title) {
        mLoginView.showTitle(title);
    }

    @Override
    public void isLogin() {
        String userName = mModel.getLoginUserName();
        if (!StringUtils.isEmpty(userName)) {
            mLoginView.showLogin(mModel.getUserId(), userName);
        }
    }

    private RMCBean currentRMCBean = null;

    @Override
    public void onUpDate(RMCBean rmcBean) {
        this.currentRMCBean = rmcBean;
    }

    private void uploadGps() {
        MyLog.d("Login", "uploadGps " + currentRMCBean + connect);
        if (currentRMCBean != null && connect) {
            MyLog.d("Login", "uploadGps " + mModel.getGpsUpload());
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
    public void onSatelliteNumber(int number) {
        mLoginView.showGpsAmount(number);
    }

    @Override
    public void requestConfig() {
        OkGo.<String>get(Urls.host_ip + Urls.url_get_config)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLog.d("LoginPresenter" + Thread.currentThread().getName(),
                                response.body());
                        Gson gson = new Gson();
                        try {
                            ConfigBean responseBean = gson.fromJson(response.body(), ConfigBean.class);
                            if ("0".equals(responseBean.getCode())) {
                                mModel.saveGpsUploadSpeed(responseBean.getData().getGPSInterval());
                                mModel.saveMaxSpeed(responseBean.getData().getSpeed());
                                mModel.saveWriteLog(responseBean.getData().getLog());
                            }
                        } catch (Exception e) {
                            mLoginView.showToastMessage("解析数据出错！");
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        MyLog.e("LoginPresenter", response.code() + " " + response.message());
                    }
                });
    }

    @Override
    public void destroy() {
        if (runnable != null) {
            Log.d("LoginPresenter", "删除runnable");
            Helper.getHandler().removeCallbacks(runnable);
        }
        if (getProductionTask != null) {
            Helper.getHandler().removeCallbacks(getProductionTask);
        }
        if (sendWarning != null) {
            Helper.getHandler().removeCallbacks(sendWarning);
        }
    }

    private boolean connect = false;

    class UploadRunnable implements Runnable {

        @Override
        public void run() {
            uploadGps();
            Helper.getHandler().postDelayed(this, mModel.getGpsUpload() * 1000);
        }
    }

    GetProductionTask getProductionTask;


    class GetProductionTask implements Runnable {

        @Override
        public void run() {
            OkGo.<String>get(Urls.host_ip + Urls.url_get_production_task)
                    .params("terminalNo", mModel.getDeviceNumber())
                    .params("Type", mModel.getCarType())
                    .tag(this)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(Response<String> response) {
                            MyLog.d("LoginPresenter" + Thread.currentThread().getName(),
                                    response.body());
                            Gson gson = new Gson();
                            try {
                                ProductionTaskBean responseBean = gson.fromJson(response.body(), ProductionTaskBean.class);
                                if ("0".equals(responseBean.getCode())) {
                                    mLoginView.voice(PlayVoiceUtil.playCount("您有任务，请登录！"));
                                    sendWarning = new SendWarning();
                                    Helper.getHandler().postDelayed(sendWarning, 5 * 60 * 1000);
                                }

                            } catch (Exception e) {
                                mLoginView.showToastMessage("解析数据出错！");
                            }
                        }

                        @Override
                        public void onError(Response<String> response) {
                            MyLog.e("LoginPresenter#GetProductionTask", response.code() + " " + response.message());
                            Helper.getHandler().postDelayed(GetProductionTask.this, 30 * 1000);
                        }
                    });
        }
    }

    private SendWarning sendWarning;

    class SendWarning implements Runnable {

        @Override
        public void run() {
            FaultPackage faultPackage = new FaultPackage();
            faultPackage.timeHHssmm = DateFormatUtils.timeHHmmss();
            if (currentRMCBean != null) {
                faultPackage.longitude = currentRMCBean.Longitude;
                faultPackage.latitude = currentRMCBean.Latitude;
            }
            faultPackage.terminalID = mModel.getDeviceNumber();
            faultPackage.equipmentNo = "";
            faultPackage.userId = "";
            faultPackage.faultMsg = "上班有生产任务，未按要求登录。";
            faultPackage.effect = "不登录系统";
            if (LocalArguments.getInstance().carTypeId() == CarType.Truck.getTypeId()) {
                faultPackage.type = "T";
            } else {
                faultPackage.type = "D";
            }
            SendDataManager.getInstance().sendData(faultPackage.toString());
        }
    }

    @Override
    public void onNetWork(boolean ok) {
        mLoginView.showNetWorkConnect(ok);
        connect = ok;
    }
}
