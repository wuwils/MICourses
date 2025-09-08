package com.xiaomi.wusheng.work_0224.question_2;

import java.util.Random;
import java.util.concurrent.*;

public class ProducerThread implements Runnable{
    private static final String[] DEVICES = {"空调", "洗衣机", "充电桩", "医疗设备"};
    private static final Random random = new Random();
    private final BlockingQueue<Request> requestQueue;
    private final int minPower;
    private final int maxPower;
    private final long produceInterval;

    public ProducerThread(BlockingQueue<Request> requestQueue, int minPower, int maxPower, long produceInterval){
        if (requestQueue == null){
            throw new IllegalArgumentException("请求队列不能为空");
        }
        if (minPower <= 0 || maxPower <= 0 || minPower > maxPower){
            throw new IllegalArgumentException("功率范围无效");
        }
        if (produceInterval < 0){
            throw new IllegalArgumentException("生产间隔时间不能为负数");
        }

        this.requestQueue = requestQueue;
        this.minPower = minPower;
        this.maxPower = maxPower;
        this.produceInterval = produceInterval;
    }

    @Override
    public void run(){
        try {
            while (!Thread.currentThread().isInterrupted()){
                String deviceName = DEVICES[random.nextInt(DEVICES.length)];
                int power = minPower + random.nextInt(maxPower - minPower + 1);
                boolean isHighPriority = deviceName.equals("医疗设备");

                Request request = new Request(deviceName, power, isHighPriority);

                TimeUnit.MILLISECONDS.sleep(random.nextInt((int) produceInterval));

                if (!requestQueue.offer(request, 30, TimeUnit.SECONDS)) {
                    System.out.println(deviceName + "请求超时！");
                } else {
                    System.out.println(deviceName + "请求已加入队列，功率: " + power + "W");
                }
            }
        }catch (InterruptedException e){
            System.out.println("设备生产者线程被中断");
            Thread.currentThread().interrupt();
        }catch (Exception e){
            System.err.println("设备生产者发生异常: " + e.getMessage());
        }finally{
            System.out.println("设备生产者线程结束");
        }
    }
}