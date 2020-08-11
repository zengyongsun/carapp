package com.dimine.cardcar.domain.kaFragment.main;

import android.text.TextUtils;

import com.dimine.cardcar.R;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.OilBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.manager.WriteLogManager;
import com.dimine.cardcar.utils.DateFormatUtils;

import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 16:46
 * desc   :
 * version: 1.0
 */
public class MainKaPresenter implements KaMainContract.Presenter {

    private MainKaModel mModel;

    private KaMainContract.View mView;

    public MainKaPresenter(MainKaModel mModel, KaMainContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;
        this.mView.setPresenter(this);
    }

    @Override
    public void addOil(double oil) {
        OilBean oilBean = new OilBean();
        oilBean.oil = oil;
        oilBean.time = new Date();
        mModel.putOidBean(oilBean);
    }

    @Override
    public void loadCurrentSchedule(SchedulingBean schedulingBean) {
        try {
            if (TextUtils.isEmpty(schedulingBean.TID)) {
                loadMessage(schedulingBean.message);
            } else {
                mView.showCurrentSchedule(mView.viewContext().getString(R.string.schedule_before_desc)
                        + schedulingBean.destination + "，"
                        + schedulingBean.message);
            }
        } catch (Exception e) {
            e.printStackTrace();
            WriteLogManager.getInstance().writeLog("loadCurrentSchedule" + e.toString());
        }
    }

    @Override
    public void loadMessage(String message) {
        mView.showMessage(message);
    }

    @Override
    public void start() {
        mView.showNightModeView(loadNightMode());
    }

    @Override
    public boolean loadNightMode() {
        return mModel.getNightModel();
    }

    @Override
    public void loadServerTruck(CarStatusBean carStatusBean) {
        mView.showCompletionTotal(carStatusBean.equipmentWeight);
        mView.showEmptyRunTime(DateFormatUtils.minuteToHour(carStatusBean.airTime / 60 + ""));
        mView.showHeavyRunTime(DateFormatUtils.minuteToHour(carStatusBean.reloadTime / 60 + ""));
    }

    @Override
    public void loadOldSchedule() {
        SchedulingBean schedulingBean = mModel.getSchedulingBean();
        SchedulingBean message = mModel.getSchedulingMessage();
        if (schedulingBean != null) {
            if (DateFormatUtils.withinTheLegalRange(schedulingBean.shortDate, schedulingBean.shortTime)) {
                loadCurrentSchedule(schedulingBean);
            }
        }
        if (message != null) {
            if (DateFormatUtils.withinTheLegalRange(message.shortDate, message.shortTime)) {
                loadCurrentSchedule(message);
            }
        }
    }
}