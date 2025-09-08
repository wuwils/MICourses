package com.xiaomi.wusheng.work_0220.question2;

import java.util.ArrayList;
import java.util.List;

public class CircularQueue<T> {
    private List<T> queue;
    private int front;
    private int rear;
    private int size;
    private final int capacity;

    public CircularQueue(int capacity) {
        this.capacity = capacity;
        this.queue = new ArrayList<>(capacity);
        this.front = 0;
        this.rear = 0;
        this.size = 0;

        for (int i = 0; i < capacity; i++) {
            queue.add(null);
        }
    }

    // 入队
    public boolean enqueue(T element) {
        if (isFull()) return false;

        // 在尾部插入元素
        queue.set(rear, element);
        rear = (rear + 1) % capacity;
        size++;
        return true;
    }

    // 出队
    public T dequeue() {
        if (isEmpty()) return null;

        T element = queue.get(front);
        front = (front + 1) % capacity;
        size--;
        return element;
    }

    // 查看队头
    public T peek() {
        return isEmpty() ? null : queue.get(front);
    }

    // 是否为空
    public boolean isEmpty() {
        return size == 0;
    }

    // 是否已满
    public boolean isFull() {
        return size == capacity;
    }

    // 队列大小
    public int size() {
        return size;
    }

    // 打印队列
    public void printQueue() {
        if (isEmpty()) {
            System.out.println("当前队列: 空对列！");
            return;
        }

        StringBuilder sb = new StringBuilder("当前队列: ");
        for (int i = 0; i < size; i++) {
            int index = (front + i) % capacity;
            sb.append(queue.get(index)).append(" ");
        }
        System.out.println(sb.toString().trim());
    }
}
