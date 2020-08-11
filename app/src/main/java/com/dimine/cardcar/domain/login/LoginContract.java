package com.dimine.cardcar.domain.login;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.data.bean.UserBean;

import java.util.List;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/26 13:38
 * desc   :
 * version: 1.0
 */
public interface LoginContract {

    interface View extends BaseView<Presenter> {

        void showLoading(boolean isShow);

        /**
         * 显示顶部title
         */
        void showTitle(String title);

        void showUserName(UserBean userBean);

        /**
         * 显示登录过的用户
         */
        void showSelectUserInfo(@NonNull UserBean userBean);

        void showFactorySettings();

        void showImplementionSettings();

        void showLogin(String userId, String userName);

        void showMainActivity();

        void showToastMessage(String msg);

        void showLoginHistory(List<UserBean> users);

        /**
         * gps星数
         */
        void showGpsAmount(int amount);

        void voice(String str);

        void showNetWorkConnect(boolean ok);

        Context getContext();

    }

    interface Presenter extends BasePresenter {

        /**
         * 登录
         *
         * @param userId   用户id（必须）
         * @param userName 用户名（选填）
         */
        void login(String userId, String userName);

        /**
         * 根据userId查找本地数据的用户信息
         */
        void loadUserById(String userId);

        /**
         * 判断之前是否是登录的状态
         * 是：显示之前的用户id 和 用户名
         * 否：不作处理
         */
        void isLogin();

        /**
         * 快捷登录，从历史列表选择一个用户，自动填充到右边的登录信息
         */
        void selectUserInfo(UserBean userBean);

        /**
         * 实施设置的密码判断
         */
        void verifySettingPassword(String passowrd);

        /**
         * 出厂设置的密码判断
         */
        void verifyFactorySettingsPassword(String password);

        /**
         * 请求配置信息
         */
        void requestConfig();

        void destroy();

        void onSatelliteNumber(int number);

        void onUpDate(RMCBean rmcBean);

    }

}
