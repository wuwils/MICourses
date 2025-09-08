package com.xiaomi.wusheng.work_0226.question_2;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TransactionAspect{

    // 使用 @Around 注解统计方法执行耗时，并输出到控制台
    @Around("execution(* com.xiaomi.wusheng.work_0226.question_2.UserService.*(..))")
    public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        String method = joinPoint.getSignature().getName();
        System.out.println("\n开始事务，方法: " + method);

        try{
            Object result = joinPoint.proceed();
            System.out.println("\n提交事务，方法: " + method);
            return result; // 模拟事务提交
        }catch(Exception e){
            System.err.println("\n回滚事务，方法: " + method);
            System.err.println("异常信息: " + e.getMessage());
            throw e; // 模拟事务回滚
        }finally{
            long time = System.currentTimeMillis() - start;
            System.out.println("方法 " + method + " 执行耗时: " + time + "ms");
        }
    }
}