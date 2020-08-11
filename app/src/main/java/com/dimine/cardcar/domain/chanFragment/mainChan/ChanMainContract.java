package com.dimine.cardcar.domain.chanFragment.mainChan;

import android.content.Context;

import com.dimine.cardcar.base.mvpBase.BasePresenter;
import com.dimine.cardcar.base.mvpBase.BaseView;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 15:13
 * desc   :
 * version: 1.0
 */
public interface ChanMainContract {

    interface View extends BaseView<Presenter> {

        /**
         * 显示当前调度指令
         */
        void showCurrentSchedule(String schedule);

        void showMessage(String message);

        /**
         * 当班计划总量
         */
        void showPlanTotal(String plan,String complete);

        /**
         * 当班完成总量
         */
        void showCompletionTotal(String total);


        Context viewContext();

        void showToastMessage(String message);

        void showNightModeView(boolean isNight);

        void showOperationView(boolean isStart);

        void showWorkTime(String minuteToHour);
    }

    interface Presenter extends BasePresenter {

        void loadCurrentSchedule(SchedulingBean schedulingBean);

        void loadMessage(String message);

        boolean loadNightMode();

        void operationChange();

        boolean isStart();

        void loadOldSechedul();

        void loadServerTruck(CarStatusBean carStatusBean);
    }

}
