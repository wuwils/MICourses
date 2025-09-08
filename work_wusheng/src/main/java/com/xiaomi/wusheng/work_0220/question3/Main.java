package com.xiaomi.wusheng.work_0220.question3;

public class Main {

    public static void main(String[] args) {
        LRUCache<Integer, String> lruCache = new LRUCache<>(3);

        System.out.println("插入 3 个元素 ……");
        lruCache.put(1, "One");
        lruCache.put(2, "Two");
        lruCache.put(3, "Three");
        lruCache.printCache();  // 1=One 2=Two 3=Three
        System.out.println("获取 1: " + lruCache.get(1));  //  One

        System.out.println("\n访问 2 并插入 4 ……");
        lruCache.get(2);  // 2 成为最近使用的
        lruCache.put(4, "Four");  // 插入 4，淘汰最久未使用的元素 3
        lruCache.printCache();  // 1=One 2=Two 4=Four
        System.out.println("获取 3: " + lruCache.get(3));  // null
        System.out.println("获取 2: " + lruCache.get(2));  // Two

        System.out.println("\n访问 1 并插入 5 ……");
        lruCache.get(1);  // 1 成为最近使用的
        lruCache.put(5, "Five");  // 插入 5，淘汰最久未使用的元素 4
        lruCache.printCache();  // 2=Two 1=One 5=Five
        System.out.println("获取 4: " + lruCache.get(4));  // null
        System.out.println("获取 1: " + lruCache.get(1));  // One
        System.out.println("获取 5: " + lruCache.get(5));  // Five

        System.out.println("\n插入 6 和 7 ……");
        lruCache.put(6, "Six");  // 插入 6，淘汰最久未使用的元素 2
        lruCache.put(7, "Seven");  // 插入 7，淘汰最久未使用的元素 1
        lruCache.printCache();  // 5=Five 6=Six 7=Seven
        System.out.println("获取 2: " + lruCache.get(2));  // null
        System.out.println("获取 1: " + lruCache.get(1));  // null
        System.out.println("获取 7: " + lruCache.get(7));  // Seven

        System.out.println("\n更新 5 ……");
        lruCache.put(5, "UpdatedFive");  // 更新 5
        lruCache.printCache();  // 6=Six 7=Seven 5=UpdatedFive
        System.out.println("获取 5: " + lruCache.get(5));  // UpdatedFive
    }
}
