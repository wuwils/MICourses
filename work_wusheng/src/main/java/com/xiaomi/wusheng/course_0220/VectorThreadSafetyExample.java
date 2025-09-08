package com. xiaomi. wusheng. course_0220;

import java.util.Vector;

public class VectorThreadSafetyExample {

    public static void main(String[] args) throws InterruptedException {
        // 创建一个 Vector
        Vector<Integer> vector = new Vector<>();

        // 创建两个线程
        Thread thread1 = new Thread(new AddTask(vector, 0, 50));
        Thread thread2 = new Thread(new AddTask(vector, 50, 100));

        // 启动线程
        thread1.start();
        thread2.start();

        // 等待两个线程执行完毕
        thread1.join();
        thread2.join();

        // 打印 Vector 的内容
        System.out.println("Vector 的内容: " + vector);

        // 验证 Vector 的线程安全性
        System.out.println("Vector 的大小: " + vector.size());
    }

    // 添加任务类
    static class AddTask implements Runnable {
        private final Vector<Integer> vector;
        private final int start;
        private final int end;

        public AddTask(Vector<Integer> vector, int start, int end) {
            this.vector = vector;
            this.start = start;
            this.end = end;
        }

        @Override
        public void run() {
            for (int i = start; i < end; i++) {
                vector.add(i); // 向 Vector 中添加元素
            }
        }
    }
}