package com.xiaomi.wusheng.work_0222.question1;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> numberList = Arrays.asList(1, 9, 2, 9, 5, 2, 0, 5, 6, 0, 9);
        System.out.println("整数列表: ");
        numberList.forEach(number -> System.out.print(number + " "));

        List<Integer> evenList = numberList.stream()
                .filter(number -> number % 2 == 0)
                .collect(Collectors.toList());
        System.out.println("\n\n筛选偶数: ");
        evenList.forEach(number -> System.out.print(number + " "));

        List<Integer> doubledList = evenList.stream()
                .map(number -> number * 2)
                .collect(Collectors.toList());
        System.out.println("\n\n乘以2后: ");
        doubledList.forEach(number -> System.out.print(number + " "));

        List<Integer> distinctList = doubledList.stream()
                .distinct()
                .collect(Collectors.toList());
        System.out.println("\n\n列表去重: ");
        distinctList.forEach(number -> System.out.print(number + " "));

        int result = distinctList.stream()
                .reduce(0, Integer::sum);
        System.out.println("\n\n输出总和: " + result);
    }
}
