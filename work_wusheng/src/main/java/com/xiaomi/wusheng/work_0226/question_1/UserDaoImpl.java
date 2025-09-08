package com.xiaomi.wusheng.work_0226.question_1;

import org.springframework.beans.factory.DisposableBean;

import java.util.HashMap;
import java.util.Map;

public class UserDaoImpl implements IUserDao{
    private Map<Integer, User> users = new HashMap<>();

    // 模拟数据库操作
    @Override
    public User findUserById(int id){
        System.out.println("[UserDao] 查找用户，id: " + id);
        return users.get(id);
    }

    @Override
    public void saveUser(User user){
        System.out.println("[UserDao] 保存用户: " + user);
        users.put(user.getId(), user);
    }

    // 输出日志（UserDao作用域为prototype）
    public void init(){
        System.out.println("[UserDao] Init...");
    }

    public void destroy(){
        System.out.println("[UserDao] Destroy...");
    }
}
