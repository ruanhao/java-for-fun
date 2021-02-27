package com.hao.ds.solution.backtracking;

import org.junit.Test;

import static org.junit.Assert.*;

public class QueenProblemTest {

    @Test
    public void count() {
        assertEquals(2, new QueenProblem(4).count());
        assertEquals(92, new QueenProblem(8).count());
        assertEquals(2, new QueenProblem(4).countNonRecursive());
        assertEquals(92, new QueenProblem(8).countNonRecursive());

        assertEquals(new QueenProblem(9).count(), new QueenProblem(9).countNonRecursive());
        assertEquals(new QueenProblem(10).count(), new QueenProblem(10).countNonRecursive());
        assertEquals(new QueenProblem(11).count(), new QueenProblem(11).countNonRecursive());
        assertEquals(new QueenProblem(12).count(), new QueenProblem(12).countNonRecursive());

//        int loop = 100;
//        long start, end;
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < loop; i++) {
//            new QueenProblem(12).countNonRecursive();
//        }
//        end = System.currentTimeMillis();
//        System.out.println("Non Recursive: " + (end - start) + "ms");
//
//        start = System.currentTimeMillis();
//        for (int i = 0; i < loop; i++) {
//            new QueenProblem(12).count();
//        }
//        end = System.currentTimeMillis();
//        System.out.println("Recursive: " + (end - start) + "ms");



    }

}