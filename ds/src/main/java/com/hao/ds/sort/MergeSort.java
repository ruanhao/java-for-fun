package com.hao.ds.sort;

public class MergeSort<E extends Comparable<E>> extends Sort<E> {

    private E[] leftBackup; // 共用的中间存储区域

    // 对 [begin, end) 范围内的元素进行排序
    private void sort(int begin, int end) {
        int numberOfElements = end - begin;
        if (numberOfElements < 2) return;

        int mid = (begin + end) / 2;
        sort(begin, mid);
        sort(mid, end);
        merge(begin, mid, end);
    }

    // 对 [begin, mid) 和 [mid, end) 两部分数据进行归并
    private void merge(int begin, int mid, int end) {
        int li = 0, le = mid - begin; // 左边数组(leftBackup)的起始索引和结束索引(不包含)
        int ri = mid, re = end; // 右边数组的起始索引和结束索引(不包含)
        int ai = begin; // 最终数据存放的起始索引

        for (int i = 0; i < le; i++) {
            leftBackup[i] = array[begin + i]; // 对左边元素进行备份
        }

        while (li < le && ri < re) {
            if (compare(leftBackup[li]).le(array[ri])) {
                array[ai++] = leftBackup[li++];
            } else {
                array[ai++] = array[ri++];
            }
        }

        while (li < le) {
            array[ai++] = leftBackup[li++];
        }

    }

    @Override
    protected void sort() {
        leftBackup = (E[]) new Comparable[array.length / 2];
        sort(0, array.length);
    }


}
