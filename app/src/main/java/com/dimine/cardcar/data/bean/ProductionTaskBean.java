package com.dimine.cardcar.data.bean;

import java.util.List;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2020/8/5 0005 11:21
 * desc   :
 * version: 1.0
 */
public class ProductionTaskBean {


    /**
     * code : 0
     * msg : 获取生产任务成功
     * data : ["100008","100009"]
     */

    private String code;
    private String msg;
    private List<String> data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
