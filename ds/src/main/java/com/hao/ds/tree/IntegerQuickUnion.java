package com.hao.ds.tree;

public class IntegerQuickUnion implements IntegerUnionFind {

    int[] parents;

    public IntegerQuickUnion() {
        this(1024);
    }

    public IntegerQuickUnion(int capacity) {
        parents = new int[capacity];
        for (int i = 0; i < capacity; i++) {
            parents[i] = i;
        }
    }

    @Override
    public Integer find(Integer v) {
        while (true) {
            int p = parents[v];
            if (p == v) return p;
            v = p;
        }
    }

    @Override
    public void union(Integer v1, Integer v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return; // already connected

        parents[p1] = p2;
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
