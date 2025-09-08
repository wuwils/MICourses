package com.xiaomi.wusheng.course_0226.Template;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("course_0226/bean.xml");
        IAccountService accountService = (IAccountService)applicationContext.getBean("accountService");
        System.out.println(accountService);
        IAccountDao accountDao = (IAccountDao)applicationContext.getBean("accountDao");
        System.out.println(accountDao);
    }
}
