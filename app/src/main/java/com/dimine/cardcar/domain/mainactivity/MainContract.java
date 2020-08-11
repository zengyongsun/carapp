package com.dimine.cardcar.domain.mainactivity;

import android.content.Context;

import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;
import com.dimine.cardcar.data.bean.RMCBean;

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

        void nightModelChange(boolean isNight);

        void recreate();

        void loadFragment(BottomType bottomType);

        void exitLogin();

        void exitDialog();

        void loadUserName();

        void verifySettingPassword(String passowrd);

        void destroy();

        void onUpDate(RMCBean rmcBean);
    }

}
