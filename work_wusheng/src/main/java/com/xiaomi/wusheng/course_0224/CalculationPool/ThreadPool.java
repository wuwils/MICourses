package com.xiaomi.wusheng.course_0224.CalculationPool;

public class ThreadPool implements Runnable{
        private String type;
        private final int a;
        private final int b;

        public ThreadPool(String type, int a, int b) {
            this.type = type;
            this.a = a;
            this.b = b;
        }

        @Override
        public void run() {
            try {
                String task = type;
                double result = 0;

                switch (task) {
                    case "add":
                        result = a + b;
                        break;
                    case "sub":
                        result = a - b;
                        break;
                    case "mul":
                        result = a * b;
                        break;
                    case "div":
                        result = (double) a / b;
                        break;
                }

                System.out.println(task + " (" + a + ", " + b + ") = " + result);

                Thread.sleep(100);
            } catch (InterruptedException e) {
                System.out.println("error: " + e.getMessage());
            }
        }
}
