package com.xiaomi.wusheng.work_0219.question2;

public class Main {
    public static void main(String[] args) {
        AddListNode solution = new AddListNode();

        // 输入：7243，564  输出：7807
        ListNode l1 = new ListNode(7);
        l1.next = new ListNode(2);
        l1.next.next = new ListNode(4);
        l1.next.next.next = new ListNode(3);

        ListNode l2 = new ListNode(5);
        l2.next = new ListNode(6);
        l2.next.next = new ListNode(4);

        ListNode result1 = solution.addTwoNumbers(l1, l2);
        System.out.print("Test Case 1 ： 7243 + 564 = ");
        solution.printList(result1);

        // 输入：243，564  输出：807
        ListNode l3 = new ListNode(2);
        l3.next = new ListNode(4);
        l3.next.next = new ListNode(3);

        ListNode l4 = new ListNode(5);
        l4.next = new ListNode(6);
        l4.next.next = new ListNode(4);

        ListNode result2 = solution.addTwoNumbers(l3, l4);
        System.out.print("Test Case 2 ： 243 + 564 = ");
        solution.printList(result2);

        // 输入：0，0   输出：0
        ListNode l5 = new ListNode(0);
        ListNode l6 = new ListNode(0);

        ListNode result3 = solution.addTwoNumbers(l5, l6);
        System.out.print("Test Case 3 ： 0 + 0 = ");
        solution.printList(result3);
    }
}