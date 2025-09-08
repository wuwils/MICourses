package com.xiaomi.wusheng.course_0224.ThreadTest;

public class OddEvenThread{
    public static void main(String[] args) {
        OddThread oddThread = new OddThread();
        EvenThread evenThread = new EvenThread();
        oddThread.start();
        evenThread.start();
    }
}

class OddThread extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 100; i += 2) {
            System.out.println("奇数: " + i);
        }
    }
}

class EvenThread extends Thread {
    @Override
    public void run() {
        for (int i = 2; i <= 100; i += 2) {
            System.out.println("偶数: " + i);
        }
    }
}


