package com.dimine.cardcar.manager;

import android.os.Handler;
import android.text.TextUtils;

import com.dimine.cardcar.data.bean.TimeRecordBean;
import com.dimine.cardcar.utils.DateFormatUtils;
import com.dimine.cardcar.utils.ObjectBox;

import java.util.Date;

import io.objectbox.Box;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/15 9:39
 * desc   :
 * version: 1.0
 */
public class TimingManager {

    /**
     * BY = 0,//备用
     * ZY = 1,//重运
     * KY = 2,//空运
     * DZ = 3,//待装
     * DX = 4,//待卸
     * ZZ = 5,//装载
     * XZ = 6,//卸载
     * JY = 7, //加油
     * GZ = 8,//故障
     * LX = 9,//离线
     * KX=20 //空闲
     */
    private final String BY = "0";
    private final String ZY = "1";
    private final String KY = "2";
    private final String DZ = "3";
    private final String DX = "4";
    private final String ZZ = "5";
    private final String XZ = "6";
    private final String JY = "7";
    private final String GZ = "8";
    private final String LX = "9";
    private final String KX = "20";

    private Handler mHandler;

    private Box<TimeRecordBean> timeBox;

    private TimingRunnable runnable;

    private int timeCount = 1;
    private int timeInterval = 8 * 60 * 60;

    private TimeRecordBean currentTimeBean;

    public TimingManager(Handler handler) {
        this.mHandler = handler;
        this.timeBox = ObjectBox.get().boxFor(TimeRecordBean.class);
        init();
    }

    private String outType = "0";

    public String getOutType() {
        return outType;
    }

    private void init() {
        TimeRecordBean recordBean = timeBox.get(1);
        if (recordBean == null) {
            recordBean = new TimeRecordBean();
            recordBean.writeTime = new Date();
            recordBean.userId = "";
            timeBox.put(recordBean);
            currentTimeBean = recordBean;
        } else {
            currentTimeBean = recordBean;
            if (!checkTime()) {
                resetBean();
            }
        }
        runnable = new TimingRunnable();
        startTiming();
    }

    private void resetBean() {
        TimeRecordBean recordBean = timeBox.get(1);
        recordBean.writeTime = new Date();
        recordBean.emptyTime = 0;
        recordBean.heavyTime = 0;
        recordBean.loadingTime = 0;
        recordBean.unloadingTime = 0;
        recordBean.idleTime = 0;
        recordBean.spareTime = 0;
        timeBox.put(recordBean);
        currentTimeBean = recordBean;
    }

    /**
     * 同一天累加
     */
    private boolean checkTime() {
        long write = currentTimeBean.writeTime.getTime();

        Date oldDate = new Date(write);
        //当前时间减去写入的时间
        Date now = new Date();
        if (DateFormatUtils.isSameDay(oldDate, now)) {
            return true;
        }
        return false;
    }

    /**
     * 是否更换用户  登录的时候需要判断
     *
     * @return true 更换了，用户需要置零数据
     */
    public void checkAccount(String userId) {
        if (TextUtils.isEmpty(currentTimeBean.userId)) {
            //1.之前没有登录的情况
        } else {
            if (userId.equals(currentTimeBean.userId)) {
                //2.登录了，关机后，再次登录同一个用户

            } else {
                resetBean();
            }
        }
        currentTimeBean.userId = userId;
        timeBox.put(currentTimeBean);
    }


    public TimeRecordBean getTimeRecordBean() {
        return timeBox.get(1);
    }

    /**
     * 设置累计的状态类型
     *
     * @param type 类型
     */
    public void setRunnableType(String type) {
        runnable.setType(type);
        outType = type;
    }

    /**
     * 开始计时
     */
    public void startTiming() {
        mHandler.post(runnable);
    }

    /**
     * 结束计时
     */
    public void stopTiming() {
        mHandler.removeCallbacks(runnable);
    }

    class TimingRunnable implements Runnable {

        private String type = "-1";

        public void setType(String type) {
            this.type = type;
        }

        @Override
        public void run() {
            switch (type) {
                case ZY:
                    //重运
                    currentTimeBean.heavyTime += timeCount;
                    break;
                case KY:
                    //空运
                    currentTimeBean.emptyTime += timeCount;
                    break;
                case XZ:
                    //卸载
                    currentTimeBean.unloadingTime += timeCount;
                    break;
                case ZZ:
                    //装载
                    currentTimeBean.loadingTime += timeCount;
                    break;
                case KX:
                    currentTimeBean.idleTime += timeCount;
                    break;
                case BY:
                    currentTimeBean.spareTime += timeCount;
                    break;
                default:
                    break;
            }
            timeBox.put(currentTimeBean);
            mHandler.postDelayed(this, timeCount * 1000);
        }
    }
}
