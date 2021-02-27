package com.hao.ds.solution.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestIncreasingSubsequenceTest {

    @Test
    public void test() {
        LongestIncreasingSubsequence lis =
                new LongestIncreasingSubsequence(new int[] {10, 2, 2, 5, 1, 7, 101, 18});
        assertEquals(4, lis.lis());
        assertEquals(4, lis.lisDP());
    }

}