package com.dimine.cardcar.db;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/16 10:18
 * desc   :
 * version: 1.0
 */
public class LinUpBeanTest {

    @Test
    public void linUpBeanTest() {
        String str = "CK100/200001";
        LineUpBean line = new LineUpBean(str);
        Assert.assertEquals("CK100", line.deviceNum);
        Assert.assertEquals("200001", line.deviceId);
    }

     @Test
    public void linUpBeanTest1() {
        String str = "CK100/200001";
        String[] s = str.split("\\|");
        Assert.assertEquals(1,s.length);
        Assert.assertEquals("CK100/200001",s[0]);
    }


}
