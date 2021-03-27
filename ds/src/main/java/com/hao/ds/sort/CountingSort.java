package com.hao.ds.sort;

import java.util.stream.Stream;

public class CountingSort extends Sort<Integer> {

    @Override
    protected void sort() {
        Integer max = Stream.of(array).max(java.util.Comparator.naturalOrder()).orElse(Integer.MIN_VALUE);
        int[] counts = new int[max + 1];
        for (Integer i : array) {
            counts[i]++;
        }
        int arrayIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            if (count == 0) continue;
            for (int j = 0; j < count; j++) {
                array[arrayIndex++] = i;
            }
        }
    }



}
