package com.xiaomi.wusheng.work_0226.question_3;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService{
    private final Map<Long, User> users = new HashMap<>();
    private Long nextId = 1L;

    // 初始化测试数据
    public UserService(){
        User user1 = new User();
        user1.setId(nextId++);
        user1.setName("Alice");
        user1.setEmail("alice@example.com");
        users.put(user1.getId(), user1);

        User user2 = new User();
        user2.setId(nextId++);
        user2.setName("Bob");
        user2.setEmail("bob@example.com");
        users.put(user2.getId(), user2);
    }

    // 根据ID查询用户
    public User getUser(Long id){
        return users.get(id);
    }

    // 创建用户
    public User createUser(User user){
        user.setId(nextId++);
        users.put(user.getId(), user);
        return user;
    }

    // 上传用户头像
    public String uploadAvatar(MultipartFile file) throws Exception{
        if (file.isEmpty()) {
            throw new IllegalArgumentException("文件不能为空");
        }
        if (!file.getContentType().startsWith("image/")){
            throw new IllegalArgumentException("只支持图片文件");
        }
        // 模拟保存文件并返回URL
        return "http://example.com/avatars/" + file.getOriginalFilename();
    }
}

