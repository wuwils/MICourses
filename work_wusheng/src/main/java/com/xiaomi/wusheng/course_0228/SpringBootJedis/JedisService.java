package com.xiaomi.wusheng.course_0228.SpringBootJedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Service
public class JedisService {

    @Autowired
    private JedisPool jedisPool;

    // 设置键值对
    public void set(String key, String value) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.set(key, value);
        }
    }

    // 获取键值
    public String get(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.get(key);
        }
    }

    // 删除键
    public void delete(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.del(key);
        }
    }

    // 判断键是否存在
    public boolean exists(String key) {
        try (Jedis jedis = jedisPool.getResource()) {
            return jedis.exists(key);
        }
    }

    // 设置键值对并设置过期时间（秒）
    public void setWithExpire(String key, String value, int expireSeconds) {
        try (Jedis jedis = jedisPool.getResource()) {
            jedis.setex(key, expireSeconds, value);
        }
    }
}