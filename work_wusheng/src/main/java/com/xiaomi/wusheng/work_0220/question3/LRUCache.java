package com.xiaomi.wusheng.work_0220.question3;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

// LRU缓存
public class LRUCache<K, V> {
    private final int capacity;
    private final Map<K, DoublyLinkedNode<K, V>> cache;
    private final DoublyLinkedNode<K, V> head, tail;
    private final ReentrantLock lock;

    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<>(capacity);
        this.lock = new ReentrantLock();

        this.head = new DoublyLinkedNode<>(null, null);
        this.tail = new DoublyLinkedNode<>(null, null);
        head.next = tail;
        tail.prev = head;
    }

    // 获取缓存中的值
    public V get(K key) {
        lock.lock();
        try {
            DoublyLinkedNode<K, V> node = cache.get(key);
            if (node == null) {
                return null;
            }
            moveToTail(node);
            return node.value;
        } finally {
            lock.unlock();
        }
    }

    // 插入新元素到缓存
    public void put(K key, V value) {
        lock.lock();
        try {
            DoublyLinkedNode<K, V> node = cache.get(key);

            if (node != null) {
                node.value = value;
                moveToTail(node);
            } else {
                if (cache.size() >= capacity) {
                    removeLRU();
                }
                DoublyLinkedNode<K, V> newNode = new DoublyLinkedNode<>(key, value);
                cache.put(key, newNode);
                addToTail(newNode);
            }
        } finally {
            lock.unlock();
        }
    }

    // 移动节点到链表尾部（表示最近使用）
    private void moveToTail(DoublyLinkedNode<K, V> node) {
        removeNode(node);
        addToTail(node);
    }

    // 从链表中移除节点
    private void removeNode(DoublyLinkedNode<K, V> node) {
        node.prev.next = node.next;
        node.next.prev = node.prev;
    }

    // 将节点插入到链表尾部
    private void addToTail(DoublyLinkedNode<K, V> node) {
        node.prev = tail.prev;
        node.next = tail;
        tail.prev.next = node;
        tail.prev = node;
    }

    // 移除最久未使用的元素（链表头部元素）
    private void removeLRU() {
        if (head.next != tail) {
            DoublyLinkedNode<K, V> node = head.next;
            removeNode(node);
            cache.remove(node.key);
        }
    }

    // 打印缓存内容
    public void printCache() {
        lock.lock();
        try {
            DoublyLinkedNode<K, V> current = head.next;
            while (current != tail) {
                System.out.print(current.key + "=" + current.value + " ");
                current = current.next;
            }
            System.out.println();
        } finally {
            lock.unlock();
        }
    }

    // 获取当前缓存的容量
    public int getCapacity() {
        return this.capacity;
    }

    // 获取当前缓存的大小
    public int getSize() {
        return this.cache.size();
    }
}

