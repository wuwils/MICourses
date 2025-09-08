package com.xiaomi.wusheng.course_0224.VolatileTest;

public class IncrementThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            synchronized (Counter.lock) {
                while (Counter.flag) {
                    try {
                        Counter.lock.wait();
                    } catch (InterruptedException e) {
                        System.out.println("增加线程被中断: " + e.getMessage());
                    }
                }

                Counter.counter++;
                System.out.println("增加计数器: " + Counter.counter);

                Counter.flag = true;

                Counter.lock.notify();
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("增加线程被中断: " + e.getMessage());
            }
        }
    }
}
