package com.xiaomi.wusheng.work_0220.question2;

public class Main {
    public static void main(String[] args) {
        CircularQueue<Integer> cq = new CircularQueue<>(5);

        System.out.println("入队……");
        cq.enqueue(1);
        cq.enqueue(2);
        cq.enqueue(3);
        cq.enqueue(4);
        cq.enqueue(5);
        cq.printQueue(); // 1 2 3 4 5

        System.out.println("\n队列已满，尝试将元素 6 入队:");
        cq.enqueue(6);
        cq.printQueue(); // 1 2 3 4 5

        System.out.println("\n出队…… 出队元素为: " + cq.dequeue());
        cq.printQueue(); // 2 3 4 5

        System.out.println("\n再次尝试将元素 6 入队:");
        cq.enqueue(6);
        cq.printQueue(); // 2 3 4 5 6

        System.out.println("\n队头元素: " + cq.peek()); // 2

        System.out.println("\n队列长度: " + cq.size());  // 5

        System.out.println("\n队列是否已满: " + cq.isFull()); //true

        System.out.println("\n队列是否为空: " + cq.isEmpty()); // false

        System.out.println("\n清空队列……");
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        cq.dequeue();
        System.out.println("\n队列是否为空: " + cq.isEmpty() + "\n"); // true
        cq.printQueue();  // 当前队列: 空对列！
    }
}
