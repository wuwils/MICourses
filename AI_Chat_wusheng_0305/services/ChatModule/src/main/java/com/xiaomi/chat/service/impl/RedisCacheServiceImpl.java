package com.xiaomi.chat.service.impl;

import com.xiaomi.chat.service.RedisCacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import java.util.concurrent.TimeUnit;

@Service
public class RedisCacheServiceImpl implements RedisCacheService {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    // 设置缓存过期时间（单位：秒），例如1小时
    private final long CACHE_EXPIRE_SECONDS = 3600;

    @Override
    public String getCachedAnswer(String prompt) {
        // 使用 "chat:" 前缀区分缓存键
        return stringRedisTemplate.opsForValue().get("chat:" + prompt);
    }

    @Override
    public void cacheAnswer(String prompt, String answer) {
        stringRedisTemplate.opsForValue().set("chat:" + prompt, answer, CACHE_EXPIRE_SECONDS, TimeUnit.SECONDS);
    }
}
