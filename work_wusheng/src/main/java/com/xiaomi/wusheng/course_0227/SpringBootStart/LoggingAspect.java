package com.xiaomi.wusheng.course_0227.SpringBootStart;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Before("execution(* com.xiaomi.wusheng.course_0227.SpringBootStart.TestSpring.print(..))")
    public void beforePrint(JoinPoint joinPoint) {
        System.out.println("Before calling " + joinPoint.getSignature().getName());
    }

    @After("execution(* com.xiaomi.wusheng.course_0227.SpringBootStart.TestSpring.print(..))")
    public void afterPrint(JoinPoint joinPoint) {
        System.out.println("After calling " + joinPoint.getSignature().getName());
    }
}
