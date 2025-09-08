package com.xiaomi.wusheng.work_0220.question1;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ListMerger {
    public static List<Integer> mergeList(List<Integer> list1, List<Integer> list2) {
        Set<Integer> set = new HashSet<>(list2);
        List<Integer> result = new ArrayList<>();

        for (Integer element : list1) {
            // 循环判断list1中的元素，若该元素不存在于list2，则加入到新列表中
            if (!set.contains(element)) {
                result.add(element);
            }
        }
        return result;
    }
}
