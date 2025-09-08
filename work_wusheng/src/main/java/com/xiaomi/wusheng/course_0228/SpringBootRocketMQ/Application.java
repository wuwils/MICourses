package com.xiaomi.wusheng.course_0228.SpringBootRocketMQ;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(Application.class, args);

        // 获取RocketMQProducer实例
        RocketMQProducer rocketMQProducer = context.getBean(RocketMQProducer.class);

        // 发送测试消息
        rocketMQProducer.sendMessage("test-topic", "Hello, RocketMQ!");
    }
}
