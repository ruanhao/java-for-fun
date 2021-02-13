package com.hao.ds.tree;

public class IntegerQuickUnionBySize extends IntegerQuickUnion {

    int[] sizes;

    public IntegerQuickUnionBySize(int cap) {
        super(cap);
        sizes = new int[cap];
        for (int i = 0; i < sizes.length; i++) {
            sizes[i] = 1;
        }
    }

    @Override
    public void union(Integer v1, Integer v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return; // already connected

        int s1 = sizes[p1];
        int s2 = sizes[p2];
        if (s1 > s2) {
            parents[p2] = p1;
            sizes[p1] += sizes[p2];
        } else {
            parents[p1] = p2;
            sizes[p2] += sizes[p1];
        }
    }
}
