package com. xiaomi. wusheng. course_0220;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class ListToLinkedHashSet {


    public static Set<String> listToLinkedHashSet(List<String> inputList) {

        Set<String> resultSet = new LinkedHashSet<>(inputList);
        return resultSet;
    }

    public static void main(String[] args) {

        List<String> inputList = new ArrayList<>();
        inputList.add("dd");
        inputList.add("bb");
        inputList.add("aa");
        inputList.add("cc");
        inputList.add("bb");
        inputList.add("aa");

        Set<String> resultSet = listToLinkedHashSet(inputList);

        System.out.println("List: " + inputList);
        System.out.println("Linked" + resultSet);
    }
}



//import java.util.*;
//
//public class ToSet {
//    // 定义一个自定义的比较器，按插入顺序进行排序
//    static class MyString implements Comparable<MyString> {
//        private String value;
//        private int index;
//
//        public MyString(String value, int index) {
//            this.value = value;
//            this.index = index;
//        }
//
//        public String getValue() {
//            return value;
//        }
//
//        @Override
//        public int compareTo(MyString other) {
//            // 按照索引顺序排序
//            return Integer.compare(this.index, other.index);
//        }
//
//        @Override
//        public String toString() {
//            return value;
//        }
//    }
//
//    // 方法：去重并保持输入顺序
//    public static TreeSet<String> removeDuplicates(List<String> inputList) {
//        // 创建一个列表来存储每个元素的 MyString 对象，记录其插入的顺序
//        List<MyString> indexedList = new ArrayList<>();
//        Set<String> seen = new HashSet<>();
//
//        // 使用 HashSet 来去重并记录每个元素的首次插入位置
//        for (int i = 0; i < inputList.size(); i++) {
//            String element = inputList.get(i);
//            if (!seen.contains(element)) {
//                seen.add(element);
//                indexedList.add(new MyString(element, i));
//            }
//        }
//
//        // 使用 TreeSet 来去重并根据插入顺序排序
//        TreeSet<MyString> treeSet = new TreeSet<>();
//        treeSet.addAll(indexedList);
//
//        // 返回一个按顺序的去重后的结果
//        TreeSet<String> resultSet = new TreeSet<>();
//        for (MyString item : treeSet) {
//            resultSet.add(item.getValue());
//        }
//        return resultSet;
//    }
//
//    public static void main(String[] args) {
//        List<String> inputList = new ArrayList<>();
//        inputList.add("dd");
//        inputList.add("bb");
//        inputList.add("aa");
//        inputList.add("cc");
//        inputList.add("bb");
//        inputList.add("aa");
//
//        // 调用方法去重并保持顺序
//        TreeSet<String> resultSet = removeDuplicates(inputList);
//
//        // 输出结果
//        System.out.println("输出的 TreeSet: " + resultSet);
//    }
//}
