package com.hao.ds.solution.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LongestCommonSubstringTest {

    @Test
    public void test() {
        LongestCommonSubstring lcs = new LongestCommonSubstring(
                new int[] {3, 4, 5, 6, 7, 8, 9, 33, 55},
                new int[] {1, 5, 6, 7, 8, 9, 10, 11}
        );
        assertEquals(5, lcs.lcs());
        assertEquals(5, lcs.lcsDP());
    }

}