package com.hao.ds.sort;

public class HeapSort<E extends Comparable<E>> extends Sort<E> {

    int size;

    @Override
    protected void sort() {
        size = array.length;
        heapify();
        for (; size > 1;) {
            swap(0, --size);
            siftDown(0);
        }

    }

    private void heapify() {
        if (size == 0) return;
        int lastNonLeafIndex = (size / 2 - 1);
        for (int i = lastNonLeafIndex; i >= 0; i--) {
            siftDown(i);
        }
    }

    private void siftDown(int index) {
        if (size <= 1) return;
        E current = array[index];
        while (true) {
            boolean hasLeft = (2 * index + 1 < size);
            if (!hasLeft) return; // leaf
            int leftIndex = 2 * index + 1;

            boolean hasRight = (2 * index + 2 < size);
            // int rightIndex = 2 * index + 2;
            int rightIndex = leftIndex + 1;
            int childIndex = leftIndex;
            if (hasRight && compare(rightIndex).gt(leftIndex)) {
                childIndex = rightIndex;
            }

            if (compare(array[childIndex]).gt(current)) {
                swap(index, childIndex);
            }
            index = childIndex;
        }
    }
}
