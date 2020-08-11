package com.dimine.cardcar.data.bean;

import java.util.List;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/6 16:46
 * desc   :
 * version: 1.0
 */
public class DriverBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"Driver_Name":"十号用户","Account":"010"},{"Driver_Name":"九号用户","Account":"009"},{"Driver_Name":"三号用户","Account":"003"}]
     */

    private String code;
    private String msg;
    private List<DataBean> data;

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

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * Driver_Name : 十号用户
         * Account : 010
         */

        private String Driver_Name;
        private String Account;

        public String getDriver_Name() {
            return Driver_Name;
        }

        public void setDriver_Name(String Driver_Name) {
            this.Driver_Name = Driver_Name;
        }

        public String getAccount() {
            return Account;
        }

        public void setAccount(String Account) {
            this.Account = Account;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "Driver_Name='" + Driver_Name + '\'' +
                    ", Account='" + Account + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DriverBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
