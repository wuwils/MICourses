package com.xiaomi.wusheng.course_0224.ThreadTest;

public class ThreadTest {
    public static void main(String[] args) {
        Thread thread = new MyThread();
        Thread thread1 = new Thread(new MyRunnable());
        Thread thread2 = new Thread(() -> System.out.println("start new thread2"));
        thread.start();
        thread1.start();
        thread2.start();
    }
}

class MyThread extends Thread{
    @Override
    public void run() {
        System.out.println("new thread start");
    }
}

class MyRunnable implements Runnable{
    @Override
    public void run() {
        System.out.println("start new thread1");
    }
}
