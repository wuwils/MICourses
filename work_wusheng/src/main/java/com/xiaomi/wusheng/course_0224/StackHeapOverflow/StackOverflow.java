package com.xiaomi.wusheng.course_0224.StackHeapOverflow;

public class StackOverflow {
    public static void main(String[] args) {
        try {
            infiniteRecursion();
        } catch (StackOverflowError e) {
            System.out.println("栈溢出异常: " + e.getMessage());
        }
    }

    public static void infiniteRecursion() {
        infiniteRecursion();
    }
}
