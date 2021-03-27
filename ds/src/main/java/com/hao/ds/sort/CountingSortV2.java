package com.hao.ds.sort;

public class CountingSortV2 extends Sort<Integer> {

    @Override
    protected void sort() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Integer value : array) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        int offset = min < 0 ? -min : 0;
        int length = (max - min + 2);
        int[] counts = new int[length];
        for (Integer i : array) {
            counts[i + offset]++;
        }
        int arrayIndex = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            if (count == 0) continue;
            for (int j = 0; j < count; j++) {
                array[arrayIndex++] = i - offset;
            }
        }
    }


}
