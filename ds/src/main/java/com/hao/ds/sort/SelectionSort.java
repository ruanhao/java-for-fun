package com.hao.ds.sort;

public class SelectionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int end = array.length - 1; end >= 1; end--) {
            int maxIndex = 0;
            for (int begin = 0; begin <= end; begin++) {
                if (compare(begin).ge(maxIndex)) maxIndex = begin;
            }
            swap(end, maxIndex);
        }
    }
}
