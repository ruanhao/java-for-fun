package com.hao.ds.solution.dp;

import com.google.common.primitives.Ints;

public class LongestCommonSubstring {

    int[] seq1;
    int[] seq2;

    public LongestCommonSubstring(int[] seq1, int[] seq2) {
        this.seq1 = seq1;
        this.seq2 = seq2;
    }

    public int lcs() {
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < seq1.length; i++) {
            for (int j = 0; j < seq2.length; j++) {
                max = Ints.max(lcs(i, j), max);
            }
        }
        return max;
    }

    public int lcsDP() {
        int[][] dp = new int[seq1.length][seq2.length];
        if (seq1[0] == seq2[0]) {
            dp[0][0] = 1;
        } else {
            dp[0][0] = 0;
        }
        int max = Integer.MIN_VALUE;
        for (int i = 0; i < seq1.length; i++) {
            for (int j = 0; j < seq2.length; j++) {
                if (i == 0) {
                    if (seq1[0] == seq2[j]) {
                        dp[i][j] = 1;
                        max = Ints.max(max, 1);
                    }
                    continue;
                }
                if (j == 0) {
                    if (seq1[i] == seq2[0]) {
                        dp[i][j] = 1;
                        max = Ints.max(max, 1);
                    }
                    continue;
                }
                if (seq1[i] == seq2[j]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                    max = Ints.max(max, dp[i][j]);
                }
            }
        }
        return max;
    }

    public int lcs(int i, int j) {
        // if (i < 0 || j < 0) return Integer.MIN_VALUE;
        if (i == 0 && j == 0) {
            if (seq1[0] == seq2[0]) return 1;
            return 0;
        }
        if (i == 0) {
            if (seq1[0] == seq2[j]) return 1;
            return 0;
        }
        if (j == 0) {
            if (seq1[i] == seq2[0]) return 1;
            return 0;
        }
        if (seq1[i] == seq2[j]) {
            return 1 + lcs(i - 1, j - 1);
        } else {
            return 0;
        }
    }

    public static void main(String[] args) {
        LongestCommonSubstring lcs = new LongestCommonSubstring(
          new int[] {3, 4, 5, 6, 7, 8, 9, 33, 55},
          new int[] {1, 5, 6, 7, 8, 9, 10, 11}
        );
        System.err.println(lcs.lcs());
    }

}
