package com.xiaomi.wusheng.work_0304.service;

public interface RedisCacheService {
    String getCachedAnswer(String prompt);
    void cacheAnswer(String prompt, String answer);
}
