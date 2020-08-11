package com.dimine.cardcar.db;

import com.dimine.cardcar.data.bean.UserBean;
import com.dimine.cardcar.domain.login.LoginModel;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * @author : Zeyo
 * e-mail : zengyongsun@163.com
 * date   : 2019/8/23 15:44
 * desc   :
 * version: 1.0
 */
public class CarBeanTest extends AbstractObjectBoxTest {

    private LoginModel loginModel;

    @Before
    public void initObject() {
        loginModel = new LoginModel(store.boxFor(UserBean.class));
    }

    @Test
    public void exampleTest() {
        // get a box and use ObjectBox as usual
        Assert.assertEquals(4, loginModel.getAll().size());
    }



}
