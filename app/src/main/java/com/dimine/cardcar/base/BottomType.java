package com.dimine.cardcar.base;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/20 11:03
 * desc   : 底部按钮的类型
 * version: 1.0
 */
public enum BottomType {

    /**
     * 侧边栏的内容
     */
    One(1, "调度"),
    Two(2, "地图"),
    Three(3, "故障"),
    Four(4, "统计"),
    Five(5, "设置");

    private int typeId;
    private String typeName;

    BottomType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public static BottomType fromInt(int typeId) {
        switch (typeId) {
            case 1:
                return One;
            case 2:
                return Two;
            case 3:
                return Three;
            case 4:
                return Four;
            case 5:
                return Five;
            default:
                return null;
        }
    }
}
