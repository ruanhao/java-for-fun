package com.hao.ds.solution.dp;

import com.google.common.primitives.Ints;

public class LongestCommonSubsequence {

    int[] seq1;
    int[] seq2;

    public LongestCommonSubsequence(int[] seq1, int[] seq2) {
        this.seq1 = seq1;
        this.seq2 = seq2;
    }

    public int lcsDP2() {
        int[][] dp = new int[seq1.length][seq2.length];
        if (seq1[0] != seq2[0]) {
            dp[0][0] = 0;
        } else {
            dp[0][0] = 1;
        }
        for (int i = 0; i < seq1.length; i++) {
            for (int j = 0; j < seq2.length; j++) {
                if (i == 0 && j == 0) continue;
                if (seq1[i] == seq2[j]) {
                    dp[i][j] = 1 + v(dp, i-1, j-1);
                } else {
                    dp[i][j] = Ints.max(
                            v(dp, i-1, j),
                            v(dp, i, j-1)
                    );
                }
            }
        }
        return dp[seq1.length - 1][seq2.length - 1];
    }

    private int v(int[][] dp, int i, int j) {
        if (i < 0) return Integer.MIN_VALUE;
        if (j < 0) return Integer.MIN_VALUE;
        return dp[i][j];
    }

    public int lcsDP() {
        int[][] dp = new int[seq1.length][seq2.length];
        if (seq1[0] != seq2[0]) {
            dp[0][0] = 0;
        } else {
            dp[0][0] = 1;
        }
        for (int i = 0; i < seq1.length; i++) {
            for (int j = 0; j < seq2.length; j++) {
                if (i == 0 && j == 0) continue;
                if (i == 0) {
                    boolean flag = false;
                    for (int k = 0; k < j; k++) {
                        if (seq1[0] == seq2[k]) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 0;
                    }
                    continue;
                }
                if (j == 0) {
                    boolean flag = false;
                    for (int k = 0; k < i; k++) {
                        if (seq1[k] == seq2[0]) {
                            flag = true;
                            break;
                        }
                    }
                    if (flag) {
                        dp[i][j] = 1;
                    } else {
                        dp[i][j] = 0;
                    }
                    continue;
                }
                if (seq1[i] == seq2[j]) {
                    dp[i][j] = 1 + dp[i-1][j-1];
                } else {
                    dp[i][j] = Ints.max(
                            // dp[i-1][j-1],
                            dp[i-1][j],
                            dp[i][j-1]
                    );
                }
            }
        }
        return dp[seq1.length - 1][seq2.length - 1];
    }

    public int lcs() {
        return lcsRecursive(seq1.length - 1, seq2.length - 1);
//        int[] candidates = new int[seq1.length * seq2.length];
//        int idx = 0;
//        for (int i  = 0; i < seq1.length; i++) {
//            for (int j = 0; j < seq2.length; j++) {
//                candidates[idx++] = lcsRecursive(i, j);
//            }
//        }
//        return Ints.max(candidates);
    }

    public int lcsRecursive(int i, int j) {
        if (i == 0 && j == 0) {
            if (seq1[0] != seq2[0]) return 0;
            return 1;
        }
        if (i == 0) {
            for (int k = 0; k < j; k++) {
                if (seq1[0] == seq2[k]) return 1;
            }
            return 0;
        }
        if (j == 0) {
            for (int k = 0; k < i; k++) {
                if (seq1[k] == seq2[0]) return 1;
            }
            return 0;
        }

        if (seq1[i] == seq2[j]) {
            return 1 + lcsRecursive(i - 1, j - 1);
        } else {
            return Ints.max(
                    lcsRecursive(i - 1, j),     // [1, 2, 3, 4] vs [1, 2, 3]
                    lcsRecursive(i, j - 1)      // [1, 2, 3]    vs [1, 2, 3, 4]
            );
        }
    }


}
