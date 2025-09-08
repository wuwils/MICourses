package com.xiaomi.wusheng.work_0224.question_2;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main{
    public static void main(String[] args){
        Manager consumerManager = new Manager();

        // 3个生产者线程
        int numProducers = 3;
        consumerManager.startProducerThread(numProducers);

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
        scheduler.scheduleAtFixedRate(() -> {
            consumerManager.checkConsumers();
        }, 0, 1, TimeUnit.SECONDS);

        try{
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        } finally {
            scheduler.shutdown();
            consumerManager.shutdown();
            System.out.println("系统已关闭");
        }
    }
}