package com.dimine.cardcar.domain.mainactivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationManager;
import android.location.LocationProvider;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.dimine.cardcar.MyApplication;
import com.dimine.cardcar.R;
import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.domain.chanFragment.mainChan.MainChanFragment;
import com.dimine.cardcar.domain.kaFragment.main.MainKaFragment;
import com.dimine.cardcar.domain.login.LoginActivity;
import com.dimine.cardcar.domain.settings.SettingsActivity;
import com.dimine.cardcar.implement.MyLocationListener;
import com.dimine.cardcar.utils.GPSConversionUtil;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.view.GpsImageView;
import com.dimine.cardcar.view.TimeView;
import com.dimine.cardcar.view.dialog.ConfirmDialog;
import com.dimine.cardcar.view.dialog.InputPwdDialog;
import com.dimine.cardcar.view.dialog.LoadingDialog;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.EasyPermissions;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 14:50
 * desc   :
 * version: 1.0
 */
public class MainActivity extends BaseActivity
        implements MainContract.View, MyApplication.ReceiveDataListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.ivGPS)
    GpsImageView ivGps;

    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;
    @BindView(R.id.timeView)
    TimeView timeView;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvHeaderDesc)
    TextView tvHeaderDesc;
    @BindView(R.id.ivTransfer)
    ImageView ivTransfer;


    private FragmentManager fragmentManager;
    private MainContract.Presenter mPresenter;
    private LocationManager mLocationManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_c_layout);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).setReceiveDataListener(this);
        fragmentManager = getSupportFragmentManager();
        clearFragment(fragmentManager);

        new MainPresenter(new MainModel(), this,
                Helper.getHelper().getApp().networkManager);

        if (null != savedInstanceState) {
            BottomType bottomType = (BottomType) savedInstanceState.getSerializable("show");
            mPresenter.loadFragment(bottomType);
        } else {
            mPresenter.loadFragment(BottomType.One);
        }
        MyLog.d("life_cycle", "MainActivity ====> onCreate");
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

    @Override
    public void myRecreate() {
        Helper.getHandler().postDelayed(new Runnable() {
            @Override
            public void run() {
                recreate();
            }
        }, 500);
    }

    @Override
    public void showLoginActivity() {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        //可以把要传递的参数放到一个bundle里传递过去，bundle可以看做一个特殊的map.
        Bundle bundle = new Bundle();
        bundle.putString("from", "main");
        intent.putExtras(bundle);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void showToastMessage(String message) {
        MyToast.showShort(this, message);
    }


    /**
     * GPS信号强度显示
     */
    @Override
    public void showGpsAmount(int amount) {
        ivGps.setGpsImg(amount);
    }

    @Override
    public void showNightModel(boolean bl) {
        mPresenter.nightModelChange(bl);
    }

    ////////////////////// Fragment的加载  ///////////////////////////////////////
    private Fragment mFragment;
    private MainKaFragment kaFragment;
    private MainChanFragment chanFragment;

    private Fragment getFragment(BottomType flag) {
        int type = LocalArguments.getInstance().carTypeId();
        switch (flag) {
            case One:
                if (type == CarType.Truck.getTypeId()) {
                    if (kaFragment == null) {
                        kaFragment = new MainKaFragment();
                    }
                    return kaFragment;
                } else if (type == CarType.Forklift.getTypeId()) {
                    if (chanFragment == null) {
                        chanFragment = new MainChanFragment();
                    }
                    return chanFragment;
                }
            default:
                return null;
        }
    }

    private BottomType bottomType;

    @Override
    public void showFragment(BottomType flag) {
        FragmentTransaction ft = fragmentManager.beginTransaction();
        Fragment fragment = getFragment(flag);
        this.bottomType = flag;
        if (mFragment == null) {
            ft.add(R.id.contentLayout, fragment).commit();
            mFragment = fragment;
            return;
        }
        if (fragment != mFragment) {
            if (!fragment.isAdded()) {
                ft.hide(mFragment).add(R.id.contentLayout, fragment).commit();
            } else {
                ft.hide(mFragment).show(fragment).commit();
            }
            mFragment = fragment;
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("show", bottomType);
    }

    ////////////////////// 文字转语音  ///////////////////////////////////////

    public void voice(String str) {
        Helper.getHelper().getApp().voice(str);
    }

    ////////////////////// 退出登录  ///////////////////////////////////////

    @Override
    public void showExitDialog() {
        ConfirmDialog confirmDialog = new ConfirmDialog(this,
                getString(R.string.hint_out_message));
        confirmDialog.setConfirmDialogClickListener(new ConfirmDialog.ConfirmDialogClickListener() {
            @Override
            public void confirm() {
                mPresenter.exitLogin();
            }
        });
        confirmDialog.show();
    }


    @Override
    public void showVoice(String prompt) {
        voice(prompt);
    }

    @Override
    public void showUserName(String name) {
        tvName.setText(name);
    }

    @Override
    public void showImplementionSettings() {
        startActivity(new Intent(this, SettingsActivity.class));
    }

    @Override
    public void showTitle(String title) {
        tvHeaderDesc.setText(title);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.destroy();
        ((MyApplication) getApplication()).setReceiveDataListener(null);
        MyLog.d("life_cycle", "MainActivity ====> onDestroy");
    }

    @OnClick({R.id.ivHeader, R.id.tvName, R.id.timeView, R.id.btCall})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ivHeader:
            case R.id.tvName:
                mPresenter.exitDialog();
                break;
            case R.id.timeView:
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
                break;
            default:
                break;
        }
    }

    /**
     * 清空之前add的fragment，否认切换夜间模式会出现重叠，FragmentManager带了重建的效果
     *
     * @param fragmentManager
     */
    public void clearFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int count = fragments.size();
        for (int i = 0; i < count; i++) {
            fragmentManager.beginTransaction().remove(fragments.get(i)).commit();
        }
    }

    @Override
    public void setPresenter(MainContract.Presenter presenter) {
        mPresenter = presenter;
    }


    @Override
    public void onCarStatus(CarStatusBean truck) {
        if ("0".equals(truck.equipmentStatus)) {
            voice("连接超时，请重新登录");
            showLoginActivity();
            return;
        }
        if (kaFragment != null) {
            kaFragment.onCarStatus(truck);
        }
        if (chanFragment != null) {
            chanFragment.onCarStatus(truck);
        }
    }


    @Override
    public void onTruckScheduling(SchedulingBean schedulingBean) {
        if (kaFragment != null) {
            kaFragment.onTruckScheduling(schedulingBean);
        }
        if (chanFragment != null) {
            chanFragment.onScheduling(schedulingBean);
        }

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
    SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd HHmmss");

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
        String[] endTime = sdf.format(new Date(time)).split(" ");
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
