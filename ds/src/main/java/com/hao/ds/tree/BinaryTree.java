package com.hao.ds.tree;

public interface BinaryTree<E> {
    int height();
    int size();
    boolean isEmpty();
    void clear();
    void add(E element);
    void remove(E element);
    boolean contains(E element);
    boolean isComplete();
}
