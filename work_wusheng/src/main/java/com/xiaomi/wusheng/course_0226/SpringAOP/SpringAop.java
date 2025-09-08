package com.xiaomi.wusheng.course_0226.SpringAOP;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class SpringAop {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("course_0226/aop.xml");
        IAccountDao accountDao = applicationContext.getBean("accountDao", IAccountDao.class);
        IAccountDao casAccountDao = applicationContext.getBean("casAccountDao", IAccountDao.class);
        accountDao.saveAccount(1);
        casAccountDao.saveAccount(2);
    }
}
