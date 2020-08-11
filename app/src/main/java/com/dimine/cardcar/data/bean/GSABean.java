package com.dimine.cardcar.data.bean;

public class GSABean {



    public GSABean(String opMode, String navMode, String sv) {
        this.opMode = opMode;
        this.navMode = navMode;
        this.sv = sv;
    }

    public String opMode;

    public String navMode;

    /**
     * 卫星数量
     */
    public String sv;

    @Override
    public String toString() {
        return "GSABean{" +
                "opMode='" + opMode + '\'' +
                ", navMode='" + navMode + '\'' +
                ", sv='" + sv + '\'' +
                '}';
    }
}
