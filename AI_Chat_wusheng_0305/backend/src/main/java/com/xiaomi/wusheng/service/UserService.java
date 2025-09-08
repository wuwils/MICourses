package com.xiaomi.wusheng.service;

import com.xiaomi.wusheng.model.User;

public interface UserService {
    boolean register(User user);
    User login(String email, String password);
    User getUserByEmail(String email);

}
