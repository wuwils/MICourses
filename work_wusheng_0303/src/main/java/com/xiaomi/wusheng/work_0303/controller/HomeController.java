package com.xiaomi.wusheng.work_0303.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

// 页面跳转控制
@Controller
public class HomeController {

    @GetMapping("/")
    public String index() {
        return "index";  // 返回 index.html
    }

    @GetMapping("/login")
    public String login() {
        return "login";  // 返回 login.html
    }

    @GetMapping("/register")
    public String register() {
        return "register";  // 返回 register.html
    }

    @GetMapping("/chat")
    public String chat() {
        return "chat";  // 返回 chat.html
    }
}
