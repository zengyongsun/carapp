package com.dimine.cardcar.data.bean;

import com.dimine.cardcar.utils.DateConverter;

import java.util.Date;

import io.objectbox.annotation.Convert;
import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class ErrorBean {

    @Id(assignable = true)
    public long id;

    public ErrorBean() {
    }

    public ErrorBean(String errorMessage, Date createTime) {
        this.errorMessage = errorMessage;
        this.createTime = createTime;
    }

    public String errorMessage;

    @Convert(converter = DateConverter.class, dbType = Long.class)
    public Date createTime;
}
