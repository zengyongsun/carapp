package com.dimine.cardcar.data.bean;

import java.util.List;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/6 17:20
 * desc   :
 * version: 1.0
 */
public class TruckStatisticsBean {


    /**
     * code : 0
     * msg : 查询成功
     * data : [{"Target":"1#卸载点","Count":1,"Type":"卸载点"},{"Target":"2#卸载点","Count":2,"Type":"卸载点"},{"Target":"采矿1号","Count":2,"Type":"装置点"},{"Target":"采矿2号","Count":1,"Type":"装置点"}]
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

        public DataBean() {
        }

        public DataBean(String target, int count, String type) {
            Target = target;
            Count = count;
            Type = type;
        }

        /**
         * Target : 1#卸载点
         * Count : 1
         * Type : 卸载点
         */

        private String Target;
        private int Count;
        private String Type;

        public String getTarget() {
            return Target;
        }

        public void setTarget(String Target) {
            this.Target = Target;
        }

        public int getCount() {
            return Count;
        }

        public void setCount(int Count) {
            this.Count = Count;
        }

        public String getType() {
            return Type;
        }

        public void setType(String Type) {
            this.Type = Type;
        }
    }
}
