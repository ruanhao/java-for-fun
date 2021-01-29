package com.hao.ds.tree;


import lombok.NonNull;

public class RBTree<E extends Comparable> extends BinarySearchTree<E> {

    public static enum Color {RED, BLACK}

    @Override
    public Object string(Object node) {
        RBNode<E> rbNode = (RBNode<E>) node;
        String color = rbNode.color == Color.RED ? "R" : "B";
        return rbNode.element + "(" + color + ")";
    }

    @Override
    protected void nodeRemoved(@NonNull Node<E> node) {
        if (isRed(node)) return;

        if (node.degree() == 1) {
            Node<E> child = (node.left == null ? node.right : node.left);
            black(child);
            return;
        }

        // leaf case
        Node<E> parent = node.parent;
        if (parent == null) return;

        boolean isLeft = parent.left == null || node.isLeft();
        // must get sibling like this way, because node.parent.left/right is set to null before this method
        Node<E> sibling = isLeft ? parent.right : parent.left;
        if (isLeft) { // sibling is at right side
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                leftRotate(parent);
                sibling = parent.right; // update sibling
            }
            // sibling is black afterwards
            if (isBlack(sibling.left) && isBlack(sibling.right)) { // can not borrow
                black(parent);
                red(sibling);
                if (isBlack(parent)) {
                    nodeRemoved(parent);
                }
            } else { // can borrow red
                if (isBlack(sibling.right)) {
                    rightRotate(sibling);
                    sibling = parent.right; // sibling is updated
                }
                paint(sibling, color(parent));
                black(sibling.right);
                black(parent);
                leftRotate(parent);
            }
        } else {
            if (isRed(sibling)) {
                black(sibling);
                red(parent);
                rightRotate(parent);
                sibling = parent.left; // update sibling
            }
            // sibling is black afterwards
            if (isBlack(sibling.left) && isBlack(sibling.right)) { // can not borrow
                black(parent);
                red(sibling);
                if (isBlack(parent)) {
                    nodeRemoved(parent);
                }
            } else { // can borrow red
                if (isBlack(sibling.left)) {
                    leftRotate(sibling);
                    sibling = parent.left; // sibling is updated
                }
                paint(sibling, color(parent));
                black(sibling.left);
                black(parent);
                rightRotate(parent);
            }
        }

    }

    @Override
    protected void nodeAdded(Node<E> node) {
        Node<E> parent = node.parent;
        if (parent == null) {
            black(node);
            return;
        }

        // e, j, k, l
        if (color(parent) == Color.BLACK) return;

        // f, g, h, i
        if (color(node.uncle()) == Color.BLACK) {
            Node<E> g = node.parent.parent;
            Node<E> p = node.parent;
            Node<E> n = node;
            if (g.left != null && n == g.left.left) {
                red(g);
                black(p);
                rightRotate(g);
            } else if (g.right != null && n == g.right.right) {
                red(g);
                black(p);
                leftRotate(g);
            } else if (g.right != null && n == g.right.left) {
                red(g);
                black(n);
                rightRotate(p);
                leftRotate(g);
            } else if (g.left != null && n == g.left.right) {
                red(g);
                black(n);
                leftRotate(p);
                rightRotate(g);
            }

            return;
        }

        // a, b, c, d
        black(node.parent);
        black(node.uncle());
        nodeAdded(red(node.parent.parent));

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

    }


    private Node<E> paint(Node<E> node, Color color) {
        if (node == null) return null;
        ((RBNode<E>) node).color = color;
        return node;
    }
    private Node<E> red(Node<E> node) {
        return paint(node, Color.RED);
    }
    private Node<E> black(Node<E> node) {
        return paint(node, Color.BLACK);
    }
    private Color color(Node<E> node) {
        if (node == null) return Color.BLACK;
        return ((RBNode<E>) node).color;
    }
    private boolean isBlack(Node<E> node) {
        return color(node) == Color.BLACK;
    }
    private boolean isRed(Node<E> node) {
        return color(node) == Color.RED;
    }

    @Override
    protected Node<E> createNode(E element, Node<E> parent) {
        return new RBNode(element, parent);
    }

    public static class RBNode<E> extends Node<E> {

        Color color = Color.RED;
        public RBNode(E element, Node<E> parent) {
            super(element, parent);
        }

    }
}
