package com.xiaomi.wusheng.work_0226.question_2;

import org.springframework.stereotype.Service;

@Service
public class UserService{
    public void save(String user){
        System.out.println("保存用户: " + user);
    }

    public void saveDetails(String details){
        System.out.println("保存用户详细信息: " + details);
    }

    public void savePreferences(String preferences){
        System.out.println("保存用户偏好设置: " + preferences);
    }

    public void createUser(String user){
        System.out.println("创建用户: " + user);
        // 模拟抛出异常
        throw new RuntimeException("用户创建失败");
    }
}