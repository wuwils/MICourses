package com.xiaomi.wusheng.work_0224.question_2;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ConsumerThread implements Runnable{
    private final BlockingQueue<Request> requestQueue;
    private final AtomicInteger currentPower;

    public ConsumerThread(BlockingQueue<Request> requestQueue){
        if (requestQueue == null){
            throw new IllegalArgumentException("请求队列不能为空");
        }
        this.requestQueue = requestQueue;
        this.currentPower = new AtomicInteger(0);
    }

    @Override
    public void run(){
        try{
            while (!Thread.currentThread().isInterrupted()){
                Request request = requestQueue.poll(30, TimeUnit.SECONDS);
                if (request == null){
                    System.out.println("请求超时或队列为空");
                    continue;
                }

                if (currentPower.get() + request.getPower() > 5000){
                    System.out.println("功率超出限制，设备 " + request.getDeviceName() + " 被延迟处理");
                    // 重新加入队列末尾，继续等待处理
                    requestQueue.offer(request);
                    continue;
                }

                currentPower.addAndGet(request.getPower());

                System.out.println("处理设备: " + request.getDeviceName() + ", 功率: " + request.getPower() + "W");

                TimeUnit.MILLISECONDS.sleep(500);

                currentPower.addAndGet(-request.getPower());
            }
        }catch(InterruptedException e){
            System.out.println("消费者线程被中断");
            Thread.currentThread().interrupt();
        }catch(Exception e){
            System.err.println("消费者线程发生异常: " + e.getMessage());
        }finally{
            System.out.println("消费者线程结束");
        }
    }
}