package com.hao.ds.solution.dp;

import com.google.common.primitives.Ints;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class LongestIncreasingSubsequence {

    int[] numbers;

    public LongestIncreasingSubsequence(int[] numbers) {
        this.numbers = numbers;
    }

    public int lisDP() {
        int[] dp = new int[numbers.length];
        dp[0] = 1;
        for (int i = 1; i < numbers.length; i++) {
            int[] candidates  = new int[i];
            for (int j = i - 1; j >=0; j--) {
                if (numbers[j] < numbers[i]) {
                    candidates[j] = dp[j];
                }
            }
            dp[i] = Ints.max(candidates) + 1;
        }
        return Ints.max(dp);
    }

    public int lis() {
        int[] result = new int[numbers.length];
        for (int i = 0; i < numbers.length; i++) {
            result[i] = lisRecursive(i);
        }
        return Ints.max(result);
    }
    // 返回值表示：以第 n 个元素作为结尾的最长上升子序列的个数
    public int lisRecursive(int n) {
        if (n == 0) return 1;
        List<Integer> candidates = new ArrayList<>();
        for (int i = n - 1; i >= 0; i--) {
            if (numbers[i] < numbers[n]) {
                candidates.add(lisRecursive(i));
            }
        }
        if (candidates.isEmpty()) return 1;
        return candidates.stream().max(Comparator.naturalOrder()).get() + 1;
    }

}
