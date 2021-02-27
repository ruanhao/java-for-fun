package com.hao.ds.solution.dp;

import java.util.HashMap;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CoinChangeTest {

    @Test
    public void test() {
        CoinChange coinChange = new CoinChange(new int[]{25, 20, 5, 2});
        assertEquals(3, coinChange.coinsCountRecursion(42));
        assertEquals(1, coinChange.coinsCountRecursion(25));
        assertEquals(2, coinChange.coinsCountRecursion(45));
        assertEquals(-1, coinChange.coinsCountRecursion(1));
        assertEquals(-1, coinChange.coinsCountRecursion(3));


        assertEquals(3, coinChange.coinsCountRecursionWithMemorization(42, new HashMap<>()));
        assertEquals(1, coinChange.coinsCountRecursionWithMemorization(25, new HashMap<>()));
        assertEquals(2, coinChange.coinsCountRecursionWithMemorization(45, new HashMap<>()));
        assertEquals(-1, coinChange.coinsCountRecursionWithMemorization(1, new HashMap<>()));
        assertEquals(-1, coinChange.coinsCountRecursionWithMemorization(3, new HashMap<>()));
        assertEquals(4, coinChange.coinsCountRecursionWithMemorization(100, new HashMap<>()));


        assertEquals(3, coinChange.coinsCountDP(42));
        assertEquals(1, coinChange.coinsCountDP(25));
        assertEquals(2, coinChange.coinsCountDP(45));
        assertEquals(-1, coinChange.coinsCountDP(1));
        assertEquals(-1, coinChange.coinsCountDP(3));
        assertEquals(4, coinChange.coinsCountDP(100));
        assertEquals(7, coinChange.coinsCountDP(101));


    }
}