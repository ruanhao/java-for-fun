package com.hao.ds.sort;

public class InsertionSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        for (int i = 1; i < array.length; i++) {
            for (int j = i; j > 0 ; j--) {
                if (compare(j-1).gt(j)) {
                    swap(j-1, j);
                } else {
                    break; // 说明头部已经是有序的
                }

            }
        }
    }
}
