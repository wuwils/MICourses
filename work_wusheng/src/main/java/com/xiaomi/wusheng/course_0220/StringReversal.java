package com. xiaomi. wusheng. course_0220;

import java.util.Stack;

public class StringReversal {

    public static String reverseString(String str) {

        Stack<Character> stack = new Stack<>();

        for (int i = 0; i < str.length(); i++) {
            stack.push(str.charAt(i));
        }

        StringBuilder reversedStr = new StringBuilder();

        while (!stack.isEmpty()) {
            reversedStr.append(stack.pop());
        }

        return reversedStr.toString();
    }

    public static void main(String[] args) {

        String original = "Xiaomi Java";

        String reversed = reverseString(original);

        System.out.println("Original: " + original);
        System.out.println("Reversed: " + reversed);
    }
}
