package com.dimine.cardcar.manager;

import android.util.Log;

import com.dimine.cardcar.data.local.LocalArguments;
import com.dimine.cardcar.data.remote.Urls;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/12/4 11:19
 * desc   : 上传到服务器的日志工具类
 * version: 1.0
 */
public class WriteLogManager {

    private static final WriteLogManager ourInstance = new WriteLogManager();

    public static WriteLogManager getInstance() {
        return ourInstance;
    }

    private SimpleDateFormat fmt;

    private WriteLogManager() {
        fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    }

    public void writeLog(String message) {
        String value = fmt.format(new Date()) + " #" + LocalArguments.getInstance().deviceNumber() + " ==>"
                + message;

        OkGo.<String>post(Urls.host_ip + Urls.write_log_url)
                .params("log", value, true)
                .tag(this)
                .execute(new StringCallback() {

                    @Override
                    public void onSuccess(Response<String> response) {
                        Log.d("WriteLogManager", "onSuccess"+message);
                    }
                });

    }


}
