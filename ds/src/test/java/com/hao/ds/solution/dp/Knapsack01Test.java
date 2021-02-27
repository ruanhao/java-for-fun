package com.hao.ds.solution.dp;

import org.junit.Assert;
import org.junit.Test;

public class Knapsack01Test {

    @Test
    public void test() {
        Knapsack01 backpack = new Knapsack01(
                new int[] {6, 3, 5, 4, 6},
                new int[] {2, 2, 6, 5, 4},
                10
        );

        Assert.assertEquals(15, backpack.maxValueDP());
        Assert.assertEquals(15, backpack.maxValue());
        Assert.assertEquals(14, backpack.maxValueExactly());
        Assert.assertEquals(14, backpack.maxValueDPExactly());

        Knapsack01 backpack2 = new Knapsack01(
                new int[] {6, 150, 5, 4, 6},
                new int[] {6, 2,   6, 5, 4},
                10
        );
        Assert.assertEquals(12, backpack2.maxValueExactly());
        Assert.assertEquals(12, backpack2.maxValueDPExactly());
    }

}