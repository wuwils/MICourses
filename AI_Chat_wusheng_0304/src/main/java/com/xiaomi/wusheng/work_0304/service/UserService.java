package com.xiaomi.wusheng.work_0304.service;

import com.xiaomi.wusheng.work_0304.model.User;

public interface UserService {
    boolean register(User user);
    User login(String email, String password);
}
