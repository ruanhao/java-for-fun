package com.hao.ds.tree;

public interface UnionFind<V> {
    V find(V v);
    void union(V v1, V v2);
    default boolean isConnected(V v1, V v2) {
        return find(v1) == find(v2);
    }
}
