package com.xiaomi.user.service;

import com.xiaomi.user.model.User;

public interface UserService {
    boolean register(User user);
    User login(String email, String password);
    User getUserByEmail(String email);

}
