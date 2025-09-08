package com.xiaomi.wusheng.work_0224.question_2;

import java.util.concurrent.*;

public class Manager {
    private final BlockingQueue<Request> request;
    private final ExecutorService executorService;
    private final ScheduledExecutorService scheduler;
    private int activeConsumers = 0;

    public Manager(){
        this.request = new LinkedBlockingQueue<>();
        this.executorService = Executors.newCachedThreadPool();
        this.scheduler = Executors.newScheduledThreadPool(1);
        startConsumer();
        scheduler.scheduleAtFixedRate(this::checkConsumers, 1000, 1000, TimeUnit.MILLISECONDS);
    }

    private void startConsumer(){
        ConsumerThread consumer = new ConsumerThread(request);
        executorService.submit(consumer);
        activeConsumers++;
        System.out.println("消费者线程已启动");
    }

    public void startProducerThread(int numProducers){
        for (int i = 0; i < numProducers; i++) {
            ProducerThread producer = new ProducerThread(request, 500, 1500, 500);
            executorService.submit(producer);
            System.out.println("生产者线程 " + i + " 已启动");
        }
    }

    public void checkConsumers(){
        try{
            int queueSize = request.size();
            System.out.println("当前队列长度: " + queueSize + "，活跃消费者线程数: " + activeConsumers);

            if (queueSize > 10 && activeConsumers < 5){
                startBackup();
            }
        }catch(Exception e){
            System.err.println(e.getMessage());
        }
    }

    private void startBackup(){
        ConsumerThread backupConsumer = new ConsumerThread(request);
        executorService.submit(backupConsumer);
        activeConsumers++;
        System.out.println("备用消费者线程已启动，当前活跃消费者线程数: " + activeConsumers);
    }

    public void shutdown() {
        System.out.println("关闭消费者管理器和线程池");
        scheduler.shutdown();
        executorService.shutdown();
        try{
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        }catch (InterruptedException e){
            executorService.shutdownNow();
        }
    }
}