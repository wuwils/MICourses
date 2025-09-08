package com.xiaomi.wusheng.work_0226.question_3;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/users")
public class UserController{

    @Autowired
    private UserService userService;

    // 根据ID查询用户
    @GetMapping("/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUser(id);
    }

    // 创建用户
    @PostMapping
    public User createUser(@RequestBody User user){
        return userService.createUser(user);
    }

    // 上传用户头像
    @PostMapping("/upload")
    public String uploadAvatar(@RequestParam("file") MultipartFile file){
        try {
            return userService.uploadAvatar(file);
        } catch (Exception e) {
            throw new RuntimeException("文件上传失败: " + e.getMessage());
        }
    }
}

