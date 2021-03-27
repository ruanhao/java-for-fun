package com.hao.ds.sort;

import com.google.common.base.Preconditions;

public class InsertionSortV3<E extends Comparable<E>> extends Sort<E> {
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

    // 查找 v 在有序部分中的插入位置，这个位置其实是"第一个大于 v 的元素"的索引
    private int search(E[] sortedArray, E v) {
        int begin = 0;
        int end = begin + sortedArray.length;
        while (begin != end) {
            int middle = (begin + end) / 2;
            if (compare(v).ge(sortedArray[middle])) {
                begin = middle + 1;
            } else {
                end = middle;
            }
        }
        return begin;
    }

    // 查找 v 在有序部分中的插入位置，这个位置其实是"第一个大于 v 的元素"的索引
    // 已排好序的数据范围是 [0, length)
    private int search(int length, E v) {
        int begin = 0;
        int end = begin + length;
        while (begin != end) {
            int middle = (begin + end) / 2;
            if (compare(v).ge(array[middle])) {
                begin = middle + 1;
            } else {
                end = middle;
            }
        }
        return begin;
    }

    // length 既是左边已排好序的序列长度，也是右边未排序序列的第一个元素索引
    // 查找 v 在有序部分中的插入位置，这个位置其实是"第一个大于 v 的元素"的索引
    // 已排好序的数据范围是 [0, length)
    private int search(int length) {
        E v = array[length];
        int begin = 0;
        int end = begin + length;
        while (begin != end) {
            int middle = (begin + end) / 2;
            if (compare(v).ge(array[middle])) {
                begin = middle + 1;
            } else {
                end = middle;
            }
        }
        return begin;
    }

    public static void main(String[] args) {
        InsertionSortV3<Integer> s = new InsertionSortV3<>();
        int pos2 = s.search(new Integer[]{3, 4, 5, 6}, 2); // 0
        int pos3 = s.search(new Integer[]{3, 4, 5, 6}, 3); // 1
        int pos4 = s.search(new Integer[]{3, 4, 5, 6}, 4); // 2
        int pos5 = s.search(new Integer[]{3, 4, 5, 6}, 5); // 3
        int pos6 = s.search(new Integer[]{3, 4, 5, 6}, 6); // 4
        int pos7 = s.search(new Integer[]{3, 4, 5, 6}, 7); // 4

        System.out.println("pos2: " + pos2);
        System.out.println("pos3: " + pos3);
        System.out.println("pos4: " + pos4);
        System.out.println("pos5: " + pos5);
        System.out.println("pos6: " + pos6);
        System.out.println("pos7: " + pos7);

        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 2) == 0);
        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 3) == 1);
        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 4) == 2);
        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 5) == 3);
        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 6) == 4);
        Preconditions.checkState(s.search(new Integer[]{3, 4, 5, 6}, 7) == 4);

        s.array = new Integer[]{3, 4, 5, 6};
        Preconditions.checkState(s.search(1, 2) == 0);
        Preconditions.checkState(s.search(2, 3) == 1);
        Preconditions.checkState(s.search(3, 4) == 2);
        Preconditions.checkState(s.search(4, 5) == 3);
        Preconditions.checkState(s.search(4, 6) == 4);
        Preconditions.checkState(s.search(4, 7) == 4);



    }

    // 将 sourceIndex 位置的元素插入到 pos 位置
    private void insert(int sourceIndex, int pos) {
        E v = array[sourceIndex];
        for (int j = sourceIndex; j > pos; j--) { // 整体向右移动一个位置
            array[j] = array[j - 1];
        }
        array[pos] = v;
    }

    @Override
    protected void sort() {
        for (int i = 1; i < array.length; i++) {
            insert(i, search(i));
        }
    }
}
