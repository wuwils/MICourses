package com. xiaomi. wusheng. course_0220;

import java.util.HashSet;

public class GoodPairArray {
    public static boolean isGoodPairArray(int[] nums) {

        HashSet<Integer> set = new HashSet<>();

        for (int num : nums) {
            if (set.contains(num)) {
                set.remove(num);
            } else {
                set.add(num);
            }
        }

        return set.isEmpty();
    }

    public static void main(String[] args) {

        int[] nums1 = {1, 2, 2, 1};
        int[] nums2 = {1, 2, 3, 4};
        int[] nums3 = {1, 1, 2, 2, 3, 3};
        int[] nums4 = {1, 2, 2, 1, 3};

        System.out.println("1, 2, 2, 1: " + isGoodPairArray(nums1));
        System.out.println("1, 2, 3, 4: " + isGoodPairArray(nums2));
        System.out.println("1, 1, 2, 2, 3, 3: " + isGoodPairArray(nums3));
        System.out.println("1, 2, 2, 1, 3: " + isGoodPairArray(nums4));
    }
}
