package com.hao.ds.solution.dp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoinChange {

    int[] coins;

    public CoinChange(int[] coins) {
        this.coins = coins;
    }

    private int min(int ... ns) {
        int m = Integer.MAX_VALUE;
        for (int n : ns) {
            m = Math.min(m, n);
        }
        return m;
    }

    public int coinsCountDP(int amount) {
        int[] dp = new int[amount + 1];

        dp[0] = 0;
        List<Integer> allCoins = new ArrayList<>();
        for (int coin : coins) {
            allCoins.add(coin);
        }

        Map<Integer, Integer> chosenCoins = new HashMap<>(); // amount -> chosenCoin
        for (int i = 1; i < dp.length; i++) {
            if (allCoins.contains(i)) {
                dp[i] = 1;
                chosenCoins.put(i, i);
                continue;
            }
            int m = Integer.MAX_VALUE;
            int chosenCoin = coins[0];
            for (int coin : coins) {
                if (i - coin < 0) continue;
                if (dp[i - coin] == -1) continue;
                if (dp[i - coin] < m) {
                    m = dp[i - coin];
                    chosenCoin = coin;
                }
                // m = Math.min(m, dp[i - coin]);
            }
            if (m == Integer.MAX_VALUE) {
                dp[i] = -1;
            } else {
                dp[i] = m + 1;
                chosenCoins.put(i, chosenCoin);
            }
        }
        if (dp[amount] != -1) {
            System.out.print("[" + amount + "] = ");
            int start = amount;
            while (start != 0) {
                System.out.print(chosenCoins.get(start) + " ");
                start -= chosenCoins.get(start);
            }
            System.out.println();
        }
        return dp[amount];
    }

    public static void main(String[] args) {
        new CoinChange(new int[]{25, 20, 5, 2}).coinsCountDP(53);
    }

    public int coinsCountRecursionWithMemorization(int amount, Map<Integer, Integer> cache) {
        if (amount < 0) {
            return -1;
        }

        if (cache.get(amount) != null) {
            return cache.get(amount);
        }

        for (int coin : coins) {
            if (coin == amount) return 1;
        }

        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            int c = coinsCountRecursionWithMemorization(amount - coin, cache);
            if (c == -1) continue;
            result = Math.min(result, c);
        }
        if (result == Integer.MAX_VALUE) return -1;
        cache.put(amount, result + 1);
        return result + 1;
    }

    public int coinsCountRecursion(int amount) {
        if (amount < 0) {
            return -1;
        }

        for (int coin : coins) {
            if (coin == amount) return 1;
        }

        int result = Integer.MAX_VALUE;
        for (int coin : coins) {
            int c = coinsCountRecursion(amount - coin);
            if (c == -1) continue;
            result = Math.min(result, c);
        }
        if (result == Integer.MAX_VALUE) return -1;
        return result + 1;
    }
}
