package com.xiaomi.wusheng.work_0220.question3;

// 双向链表
class DoublyLinkedNode<K, V> {
    K key;
    V value;
    DoublyLinkedNode<K, V> prev;
    DoublyLinkedNode<K, V> next;

    public DoublyLinkedNode(K key, V value) {
        this.key = key;
        this.value = value;
    }
}
