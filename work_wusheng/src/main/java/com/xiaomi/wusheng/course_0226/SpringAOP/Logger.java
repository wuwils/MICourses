package com.xiaomi.wusheng.course_0226.SpringAOP;

import java.util.Arrays;
import java.util.List;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class Logger {
    @Around("execution(* com.xiaomi.wusheng.course_0226.SpringAOP.*.*(..))")
    public Object aroundMethod(ProceedingJoinPoint joinPoint) {
        Object rtValue = null;
        try {
            Object[] args = joinPoint.getArgs();
            System.out.println("开始环绕通知");
            joinPoint.proceed(args);
            System.out.println("结束环绕通知");
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        } finally {
            afterFinally();
        }
        return rtValue;
    }

    @Before("execution(* com.xiaomi.wusheng.course_0226.SpringAOP.*.*(..))")
    public void beforeMethod(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        List<Object> list = Arrays.asList(args);
        Class<?> target = joinPoint.getTarget().getClass();
        System.out.println("beforeMethod: " + target.getName() + ", param：" + list);
    }

    public void afterReturning(Object result) {
        System.out.println("afterReturning: " + result);
    }

    public void afterThrowing(Exception e) {
        System.out.println("afterThrowing: " + e.getMessage());
    }

    public void afterFinally() {
        System.out.println("afterFinally");
    }
}
