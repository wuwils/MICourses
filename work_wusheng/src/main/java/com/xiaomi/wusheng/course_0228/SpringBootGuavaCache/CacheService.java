package com.xiaomi.wusheng.course_0228.SpringBootGuavaCache;

import com.google.common.cache.Cache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CacheService {

    @Autowired
    private Cache<String, Object> guavaCache;

    // 添加缓存
    public void put(String key, Object value) {
        guavaCache.put(key, value);
    }

    // 获取缓存
    public Object get(String key) {
        return guavaCache.getIfPresent(key);
    }

    // 删除缓存
    public void delete(String key) {
        guavaCache.invalidate(key);
    }

    // 清空缓存
    public void clear() {
        guavaCache.invalidateAll();
    }

    // 获取缓存统计信息
    public String getStats() {
        return guavaCache.stats().toString();
    }
}