package com. xiaomi. wusheng. course_0220;

import java.util.ArrayList;
import java.util.Collections;

public class MedianCalculator {

    public static double calculateMedian(ArrayList<Integer> list) {

        Collections.sort(list);

        int size = list.size();
        int middle = size / 2;

        if (size % 2 == 1) {
            return list.get(middle);
        } else {
            return (list.get(middle - 1) + list.get(middle)) / 2.0;
        }
    }

    public static void main(String[] args) {
        // 1 3 5 7 9
        ArrayList<Integer> list1 = new ArrayList<>();
        list1.add(5);
        list1.add(3);
        list1.add(9);
        list1.add(1);
        list1.add(7);

        // 2 4 6 8
        ArrayList<Integer> list2 = new ArrayList<>();
        list2.add(2);
        list2.add(8);
        list2.add(4);
        list2.add(6);

        System.out.println("列表1的中位数: " + calculateMedian(list1));
        System.out.println("列表2的中位数: " + calculateMedian(list2));
    }
}
