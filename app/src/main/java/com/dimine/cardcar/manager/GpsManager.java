package com.dimine.cardcar.manager;

import android.text.TextUtils;

import com.dimine.cardcar.data.bean.GSABean;
import com.dimine.cardcar.data.bean.RMCBean;
import com.dimine.cardcar.utils.MyLog;

import java.text.DecimalFormat;
import java.util.LinkedList;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/6/19 8:42
 * desc   : GPS的管理者类
 * version: 1.0
 */
public class GpsManager  {

    private final LinkedList<RMCBean> linkedList;

    private final int one_second_count = 5;

    private DecimalFormat df2 = new DecimalFormat("#0");

    public GpsManager() {

        linkedList = new LinkedList<>();
    }

    public RMCBean currentRMCBean;

    int counter = 0;

    double maxSpeed;

    public void RMCdata(RMCBean rmcBean) {
        //1. 存数据
        linkedList.addLast(rmcBean);
        int linkSize = linkedList.size();
        counter++;
        //2. 每秒取一次平均值（第一次是5个数，之后都是10个数，相当于两秒的数据平均值）
        if (counter % one_second_count == 0) {
            double count = 0;
            for (RMCBean bean : linkedList) {
                count += Double.parseDouble(bean.getSpd());
            }
            double speed = count / linkSize;

            if (maxSpeed == 0) {
                maxSpeed = speed;
            } else {
                maxSpeed = maxSpeed > speed ? maxSpeed : speed;
            }
            MyLog.d("GpsManager", "maxSpeed = " + maxSpeed);
            if (gpsListener != null) {

                gpsListener.onSpeed(Double.parseDouble(df2.format(speed)));
                gpsListener.onLatitudeAndLongitude(rmcBean.getLatitude(), rmcBean.getLongitude());
                gpsListener.onUpDate(rmcBean);
                this.currentRMCBean = rmcBean;
                if (rmcBean.timestamp() != -1) {
                    gpsListener.onTimestamp(rmcBean.timestamp());
                }
            }
        }
        //等于10个数据的时候，加入一个删除一个
        if (linkSize == one_second_count * 2) {
            linkedList.removeFirst();
        }
    }



    public void GSAData(GSABean gsaBean) {
        if (gpsListener != null) {
            if (TextUtils.isEmpty(gsaBean.sv)) {
                gpsListener.onSatelliteNumber(0);
            } else {
                gpsListener.onSatelliteNumber(Integer.parseInt(gsaBean.sv));
            }
        }
    }


    public interface GpsSensorDataListener {

        void onLatitudeAndLongitude(double latitude, double longitude);

        void onUpDate(RMCBean rmcBean);

        void onSpeed(double speed);

        void onSatelliteNumber(int number);

        void onTimestamp(long timestamp);

    }

    private GpsSensorDataListener gpsListener;

    public void setGpsSensorDataListener(GpsSensorDataListener gpsListener) {
        this.gpsListener = gpsListener;
    }
}
