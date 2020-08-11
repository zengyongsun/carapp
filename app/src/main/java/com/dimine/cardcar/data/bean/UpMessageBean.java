package com.dimine.cardcar.data.bean;

import com.dimine.cardcar.utils.DateConverter;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/10 10:06
 * desc   : 断网是上传信息的缓存实体类
 * version: 1.0
 */
@Entity
public class UpMessageBean {


    public UpMessageBean() {
    }

    public UpMessageBean(String message, Date createTime) {
        this.message = message;
        this.createTime = createTime;
    }

    @Id
    public long id;

    public String message;

    @Convert(converter = DateConverter.class, dbType = Long.class)
    public Date createTime;

}
