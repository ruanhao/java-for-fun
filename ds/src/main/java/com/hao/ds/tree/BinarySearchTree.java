package com.hao.ds.tree;

import com.hao.ds.utils.printer.BinaryTreeInfo;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Stack;
import java.util.function.Consumer;
import lombok.NonNull;

public class BinarySearchTree<E extends Comparable> implements BinaryTree<E>, BinaryTreeInfo {

    private static final int RIGHT = 1;
    private static final int LEFT = -1;

    int size;
    Node<E> root;

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    protected void nodeRemoved(Node<E> node) {

    }

    public void remove(Node<E> node) {
        if (node == null) return;
        if (node.degree() == 0) {
            if (node.parent == null) {
                root = null;
                size--;
                nodeRemoved(node);
                return;
            }
            if (node.isLeft()) {
                node.parent.left = null;
                size--;
                nodeRemoved(node);
            } else {
                node.parent.right = null;
                size--;
                nodeRemoved(node);
            }
        } else if (node.degree() == 1) {
            Node<E> child = node.left != null ? node.left : node.right;
            if (node.parent == null) {
                child.parent = null;
                root = child;
                size--;
                nodeRemoved(node);
                return;
            }
            child.parent = node.parent;
            if (node.isLeft()) {
                node.parent.left = child;
                size--;
                nodeRemoved(node);
            } else {
                node.parent.right = child;
                size--;
                nodeRemoved(node);
            }
        } else { // degree == 2
            Node<E> predecessor = node.predecessor();
            if (predecessor != null) {
                node.element = predecessor.element;
                remove(predecessor);
            } else {
                Node<E> successor = node.successor();
                node.element = successor.element;
                remove(successor);
            }
        }
    }

    protected void nodeAdded(Node<E> newNode) {
        // override by sub class
    }

    @Override
    public void add(@NonNull E element) {
        if (root == null) { // first node
            root = createNode(element, null);
            size++;
            nodeAdded(root);
            return;
        }

        Node<E> node = root;
        Node<E> parent = root;
        int position = LEFT;
        while (node != null) {
            parent = node;
            int cmp = element.compareTo(node.element);
            if (cmp > 0) {
                node = node.right;
                position = RIGHT;
            } else if (cmp < 0) {
                node = node.left;
                position = LEFT;
            } else {
                node.element = element;
                size++;
                return;
            }
        }
        Node<E> newNode = createNode(element, parent);
        if ( position == LEFT ) {
            parent.left = newNode;
        } else {
            parent.right = newNode;
        }
        size++;
        nodeAdded(newNode);
    }

    protected Node<E> createNode(E element, Node<E> parent) {
        return Node.of(element, parent);
    }

    @Override
    public void remove(@NonNull E element) {
        Node<E> node = root;
        while(node != null){
            int cmp = element.compareTo(node.element);
            if(cmp < 0){
                node = node.left;
            }else if (cmp > 0){
                node = node.right;
            }else{ // cmp == 0
                remove(node);
                return;
            }
        }
    }

    @Override
    public boolean contains(E element) {
        return false;
    }

    @Override
    public int height() {
        return height(root);
    }
    public int height(Node<E> node) {
        if ( node == null ) return 0;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        int numOfNodesLeftForLayer = 1;
        int height = 0;
        while ( !queue.isEmpty() ) {
            Node<E> head = queue.poll();
            numOfNodesLeftForLayer--;
            if ( head.left != null ) {
                queue.offer(head.left);
            }
            if ( head.right != null ) {
                queue.offer(head.right);
            }
            if ( numOfNodesLeftForLayer == 0 ) {
                height++;
                numOfNodesLeftForLayer = queue.size();
            }
        }
        return height;
    }
//    public int height() {
//        return height(root);
//    }
//
//    public int height(Node<E> node) {
//        if ( node == null ) return 0;
//        return Math.max(height(node.left), height(node.right)) + 1;
//    }

    public void levelOrderTraverse(Consumer<E> visitor) {
        if ( root == null ) return;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        Node<E> node;
        while ( (node = queue.poll()) != null ) {
            visitor.accept(node.element);
            if ( node.left != null ) {
                queue.offer(node.left);
            }
            if ( node.right != null ) {
                queue.offer(node.right);
            }
        }
    }

    @Override
    public boolean isComplete() {
        if ( root == null ) return false;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.offer(root);
        boolean leftHasToBeLeaf = false;
        while ( !queue.isEmpty() ) {
            Node<E> head = queue.poll();
            if ( leftHasToBeLeaf && !head.isLeaf() ) return false;
            if (head.left != null && head.right != null) {
                queue.offer(head.left);
                queue.offer(head.right);
            } else if (head.right == null) {
                leftHasToBeLeaf = true;
            } else { // head.left == null && head.right != null
                return false;
            }
        }
        return true;
    }

    public BinaryTree<E> invert() {
        invert(root);
        return this;
    }

    private void invert(Node<E> node) {
        if (node == null) return;
        Node<E> tmp = node.left;
        node.left = node.right;
        node.right = tmp;
        invert(node.left);
        invert(node.right);
    }

    public void inOrderTransverse(Consumer<E> visitor) {
        inOrderTransverse(root, visitor);
    }

    private boolean isAncestor(Node<E> parent, Node<E> child) {
        while (child != null) {
            if (child.parent == parent) return true;
            child = child.parent;
        }
        return false;
    }

    private boolean isParent(Node<E> parent, Node<E> child) {
        return child != null && parent == child.parent;
    }

    private boolean isParentOrGrand(Node<E> parentOrGrand, Node<E> child) {
        return isParent(parentOrGrand, child) ||
                (child != null && child.parent != null && parentOrGrand == child.parent.parent);
    }

    private void inOrderTransverse(Node<E> root, Consumer<E> visitor) {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        Node<E> prev = null; // last popped node
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            if (node.isLeaf() || isAncestor(node, prev)) {
                prev = node;
                visitor.accept(node.element);
            } else {
                if (node.right != null) {
                    stack.push(node.right);
                    prev = node.right;
                }
                stack.push(node);
                if (node.left != null) {
                    stack.push(node.left);
                    // prev = node.left;
                }
            }
        }
    }

    private void inOrderTransverse0(Node<E> root, Consumer<E> visitor) {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        Node<E> node = root;
        while (!stack.isEmpty() || node != null) {
            if (node != null) {
                stack.push(node);
                node = node.left;
            } else {
                node = stack.pop();
                visitor.accept(node.element);
                node = node.right;
            }
        }
    }

    public void inOrderTransverseRecursive(Consumer<E> visitor) {
        inOrderTransverseRecursive(root, visitor);
    }

    public void preOrderTransverseRecursive(Consumer<E> visitor) {
        preOrderTransverseRecursive(root, visitor);
    }

    private void preOrderTransverseRecursive(Node<E> root, Consumer<E> visitor) {
        if (root == null) return;
        visitor.accept(root.element);
        preOrderTransverseRecursive(root.left, visitor);
        preOrderTransverseRecursive(root.right, visitor);
    }



    public void preOrderTransverse(@NonNull Consumer<E> visitor) {
        preOrderTransverse(root, visitor);
    }

    private void preOrderTransverse(Node<E> root, Consumer<E> visitor) {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        while (!stack.isEmpty()) {
            Node<E> node = stack.pop();
            visitor.accept(node.element);
            if (node.right != null) stack.push(node.right);
            if (node.left != null) stack.push(node.left);
        }
    }

    public void postOrderTransverseRecursive(@NonNull Consumer<E> visitor) {
        postOrderTransverseRecursive(root, visitor);
    }

    private void postOrderTransverseRecursive(Node<E> root, @NonNull Consumer<E> visitor) {
        if (root == null) return;
        postOrderTransverseRecursive(root.left, visitor);
        postOrderTransverseRecursive(root.right, visitor);
        visitor.accept(root.element);
    }

    public void postOrderTransverse(@NonNull Consumer<E> visitor) {
        postOrderTransverse(root, visitor);
    }

    private void postOrderTransverse(Node<E> root, Consumer<E> visitor) {
        if (root == null) return;
        Stack<Node<E>> stack = new Stack<>();
        stack.push(root);
        Node<E> prev = null; // last popped node
        while (!stack.isEmpty()) {
            Node<E> top = stack.peek();
            if (top.isLeaf() || isParent(top, prev)) {
                prev = stack.pop();
                visitor.accept(prev.element);
            } else {
                if (top.right != null) stack.push(top.right);
                if (top.left != null) stack.push(top.left);
            }
        }
    }

    private void inOrderTransverseRecursive(Node<E> node, Consumer<E> visitor) {
        if (node == null) return;
        inOrderTransverse(node.left, visitor);
        visitor.accept(node.element);
        inOrderTransverse(node.right, visitor);
    }

    public static class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;
        Node<E> parent;

        public static <E> Node<E> of(E element, Node<E> parent) {
            return new Node<>(element, parent);
        }

        public int degree() {
            int degree = 0;
            if (left != null) degree++;
            if (right != null) degree++;
            return degree;
        }

        public Node(E element, Node<E> parent) {
            this.element = element;
            this.parent = parent;
        }

        public boolean isLeft() {
            return parent != null && parent.left == this;
        }

        public boolean isRight() {
            return parent != null && parent.right == this;
        }

        public boolean isLeaf() {
            return left == null && right == null;
        }

        public Node<E> successor() {
            if (right != null) {
                Node<E> node = right;
                while (node.left != null) node = node.left;
                return node;
            } else {
                Node<E> node = parent;
                while (true) {
                    if (node == null) return null;
                    if (node.parent.left == node) return node.parent;
                    node = node.parent;
                }
            }

        }
        public Node<E> predecessor() {
            if (left != null) {
                Node<E> node = left;
                while (node.right != null) node = node.right;
                return node;
            } else { // left == null
                // if (parent == null) return null;
                Node<E> node = parent;
                while (true) {
                    if (node == null) return null;
                    if (node.parent.right == node) return node.parent;
                    node = node.parent;
                }
            }
        }

        public Node<E> sibling() {
            if (isLeft()) return parent.right;
            if (isRight()) return parent.left;
            return null;
        }

        public Node<E> uncle() {
            if (parent == null) return null;
            return parent.sibling();
        }

        @Override
        public String toString() {
            return element.toString();
        }
    }

    // debug
    @Override
    public Object root() {
        return root;
    }

    @Override
    public Object left(Object node) {
        return ((Node<E>) node).left;
    }

    @Override
    public Object right(Object node) {
        return ((Node<E>) node).right;
    }

    @Override
    public Object string(Object node) {
        return ((Node<E>) node).element.toString();
    }
}
