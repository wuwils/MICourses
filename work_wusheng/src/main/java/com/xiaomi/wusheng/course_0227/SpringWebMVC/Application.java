package com.xiaomi.wusheng.course_0227.SpringWebMVC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.ApplicationContext;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // 使用RestTemplate测试Controller
        RestTemplate restTemplate = new RestTemplateBuilder().build();
        String baseUrl = "http://localhost:8080/api/users";

        // 测试创建用户
        User newUser = new User();
        newUser.setId(1L);
        newUser.setName("Alice");
        newUser.setEmail("alice@example.com");

        String createResponse = restTemplate.postForObject(baseUrl, newUser, String.class);
        System.out.println("Create User Response: " + createResponse);

        // 测试获取所有用户
        User[] users = restTemplate.getForObject(baseUrl, User[].class);
        System.out.println("All Users:");
        for (User user : users) {
            System.out.println(user);
        }

        // 测试根据ID获取用户
        User user = restTemplate.getForObject(baseUrl + "/1", User.class);
        System.out.println("User with ID 1: " + user);

        // 测试更新用户
        User updatedUser = new User();
        updatedUser.setName("Alice Smith");
        updatedUser.setEmail("alice.smith@example.com");
        restTemplate.put(baseUrl + "/1", updatedUser);

        // 验证更新后的用户
        user = restTemplate.getForObject(baseUrl + "/1", User.class);
        System.out.println("Updated User with ID 1: " + user);

        // 测试删除用户
        restTemplate.delete(baseUrl + "/1");

        // 验证删除后的用户列表
        users = restTemplate.getForObject(baseUrl, User[].class);
        System.out.println("All Users after deletion:");
        for (User u : users) {
            System.out.println(u);
        }
    }
}