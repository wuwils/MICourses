package com.xiaomi.wusheng.course_0224.ThreadTest;

import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeThread implements Runnable {
    public static void main(String[] args) {
        Runnable timerTask = new TimeThread();
        Thread thread = new Thread(timerTask, "TimerThread");
        thread.start();
    }

    @Override
    public void run() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String threadName = Thread.currentThread().getName();

        while (true) {
            try {
                String currentTime = dateFormat.format(new Date());
                System.out.println("线程名称: " + threadName + ", 当前时间: " + currentTime);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                System.out.println("线程被中断: " + e.getMessage());
                break;
            }
        }
    }


}
