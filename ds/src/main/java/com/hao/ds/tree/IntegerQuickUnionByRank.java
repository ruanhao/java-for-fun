package com.hao.ds.tree;

public class IntegerQuickUnionByRank extends IntegerQuickUnion {

    int[] ranks;

    public IntegerQuickUnionByRank(int cap) {
        super(cap);
        ranks = new int[cap];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public void union(Integer v1, Integer v2) {
        int p1 = find(v1);
        int p2 = find(v2);
        if (p1 == p2) return; // already connected

        int r1 = ranks[p1];
        int r2 = ranks[p2];
        if (r1 > r2) {
            parents[p2] = p1;
        } else {
            parents[p1] = p2;
        }
        if (r1 == r2) {
            ranks[p2]++;
        }
    }
}
