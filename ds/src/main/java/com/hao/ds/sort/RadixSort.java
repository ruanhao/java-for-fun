package com.hao.ds.sort;

public class RadixSort extends Sort<Integer> {

    private int offset;

    private int radix(int value, int bit) {
        int r = 1;
        bit--;
        while (bit-- > 0) {
            r *= 10;
        }
        return (value / r) % 10 ;
    }

    private static void test(boolean condition) {
        if (!condition) {
            throw new RuntimeException("wrong");
        }
    }
    public static void main(String[] args) {
        RadixSort radixSort = new RadixSort();

        test(radixSort.radix(123, 4) == 0);
        test(radixSort.radix(123, 5) == 0);
        test(radixSort.radix(123, 6) == 0);

        test(radixSort.radix(123, 1) == 3);
        test(radixSort.radix(123, 2) == 2);
        test(radixSort.radix(123, 3) == 1);

        test(radixSort.radix(5123, 4) == 5);
        test(radixSort.radix(61343, 5) == 6);
    }

    private void countingSort(int bit) {
        int length = 10;
        int[] counts = new int[length];
        for (Integer i : array) {
            counts[radix(i + offset, bit)]++;
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
            int radix = radix(i + offset, bit);
            newArray[(aggCounts[radix] - (counts[radix]--))] = i;
        }
        for (int i = 0; i < array.length; i++) {
            array[i] = newArray[i];
        }
    }

    @Override
    protected void sort() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        for (Integer value : array) {
            min = Math.min(min, value);
            max = Math.max(max, value);
        }
        offset = min < 0 ? -min : 0;
        int bits = String.valueOf(max + offset).length();
        for (int bit = 1; bit <= bits; bit++)
            countingSort(bit);

    }


}
