package com.dimine.cardcar.domain.chanFragment.mainChan;

import android.text.TextUtils;

import com.dimine.cardcar.R;
import com.dimine.cardcar.command.ForkiftUpPackage;
import com.dimine.cardcar.data.bean.CarStatusBean;
import com.dimine.cardcar.data.bean.SchedulingBean;
import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.manager.SendDataManager;
import com.dimine.cardcar.manager.WriteLogManager;
import com.dimine.cardcar.utils.DateFormatUtils;
import com.dimine.cardcar.utils.MyLog;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/28 15:24
 * desc   :
 * version: 1.0
 */
public class MainChanPresenter implements ChanMainContract.Presenter {

    private final MainChanModel mModel;
    private final ChanMainContract.View mView;

    public MainChanPresenter(MainChanModel mModel, ChanMainContract.View mView) {
        this.mModel = mModel;
        this.mView = mView;

        this.mView.setPresenter(this);
    }

    private boolean isStart = true;

    @Override
    public void loadCurrentSchedule(SchedulingBean schedulingBean) {
        try {
            if (TextUtils.isEmpty(schedulingBean.TID)) {
                loadMessage(schedulingBean.message);
            } else {
                mView.showCurrentSchedule(mView.viewContext().getString(R.string.schedule_before_desc)
                        + schedulingBean.destination +
                        "，" + schedulingBean.message);
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
    public boolean loadNightMode() {
        return mModel.getNightModel();
    }

    @Override
    public void operationChange() {
        isStart = !isStart;
        mView.showOperationView(isStart);
    }

    @Override
    public boolean isStart() {
        return isStart;
    }

    @Override
    public void loadOldSechedul() {
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

    @Override
    public void loadServerTruck(CarStatusBean carStatusBean) {
        mView.showCompletionTotal(carStatusBean.equipmentWeight);
        mView.showPlanTotal(carStatusBean.planWeight, carStatusBean.equipmentWeight);
        mView.showWorkTime(DateFormatUtils.minuteToHour(carStatusBean.loadTime / 60 + ""));
    }

    @Override
    public void start() {
        mView.showOperationView(isStart);
        mView.showNightModeView(loadNightMode());
    }
}
