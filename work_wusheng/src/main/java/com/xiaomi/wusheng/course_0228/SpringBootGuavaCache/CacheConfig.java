package com.xiaomi.wusheng.course_0228.SpringBootGuavaCache;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
public class CacheConfig {

    @Bean
    public Cache<String, Object> guavaCache() {
        return CacheBuilder.newBuilder()
                .maximumSize(100) // 缓存最大容量
                .expireAfterWrite(20, TimeUnit.SECONDS) // 缓存过期时间
                .concurrencyLevel(8) // 并发级别
                .recordStats() // 开启缓存统计
                .build();
    }
}