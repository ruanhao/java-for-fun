package com.hao.ds.string;

import org.junit.Test;

import static org.junit.Assert.*;

public class StringMatcherTest {

    @Test
    public void testNextArray() {
        StringMatcher sm = new StringMatcher("ABCDABCE");

        int[] next = sm.buildNextArrayNoShift();
        int[] expected = {0, 0, 0, 0, 1, 2, 3, 0};

        int[] nextShifted = sm.buildNextArrayShift();
        int[] expectedShifted = {0, 0, 0, 0, 0, 1, 2, 3};

        for (int i = 0; i < expected.length; i++) {
            assertEquals("shift: " + i, expectedShifted[i], nextShifted[i]);
            assertEquals("no shift: " + i, expected[i], next[i]);
        }


        StringMatcher sm2 = new StringMatcher("AAAAB");
        nextShifted = sm2.buildNextArrayShift();
        int[] nextShiftedOpti = sm2.buildNextArrayShiftOptimized();
        expectedShifted = new int[] {0, 0, 1, 2, 3};
        int[] expectedShiftedOpt = new int[] {0, 0, 0, 0, 3};

        for (int i = 0; i < nextShiftedOpti.length; i++) {
            System.out.print(nextShiftedOpti[i] + " ");

        }

        for (int i = 0; i < expectedShifted.length; i++) {
            assertEquals(expectedShifted[i], nextShifted[i]);
            assertEquals(expectedShiftedOpt[i], nextShiftedOpti[i]);
        }


    }

    @Test
    public void testMatch() {
        StringMatcher sm = new StringMatcher("abc");
        assertEquals(1, sm.bf_match("aabc"));
        assertEquals(0, sm.bf_match("abcd"));
        assertEquals(-1, sm.bf_match("xyz"));
        assertEquals(-1, sm.bf_match("cab"));
        assertEquals(-1, sm.bf_match("bca"));
        assertEquals(-1, sm.bf_match("cbc"));

        assertEquals(1, sm.bf_optimized_match("aabc"));
        assertEquals(0, sm.bf_optimized_match("abcd"));
        assertEquals(-1, sm.bf_optimized_match("xyz"));
        assertEquals(-1, sm.bf_optimized_match("cab"));
        assertEquals(-1, sm.bf_optimized_match("bca"));
        assertEquals(-1, sm.bf_optimized_match("cbc"));

        assertEquals(1, sm.kmp_match("aabc"));
        assertEquals(0, sm.kmp_match("abcd"));
        assertEquals(-1, sm.kmp_match("xyz"));
        assertEquals(-1, sm.kmp_match("cab"));
        assertEquals(-1, sm.kmp_match("bca"));
        assertEquals(-1, sm.kmp_match("cbc"));

        assertEquals(1, sm.kmp_match_optimized("aabc"));
        assertEquals(0, sm.kmp_match_optimized("abcd"));
        assertEquals(-1, sm.kmp_match_optimized("xyz"));
        assertEquals(-1, sm.kmp_match_optimized("cab"));
        assertEquals(-1, sm.kmp_match_optimized("bca"));
        assertEquals(-1, sm.kmp_match_optimized("cbc"));

        StringMatcher sm2 = new StringMatcher("AAAAB");
        assertEquals(4, sm2.kmp_match("AAABAAAAB"));
        assertEquals(4, sm2.kmp_match_optimized("AAABAAAAB"));
    }


}