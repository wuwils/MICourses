/*
程序启动一个线程持有一个共享锁，并进入长时间睡眠。
同时启动多个线程不断尝试获取该锁，这些线程一直处于阻塞状态，
最终导致程序整体运行缓慢，而CPU利用率低。
*/
package com.xiaomi.wusheng.work_0301.question_1;

public class LockContentionDemo {
    private static final Object lock = new Object();

    public static void main(String[] args) {
        new Thread(() -> {
            synchronized(lock){
                try {
                    System.out.println("Thread-1 获取锁");
                    Thread.sleep(600_000); // 模拟持有锁后执行耗时I/O操作
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }, "Thread-1").start();

        for(int i = 2; i <= 10; i++){
            Thread newThread = new Thread(() -> {
                while (true) {
                    synchronized (lock) {
                        System.out.println(Thread.currentThread().getName() + "获取锁");
                    }
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }, "Thread-" + i);
            newThread.start();
        }
    }
}
