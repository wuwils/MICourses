package com.xiaomi.wusheng.course_0226.SpringIoC;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
//        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("course_0226/bean.xml");
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("course_0226/applicationContext.xml");
        IAccountService accountService = (IAccountService)applicationContext.getBean("accountService");
        accountService.saveAccount(100);
        System.out.println(accountService);
        IAccountDao accountDao = (IAccountDao)applicationContext.getBean("accountDao");
        System.out.println(accountDao);
    }
}
