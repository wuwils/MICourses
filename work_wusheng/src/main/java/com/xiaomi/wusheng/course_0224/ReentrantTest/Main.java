package com.xiaomi.wusheng.course_0224.ReentrantTest;

public class Main {
    public static void main(String[] args) {
        Counter counter = new Counter();

        Runnable incrementTask = () -> {
            for (int i = 0; i < 1000; i++) {
                counter.increment();
            }
        };

        Runnable readTask = () -> {
            for (int i = 0; i < 1000; i++) {
                System.out.println("当前计数器值: " + counter.get());
            }
        };

        Thread thread1 = new Thread(incrementTask);
        Thread thread2 = new Thread(incrementTask);
        Thread thread3 = new Thread(readTask);
        Thread thread4 = new Thread(readTask);

        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();

        try {
            thread1.join();
            thread2.join();
            thread3.join();
            thread4.join();
        } catch (InterruptedException e) {
            System.out.println("主线程被中断: " + e.getMessage());
        }

        System.out.println("最终计数器值: " + counter.get());
    }
}
