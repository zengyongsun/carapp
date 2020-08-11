package com.dimine.cardcar.data.bean;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/7/10 15:44
 * desc   :
 * version: 1.0
 */
public class TelnetWork {

    /**
     * platform : mtk
     * hardware : mt7628
     * custom : d218
     * version : 4.3.1
     * mac : 88:12:4E:D0:E8:D0
     * magic : CF525426DF681890
     * model : V518
     * language : cn
     * name : V518-D0E8D0
     * mode : misp
     * livetime : 01:06:48:0
     */

    private String platform;
    private String hardware;
    private String custom;
    private String version;
    private String mac;
    private String magic;
    private String model;
    private String language;
    private String name;
    private String mode;
    private String livetime;

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getHardware() {
        return hardware;
    }

    public void setHardware(String hardware) {
        this.hardware = hardware;
    }

    public String getCustom() {
        return custom;
    }

    public void setCustom(String custom) {
        this.custom = custom;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getMac() {
        return mac;
    }

    public void setMac(String mac) {
        this.mac = mac;
    }

    public String getMagic() {
        return magic;
    }

    public void setMagic(String magic) {
        this.magic = magic;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getName() {
        return name;
    }


    public void setName(String name) {
        this.name = name;
    }

    private final String MODE_4G = "misp";
    private final String MODE_GATEWAY = "gateway";
    private final String MODE_WISP = "wisp";
    private final String MODE_MIX = "mix";

    /**
     * 值为 misp 为 4G 工作模式, 而为 gateway 表示为网关工作模式, wisp 为无线互联网工作模式, mix 为混合工作模式
     */
    public String getMode() {
        if (MODE_4G.equals(mode)) {
            return "4G工作模式";
        }
        if (MODE_GATEWAY.equals(mode)) {
            return "网关工作模式";
        }
        if (MODE_WISP.equals(mode)) {
            return "无线互联网工作模式";
        }
        if (MODE_MIX.equals(mode)) {
            return "混合工作模式";
        }
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public String getLivetime() {
        return livetime;
    }

    public void setLivetime(String livetime) {
        this.livetime = livetime;
    }

    @Override
    public String toString() {
        return "TelnetWork{" +
                "platform='" + platform + '\'' +
                ", hardware='" + hardware + '\'' +
                ", custom='" + custom + '\'' +
                ", version='" + version + '\'' +
                ", mac='" + mac + '\'' +
                ", magic='" + magic + '\'' +
                ", model='" + model + '\'' +
                ", language='" + language + '\'' +
                ", name='" + name + '\'' +
                ", mode='" + mode + '\'' +
                ", livetime='" + livetime + '\'' +
                '}';
    }
}
