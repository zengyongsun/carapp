package com.dimine.cardcar.manager;

import com.dimine.cardcar.implement.NetWork;


/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/20 10:16
 * desc   : 用来判断网络是否中断
 * version: 1.0
 */
public class MyNetWorkManager implements NetWork {

    public MyNetWorkManager() {
    }

    private boolean connection;

    public void init() {

    }

    private boolean setTime = false;

    private OnNetWorkManagerListener mNetWorkListener;

    @Override
    public boolean connection() {
        return this.connection;
    }

    public interface OnNetWorkManagerListener {
        void onNetWork(boolean ok);
    }

    public void setNetWorkListener(OnNetWorkManagerListener mListener) {
        this.mNetWorkListener = mListener;
    }

}
