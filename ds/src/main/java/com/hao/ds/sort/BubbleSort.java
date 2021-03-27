package com.hao.ds.sort;

public class BubbleSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end >= 1; end--) {
            for (int begin = 1; begin <= end; begin++) {
                // E left = array[begin-1];
                // E right = array[begin];
                if (compare(begin-1).gt(begin)) {
                    swap(begin-1, begin);
//                    array[begin-1] = right;
//                    array[begin] = left;
                }
            }
        }
    }
}
