package com. xiaomi. wusheng. course_0220;

import java.util.LinkedList;

public class LinkedListMerger {

    public static LinkedList<Integer> merge(LinkedList<Integer> list1, LinkedList<Integer> list2) {

        LinkedList<Integer> resultList = new LinkedList<>();

        for (Integer num1 : list1) {
            for (Integer num2 : list2) {
                if (num1.equals(num2) && !resultList.contains(num1)) {
                    resultList.add(num1);
                }
            }
        }

        return resultList;
    }

    public static void main(String[] args) {

        LinkedList<Integer> list1 = new LinkedList<>();
        list1.add(1);
        list1.add(2);
        list1.add(3);
        list1.add(4);

        LinkedList<Integer> list2 = new LinkedList<>();
        list2.add(3);
        list2.add(4);
        list2.add(5);
        list2.add(6);

        LinkedList<Integer> result = merge(list1, list2);

        System.out.println("Merged result: " + result);
    }
}
