package com.hao.ds.tree;

import java.util.HashMap;
import java.util.Map;
import lombok.NonNull;

public class GenericQuickUnionByRankAndPathSplitting<V> extends AbstractUnionFind<V> {

    Map<V, Node<V>> nodes = new HashMap<>();

    private Node<V> findNode(V v) {
        Node<V> node = nodes.get(v);
        if (node == null) return null;
        while (!node.isRoot()) {
            Node<V> p = node.parent;
            node.parent = node.parent.parent;
            node = p;
        }
        return node;
    }

    @Override
    public V find(@NonNull V v) {
        Node<V> node = findNode(v);
        return node == null ? null : node.value;
    }

    @Override
    public void union(V v1, V v2) {
        nodes.computeIfAbsent(v1, v -> Node.of(v));
        nodes.computeIfAbsent(v2, v -> Node.of(v));

        Node<V> node1 = findNode(v1);
        Node<V> node2 = findNode(v2);
        Node<V> p1 = node1.parent;
        Node<V> p2 = node2.parent;

        if (p1 == p2) return; // already connected

        int r1 = p1.rank;
        int r2 = p2.rank;
        if (r1 > r2) {
            p2.parent = p1;
        } else {
            p1.parent = p2;
        }
        if (r1 == r2) {
            p2.rank++;
        }

    }
}
