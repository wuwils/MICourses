package com.xiaomi.wusheng.course_0228.SpringBootRedis;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // 获取RedisService实例
        RedisService redisService = context.getBean(RedisService.class);

        // 测试设置缓存
        User user = new User();
        user.setId(1L);
        user.setName("Alice");
        user.setEmail("alice@example.com");

        redisService.set("user:1", user, 60);
        System.out.println("User cached successfully!");

        // 测试获取缓存
        User cachedUser = (User) redisService.get("user:1");
        System.out.println("Cached User: " + cachedUser);

        // 测试删除缓存
        redisService.delete("user:1");
        System.out.println("User cache deleted!");
    }
}
