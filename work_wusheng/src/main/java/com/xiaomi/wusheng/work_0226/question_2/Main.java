package com.xiaomi.wusheng.work_0226.question_2;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main{
    public static void main(String[] args){
        ApplicationContext context = new ClassPathXmlApplicationContext("work_0226/question_2/applicationContext.xml");
        UserService service = context.getBean(UserService.class);

        String user = "张三";
        String details = "张三的详细信息";
        String preferences = "张三的偏好设置";

        // 测试save方法
        testMethod(service::save, user);

        // 测试saveDetails方法
        testMethod(service::saveDetails, details);

        // 测试savePreferences方法
        testMethod(service::savePreferences, preferences);

        // 测试createUser方法，模拟方法中抛出异常时触发事务回滚，并记录日志
        testMethod(service::createUser, user);
    }

    private static void testMethod(java.util.function.Consumer<String> method, String arg){
        try{
            method.accept(arg);
        }catch (Exception e){
            System.err.println("发生错误: " + e.getMessage());
        }
    }
}


