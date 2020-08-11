package com.dimine.cardcar.domain.mainactivity;

import android.content.Context;

import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/27 11:04
 * desc   :
 * version: 1.0
 */
public interface MainContract {

    interface View extends BaseView<Presenter> {

        /**
         * gps星数
         */
        void showGpsAmount(int amount);

        /**
         * 网络强度
         */
        void showNetStrength(int net);

        void showFragment(BottomType flag);


        void showNightModel(boolean bl);

        void showToastMessage(String message);

        void myRecreate();

        void showLoginActivity();

        void showExitDialog();


        void showVoice(String prompt);

        void showUserName(String name);

        void showImplementionSettings();

        void showTitle(String title);

        void showLoading(boolean isShow);

        void showNetWorkConnect(boolean ok);

        Context getContext();
    }

    interface Presenter extends BasePresenter {

        void carSpeed(String speed);

        void gpsAmounts(int number);

        void netStrength(int net);

        void nightModelChange(boolean isNight);

        void recreate();

        void loadFragment(BottomType bottomType);

        void exitLogin();

        void exitDialog();

        void callControlRoom();

        void speedAlarm(double speed);

        void loadUserName();

        void verifySettingPassword(String passowrd);

        void destroy();
    }

}
