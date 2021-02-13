package com.hao.ds.tree;

public class IntegerQuickFind implements IntegerUnionFind {

    int[] parents;

    public IntegerQuickFind() {
        this(1024);
    }

    public IntegerQuickFind(int capacity) {
        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
        }
    }

    @Override
    public Integer find(Integer v) {
        return parents[v];
    }

    @Override
    public void union(Integer v1, Integer v2) {
        int p1 = parents[v1];
        int p2 = parents[v2];
        if (p1 == p2) return; // already connected

        for (int i = 0; i < parents.length; i++) {
            if (parents[i] == p1) {
                parents[i] = p2;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("==================\n");
        for (int i = 0; i < parents.length; i++) {
            sb.append(i + "\t");
        }
        sb.append("\n");
        for (int p : parents) {
            sb.append(p + "\t");
        }
        sb.append("\n");
        sb.append("==================\n");
        return sb.toString();
    }
}
