package com.xiaomi.wusheng.work_0226.question_3;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.xiaomi.wusheng.work_0226.question_3")
public class AppConfig{
    @Bean
    public UserService userService() {
        return new UserService();
    }
}