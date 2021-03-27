package com.hao.ds.sort;

public class InsertionSortV2<E extends Comparable<E>> extends Sort<E> {
//    @Override
//    protected void sort() {
//        for (int begin = 1; begin < array.length ; begin++) {
//            int cur = begin;
//            E v = array[cur];
//            while (cur > 0 && cmp(v, array[cur - 1]) < 0) {
//                array[cur] = array[cur - 1];
//                cur--;
//            }
//            array[cur] = v;
//        }
//    }

    @Override
    protected void sort() {
        for (int i = 1; i < array.length; i++) {
            E v = array[i];
            int pos = i;
            for (int j = i; j > 0 ; j--) {
                if (compare(array[j - 1]).gt(v)) {
                    pos = j - 1;
                    array[pos + 1] = array[pos]; // 挪动
                } else {
                    break; // 说明头部已经是有序的
                }
            }
            array[pos] = v; // 插入
        }
    }
}
