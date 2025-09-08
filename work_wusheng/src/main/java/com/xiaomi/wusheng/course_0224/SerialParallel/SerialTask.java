package com.xiaomi.wusheng.course_0224.SerialParallel;

public class SerialTask {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        long endTime = System.currentTimeMillis();
        System.out.println("串行执行耗时: " + (endTime - startTime) + "ms");
    }
}
