package com.hao.ds.tree;

import com.hao.ds.utils.printer.BinaryTreeInfo;
import java.util.ArrayList;
import java.util.List;
import lombok.NonNull;

public class BinaryHeap<E extends Comparable> implements Heap<E>, BinaryTreeInfo {

    List<E> elements;

    boolean isBigHeap = true;

    public BinaryHeap(boolean isBigHeap) {
        this();
        this.isBigHeap = isBigHeap;
    }

    public BinaryHeap() {
        this.elements = new ArrayList<>();
    }

    public BinaryHeap(@NonNull List<E> elements) {
        this();
        elements.forEach(this.elements::add);
        heapify();
    }

    public BinaryHeap(@NonNull List<E> elements, boolean isBigHeap) {
        this();
        elements.forEach(this.elements::add);
        this.isBigHeap = isBigHeap;
        heapify();
    }

    private void heapify0() {
        if (isEmpty()) return;
        for (int i = 1; i < size(); i++) {
            siftUp(i);
        }
    }

    private void heapify() {
        if (isEmpty()) return;
        int lastNonLeafIndex = (size() / 2 - 1);
        for (int i = lastNonLeafIndex; i >= 0; i--) {
            siftDown(i);
        }
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public boolean isEmpty() {
        return elements.isEmpty();
    }

    @Override
    public void clear() {
        elements = new ArrayList<>();
    }

    @Override
    public void add(E element) {
        elements.add(element);
        siftUp(size() - 1);
    }

    private void siftUp(int index) {
        if (index == 0) return;

        while (true) {
            int parentIndex = (index - 1) / 2;
            if (parentIndex < 0) return;

            E current = elements.get(index);
            E parent = elements.get(parentIndex);

            if (compare(current, parent) < 1) return;
            elements.set(parentIndex, current);
            elements.set(index, parent);
            index = parentIndex;
        }
    }

    @Override
    public E get() {
        if (isEmpty()) return null;
        return elements.get(0);
    }

    @Override
    public E remove() {
        if (isEmpty()) return null;
        E root = elements.get(0);
        elements.set(0, elements.get(size() - 1));
        elements.remove(size() - 1);
        siftDown(0);
        return root;
    }

    private void siftDown(int index) {
        if (size() <= 1) return;
        E current = elements.get(index);
        while (true) {
            boolean hasLeft = (2 * index + 1 < size());
            if (!hasLeft) return; // leaf
            int leftIndex = 2 * index + 1;

            boolean hasRight = (2 * index + 2 < size());
            // int rightIndex = 2 * index + 2;
            int rightIndex = leftIndex + 1;
            int childIndex = leftIndex;
            if (hasRight && compare(elements.get(rightIndex), elements.get(leftIndex)) > 0) {
                childIndex = rightIndex;
            }

            if (compare(elements.get(childIndex), current) > 0) {
                elements.set(index, elements.get(childIndex));
                elements.set(childIndex, current);
            }
            index = childIndex;
        }


    }

    @Override
    public E replace(E element) {
        if (isEmpty()) {
            elements.add(element);
            return element;
        }
        E root = elements.get(0);
        elements.set(0, element);
        siftDown(0);
        return root;
    }

    int compare(E e1, E e2) {
        if (isBigHeap) return e1.compareTo(e2);
        return e2.compareTo(e1);
    }

    @Override
    public Object root() {
        return 0;
    }

    @Override
    public Object left(Object parentIndex) {
        int index = 2 * ((Integer) parentIndex) + 1;
        if (index < elements.size()) return index;
        return null;
    }

    @Override
    public Object right(Object parentIndex) {
        int index = 2 * ((Integer) parentIndex) + 2;
        if (index < elements.size()) return index;
        return null;
    }

    @Override
    public Object string(Object index) {
        return elements.get((Integer) index);
    }
}
