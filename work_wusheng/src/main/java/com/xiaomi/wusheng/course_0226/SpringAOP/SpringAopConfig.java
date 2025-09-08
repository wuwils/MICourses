package com.xiaomi.wusheng.course_0226.SpringAOP;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.xiaomi.wusheng.course_0226.SpringAOP")
@EnableAspectJAutoProxy
public class SpringAopConfig {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(SpringAopConfig.class);
        IAccountDao accountDao = applicationContext.getBean("accountDao", IAccountDao.class);
        IAccountDao casAccountDao = applicationContext.getBean("casAccountDao", IAccountDao.class);
        accountDao.saveAccount(1);
        casAccountDao.saveAccount(2);
    }
}
