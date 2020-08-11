package com.dimine.cardcar;

import com.dimine.cardcar.domain.mainactivity.MainContract;
import com.dimine.cardcar.domain.mainactivity.MainModel;
import com.dimine.cardcar.domain.mainactivity.MainPresenter;
import com.dimine.cardcar.manager.GpsManager;
import com.dimine.cardcar.manager.MyNetWorkManager;
import com.dimine.cardcar.data.bean.TelnetStatus;
import com.dimine.cardcar.base.BottomType;
import com.dimine.cardcar.utils.EncryptionUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.RETURNS_DEEP_STUBS;
import static org.mockito.Mockito.anyBoolean;
import static org.mockito.Mockito.anyDouble;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/23 16:27
 * desc   :
 * version: 1.0
 */
public class MainPresenterTest {

    @Mock
    private MainModel mModel;

    @Mock
    private MainContract.View mMainView;


    private MainPresenter mainPresenter;

    @Before
    public void setupMocksAndView() {
        MockitoAnnotations.initMocks(this);
        GpsManager gpsManager = mock(GpsManager.class, RETURNS_DEEP_STUBS);
        MyNetWorkManager netWorkManager = mock(MyNetWorkManager.class, RETURNS_DEEP_STUBS);

        mainPresenter = new MainPresenter(mModel, mMainView, gpsManager, netWorkManager);
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        //验证 setPresenter 行为是否发生
        verify(mMainView).setPresenter(mainPresenter);
    }

    @Test
    public void startMethod_call() {
        when(mModel.getCarTypeId()).thenReturn(1);
        when(mModel.getNightModel()).thenReturn(true);

        mainPresenter.start();

        verify(mMainView).showNightModel(anyBoolean());
    }

    @Test
    public void telnetMethod_message() {
        TelnetStatus status = mock(TelnetStatus.class, RETURNS_DEEP_STUBS);

        verify(mMainView).showNetStrength(anyInt());
    }

    @Test
    public void gpsSensor_message() {
        mainPresenter.gpsAmounts(anyInt());
        mainPresenter.onLatitudeAndLongitude(anyDouble(), anyDouble());
        mainPresenter.onSpeed(anyDouble());

        verify(mMainView).showGpsAmount(anyInt());
        verify(mMainView).showMapLocation(anyDouble(), anyDouble());
        verify(mMainView).showCarSpeed(anyString());
    }

    @Test
    public void nightModelMethod_call() {
        mainPresenter.nightModelChange(anyBoolean());
        verify(mModel).saveNightModel(anyBoolean());
        verify(mMainView).myRecreate();
    }

    @Test
    public void loadFragmentMethod_call() {
        mainPresenter.loadFragment(BottomType.One);
        verify(mMainView).showFragment(BottomType.One);
    }

    @Test
    public void exitLoginMethod_call() {
        mainPresenter.exitDialog();
        verify(mMainView).showExitDialog();

        mainPresenter.exitLogin();
        verify(mModel).saveUserName(anyString());
        verify(mMainView).showLoginActivity();
    }

    @Test
    public void name() {
        Assert.assertEquals("", EncryptionUtils.EncodeByMD5("8888"));
    }
}
