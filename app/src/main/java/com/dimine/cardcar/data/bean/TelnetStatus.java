package com.dimine.cardcar.data.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/7/10 17:51
 * desc   :
 * version: 1.0
 */
public class TelnetStatus {


    /**
     * status : up
     * ip : 192.168.8.1
     * dstip : 192.168.8.1
     * mask : 255.255.255.0
     * gw : 192.168.2.1
     * dns : 192.168.2.1
     * dns2 :
     * ontime : 17
     * link : {"step":"dhcp_dial","sim":"nocard","signal":"-59","nettype":"LTE","operator":{"name":"中国联通"}}
     */

    private String status;
    private String ip;
    private String dstip;
    private String mask;
    private String gw;
    private String dns;
    private String dns2;
    private String ontime;
    private LinkBean link;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getDstip() {
        return dstip;
    }

    public void setDstip(String dstip) {
        this.dstip = dstip;
    }

    public String getMask() {
        return mask;
    }

    public void setMask(String mask) {
        this.mask = mask;
    }

    public String getGw() {
        return gw;
    }

    public void setGw(String gw) {
        this.gw = gw;
    }

    public String getDns() {
        return dns;
    }

    public void setDns(String dns) {
        this.dns = dns;
    }

    public String getDns2() {
        return dns2;
    }

    public void setDns2(String dns2) {
        this.dns2 = dns2;
    }

    public String getOntime() {
        return ontime;
    }

    public void setOntime(String ontime) {
        this.ontime = ontime;
    }

    public LinkBean getLink() {
        return link;
    }

    public void setLink(LinkBean link) {
        this.link = link;
    }

    public static class LinkBean {
        /**
         * mnc : 46000
         * sim : nocard
         * signal : -59
         * nettype : LTE
         * operator : {"name":"中国联通"}
         */

        public String mnc;

        /**
         * 检 SIM 卡时出错将会显示， 值为 nocard 表示无 SIM 卡, 值为 pin 表示 PIN 码错误
         */
        private String sim;
        /**
         * 信号值, 信号为空或是为 0 为无信号，大于 0 表示信号百分比, 小于 0 表示 dBm 值
         */
        private String signal;

        /**
         * 当前网络类型,跟据 4G 模组的不同而不同，通常会有 LTE, 4G, 3G, 2G, WCDMA, EVDO, HSPA, HSDPA, GSM, TDSCDMA 等
         */
        private String nettype;
        private OperatorBean operator;


        public String getSim() {
            return sim;
        }

        public void setSim(String sim) {
            this.sim = sim;
        }

        public String getSignal() {
            return signal;
        }

        public void setSignal(String signal) {
            this.signal = signal;
        }

        public String getNettype() {
            return nettype;
        }

        public void setNettype(String nettype) {
            this.nettype = nettype;
        }

        public OperatorBean getOperator() {
            return operator;
        }

        public void setOperator(OperatorBean operator) {
            this.operator = operator;
        }


        @Override
        public String toString() {
            return "LinkBean{" +
                    "mnc='" + mnc + '\'' +
                    ", sim='" + sim + '\'' +
                    ", signal='" + signal + '\'' +
                    ", nettype='" + nettype + '\'' +
                    ", operator=" + operator +
                    '}';
        }
    }

    public static class OperatorBean {
        /**
         * name : 中国联通
         */

        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "OperatorBean{" +
                    "name='" + name + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "TelnetStatus{" +
                "status='" + status + '\'' +
                ", ip='" + ip + '\'' +
                ", dstip='" + dstip + '\'' +
                ", mask='" + mask + '\'' +
                ", gw='" + gw + '\'' +
                ", dns='" + dns + '\'' +
                ", dns2='" + dns2 + '\'' +
                ", ontime='" + ontime + '\'' +
                ", link=" + link.toString() +
                '}';
    }
}
