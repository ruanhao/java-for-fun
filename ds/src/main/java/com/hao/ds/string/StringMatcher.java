package com.hao.ds.string;

import java.util.Arrays;
import lombok.NonNull;

public class StringMatcher {


    char[] p; // pattern char array

    public StringMatcher(@NonNull String pattern) {
        this.p = pattern.toCharArray();
    }

    public int bf_match(String text) {
        int pi = 0;
        int ti = 0;
        char[] t = text.toCharArray();
        while (ti < t.length) {
            if (p[pi] == t[ti]) {
                pi++;
                ti++;
                if (pi == p.length) return ti - pi;
            } else {
                ti = (ti - pi + 1);
                pi = 0;
            }
        }
        return -1;
    }

    public int kmp_match(String text) {
        int[] next = buildNextArrayShift();
        int pi = 0;
        int ti = 0;
        char[] t = text.toCharArray();
        while (ti < t.length) {
            if (p[pi] == t[ti]) {
                pi++;
                ti++;
                if (pi == p.length) return ti - pi;
            } else { // not match
                if (pi == 0) ti++;
                pi = next[pi];
                if (ti > t.length - p.length) return -1;
            }
        }
        return -1;
    }

    public int kmp_match_optimized(String text) {
        int[] next = buildNextArrayShiftOptimized();
        int pi = 0;
        int ti = 0;
        char[] t = text.toCharArray();
        while (ti < t.length) {
            if (p[pi] == t[ti]) {
                pi++;
                ti++;
                if (pi == p.length) return ti - pi;
            } else { // not match
                if (pi == 0) ti++;
                pi = next[pi];
                if (ti > t.length - p.length) return -1;
            }
        }
        return -1;
    }

    public int bf_optimized_match(String text) {
        int pi = 0;
        int ti = 0;
        char[] t = text.toCharArray();
        while (ti < t.length) {
            if (p[pi] == t[ti]) {
                pi++;
                ti++;
                if (pi == p.length) return ti - pi;
            } else {
                ti = (ti - pi + 1);
                pi = 0;
                if (ti > t.length - p.length) return -1;
            }
        }
        return -1;
    }

    public static void main(String[] args) {
        StringMatcher kmp = new StringMatcher("abcabdddabcabc");
        int[] next = kmp.buildNextArrayShift();
        Arrays.stream(next).forEach(i -> System.err.print(i + " "));
        // System.err.println("next: " + Arrays.asList(next));
        System.err.println();
        kmp = new StringMatcher("ababaaababaa");
        next = kmp.buildNextArrayShift();
        Arrays.stream(next).forEach(i -> System.err.print(i + " "));

    }

    // a b c a b d d d a b c a b c
    // 0 0 0 1 2 3 0 0 1 2 3 4 5 3

    // a b a b a a a b a b a a
    // 0 0 1 2 3 1 1 2 3 4 5 6

    int[] buildNextArrayShift() {
        int[] next = new int[p.length];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < p.length; i++) {
            while (j >= 1 && p[j] != p[i]) {
                j = next[j - 1];
            }
            if (p[j] == p[i]) {
                // next[i] = ++j;
                next[i+1] = ++j; // 这种做法效果是所有值向右移动一位
            }
        }
        return next;
    }

    int[] buildNextArrayShiftOptimized() {
        int[] next = new int[p.length];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < p.length; i++) {
            while (j >= 1 && p[j] != p[i]) {
                j = next[j - 1];
            }
            if (p[j] == p[i]) {
                next[i + 1] = ++j;
                if (p[i + 1] == p[next[j]]) {
                    next[i + 1] = next[j];
                }
            }
        }
        return next;
    }

    int[] buildNextArrayNoShift() {
        int[] next = new int[p.length];
        next[0] = 0;
        int j = 0;
        for (int i = 1; i < p.length; i++) {
            while (j >= 1 && p[j] != p[i]) {
                j = next[j - 1];
            }
            if (p[j] == p[i]) {
                next[i] = ++j; // 不向右位移
            }
        }
        return next;
    }

}
