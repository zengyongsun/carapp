package com.dimine.cardcar.domain.login;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dimine.cardcar.MyApplication;
import com.dimine.cardcar.R;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.domain.advance.AdvanceSettingActivity;
import com.dimine.cardcar.domain.mainactivity.MainActivity;
import com.dimine.cardcar.domain.settings.SettingsActivity;
import com.dimine.cardcar.implement.MyTextWatcher;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.utils.ObjectBox;
import com.dimine.cardcar.view.GpsImageView;
import com.dimine.cardcar.view.SignalImageView;
import com.dimine.cardcar.view.dialog.InputPwdDialog;
import com.dimine.cardcar.view.dialog.LoadingDialog;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/30 14:50
 * desc   :
 * version: 1.0
 */
public class LoginActivity extends BaseActivity implements LoginContract.View {

    private static final String TAG = LoginActivity.class.getSimpleName();

    @BindView(R.id.etUser)
    EditText etUser;
    @BindView(R.id.etPassword)
    EditText etPassword;
    @BindView(R.id.btLogin)
    Button btLogin;
    @BindView(R.id.ivWifi)
    SignalImageView ivWifi;
    @BindView(R.id.ivGPS)
    GpsImageView ivGps;
    @BindView(R.id.tvHeaderDesc)
    TextView tvHeaderDesc;
    @BindView(R.id.led)
    ImageView mLed;
    @BindView(R.id.loginHistory)
    RecyclerView loginHistoryRecyclerView;
    @BindView(R.id.ivTransfer)
    ImageView ivTransfer;

    private LoginContract.Presenter mPresenter;

    private LoginUserAdapter loginUserAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).initRabbit();
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        MyLog.d("from", "LoginActivity ====> onCreate" + from);
        new LoginPresenter(new LoginModel(ObjectBox.get().boxFor(UserBean.class)), this,
                Helper.getHelper().getApp().gpsManager, Helper.getHelper().getApp().networkManager, from);

        initEventListener();
        setLoginRecyclerView();

        MyLog.d("life_cycle", "LoginActivity ====> onCreate");
    }

    private void isNeedUpdate() {
        //获取服务器的版本，看是否需要更新,后台下载apk
        checkUpdate();
    }

    private void checkUpdate() {
        String path = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) ||
                !Environment.isExternalStorageRemovable()) {
            try {
                path = Objects.requireNonNull(getExternalCacheDir()).getAbsolutePath();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (TextUtils.isEmpty(path)) {
                path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getAbsolutePath();
            }
        } else {
            path = getCacheDir().getAbsolutePath();
        }
        MyLog.d("UpdateAppManager", "path" + path);
    }

    private void initEventListener() {
        etUser.addTextChangedListener(new MyTextWatcher() {
            @Override
            public void afterTextChanged(Editable s) {
                mPresenter.loadUserById(s.toString());
            }
        });
    }

    private void setLoginRecyclerView() {
        loginHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        loginUserAdapter = new LoginUserAdapter();
        loginHistoryRecyclerView.setAdapter(loginUserAdapter);
        loginUserAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                UserBean userBean = (UserBean) adapter.getItem(position);
                mPresenter.selectUserInfo(userBean);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestInternet();
        isNeedUpdate();
    }


    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        MyLog.d("life_cycle", "LoginActivity ====> onDestroy");
    }

    @OnClick(R.id.btLogin)
    public void onBtLoginClicked() {
        mPresenter.login(etUser.getText().toString(), etPassword.getText().toString());
    }

    LoadingDialog loadingDialog;

    public void initLoading() {
        if (loadingDialog == null) {
            loadingDialog = new LoadingDialog(this);
        }
    }

    @Override
    public void showLoading(boolean isShow) {
        initLoading();
        if (isShow) {
            loadingDialog.show();
        } else {
            loadingDialog.dismiss();
        }
    }

    @Override
    public void showTitle(String title) {
        tvHeaderDesc.setText(title);
    }

    @Override
    public void showUserName(UserBean userBean) {
        if (userBean != null) {
            etPassword.setText(userBean.UserName);
        } else {
            etPassword.setText("");
        }
    }

    @Override
    public void showSelectUserInfo(@NonNull UserBean userBean) {
        etUser.setText(userBean.UserId);
        etPassword.setText(userBean.UserName);
    }

    @Override
    public void showFactorySettings() {
        //出厂设置界面
        startActivity(new Intent(LoginActivity.this, AdvanceSettingActivity.class));
    }

    @Override
    public void showImplementionSettings() {
        startActivity(new Intent(LoginActivity.this, SettingsActivity.class));
    }

    @Override
    public void showLogin(String userId, String userName) {
        etUser.setText(userId);
        etPassword.setText(userName);
    }

    @Override
    public void showMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void showToastMessage(String msg) {
        MyToast.showShort(this, msg);
    }

    @Override
    public void showLoginHistory(List<UserBean> users) {
        loginUserAdapter.setNewData(users);
    }

    @Override
    public void showGpsAmount(int amount) {
        ivGps.setGpsImg(amount);
    }

    @Override
    public void showNetStrength(int net) {
        ivWifi.setSignalImg(net);
    }

    @Override
    public void setPresenter(LoginContract.Presenter presenter) {
        mPresenter = presenter;
    }

    @Override
    public void voice(String str) {
        Helper.getHelper().getApp().voice(str);
    }

    @Override
    public void showNetWorkConnect(boolean ok) {
        if (ok) {
            ivTransfer.setImageResource(R.mipmap.icon_connect_yes);
        } else {
            ivTransfer.setImageResource(R.mipmap.icon_connect_no);
        }
    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.tvHeaderDesc)
    public void onTopViewClicked() {
        hiddenFunction();
    }

    ////////////////////// 隐藏的界面 start ///////////////////////////////////////

    private int count = 0;
    private long timeStart = 0;

    public void hiddenFunction() {
        long clickTime = 2 * 1000;
        if (timeStart == 0) {
            timeStart = System.currentTimeMillis();
        }
        count++;
        if (count == 7) {
            if (System.currentTimeMillis() - timeStart < clickTime) {
                InputPwdDialog inputDialog = new InputPwdDialog(this,
                        getString(R.string.dialog_one_setting_title),
                        getString(R.string.dialog_password_desc));

                inputDialog.setListener(new InputPwdDialog.LoginDialogClickListener() {
                    @Override
                    public void confirm(String password) {
                        mPresenter.verifyFactorySettingsPassword(password);
                        inputDialog.dismiss();
                    }
                });
                inputDialog.show();
            } else {
                timeStart = 0;
            }
            count = 0;
        }
    }

    ////////////////////// 隐藏的界面 end ///////////////////////////////////////

    public boolean setTime(int time) {
        MyLog.d(TAG, "setTime: " + time);
        return SystemClock.setCurrentTimeMillis(time * 1000L);
    }

    @OnClick(R.id.timeView)
    public void onTimeViewClicked() {

        InputPwdDialog inputDialog = new InputPwdDialog(this,
                getString(R.string.dialog_two_setting_title),
                getString(R.string.dialog_password_desc));

        inputDialog.setListener(new InputPwdDialog.LoginDialogClickListener() {
            @Override
            public void confirm(String password) {
                mPresenter.verifySettingPassword(password);
                inputDialog.dismiss();
            }
        });
        inputDialog.show();
    }


    public static final int request_location_code = 100;
    public static final int request_internet_code = 101;


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //将结果转发给 EasyPermissions
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @AfterPermissionGranted(request_location_code)
    private void requestLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
//            mLocationManager.requestLocationUpdates("gps", 0, 0, locationListener);
        } else {
            EasyPermissions.requestPermissions(this, "需要定位权限才能使用该功能",
                    request_location_code, perms);
        }
    }

    @AfterPermissionGranted(request_internet_code)
    private void requestInternet() {
        String[] perms = {Manifest.permission.INTERNET};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mPresenter.start();
        } else {
            EasyPermissions.requestPermissions(this, "网络访问权限",
                    request_location_code, perms);
        }
    }

}
