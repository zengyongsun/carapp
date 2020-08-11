package com.dimine.cardcar.manager;

import com.dimine.cardcar.base.Helper;
import com.dimine.cardcar.data.remote.Urls;
import com.dimine.cardcar.implement.NetWork;
import com.dimine.cardcar.utils.MyLog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import okhttp3.Headers;


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
        Helper.getHandler().post(new Runnable() {
            @Override
            public void run() {
                request();
                Helper.getHandler().postDelayed(this, 8 * 1000);
            }
        });
    }

    public void request() {
        OkGo.<String>get(Urls.host_ip + Urls.heartbeat_url)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(Response<String> response) {
                        MyLog.e("NetWorkManager", response.code() + " " + response.message());
                        Headers headers = response.headers();
                        if (response.code() == 200) {
                            if (mNetWorkListener != null) {
                                mNetWorkListener.onNetWork(true);
                                connection = true;
                            }

                            if (SendRabbitMqManager.getInstance().isConnect == 2) {
                                SendRabbitMqManager.getInstance().changeInstance();
                            }
                        }
                    }

                    @Override
                    public void onError(Response<String> response) {
                        if (mNetWorkListener != null) {
                            mNetWorkListener.onNetWork(false);
                            connection = false;
                        }
                        MyLog.e("NetWorkManager", response.code() + " " + response.message());
                    }
                });
    }

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
