package com.dimine.cardcar.domain.login;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.dimine.cardcar.MyApplication;
import com.dimine.cardcar.R;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.domain.advance.AdvanceSettingActivity;
import com.dimine.cardcar.domain.mainactivity.MainActivity;
import com.dimine.cardcar.domain.settings.SettingsActivity;
import com.dimine.cardcar.implement.MyLocationListener;
import com.dimine.cardcar.implement.MyTextWatcher;
import com.dimine.cardcar.utils.DateFormatUtils;
import com.dimine.cardcar.utils.GPSConversionUtil;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.utils.ObjectBox;
import com.dimine.cardcar.view.GpsImageView;
import com.dimine.cardcar.view.dialog.InputPwdDialog;
import com.dimine.cardcar.view.dialog.LoadingDialog;

import java.text.DecimalFormat;
import java.util.List;

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
    @BindView(R.id.ivGPS)
    GpsImageView ivGps;
    @BindView(R.id.tvHeaderDesc)
    TextView tvHeaderDesc;
    @BindView(R.id.loginHistory)
    RecyclerView loginHistoryRecyclerView;
    @BindView(R.id.ivTransfer)
    ImageView ivTransfer;

    private LoginContract.Presenter mPresenter;

    private LoginUserAdapter loginUserAdapter;

    private LocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).initRabbit();
        Intent intent = getIntent();
        String from = intent.getStringExtra("from");
        mLocationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        new LoginPresenter(new LoginModel(ObjectBox.get().boxFor(UserBean.class)), this,
                Helper.getHelper().getApp().networkManager, from);

        initEventListener();
        setLoginRecyclerView();
        requestLocation();
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

    private final long time_interval = 2 * 1000;
    private long last_out_time = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    private void exit() {
        long current_time = System.currentTimeMillis();
        if (current_time - last_out_time < time_interval) {
            finish();
        } else {
            last_out_time = current_time;
            MyToast.showShort(this, "再按一次退出程序");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        if (mLocationManager != null) {
            mLocationManager.removeUpdates(gpsLocationListener);
        }
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

    @AfterPermissionGranted(request_internet_code)
    private void requestPermission() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.INTERNET};
        if (EasyPermissions.hasPermissions(this, perms)) {
            mPresenter.start();
        } else {
            EasyPermissions.requestPermissions(this, "网络访问权限",
                    request_location_code, perms);
        }
    }

    @SuppressLint("MissingPermission")
    @AfterPermissionGranted(request_location_code)
    private void requestLocation() {
        String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
        if (EasyPermissions.hasPermissions(this, perms)) {
            //参数2和3，如果参数3不为0，则以参数3为准；参数3为0，则通过时间来定时更新；两者为0，则随时刷新
            boolean gpsOpen = mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            if (gpsOpen) {
                mLocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000,
                        0, netWorkLocationListener);
                mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 1000,
                        0, gpsLocationListener);
            } else {
                openGpsAction();
            }
        } else {
            EasyPermissions.requestPermissions(this, "定位权限",
                    request_location_code, perms);
        }
    }

    DecimalFormat df7 = new DecimalFormat("#.0000000");

    private Location mLocation;

    private MyLocationListener gpsLocationListener = new MyLocationListener() {
        /**
         * 位置更改时调用
         * @param location 位置信息
         */
        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, mLocation)) {
                mLocation = location;
                upData(location);
            }
        }

        /**
         * GPS状态变化时触发
         * @param provider
         * @param status LocationProvider.AVAILABLE 可用； LocationProvider.OUT_OF_SERVICE 服务区外状态；
         *               LocationProvider.TEMPORARILY_UNAVAILABLE 暂停服务状态
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            statusChanged(status);
        }
    };

    private MyLocationListener netWorkLocationListener = new MyLocationListener() {
        /**
         * 位置更改时调用
         * @param location 位置信息
         */
        @Override
        public void onLocationChanged(Location location) {
            if (isBetterLocation(location, mLocation)) {
                mLocation = location;
                upData(location);
            }
        }

        /**
         * GPS状态变化时触发
         * @param provider
         * @param status LocationProvider.AVAILABLE 可用； LocationProvider.OUT_OF_SERVICE 服务区外状态；
         *               LocationProvider.TEMPORARILY_UNAVAILABLE 暂停服务状态
         * @param extras
         */
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            statusChanged(status);
        }
    };

    private void upData(Location location) {
        //精确度，以密为单位
        float accuracy = location.getAccuracy();
        //获取海拔高度
        double altitude = location.getAltitude();
        //经度
        double longitude = location.getLongitude();
        //纬度
        double latitude = location.getLatitude();
        //角度
        float bearing = location.getBearing();
        //速度
        float speed = location.getSpeed() / 1.85f;
        //时间
        long time = location.getTime();
        String[] endTime = DateFormatUtils.splitGpsTime(time);
        if (mPresenter != null) {
            mPresenter.onUpDate(new RMCBean(endTime[0], "A",
                    df7.format(GPSConversionUtil.mapCoordinateToDegrees(latitude + "")) + "",
                    df7.format(GPSConversionUtil.mapCoordinateToDegrees(longitude + "")) + "",
                    speed + "", bearing + "", endTime[0]));
        }
        Log.d(TAG, location.toString());
    }

    private void statusChanged(int status) {
        switch (status) {
            case LocationProvider.AVAILABLE:
                showGpsAmount(8);
                Log.e(TAG, "onStatusChanged" + "当前GPS状态为可见状态");
                break;
            case LocationProvider.OUT_OF_SERVICE:
                showGpsAmount(0);
                Log.e(TAG, "onStatusChanged" + "当前GPS状态为服务区外状态");
                break;
            case LocationProvider.TEMPORARILY_UNAVAILABLE:
                showGpsAmount(0);
                Log.e(TAG, "onStatusChanged" + "当前GPS状态为暂停服务状态");
                break;
            default:
                break;
        }
    }

    public static final int GPS_CODE = 200;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GPS_CODE) {
            requestLocation();
        }
    }

    private void openGpsAction() {
        Toast.makeText(this, "系统检测到未开启GPS定位服务,请开启",
                Toast.LENGTH_SHORT).show();
        Intent intent = new Intent();
        intent.setAction(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        startActivityForResult(intent, GPS_CODE);
    }

    private static final int TWO_MINUTES = 1000 * 60 * 2;

    /**
     * 确定一个位置读数是否比当前位置修正更好
     *
     * @param location            您要评估的新位置
     * @param currentBestLocation 您要与新位置信息进行比较的当前位置信息
     */
    protected boolean isBetterLocation(Location location, Location currentBestLocation) {
        if (currentBestLocation == null) {
            // 新位置总比没有位置好
            return true;
        }

        // 检查新的位置修复是新的还是旧的
        long timeDelta = location.getTime() - currentBestLocation.getTime();
        boolean isSignificantlyNewer = timeDelta > TWO_MINUTES;
        boolean isSignificantlyOlder = timeDelta < -TWO_MINUTES;
        boolean isNewer = timeDelta > 0;

        // 如果距当前位置已超过两分钟，请使用新位置
        // 因为用户可能已经移动
        if (isSignificantlyNewer) {
            return true;
            //如果新位置的时间早于两分钟，那肯定会更糟
        } else if (isSignificantlyOlder) {
            return false;
        }

        // 检查新的位置修复是否或多或少准确
        int accuracyDelta = (int) (location.getAccuracy() - currentBestLocation.getAccuracy());
        boolean isLessAccurate = accuracyDelta > 0;
        boolean isMoreAccurate = accuracyDelta < 0;
        boolean isSignificantlyLessAccurate = accuracyDelta > 200;

        // 检查旧位置和新位置是否来自同一提供商
        boolean isFromSameProvider = isSameProvider(location.getProvider(),
                currentBestLocation.getProvider());

        // 结合及时性和准确性来确定位置质量
        if (isMoreAccurate) {
            return true;
        } else if (isNewer && !isLessAccurate) {
            return true;
        } else if (isNewer && !isSignificantlyLessAccurate && isFromSameProvider) {
            return true;
        }
        return false;
    }

    /**
     * 检查两个提供者是否相同
     */
    private boolean isSameProvider(String provider1, String provider2) {
        if (provider1 == null) {
            return provider2 == null;
        }
        return provider1.equals(provider2);
    }

}
