package com.xiaomi.wusheng.work_0224.question_1;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Seckill{
    private AtomicInteger stock;
    private ConcurrentLinkedQueue<String> orders;
    private ConcurrentLinkedQueue<String> logs;
    private Lock lock;

    public Seckill(int initialStock){
        this.stock = new AtomicInteger(initialStock);
        this.orders = new ConcurrentLinkedQueue<>();
        this.logs = new ConcurrentLinkedQueue<>();
        this.lock = new ReentrantLock();
    }

    public void purchase(String userId){
        if (lock.tryLock()){
            try{
                int remainingStock = stock.get();
                if (remainingStock > 0){
                    if (stock.compareAndSet(remainingStock, remainingStock - 1)){
                        orders.add("Order for " + userId);
                        logs.add("用户 " + userId + " 抢购成功，剩余库存: " + (remainingStock - 1));
                    }else{
                        logs.add("用户 " + userId + " 抢购失败，系统繁忙");
                    }
                }else{
                    logs.add("用户 " + userId + " 抢购失败，已售罄");
                }
            }finally{
                lock.unlock();
            }
        }else{
            logs.add("用户 " + userId + " 抢购失败，系统繁忙");
        }
    }

    public AtomicInteger getStock(){
        return stock;
    }

    public ConcurrentLinkedQueue<String> getOrders(){
        return orders;
    }

    public ConcurrentLinkedQueue<String> getLogs(){
        return logs;
    }

    public void addLog(String log){
        logs.add(log);
    }
}
