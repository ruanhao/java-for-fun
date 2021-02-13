package com.hao.ds.tree;

public abstract class AbstractUnionFind<V> implements UnionFind<V> {

    protected static class Node<V> {
        V value;
        Node<V> parent = this;
        int rank = 1;

        boolean isRoot() {
            return parent == this;
        }

        protected static <V> Node<V> of(V v) {
            Node<V> node = new Node<>();
            node.value = v;
            return node;
        }
    }
}
