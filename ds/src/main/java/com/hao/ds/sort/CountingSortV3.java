package com.hao.ds.sort;

public class CountingSortV3 extends Sort<Integer> {

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
        int[] aggCounts = new int[counts.length];
        int total = 0;
        for (int i = 0; i < counts.length; i++) {
            int count = counts[i];
            total += count;
            aggCounts[i] = total;
        }
        Integer[] newArray = new Integer[array.length];
        for (Integer i : array) {
            newArray[(aggCounts[i + offset] - (counts[i + offset]--))] = i;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = newArray[i];
        }
    }


}
