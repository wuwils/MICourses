package com.xiaomi.wusheng.course_0224.StackHeapOverflow;

import java.util.ArrayList;
import java.util.List;

public class HeapOverflow {
    public static void main(String[] args) {
        try {
            List<Object> list = new ArrayList<>();
            while (true) {
                list.add(new Object());
            }
        } catch (OutOfMemoryError e) {
            System.out.println("堆溢出异常: " + e.getMessage());
        }
    }
}
