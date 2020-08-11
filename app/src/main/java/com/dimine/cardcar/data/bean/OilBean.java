package com.dimine.cardcar.data.bean;

import com.dimine.cardcar.utils.DateConverter;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/21 16:20
 * desc   :
 * version: 1.0
 */
@Entity
public class OilBean {
    @Id(assignable = true)
    public long id;

    public double oil;

    @Convert(converter = DateConverter.class, dbType = Long.class)
    public Date time;

}
