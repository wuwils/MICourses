package com.xiaomi.wusheng.work_0224.question_1;

import java.util.concurrent.*;

public class Main{
    private static Seckill seckill = new Seckill(100);
    private static RateLimiter rateLimiter = new RateLimiter(200); // 每秒允许200个请求

    public static void main(String[] args){
        ExecutorService executor = Executors.newFixedThreadPool(1000); // 接收1000个并发请求
        for (int i = 1; i <= 1000; i++) {
            String userId = "User" + i;
            executor.execute(() -> seckill(userId));
        }
        executor.shutdown();
        try{
            executor.awaitTermination(1, TimeUnit.MINUTES);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
        seckill.getLogs().forEach(System.out::println);
        System.out.println("订单数量: " + seckill.getOrders().size());
        System.out.println("剩余库存: " + seckill.getStock());
        System.out.println("订单信息: ");
        seckill.getOrders().forEach(System.out::println);
    }

    private static void seckill(String userId){
//        if (!rateLimiter.tryAcquire()) {
//            seckill.addLog("用户 " + userId + " 抢购失败，系统限流");
//            return;
//        }
        seckill.purchase(userId);
    }
}
