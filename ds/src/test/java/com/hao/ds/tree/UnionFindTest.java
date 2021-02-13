package com.hao.ds.tree;

import java.util.Arrays;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class UnionFindTest {

    private static int COUNT = 1000000;

//               0---1     6     8--10
//              /|         |     | /
//             / |         |     |/
//            4  3---5     7     9---11
//               \ /
//                2

    private void initUF(UnionFind uf) {
        uf.union(0, 1);
        uf.union(0, 3);
        uf.union(0, 4);
        uf.union(2, 3);
        uf.union(2, 5);

        uf.union(6, 7);
        uf.union(8, 10);
        uf.union(9, 10);
        uf.union(9, 11);
    }

    @Test
    public void functionTest() {
        Arrays.asList(
                new IntegerQuickFind(16),
                new IntegerQuickUnion(16),
                new IntegerQuickUnionBySize(16),
                new IntegerQuickUnionByRank(16),
                new IntegerQuickUnionByRankAndPathCompression(16),
                new IntegerQuickUnionByRankAndPathSplitting(16),
                new GenericQuickUnionByRankAndPathSplitting<Integer>()
        ).forEach(uf -> {
            initUF(uf);
            doTest(uf);
        });
    }

//    @Test
//    public void quickFind() {
//        IntegerQuickFind uf = new IntegerQuickFind(16);
//        initUF(uf);
//        // System.err.println(uf);
//        doTest(uf);
//    }

//    IntegerQuickFind: 21895ms
//    IntegerQuickUnion: 72371ms
//    IntegerQuickUnionBySize: 74ms
//    IntegerQuickUnionByRank: 51ms
//    IntegerQuickUnionByRankAndPathCompression: 55ms
//    IntegerQuickUnionByRankAndPathSplitting: 44ms
//    GenericQuickUnionByRankAndPathSplitting: 168ms
    @Test
    public void performanceTest() {
//        doPerformance(new IntegerQuickFind(COUNT));
//        doPerformance(new IntegerQuickUnion(COUNT));
        doPerformance(new IntegerQuickUnionBySize(COUNT));
        doPerformance(new IntegerQuickUnionByRank(COUNT));
        doPerformance(new IntegerQuickUnionByRankAndPathCompression(COUNT));
        doPerformance(new IntegerQuickUnionByRankAndPathSplitting(COUNT));
        doPerformance(new GenericQuickUnionByRankAndPathSplitting<Integer>());
    }

    private void doPerformance(UnionFind uf) {
        long start = System.currentTimeMillis();
        for (int i = 0; i < COUNT; i++) {
            uf.union((int) (Math.random() * COUNT), (int) (Math.random() * COUNT));
        }
        for (int i = 0; i < COUNT; i++) {
            uf.isConnected((int) (Math.random() * COUNT), (int) (Math.random() * COUNT));
        }
        long end = System.currentTimeMillis();
        System.out.println(uf.getClass().getSimpleName() + ": " + (end - start) + "ms");
    }

    private void doTest(UnionFind uf) {
        assertTrue(uf.isConnected(0, 1));
        assertTrue(uf.isConnected(0, 2));
        assertTrue(uf.isConnected(0, 3));
        assertTrue(uf.isConnected(0, 4));
        assertTrue(uf.isConnected(0, 5));

        assertFalse(uf.isConnected(0, 6));
        assertFalse(uf.isConnected(0, 6));
        assertTrue(uf.isConnected(6, 7));

        assertTrue(uf.isConnected(8, 9));
        assertTrue(uf.isConnected(9, 10));
        assertTrue(uf.isConnected(10, 11));
        assertFalse(uf.isConnected(6, 8));
        assertFalse(uf.isConnected(6, 11));
    }

//    @Test
//    public void quickUnion() {
//        IntegerQuickUnion uf = new IntegerQuickUnion(16);
//        initUF(uf);
//        // System.err.println(uf);
//        doTest(uf);
//    }

//    @Test
//    public void quickUnionBySize() {
//        IntegerQuickUnion uf = new IntegerQuickUnionBySize(16);
//        initUF(uf);
//        // System.err.println(uf);
//        doTest(uf);
//    }
//
//    @Test
//    public void quickUnionByRank() {
//        IntegerQuickUnion uf = new IntegerQuickUnionByRank(16);
//        initUF(uf);
//        // System.err.println(uf);
//        doTest(uf);
//    }
}