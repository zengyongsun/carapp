package com.dimine.cardcar.base;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/16 11:03
 * desc   : 卡车的类型
 * version: 1.0
 */
public enum CarType {

    /**
     * 车辆的类型
     */
    Forklift(1, "铲车"),
    Truck(2, "卡车"),
    AuxiliaryCar(3, "辅助车");

    private int typeId;
    private String typeName;

    CarType(int typeId, String typeName) {
        this.typeId = typeId;
        this.typeName = typeName;
    }

    public int getTypeId() {
        return this.typeId;
    }

    public String getTypeName() {
        return this.typeName;
    }

    public static CarType fromInt(int typeId) {
        switch (typeId) {
            case 1:
                return Forklift;
            case 2:
                return Truck;
            case 3:
                return AuxiliaryCar;
            default:
                return null;
        }
    }
}
