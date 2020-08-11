package com.dimine.cardcar.data.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/31 16:22
 * desc   :
 * version: 1.0
 */
public class ConfigBean {

    public ConfigBean() {
    }

    /**
     *
     *
     * code : 1
     * msg : 查询成功
     * data : {"speed":"30","GPSInterval":"5"}
     */



    private String code;
    private String msg;
    private DataBean data;

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

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * speed : 30
         * GPSInterval : 5
         */

        private String speed;
        private String GPSInterval;
        private boolean log;

        public boolean getLog() {
            return log;
        }

        public void setLog(boolean log) {
            this.log = log;
        }

        public String getSpeed() {
            return speed;
        }

        public void setSpeed(String speed) {
            this.speed = speed;
        }

        public String getGPSInterval() {
            return GPSInterval;
        }

        public void setGPSInterval(String GPSInterval) {
            this.GPSInterval = GPSInterval;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "speed='" + speed + '\'' +
                    ", GPSInterval='" + GPSInterval + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ConfigBean{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
