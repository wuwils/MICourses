package com.xiaomi.wusheng.work_0226.question_1;

public class UserServiceImpl implements IUserService{
    private final IUserDao userDao;

    // 使用构造方法注入UserDao到UserService
    public UserServiceImpl(IUserDao userDao){
        this.userDao = userDao;
    }

    @Override
    public User getUserById(int id){
        System.out.println("[UserService] 获取用户信息，id: " + id);
        return userDao.findUserById(id);
    }

    @Override
    public void createUser(User user){
        System.out.println("[UserService] 创建用户: " + user);
        userDao.saveUser(user);
    }
}
