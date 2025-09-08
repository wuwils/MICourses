package com.xiaomi.wusheng.work_0227.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {

    @GetMapping("/login")
    public String login() {
        return "login"; // 返回 login.html 模板
    }

    @GetMapping("/register")
    public String register() {
        return "register"; // 返回 register.html 模板
    }

    @GetMapping("/")
    public String home() {
        return "index"; // 返回 index.html 模板
    }
}
