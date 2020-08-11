package com.dimine.cardcar.domain.mainactivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.dimine.cardcar.MyApplication;
import com.dimine.cardcar.R;
import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.CarType;
import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.base.mvpBase.BaseActivity;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.domain.chanFragment.mainChan.MainChanFragment;
import com.dimine.cardcar.domain.kaFragment.main.MainKaFragment;
import com.dimine.cardcar.domain.login.LoginActivity;
import com.dimine.cardcar.domain.settingFragment.SettingFragment;
import com.dimine.cardcar.domain.settings.SettingsActivity;
import com.dimine.cardcar.utils.MyLog;
import com.dimine.cardcar.utils.MyToast;
import com.dimine.cardcar.view.BottomActionLayout;
import com.dimine.cardcar.view.GpsImageView;
import com.dimine.cardcar.view.SignalImageView;
import com.dimine.cardcar.view.TimeView;
import com.dimine.cardcar.view.dialog.ConfirmDialog;
import com.dimine.cardcar.view.dialog.InputPwdDialog;
import com.dimine.cardcar.view.dialog.LoadingDialog;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 14:50
 * desc   :
 * version: 1.0
 */
public class MainActivity extends BaseActivity
        implements MainContract.View, BottomActionLayout.BottomActionClickListener,
        MyApplication.ReceiveDataListener {

    public static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.ivWifi)
    SignalImageView ivWifi;
    @BindView(R.id.ivGPS)
    GpsImageView ivGps;

    @BindView(R.id.contentLayout)
    FrameLayout contentLayout;
    @BindView(R.id.timeView)
    TimeView timeView;
    @BindView(R.id.led)
    ImageView mLed;

    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvHeaderDesc)
    TextView tvHeaderDesc;
    @BindView(R.id.ivTransfer)
    ImageView ivTransfer;


    private FragmentManager fragmentManager;
    private MainContract.Presenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_c_layout);
        ButterKnife.bind(this);
        ((MyApplication) getApplication()).setReceiveDataListener(this);
        fragmentManager = getSupportFragmentManager();
        clearFragment(fragmentManager);

        new MainPresenter(new MainModel(), this,
                Helper.getHelper().getApp().gpsManager,
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

    /**
     * 4G强度显示
     */
    @Override
    public void showNetStrength(int net) {
        ivWifi.setSignalImg(net);
    }


    @Override
    public void showNightModel(boolean bl) {
        mPresenter.nightModelChange(bl);
    }

    ////////////////////// Fragment的加载  ///////////////////////////////////////


    private Fragment mFragment;

    private MainKaFragment kaFragment;


    private MainChanFragment chanFragment;



    private SettingFragment settingFragment;

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

            case Two:
                if (type == CarType.Truck.getTypeId()) {

                }
            case Three:

            case Four:

            case Five:
                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
                return settingFragment;
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

    @Override
    public void onClick(BottomType bottomType) {
        mPresenter.loadFragment(bottomType);
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
            case R.id.btCall:
                mPresenter.callControlRoom();
                break;
            default:
                break;
        }
    }

    public void callControlRoom() {
        mPresenter.callControlRoom();
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



}
