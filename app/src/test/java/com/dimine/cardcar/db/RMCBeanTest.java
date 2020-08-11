package com.dimine.cardcar.db;

import com.dimine.cardcar.data.bean.RMCBean;
import com.zy.generallib.TimeUtils;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/9/9 17:56
 * desc   :
 * version: 1.0
 */
public class RMCBeanTest {

    RMCBean rmcBean;

    @Before
    public void init() {
        // RMCBean{time='095739.20', status='V', Latitude='', Longitude='', spd='', cog='', date='090919'}
        rmcBean = new RMCBean("095739.20", "V", "", "",
                "", "", "090919");
    }

    @Test
    public void timeTest() {
        Assert.assertEquals("17:57:39", rmcBean.getTime());
    }

    @Test
    public void dateTest() {
        Assert.assertEquals("2019-09-09", rmcBean.getDate());
    }

    @Test
    public void time() throws ParseException {
        String a = TimeUtils.date2TimeStamp("2019-09-09 17:26:55", "yyyy-MM-dd HH:mm:ss");
        Assert.assertEquals("1568021215", a);
    }

    @Test
    public void name() {
//          {time='012014.40', status='V', Latitude='', Longitude='', spd='', cog='', date='100919'}
        String time = "012014.40";
         String hh = Integer.parseInt(time.substring(0, 2)) + 8 + "";
        Assert.assertEquals("9", hh);
    }
}
