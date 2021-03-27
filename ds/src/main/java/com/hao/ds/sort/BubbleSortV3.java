package com.hao.ds.sort;

public class BubbleSortV3<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end >= 1; end--) {
            int lastSwapIndex = 1; // 初始值设为 1 的意义在于如果序列完全有序，则可以提前终止排序
            for (int begin = 1; begin <= end; begin++) {
                if (compare(begin - 1).gt(begin)) {
                    swap(begin - 1, begin);
                    lastSwapIndex = begin;
                }
            }
            end = lastSwapIndex;
        }
    }
}
