package com.dimine.cardcar.utils;

import java.util.Date;

import io.objectbox.converter.PropertyConverter;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/4/16 13:57
 * desc   :
 * version: 1.0
 */
public class DateConverter implements PropertyConverter<Date, Long> {

    @Override
    public Date convertToEntityProperty(Long databaseValue) {
        return new Date(databaseValue);
    }

    @Override
    public Long convertToDatabaseValue(Date entityProperty) {
        return entityProperty.getTime();
    }
}
