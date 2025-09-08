package com.xiaomi.wusheng.course_0227.SpringBootMyBatis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import java.util.List;


@SpringBootApplication
public class Application {
//        public static void main(String[] args) {
//        SpringApplication.run(Application.class, args);
//    }
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        UserService userService = context.getBean(UserService.class);

        // 查询所有用户
        System.out.println("查询所有用户：");
        List<User> users = userService.findAll();
        users.forEach(System.out::println);

        // 根据ID查询用户
        System.out.println("\n根据ID查询用户：");
        User user = userService.findById(1L);
        System.out.println(user);
    }
}