package com.xiaomi.wusheng.course_0228.SpringBootJedis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/redis")
public class JedisController {

    @Autowired
    private JedisService jedisService;

    @PostMapping("/set")
    public String set(@RequestParam String key, @RequestParam String value) {
        jedisService.set(key, value);
        return "Key set: " + key + " -> " + value;
    }

    @GetMapping("/get")
    public String get(@RequestParam String key) {
        return jedisService.get(key);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        jedisService.delete(key);
        return "Key deleted: " + key;
    }

    @GetMapping("/exists")
    public boolean exists(@RequestParam String key) {
        return jedisService.exists(key);
    }

    @PostMapping("/setWithExpire")
    public String setWithExpire(@RequestParam String key, @RequestParam String value, @RequestParam int expireSeconds) {
        jedisService.setWithExpire(key, value, expireSeconds);
        return "Key set with expire: " + key + " -> " + value + " (expire in " + expireSeconds + " seconds)";
    }
}
