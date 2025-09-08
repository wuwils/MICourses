package com.xiaomi.wusheng.work_0226.question_3;

import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.io.File;

public class Main{
    public static void main(String[] args) throws Exception{
        startTomcat();
        testUserService();
    }

    private static void startTomcat() throws Exception{
        Tomcat tomcat = new Tomcat();
        tomcat.setPort(8080);

        AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();
        context.register(WebConfig.class, AppConfig.class);

        DispatcherServlet dispatcherServlet = new DispatcherServlet(context);
        Context tomcatContext = tomcat.addContext("", new File(".").getAbsolutePath());
        Tomcat.addServlet(tomcatContext, "dispatcher", dispatcherServlet).addMapping("/");

        tomcat.start();
        System.out.println("Tomcat 启动成功，访问地址: http://localhost:8080");
        tomcat.getServer().await();
    }

    private static void testUserService(){
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        UserService userService = context.getBean(UserService.class);

        // 添加用户
        User user = new User();
        user.setName("Charlie");
        user.setEmail("charlie@example.com");
        userService.createUser(user);
        System.out.println("用户添加成功: " + user);

        // 查询用户
        User fetchedUser = userService.getUser(user.getId());
        System.out.println("查询用户: " + fetchedUser);
    }
}