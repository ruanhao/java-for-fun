package com.hao.ds.tree;

import com.sun.istack.internal.Nullable;

public class AVLTree<E extends Comparable> extends BinarySearchTree<E> {

    @Override
    public Object string(Object node) {
        AVLNode<E> avlNode = (AVLNode<E>) node;
        return avlNode.element + "(" + avlNode.balanceFactor() + ")";
    }

    @Override
    protected void nodeRemoved(Node<E> node) {
        super.nodeRemoved(node);
        Node<E> g = node.parent;
        while (g != null) {
            if (((AVLNode<E>) g).isBalanced()) {
                ((AVLNode<E>) g).updateHeight();
            } else {
                balance(g);
                // do break, go on loop
            }
            g = g.parent;
        }
    }

    @Override
    protected void nodeAdded(Node<E> node) {
        super.nodeAdded(node);
        Node<E> n = node;
        Node<E> p = node;
        Node<E> g = node.parent;
        while (g != null) {
            if (((AVLNode<E>) g).isBalanced()) {
                ((AVLNode<E>) g).updateHeight();
            } else {
                balance(g, p, n); // will update height for g and p when doing balancing
                // balance(g);
                break;
            }
            n = p;
            p = g;
            g = g.parent;
        }
    }

    public void leftRotate(Node<E> node) {
        Node<E> p = node.right;
        Node<E> t1 = p.left;

        node.right = t1;
        p.left = node;
        if (node.isLeft()) {
            node.parent.left = p;
        } else if (node.isRight()) {
            node.parent.right = p;
        } else { // node is root
            root = p;
        }

        p.parent = node.parent;
        node.parent = p;
        if (t1 != null) t1.parent = node;

        ((AVLNode<E>) node).updateHeight();
        ((AVLNode<E>) p).updateHeight();
    }

    public void rightRotate(Node<E> node) {
        Node<E> p = node.left;
        Node<E> t3 = p.right;

        p.right = node;
        node.left = t3;
        if (node.isLeft()) {
            node.parent.left = p;
        } else if (node.isRight()) {
            node.parent.right = p;
        } else { // node is root
            root = p;
        }

        p.parent = node.parent;
        node.parent = p;
        if (t3 != null) t3.parent = node;

        ((AVLNode<E>) node).updateHeight();
        ((AVLNode<E>) p).updateHeight();
    }

    private void balance(Node<E> g0) {
        AVLNode<E> g = (AVLNode<E>) g0;
        AVLNode<E> p = g.higher();
        AVLNode<E> n = p.higher();
        balance(g, p, n);
    }

    private void balance(Node<E> g0, Node<E> p0, Node<E> n0) {
        AVLNode<E> g = (AVLNode<E>) g0;
        AVLNode<E> p = (AVLNode<E>) p0;
        AVLNode<E> n = (AVLNode<E>) n0;
        if (g.left != null && n == g.left.left) {
            rightRotate(g);
        } else if (g.right != null && n == g.right.right) {
            leftRotate(g);
        } else if (g.right != null && n == g.right.left) {
            rightRotate(p);
            leftRotate(g);
        } else if (g.left != null && n == g.left.right) {
            leftRotate(p);
            rightRotate(g);
        }
    }

    private void balance0(Node<E> g0, Node<E> p0, Node<E> n0) {
        AVLNode<E> g = (AVLNode<E>) g0;
        AVLNode<E> p = (AVLNode<E>) p0;
        AVLNode<E> n = (AVLNode<E>) n0;
        if (g.left != null && n == g.left.left) {
            balance(n.left, n, n.right, p, p.right, g, g.right, g);
        } else if (g.right != null && n == g.right.right) {
            balance(g.left, g, p.left, p, n.left, n, n.right, g);
        } else if (g.right != null && n == g.right.left) {
            balance(g.left, g, n.left, n, n.right, p, p.right, g);
        } else if (g.left != null && n == g.left.right) {
            balance(p.left, p, n.left, n, n.right, g, g.right, g);
        }
    }

    private void balance(@Nullable Node<E> a, Node<E> b, @Nullable Node<E> c,
                         Node<E> d,
                         @Nullable Node<E> e, Node<E> f, @Nullable Node<E> g,
                         Node<E> top) {

        if (top.isLeft()) {
            top.parent.left = d;
        } else if (top.isRight()) {
            top.parent.right = d;
        } else {
            root = d;
        }

        b.right = c;
        f.left = e;
        d.left = b;
        d.right = f;

        if (c != null) c.parent = b;
        if (e != null) e.parent = f;

        d.parent = top.parent;
        b.parent = d;
        f.parent = d;

        ((AVLNode<E>) b).updateHeight();
        ((AVLNode<E>) f).updateHeight();
        ((AVLNode<E>) d).updateHeight();
    }

    @Override
    protected BinarySearchTree.Node<E> createNode(E element, Node<E> parent) {
        return new AVLNode(element, parent);
    }

    public static class AVLNode<E> extends Node<E> {
        int height = 1;
        public AVLNode(E element, Node<E> parent) {
            super(element, parent);
        }

        public boolean isBalanced() {
            return balanceFactor() <= 1;
        }

        public AVLNode<E> higher() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>)left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>)right).height;
            if(leftHeight > rightHeight) return (AVLNode<E>) left;
            if(rightHeight > leftHeight) return (AVLNode<E>) right;
            return isLeft() ? (AVLNode<E>) left : (AVLNode<E>) right;
        }

        public int balanceFactor() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            return Math.abs(leftHeight - rightHeight);
        }

        public void updateHeight() {
            int leftHeight = left == null ? 0 : ((AVLNode<E>) left).height;
            int rightHeight = right == null ? 0 : ((AVLNode<E>) right).height;
            height = 1 + Math.max(leftHeight, rightHeight);
        }
    }
}
