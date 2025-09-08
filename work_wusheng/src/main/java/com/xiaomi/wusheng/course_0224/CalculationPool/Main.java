package com.xiaomi.wusheng.course_0224.CalculationPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        ExecutorService executor = Executors.newFixedThreadPool(10);

        for (int i = 1; i <= 100; i++) {
            String task;
            if (i <= 25) {
                task = "add";
            } else if (i <= 50) {
                task = "sub";
            } else if (i <= 75) {
                task = "mul";
            } else {
                task = "div";
            }

            int a = (int) (Math.random() * 100);
            int b = (int) (Math.random() * 100) + 1;

            executor.submit(new ThreadPool(task, a, b));
        }

        executor.shutdown();
    }
}
