package com.xiaomi.wusheng.service;

public interface RedisCacheService {
    String getCachedAnswer(String prompt);
    void cacheAnswer(String prompt, String answer);
}
