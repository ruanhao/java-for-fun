package com.hao.ds.solution.backtracking;

import java.util.Stack;
import lombok.AllArgsConstructor;

public class QueenProblem {

    int counts = 0;
    int n;

    // index: row
    // value: the column where to place queen
    int[] queens;

    public QueenProblem(int n) {
        this.n = n;
        queens = new int[n];
    }




    private void print() {
        for (int r = 0; r < queens.length; r++) {
            for (int c = 0; c < this.n; c++) {
                if (c == queens[r]) {
                    System.out.print(" * ");
                } else {
                    System.out.print(" 0 ");
                }
            }
            System.out.println();
        }
        System.out.println("=============================");
    }

    private void placeQueenAtRow(int r) {
        if (r == n) {
            // successfully placed all n queens
            counts++;
            // print();
            return;
        }
        // iterate over col
        for (int c = 0; c < n; c++) {
            if (isValid(r, c)) {
                queens[r] = c;
                placeQueenAtRow(r + 1);
            }
        }
    }

    private boolean placeQueueAt(int r, int c) {
        if (isValid(r, c)) {
            queens[r] = c;
            return true;
        }
        return false;
    }

    @AllArgsConstructor
    private class Coordinate {
        int row;
        int col;
    }

    public int countNonRecursive() {
        counts = 0;
        Stack<Coordinate> stack = new Stack();
        stack.push(new Coordinate(0, 0));
        while (!stack.isEmpty()) {
            Coordinate coordinate = stack.pop();
            int row = coordinate.row;
            int col = coordinate.col;
            if (isValid(row, col)) {
                queens[row] = col;
                if (row == n - 1) {
                    counts++;
                } else {
                    if (col + 1 < n) {
                        stack.push(new Coordinate(row, col + 1));
                    }
                    stack.push(new Coordinate(row + 1, 0));
                }
            } else {
                if (col + 1 < n) {
                    stack.push(new Coordinate(row, col + 1));
                }
            }
        }
        return counts;
    }

    private boolean isValid(int r, int c) {
        for (int row = 0; row < r; row++) {
            if (queens[row] == c) return false;
            if (r - row == Math.abs(c - queens[row])) return false;
        }
        return true;
    }

    public int count() {
        counts = 0;
        placeQueenAtRow(0);
        return counts;
    }
}
