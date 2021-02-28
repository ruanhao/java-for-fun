package com.hao.ds.map;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

public class SkipList<K extends Comparable, V> {

    // 限制最高层数
    private static final int MAX_LEVEL = 32;
    // 用于计算新节点层高的概率因子
    private static final double P  = 0.25;



    // 有效层数
    int level;

    int size;

    Node<K, V> head;

    public SkipList() {
        head = new Node<>();
        head.nexts = new Node[MAX_LEVEL];
    }

    private int randomLevel() {
        int level = 1;
        while (Math.random() < P && level < MAX_LEVEL) level++;
        return level;
    }

    private int compare(K k1, K k2) {
        return k1.compareTo(k2);
    }

    public V remove(@NonNull K key) {
        Node<K, V> node = head;
        Node[] prevs = new Node[level]; // 记录每一层对应的前驱节点
        boolean exist = false;
        for (int i = level - 1; i >= 0; i--) { // 从顶层到底层，遍历头节点的 nexts 指针数组
            int cmp = -1;
            while (node.nexts[i] != null &&
                    (cmp = compare(node.nexts[i].key, key)) < 0) {
                node = node.nexts[i];
            }
            // 就算找到要删除的节点，遍历也要继续，因为要找到所有的前驱节点
            // 这里要做的只是记录下被删节点是否存在
            if (cmp == 0) exist = true;
            prevs[i] = node;
        }
        if (!exist) return null;
        Node<K, V> nodeToRemove = node.nexts[0]; // node.nexts[0] 必然是被删节点

        for (int i = 0; i < nodeToRemove.nexts.length; i++) {
            prevs[i].nexts[i] = nodeToRemove.nexts[i];
        }
        size++;
        // 更新跳表有效层数
        for (int i = level; i >= 0; i--) {
            if (head.nexts[i - 1] != null) {
                level = i;
                break;
            }
        }
        return nodeToRemove.value;
    }

    public V put(@NonNull K key, V value) {
        Node<K, V> node = head;
        Node[] prevs = new Node[level]; // 记录每一层对应的前驱节点
        for (int i = level - 1; i >= 0; i--) { // 从顶层到底层，遍历头节点的 nexts 指针数组
            int cmp = -1;
            while (node.nexts[i] != null &&
                    (cmp = compare(node.nexts[i].key, key)) < 0) {
                node = node.nexts[i];
            }
            if (cmp == 0) {  // already exists, just update value
                Node<K, V> old = node.nexts[i];
                old.value = value;
                return old.value;
            }
            prevs[i] = node;
        }
        // 到这里说明需要添加一个全新的节点
        int newNodeLevel = randomLevel();
        Node<K, V> newNode = new Node<>(key, value, newNodeLevel);
        for (int i = 0; i < newNodeLevel; i++) {
            if (i >= level) { // 新节点层数高于跳表有效层数
                head.nexts[i] = newNode; // 直接将首节点对应层数中的 next 指针指向新节点即可
                continue;
            }
            newNode.nexts[i] = prevs[i].nexts[i];
            prevs[i].nexts[i] = newNode;
        }
        level = Math.max(level, newNodeLevel);
        size++;
        return null;
    }

    public V get(@NonNull K key) {
        Node<K, V> node = head;
        for (int i = level - 1; i >= 0; i--) { // 从顶层到底层，遍历头节点的 nexts 指针数组
            int cmp = -1;
            while (node.nexts[i] != null &&
                    (cmp = compare(node.nexts[i].key, key)) < 0) {
                node = node.nexts[i];
            }
            // 到这里说明 node.nexts[i].key >= key
            if (cmp == 0) return node.nexts[i].value;
        }
        return null;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @ToString
    static class Node<K, V> {
        K key;
        V value;
        Node<K, V>[] nexts;

        public Node(K key, V value, int level) {
            this.key = key;
            this.value = value;
            nexts = new Node[level];
        }
    }
}
