package com.hao.notes.properties;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest()
public class Examples {

    @Autowired
    Mail mail;

    @Autowired
    MyProperties myProps;

    @Autowired
    MyBean myBean;

    @Autowired
    User user;

    @Test
    public void showMail() {
        System.out.println(mail);
    }

    @Test
    public void showMyProperties() {
        System.out.println(myProps);
    }

    @Test
    public void testImportingBeanFromXml() {
        myBean.sayHello();
    }

    @Test
    public void testPropertyPlaceHolder() {
        System.out.println(user);
    }
}