package com.hao.ds.solution.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestCommonSubsequenceTest {

    @Test
    public void test() {
        LongestCommonSubsequence lcs =
                new LongestCommonSubsequence(
                        new int[] {1, 3, 5, 9, 10},
                        new int[] {1, 4, 9, 10});
        assertEquals(3, lcs.lcs());
        assertEquals(3, lcs.lcsDP());
        assertEquals(3, lcs.lcsDP2());

        LongestCommonSubsequence lcs2 =
                new LongestCommonSubsequence(
                        new int[] {1, 3, 2, 6, 5, 9, 7, 10, 11},
                        new int[] {1, 55, 4, 44, 9, 33, 10, 11, 22});
        assertEquals(4, lcs2.lcs());
        assertEquals(4, lcs2.lcsDP());
        assertEquals(4, lcs2.lcsDP2());

        LongestCommonSubsequence lcs3 =
                new LongestCommonSubsequence(
                        new int[] {1, 3, 2, 6, 5, 9, 7, 10, 11, 12, 13, 14},
                        new int[] {1, 55, 4, 44, 9, 33, 10, 11, 22, 12, 13, 14});
        // System.err.println("lcs3.lcsRecursive(7, 7): " + lcs3.lcsRecursive(7, 7));
        assertEquals(7, lcs3.lcs());
        assertEquals(7, lcs3.lcsDP());
        assertEquals(7, lcs3.lcsDP2());
    }
}