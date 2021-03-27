package com.hao.ds.sort;

public class BubbleSortV2<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end >= 1; end--) {
            boolean sorted = true;
            for (int begin = 1; begin <= end; begin++) {
                if (compare(begin - 1).gt(begin)) {
                    swap(begin - 1, begin);
                    sorted = false;
                }
            }
            if (sorted) {
                return; // 数组已经有序，提前结束
            }
        }
    }
}
