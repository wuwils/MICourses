package com.xiaomi.wusheng.work_0226.question_1;

public interface IUserDao {
    User findUserById(int id);
    void saveUser(User user);
}
