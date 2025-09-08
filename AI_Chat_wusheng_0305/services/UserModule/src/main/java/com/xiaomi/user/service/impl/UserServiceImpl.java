package com.xiaomi.user.service.impl;

import com.xiaomi.user.dao.UserMapper;
import com.xiaomi.user.model.User;
import com.xiaomi.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public boolean register(User user) {
        // 检查昵称是否已存在
        Optional<User> existingName = Optional.ofNullable(userMapper.selectByUserName(user.getUserName()));
        if(existingName.isPresent()){
            return false; // 昵称已存在
        }
        // 检查邮箱是否已注册
        Optional<User> existingEmail = Optional.ofNullable(userMapper.selectByEmail(user.getEmail()));
        if(existingEmail.isPresent()){
            return false; // 邮箱已存在
        }
        // 使用 BCrypt 加密密码
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        // 设置默认状态 1（正常）
        user.setStatus((byte) 1);
        return userMapper.insertUser(user) > 0;
    }

    @Override
    public User login(String email, String password) {
        // 根据邮箱查询用户
        User user = userMapper.selectByLoginEmail(email);
        if(user != null && passwordEncoder.matches(password, user.getPassword())){
            // 登录成功后，更新登录时间
            userMapper.updateLoginTime(user.getUserId());
            return user;
        }
        return null;
    }

    @Override
    public User getUserByEmail(String email) {
        return userMapper.selectByEmail(email);
    }

}
