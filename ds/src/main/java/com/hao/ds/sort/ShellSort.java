package com.hao.ds.sort;

import java.util.ArrayList;
import java.util.List;

public class ShellSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        List<Integer> steps = shellStepSequence();
        for (Integer step : steps) {
            doSort(step);
        }
    }
    private void doSort0(int step) {
        for (int col = 0; col < step; col++) {
            for (int begin = col + step; begin < array.length; begin += step) {
                int cur = begin;
                while (cur > col && cmp(cur, cur - step) < 0) {
                    swap(cur, cur - step);
                    cur -= step;
                }
            }
        }
    }

    private void doSort(int step) {
        // 基本插入排序实现（交换法）
        for (int col = 0; col < step; col++) {
            for (int i = col + step; i < array.length; i += step) {
                for (int j = i; j > col; j -= step) {
                    if (compare(j - step).gt(j)) {
                        swap(j - step, j);
                    } else {
                        break;
                    }

                }
            }
        }
    }

    /**
     * 希尔算法默认使用的步长序列
     */
    public List<Integer> shellStepSequence(){
     // return Arrays.asList(500, 250, 100, 50, 10, 1);
        List<Integer> stepSequence = new ArrayList<>();
        int step = 1;
        while (step < array.length) {
            stepSequence.add(0, step);
            step = step << 1;
        }
        return stepSequence;
    }



}
