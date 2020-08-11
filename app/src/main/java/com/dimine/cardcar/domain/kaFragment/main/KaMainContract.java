package com.dimine.cardcar.domain.kaFragment.main;

import android.content.Context;

import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.bean.CarStatusBean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 16:30
 * desc   :
 * version: 1.0
 */
public interface KaMainContract {


    interface View extends BaseView<Presenter> {

        /**
         * 显示当前调度指令
         */
        void showCurrentSchedule(String schedule);

        void showMessage(String message);

        /**
         * 当班完成总量
         */
        void showCompletionTotal(String total);

        void showEmptyRunTime(String time);

        void showHeavyRunTime(String time);

        void showToastMessage(String message);

        Context viewContext();

        void showNightModeView(boolean isNight);

        void voice(String message);

    }

    interface Presenter extends BasePresenter {


        void addOil(double oil);

        void loadCurrentSchedule(SchedulingBean schedulingBean);

        void loadMessage(String message);

        boolean loadNightMode();

        void loadServerTruck(CarStatusBean carStatusBean);

        void loadOldSchedule();
    }

}
