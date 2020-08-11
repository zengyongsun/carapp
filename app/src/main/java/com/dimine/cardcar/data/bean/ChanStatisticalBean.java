package com.dimine.cardcar.data.bean;

import java.util.List;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2020/4/24 0024 13:13
 * desc   :
 * version: 1.0
 */
public class ChanStatisticalBean {

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

        public DataBean(String loadPoint, int planWeight, int count) {
            LoadPoint = loadPoint;
            PlanWeight = planWeight;
            RealWeight = count;
        }

        /**
         * GetDiggerWeightCount
         * {“LoadPoint”：“爆堆名称”，“PlanWeight”：“计划产量”，“RealWeight”：“实际产量”}
         */
        private String LoadPoint;
        private int PlanWeight;
        private int RealWeight;

        public String getLoadPoint() {
            return LoadPoint;
        }

        public void setLoadPoint(String loadPoint) {
            LoadPoint = loadPoint;
        }

        public int getPlanWeight() {
            return PlanWeight;
        }

        public void setPlanWeight(int planWeight) {
            PlanWeight = planWeight;
        }

        public int getRealWeight() {
            return RealWeight;
        }

        public void setRealWeight(int realWeight) {
            RealWeight = realWeight;
        }
    }
}
