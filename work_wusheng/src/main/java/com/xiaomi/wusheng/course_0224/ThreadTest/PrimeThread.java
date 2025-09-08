package com.xiaomi.wusheng.course_0224.ThreadTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class PrimeThread implements Callable<List<Integer>> {
    public static void main(String[] args) {
        int[] numbers = new int[100];
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = i + 1;
        }

        Callable<List<Integer>> task = new PrimeThread(numbers);
        ExecutorService executor = Executors.newSingleThreadExecutor();
        Future<List<Integer>> future = executor.submit(task);

        try {
            List<Integer> primes = future.get();
            System.out.println("数组中的质数: " + primes);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("任务执行异常: " + e.getMessage());
        } finally {
            executor.shutdown();
        }
    }

    private final int[] numbers;

    public PrimeThread(int[] numbers) {
        this.numbers = numbers;
    }

    @Override
    public List<Integer> call() {
        List<Integer> primes = new ArrayList<>();
        for (int number : numbers) {
            if (isPrime(number)) {
                primes.add(number);
            }
        }
        return primes;
    }

    private boolean isPrime(int number) {
        if (number < 2) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }
}
