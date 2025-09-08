package com.xiaomi.wusheng.course_0227.SpringBootJDBC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.List;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // 从Spring容器中获取UserService
        UserService userService = context.getBean(UserService.class);

        // 测试查询所有用户
        System.out.println("查询所有用户：");
        List<User> users = userService.getAllUsers();
        for (User user : users) {
            System.out.println(user);
        }

        // 测试根据ID查询用户
        System.out.println("\n根据ID查询用户：");
        User user = userService.getUserById(1L);
        System.out.println(user);
    }
}