package com.xiaomi.wusheng.course_0224.VolatileTest;

public class PrintThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (Counter.lock) {
                while (!Counter.flag) {
                    try {
                        Counter.lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("打印线程被中断: " + e.getMessage());
                    }
                }

                System.out.println("当前计数器值: " + Counter.counter);

                Counter.flag = false;

                Counter.lock.notify();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("打印线程被中断: " + e.getMessage());
            }
        }
    }
}
