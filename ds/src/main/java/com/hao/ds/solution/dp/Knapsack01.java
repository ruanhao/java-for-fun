package com.hao.ds.solution.dp;

import com.google.common.primitives.Ints;
import com.google.common.primitives.Longs;
import java.util.ArrayList;
import java.util.List;
import lombok.*;

public class Knapsack01 {
    List<Item> items;
    int total;

    public Knapsack01(int[] values, int[] weights, int total) {
        assert values.length == weights.length;
        items = new ArrayList<>();
        for (int i = 0; i < values.length; i++) {
            items.add(Item.builder()
                    .value(values[i])
                    .weight(weights[i])
                    .build());
        }
        this.total = total;
    }

    public static void main(String[] args) {
        Knapsack01 backpack = new Knapsack01(
                new int[] {6, 3, 5, 4, 6},
                new int[] {2, 2, 6, 5, 4},
               10
        );

        System.err.println("backpack.maxValueDP(): " + backpack.maxValueDP());
        System.err.println("backpack.maxValue(): " + backpack.maxValue());
        System.err.println("backpack.maxValueMustFull(): " + backpack.maxValueExactly());
    }

    public int maxValueExactly() {
        return maxValueExactly(items, total);

    }
    public int maxValueExactly(List<Item> items, long total) {
        if (total == 0) return 0;
        if (total < 0) return -1;
        if (items.isEmpty()) {
            return -1;
        }

        int[] candidates = new int[items.size()];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            int c = maxValueExactly(newItems(items, i), total - item.weight);
            if (c == -1) {
                candidates[i] = -1;
            } else {
                candidates[i] = item.value + c;
            }
                }
        int max = Ints.max(candidates);
        return max == -1 ? -1 : max;
    }

    public int maxValueDP() {
        return maxValueDP(items.size(), total);
    }
    public int maxValueDP(int n, int total) {
        int[][] dp = new int[n + 1][total + 1];
        for (int t = 0; t <= total; t++) {
            for (int i = 1; i <= n; i++) {
                if (t == 0) {
                    dp[i][t] = 0;
                    continue;
                }
                Item item = items.get(i - 1);
                if (t - item.weight >= 0 && (i - 1) >= 0) {
                    dp[i][t] = Ints.max(
                            item.value + dp[i-1][t - item.weight], // choose last item
                            dp[i - 1][t] // don't choose last item
                    );
                }
            }
        }
        return dp[n][total];
    }

    public int maxValueDPExactly() {
        return maxValueDPExactly(items.size(), total);
    }
    public int maxValueDPExactly(int n, int total) {
        int[][] dp = new int[n + 1][total + 1];
        for (int t = 0; t <= total; t++) {
            for (int i = 1; i <= n; i++) {
                if (t == 0) {
                    dp[i][t] = 0;
                    continue;
                }
                Item item = items.get(i - 1);
                if (i == 1) {
                    dp[i][t] = (item.weight == t) ? item.value : -1;
                    continue;
                }
                if (t - item.weight >= 0 && (i - 1) >= 0) {
                    dp[i][t] = Ints.max(
                            dp[i-1][t - item.weight] == -1 ? -1 : item.value + dp[i-1][t - item.weight], // choose last item
                            dp[i - 1][t] // don't choose last item
                    );
                }
            }
        }
        return dp[n][total];
    }

    public long maxValue() {
       return maxValue(items, total);
    }
    public long maxValue(List<Item> items, long total) {
        if (total <= 0) return 0;
        if (items.isEmpty()) return 0;
        long[] candidates = new long[items.size()];
        for (int i = 0; i < items.size(); i++) {
            Item item = items.get(i);
            candidates[i] = item.value + maxValue(newItems(items, i), total - item.value);
        }
        return Longs.max(candidates);
    }

    private List<Item> newItems(List<Item> originItems, int indexOfItemToBeRemoved) {
        assert indexOfItemToBeRemoved < originItems.size();
        List<Item> result = new ArrayList<>();
        for (int i = 0; i < originItems.size(); i++) {
            if (i != indexOfItemToBeRemoved) result.add(originItems.get(i));
        }
        return result;
    }

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    @Builder
    @ToString
    private static class Item {
        int weight;
        int value;
    }

}
