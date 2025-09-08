package com.xiaomi.wusheng.work_0219.question2;

import java.util.Stack;

public class AddListNode {

    // 将两个链表表示的数字相加，返回结果链表
    public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
        // 使用两个栈来存储链表中的数字
        Stack<Integer> stack1 = new Stack<>();
        Stack<Integer> stack2 = new Stack<>();

        // 分别将链表l1和链表l2中的数字压入栈1和栈2
        while (l1 != null) {
            stack1.push(l1.val);
            l1 = l1.next;
        }

        while (l2 != null) {
            stack2.push(l2.val);
            l2 = l2.next;
        }

        // 初始化进位为0，结果链表为null
        int carry = 0;
        ListNode result = null;

        // 当栈1或栈2不为空，或者有进位，就继续循环
        while (!stack1.isEmpty() || !stack2.isEmpty() || carry != 0) {
            // 计算当前位的和，加上进位
            int sum = carry;
            if (!stack1.isEmpty()) {
                sum += stack1.pop();
            }
            if (!stack2.isEmpty()) {
                sum += stack2.pop();
            }

            // 计算当前位的值和新的进位
            int digit = sum % 10;
            carry = sum / 10;

            // 创建新节点，并将其插入到结果链表的最前面
            ListNode newNode = new ListNode(digit);
            newNode.next = result;
            result = newNode;
        }

        // 返回结果链表
        return result;
    }

    // 打印链表
    public void printList(ListNode head) {
        while (head != null) {
            System.out.print(head.val + " ");
            head = head.next;
        }
        System.out.println();
    }
}