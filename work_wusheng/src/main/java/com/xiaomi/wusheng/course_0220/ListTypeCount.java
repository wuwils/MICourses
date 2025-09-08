package com. xiaomi. wusheng. course_0220;

import java.util.*;

public class ListTypeCount {
    public static void main(String[] args) {
        List list = Arrays.asList("1", "2", 1, 2, 12.3, "333");

        int stringCount = 0;
        int intCount = 0;
        int doubleCount = 0;

        for (Object obj : list) {
            if (obj instanceof String) {
                stringCount++;
            } else if (obj instanceof Integer) {
                intCount++;
            } else if (obj instanceof Double) {
                doubleCount++;
            }
        }

        int maxCount = Math.max(stringCount, Math.max(intCount, doubleCount));
        int minCount = Math.min(stringCount, Math.min(intCount, doubleCount));

        int difference = maxCount - minCount;

        System.out.println("字符串数量: " + stringCount);
        System.out.println("整数数量: " + intCount);
        System.out.println("浮点数数量: " + doubleCount);
        System.out.println("最大值和最小值的差值是: " + difference);
    }
}
