package com.hao.ds.solution.dp;

import com.google.common.primitives.Ints;

public class LargestSumOfContinuousSubSequence {

    int[] numbers;

    public LargestSumOfContinuousSubSequence(int[] numbers) {
        this.numbers = numbers;
    }

    public int maximumSubArrayDP() {
        int[] dp = new int[numbers.length];
        dp[0] = numbers[0];
        for (int i = 1; i < numbers.length; i++) {
            if (dp[i - 1] <= 0) {
                dp[i] = numbers[i];
            } else {
                dp[i] = dp[i - 1] + numbers[i];
            }
        }
        return Ints.max(dp);
    }

    public int maximumSubArrayRecursive() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < numbers.length; i++) {
            max = Math.max(max, maximumSubArrayRecursive(i));
        }
        return max;
    }

    // 该函数的返回值表示：以第 n 个元素作为结尾的最大连续子序列和
    private int maximumSubArrayRecursive(int n) { // n = [0, numbers.len)
        if (n == 0) return numbers[0];
        if (maximumSubArrayRecursive(n - 1) <= 0) {
            return numbers[n];
        } else {
            return maximumSubArrayRecursive(n - 1) + numbers[n];
        }
    }

    public static void main(String[] args) {
        LargestSumOfContinuousSubSequence seq = new LargestSumOfContinuousSubSequence(new int[]{-2, 1, -3, 4, -1, 2, 1, -5, 4});
        System.err.println("seq.maximumSubArrayRecursive(): " + seq.maximumSubArrayRecursive());


    }
}
