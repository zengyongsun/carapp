package com.dimine.cardcar;

import com.dimine.cardcar.data.bean.DriverBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.domain.login.LoginContract;
import com.dimine.cardcar.domain.login.LoginModel;
import com.dimine.cardcar.domain.login.LoginPresenter;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/12 15:07
 * desc   :
 * version: 1.0
 */
public class LoginPresentTest {

    @Mock
    private LoginModel mModel;

    @Mock
    private LoginContract.View mLoginView;

    private LoginPresenter mLoginPresenter;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);

//        mLoginPresenter = new LoginPresenter(mModel, mLoginView,mock(GpsManager.class),mock(TelnetManager.class));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        //验证 setPresenter 行为是否发生
        verify(mLoginView).setPresenter(mLoginPresenter);
    }

    @Test
    public void startLoginActivity_startMethod() {
        //验证开始的时候是否调用，title 和 isLogin
        when(mModel.getCarTypeId()).thenReturn(1);
        when(mModel.getLoginUserName()).thenReturn("1");
        mLoginPresenter.start();
        verify(mLoginView).showTitle(Mockito.anyString());
        verify(mLoginView).showLogin(anyString(),anyString());
    }

    @Test
    public void showUserDialog() {
        verify(mModel).getAll();
        verify(mLoginView).showLoginHistory(any(ArrayList.class));
    }

    @Test
    public void selectUser() {
//        mLoginPresenter = new LoginPresenter(mModel, mLoginView,mock(GpsManager.class),mock(TelnetManager.class));
        mLoginPresenter.selectUserInfo(any(UserBean.class));
        verify(mLoginView).showSelectUserInfo(any(UserBean.class));
    }

    @Test
    public void inputUserId() {
        mLoginPresenter.loadUserById("1");
        verify(mModel).queryUser(anyString());
        verify(mLoginView).showUserName(any(DriverBean.DataBean.class));
    }

    @Test
    public void goSettingsActivity() {
        when(mModel.getAdvancePwd()).thenReturn("666");
//        mLoginPresenter.verifySettingPassword("666");
        verify(mLoginView).showFactorySettings();
//        mLoginPresenter.verifySettingPassword("555");
        verify(mLoginView).showToastMessage(anyString());
    }

    @Test
    public void loginOperation_userIdEmpty() {
        //情景一：userId 为空
        mLoginPresenter.login("", "");
        verify(mLoginView).showToastMessage(anyString());
    }

    @Test
    public void loginOperation_userNameNoPresence() {
        //情景二：userId 为 "不存在工号"
        mLoginPresenter.login("", "不存在工号");
        verify(mLoginView).showToastMessage(anyString());
    }

    @Test
    public void loginOperation_pass() {
        //通过
        mLoginPresenter.login("12", anyString());
        verify(mModel).saveUserId(anyString());
        verify(mModel).saveUserName(anyString());

        verify(mLoginView).showLogin(anyString(),anyString());
    }
}
