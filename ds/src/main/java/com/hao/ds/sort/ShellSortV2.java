package com.hao.ds.sort;

import java.util.LinkedList;
import java.util.List;

public class ShellSortV2<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        List<Integer> steps = sedgewickStepSequence();
        // System.err.println("steps: " + steps);
        for (Integer step : steps) {
            doSort(step);
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
     * 目前效率最高的步长序列
     */
    private List<Integer> sedgewickStepSequence() {
        List<Integer> stepSequence = new LinkedList<>();
        int k = 0, step = 0;
        while (true) {
            if (k % 2 == 0) {
                int pow = (int) Math.pow(2, k >> 1);
                step = 1 + 9 * (pow * pow - pow);
            } else {
                int pow1 = (int) Math.pow(2, (k - 1) >> 1);
                int pow2 = (int) Math.pow(2, (k + 1) >> 1);
                step = 1 + 8 * pow1 * pow2 - 6 * pow2;
            }
            if (step >= array.length) break;
            stepSequence.add(0, step);
            k++;
        }
        return stepSequence;
    }




}
