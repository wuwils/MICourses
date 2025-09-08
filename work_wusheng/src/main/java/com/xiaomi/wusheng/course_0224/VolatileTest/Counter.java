package com.xiaomi.wusheng.course_0224.VolatileTest;

public class Counter {
    public static volatile int counter = 0;
    public static final Object lock = new Object();
    public static boolean flag = false;
}
