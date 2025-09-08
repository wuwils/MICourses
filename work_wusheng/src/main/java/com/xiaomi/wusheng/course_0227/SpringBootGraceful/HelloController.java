package com.xiaomi.wusheng.course_0227.SpringBootGraceful;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HelloController {

    @GetMapping("/hello")
    public String sayHello() throws InterruptedException {
        // 模拟一个长时间运行的请求
        Thread.sleep(10000); // 10秒
        return "Hello, Spring Boot!";
    }
}