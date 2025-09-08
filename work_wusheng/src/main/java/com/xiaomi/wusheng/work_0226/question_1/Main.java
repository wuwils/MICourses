package com.xiaomi.wusheng.work_0226.question_1;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main{
    public static void main(String[] args) {
        // applicationContext.xml保存在src/main/resources/work_0226/question_1/
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("work_0226/question_1/applicationContext.xml");

        // 触发init方法
        IUserService userService = (IUserService) applicationContext.getBean("userService");

        try{
            // 测试UserService的createUser和getUserById方法，依赖UserDao
            System.out.println("\n----- 模拟数据库操作 -----");
            userService.createUser(new User(1, "张三"));
            userService.createUser(new User(2, "李四"));
            User user1 = userService.getUserById(1);
            System.out.println("用户信息: " + user1);
            User user2 = userService.getUserById(2);
            System.out.println("用户信息: " + user2);

            System.out.println("\n----- 验证容器中多次获取的UserDao是否为不同实例 -----");
            IUserDao userDao1 = (IUserDao) applicationContext.getBean("userDao");
            IUserDao userDao2 = (IUserDao) applicationContext.getBean("userDao");
            verifyInstances(userDao1, userDao2, "userDao");

        }catch(Exception e){
            System.out.println( e.getMessage());
        }finally{
            // 触发destroy方法
            ((ClassPathXmlApplicationContext)applicationContext).close();
        }
    }

    private static void verifyInstances(Object obj1, Object obj2, String beanName){
        if(obj1 == obj2){
            System.out.println(beanName + " 是同一个实例。");
        }else{
            System.out.println(beanName + " 不是同一个实例。");
            System.out.println(beanName + " 实例1的内存地址: " + System.identityHashCode(obj1));
            System.out.println(beanName + " 实例2的内存地址: " + System.identityHashCode(obj2));
        }
    }
}
