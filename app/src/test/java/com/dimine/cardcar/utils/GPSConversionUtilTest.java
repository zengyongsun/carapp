package com.dimine.cardcar.utils;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/10/17 9:33
 * desc   :
 * version: 1.0
 */
public class GPSConversionUtilTest {

    @Test
    public void degreesToMapTest() {
        String data = "00833.91522";

        Assert.assertEquals("", GPSConversionUtil.degreesToMapCoordinate(data) + "");
    }

    @Test
    public void mapToDegreesTest() {
        String data = "8.5652536";
        Assert.assertEquals("", GPSConversionUtil.mapCoordinateToDegrees(data) + "");
    }

    @Test
    public void tst() {
        String data = "192.168.4.1";
        String[] spl = data.split("\\.");
        Assert.assertEquals("192", spl[0]);
        Assert.assertEquals("168", spl[1]);
        Assert.assertEquals("4", spl[2]);
        Assert.assertEquals("1", spl[3]);


    }

    @Test
    public void dateTest() throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd hh:mm");
        Date date = format.parse("2019/11/08 11:56");
        Date now = new Date();

        System.out.println(date.getTime());
        System.out.println(format.format(date));
        System.out.println(now.getTime());
        System.out.println(format.format(now));
        System.out.println((now.getTime()-date.getTime())/60000);
    }

}
