package com.dimine.cardcar.data.bean;

import com.dimine.cardcar.utils.DateConverter;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/11/15 9:50
 * desc   :
 * version: 1.0
 */
@Entity
public class TimeRecordBean {

    @Id(assignable = true)
    public long id;

    /**
     * 登录的用户账号，用来判读是否清空记录数据 ，注意为空的情况
     */
    public String userId = "";

    /**
     * 空运时间的秒数
     */
    public int emptyTime;

    public int heavyTime;

    public int loadingTime;

    public int unloadingTime;

    public int idleTime;

    public int spareTime;

    /**
     * 写入数据的时间，用来判断，是否是同一个班次的
     */
    @Convert(converter = DateConverter.class, dbType = Long.class)
    public Date writeTime;

}
