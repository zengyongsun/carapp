package com.dimine.cardcar.utils;

import android.os.Process;
import android.util.Log;

import com.dimine.cardcar.data.bean.ErrorBean;
import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.manager.WriteLogManager;

import java.util.Date;

import io.objectbox.Box;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/12/4 13:21
 * desc   :
 * version: 1.0
 */
public class CrashHandler implements Thread.UncaughtExceptionHandler {

    private static final String TAG = CrashHandler.class.getSimpleName();

    private static CrashHandler mInstance = new CrashHandler();
    private Thread.UncaughtExceptionHandler mDefaultCrashHandler;

    public static CrashHandler getInstance() {
        return mInstance;
    }

    public void init() {
        mDefaultCrashHandler = Thread.getDefaultUncaughtExceptionHandler();
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    /**
     * 关键函数,当程序中有未捕获的异常,系统将自动调用uncaughtException,
     *
     * @param t    未捕获异常的线程
     * @param e  throwable为未捕获的异常
     */
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        //上传服务器
        saveException(e);
        Log.d("CrashHandler", t.getName() + "调用了" + e.toString());
        //打印堆栈
        e.printStackTrace();
        //如果系统提供了默认的处理器,交给系统处理,否则kill掉自己
        if (mDefaultCrashHandler != null) {
            mDefaultCrashHandler.uncaughtException(t, e);
        } else {
            Process.killProcess(Process.myPid());
        }
    }

    private void saveException(Throwable e) {
        Box<ErrorBean> errorBeanBox = ObjectBox.get().boxFor(ErrorBean.class);
        errorBeanBox.put(new ErrorBean(LocalArguments.getInstance().appVersion() +" "+
                e.toString(), new Date()));
    }
}
