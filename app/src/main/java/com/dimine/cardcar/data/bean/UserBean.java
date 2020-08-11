package com.dimine.cardcar.data.bean;

import com.dimine.cardcar.utils.DateConverter;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/5/31 10:27
 * desc   :
 * version: 1.0
 */
@Entity
public class UserBean {

    /**
     * UserId : 001
     * UserName : 一号用户
     * EquipmentNo : CK110
     */
    public String UserId;

    public String UserName;

    public String EquipmentNo;


    public int type;

    @Id(assignable = true)
    public long id;

    @Convert(converter = DateConverter.class, dbType = Long.class)
    public Date lastLoginTime;


}
