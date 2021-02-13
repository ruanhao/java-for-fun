package com.hao.ds.tree;

public class IntegerQuickUnionByRankAndPathSplitting extends IntegerQuickUnion {

    int[] ranks;

    public IntegerQuickUnionByRankAndPathSplitting(int cap) {
        super(cap);
        ranks = new int[cap];
        for (int i = 0; i < ranks.length; i++) {
            ranks[i] = 1;
        }
    }

    @Override
    public Integer find(Integer v) {
        while (parents[v] != v) {
            int p = parents[v];
            parents[v] = parents[p];
            v = p;
        }
        return v;
    }

    @Override
    public void union(Integer v1, Integer v2) {
        int p1 = super.find(v1);
        int p2 = super.find(v2);
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
