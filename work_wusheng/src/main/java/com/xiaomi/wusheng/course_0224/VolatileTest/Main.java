package com.xiaomi.wusheng.course_0224.VolatileTest;

public class Main {
    public static void main(String[] args) {
        IncrementThread incrementThread = new IncrementThread();
        incrementThread.start();

        PrintThread printThread = new PrintThread();
        printThread.start();
    }
}
