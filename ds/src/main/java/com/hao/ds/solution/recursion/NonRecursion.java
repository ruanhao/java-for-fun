package com.hao.ds.solution.recursion;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class NonRecursion {


    public static long fib(int n) {
        System.out.println("calculating " + n);
        if (n <= 2) return n;
        return fib(n - 2) + fib(n - 1);
    }

    public static long fibNonRecursive(int N) {
        Stack<Frame> stack = new Stack();
        stack.push(new Frame(N));
        Map<Integer, Long> cache = new HashMap<>();
        cache.put(1, (long) 1);
        cache.put(2, (long) 2);
        while (!stack.isEmpty()) {
            Frame frame = stack.pop();
            int n = frame.n;
            Long r1 = cache.get(n - 1);
            Long r2 = cache.get(n - 2);
            if (r1 != null && r2 != null) {
                cache.put(n, r1 + r2);
            } else {
                stack.push(frame);
                if (r1 == null) {
                    stack.push(new Frame(n - 1));
                }
                if (r2 == null) {
                    stack.push(new Frame(n - 2));
                }
            }
        }
        return cache.get(N - 1) + cache.get(N - 2);
    }


    public static void main(String[] args) {
        long result = fib(10);
        System.err.println("result: " + result);
        long r2 = fibNonRecursive(10);
        System.err.println("r2: " + r2);

    }

    public static class Frame {
        int n;

        public Frame(int n) {
            this.n = n;
        }
    }
}
