package com.hao.ds.solution.dp;

import org.junit.Test;

import static org.junit.Assert.*;

public class LargestSumOfContinuousSubSequenceTest {


    @Test
    public void test() {
        LargestSumOfContinuousSubSequence seq =
                new LargestSumOfContinuousSubSequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});

        assertEquals(6, seq.maximumSubArrayRecursive());
        assertEquals(6, seq.maximumSubArrayDP());
    }
}