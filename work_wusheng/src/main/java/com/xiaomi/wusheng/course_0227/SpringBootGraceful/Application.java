package com.xiaomi.wusheng.course_0227.SpringBootGraceful;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        // 启动Spring Boot应用程序
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // 模拟发送请求
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.submit(() -> {
            try {
                System.out.println("发送请求...");
                String response = sendRequest();
                System.out.println("请求响应: " + response);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });

        // 模拟停机
        try {
            Thread.sleep(5000); // 等待2秒，确保请求已经开始处理
            System.out.println("触发停机...");
            context.close(); // 关闭应用程序上下文，触发优雅停机
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            executor.shutdown();
        }
    }

    private static String sendRequest() {
        try {
            // 使用RestTemplate发送请求
            RestTemplate restTemplate = new RestTemplate();
            return restTemplate.getForObject("http://localhost:8080/api/hello", String.class);
        } catch (ResourceAccessException e) {
            System.err.println("连接被拒绝，请确保目标服务已启动并正确配置：" + e.getMessage());
            return null;
        }
    }
}
