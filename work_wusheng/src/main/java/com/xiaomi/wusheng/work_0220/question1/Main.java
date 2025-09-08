package com.xiaomi.wusheng.work_0220.question1;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> list1 = new ArrayList<>();
        list1.add(5);
        list1.add(7);
        list1.add(3);
        list1.add(9);
        System.out.println("list1: " + list1);

        List<Integer> list2 = new ArrayList<>();
        list2.add(1);
        list2.add(5);
        list2.add(2);
        list2.add(4);
        list2.add(3);
        System.out.println("list2: " + list2);

        List<Integer> result = ListMerger.mergeList(list1, list2);
        System.out.println("新列表: " + result);
    }
}