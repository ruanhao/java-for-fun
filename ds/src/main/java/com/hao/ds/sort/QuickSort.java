package com.hao.ds.sort;

public class QuickSort<E extends Comparable<E>> extends Sort<E> {

    @Override
    protected void sort() {
        doSort(0, array.length);
    }

    // [begin, end)
    private void doSort(int begin, int end) {
        if ((end - begin) < 2) return;
        int pivotIndex = quickSort(begin, end);
        doSort(begin, pivotIndex);
        doSort(pivotIndex + 1, end);
    }

    /**
     * search through [begin, end)
     *
     * @return the index where the pivot should be at
     */
    private int quickSort(int begin, int end) {
        end--; // 先减一，不然会越界
        E pivot = array[begin];
        while (begin != end) {
            // 右到左
            while (begin != end) {
                if (compare(array[end]).lt(pivot)) {
                    array[begin++] = array[end];
                    break;
                } else {
                    end--;
                }
            }

            // 左到右
            while (begin != end) {
                if (compare(begin).gt(pivot)) {
                    array[end--] = array[begin];
                    break;
                } else {
                    begin++;
                }
            }
        }
        array[begin] = pivot;
        return begin;
    }
}
