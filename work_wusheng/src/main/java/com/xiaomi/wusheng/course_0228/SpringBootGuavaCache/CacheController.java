package com.xiaomi.wusheng.course_0228.SpringBootGuavaCache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cache")
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @PostMapping("/put")
    public String put(@RequestParam String key, @RequestParam String value) {
        cacheService.put(key, value);
        return "Cache added: " + key + " -> " + value;
    }

    @GetMapping("/get")
    public Object get(@RequestParam String key) {
        return cacheService.get(key);
    }

    @DeleteMapping("/delete")
    public String delete(@RequestParam String key) {
        cacheService.delete(key);
        return "Cache deleted: " + key;
    }

    @DeleteMapping("/clear")
    public String clear() {
        cacheService.clear();
        return "Cache cleared!";
    }

    @GetMapping("/stats")
    public String stats() {
        return cacheService.getStats();
    }
}
