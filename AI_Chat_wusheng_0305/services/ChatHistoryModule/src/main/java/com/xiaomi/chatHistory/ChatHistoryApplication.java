package com.xiaomi.chatHistory;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
@MapperScan("com.xiaomi.chatHistory.dao")
public class ChatHistoryApplication {
    public static void main(String[] args) {
        SpringApplication.run(ChatHistoryApplication.class, args);
    }
}
