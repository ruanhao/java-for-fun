package com.hao.ds.tree;

import com.hao.ds.utils.printer.BinaryTrees;
import java.util.Arrays;
import java.util.function.Consumer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class BinarySearchTreeTest {
    private static final BinarySearchTree<Integer> bstA() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Arrays.asList(8, 2, 4, 3, 1, 8, 10, 9, 11, 7, 12).forEach(bst::add);
        return bst;
    }

    private static final BinarySearchTree<Integer> bstB() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Arrays.asList(33, 43, 2, 56, 22, 6, 1, 90, 111, 4, 89, 21).forEach(bst::add);
        return bst;
    }

    private static final BinarySearchTree<Integer> bstC() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Arrays.asList(5, 2, 1, 3, 9, 7).forEach(bst::add);
        return bst;
    }

    private static final BinarySearchTree<Integer> bstD() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        Arrays.asList(8, 4, 13, 2, 6, 10, 1, 3, 5, 7, 9, 12, 11).forEach(bst::add);
        return bst;
    }

    @Test
    public void inOrderTransverseNonRecursive() {
        inOrderTransverseNonRecursive(bstA());
        inOrderTransverseNonRecursive(bstB());
        inOrderTransverseNonRecursive(bstC());
        inOrderTransverseNonRecursive(bstD());
    }

    public void inOrderTransverseNonRecursive(BinarySearchTree<Integer> tree) {
        BinaryTrees.println(tree);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> c = (i) -> sb.append(i + " ");
        tree.inOrderTransverse(c);
        String outputOfNonRecursive = sb.toString();
        sb.setLength(0);
        tree.inOrderTransverseRecursive(c);
        String outputOfRecursive = sb.toString();
        assertEquals(outputOfRecursive, outputOfNonRecursive);
    }

    @Test
    public void postOrderTransverseNonRecursive() {
        postOrderTransverseNonRecursive(bstA());
        postOrderTransverseNonRecursive(bstB());
        postOrderTransverseNonRecursive(bstC());
        postOrderTransverseNonRecursive(bstD());
    }
    private void postOrderTransverseNonRecursive(BinarySearchTree<Integer> tree) {
        BinaryTrees.println(tree);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> c = (i) -> sb.append(i + " ");
        tree.postOrderTransverse(c);
        String outputOfNonRecursive = sb.toString();
        System.err.println("outputOfNonRecursive: " + outputOfNonRecursive);
        sb.setLength(0);
        tree.postOrderTransverseRecursive(c);
        String outputOfRecursive = sb.toString();
        assertEquals(outputOfNonRecursive, outputOfRecursive);
    }

    @Test
    public void preOrderTransverseNonRecursive() {
        preOrderTransverseNonRecursive(bstA());
        preOrderTransverseNonRecursive(bstB());
        preOrderTransverseNonRecursive(bstC());
        preOrderTransverseNonRecursive(bstD());
    }

    private void preOrderTransverseNonRecursive(BinarySearchTree<Integer> tree) {
        BinaryTrees.println(tree);
        StringBuilder sb = new StringBuilder();
        Consumer<Integer> c = (i) -> sb.append(i + " ");
        tree.preOrderTransverse(c);
        String outputOfNonRecursive = sb.toString();
        sb.setLength(0);
        tree.preOrderTransverseRecursive(c);
        String outputOfRecursive = sb.toString();
        assertEquals(outputOfNonRecursive, outputOfRecursive);

    }

    @Test
    public void isComplete() {
        assertFalse(bstA().isComplete());
        assertFalse(bstB().isComplete());
        assertTrue(bstC().isComplete());
    }

    @Test
    public void invert() {
        BinaryTrees.println(bstA());
        System.out.println();
        String expected = "" +
                "       ┌───8───┐\n" +
                "       │       │\n" +
                "    ┌─10─┐   ┌─2─┐\n" +
                "    │    │   │   │\n" +
                " ┌─11    9 ┌─4─┐ 1\n" +
                " │         │   │\n" +
                "12         7   3\n";
        assertEquals(expected.trim(), BinaryTrees.printString(((BinarySearchTree<Integer>)bstA().invert())).trim());
    }

    @Test
    public void height() {
        assertEquals(4, bstA().height());
        assertEquals(5, bstB().height());

    }

    @Test
    public void traverse() {
        StringBuilder sbAInOrder = new StringBuilder();
        bstA().inOrderTransverse(e -> sbAInOrder.append(e + " "));
        assertEquals("1 2 3 4 7 8 9 10 11 12 ", sbAInOrder.toString());

        StringBuilder sbALevelOrder = new StringBuilder();
        bstA().levelOrderTraverse(e -> sbALevelOrder.append(e + " "));
        assertEquals("8 2 10 1 4 9 11 3 7 12 ", sbALevelOrder.toString());
    }


    @Test
    public void add() {
        String expected = "" +
                "  ┌───8───┐\n" +
                "  │       │\n" +
                "┌─2─┐   ┌─10─┐\n" +
                "│   │   │    │\n" +
                "1 ┌─4─┐ 9    11─┐\n" +
                "  │   │         │\n" +
                "  3   7         12";

        BinaryTrees.println(bstA());
        assertEquals(expected.trim(), BinaryTrees.printString(bstA()).trim());

    }

    @Test
    public void predecessor() {
        BinaryTrees.println(bstD());
        assertEquals(new Integer(7), bstD().root.predecessor().element); // 8's predecessor is 7
        assertEquals(new Integer(8), bstD().root.right.left.left.predecessor().element); // 9's predecessor is root(8)
    }

    @Test
    public void successor() {
        BinaryTrees.println(bstD());
        assertEquals(new Integer(5), bstD().root.left.successor().element); // 4's successor is 5
        assertEquals(new Integer(8), bstD().root.left.right.right.successor().element); // 7's successor is root(8)
    }

    @Test
    public void remove() {
        BinarySearchTree<Integer> tree = bstD();
        int originSize = tree.size();
        System.err.println("originSize: " + originSize);
        BinaryTrees.println(tree);
        BinarySearchTree.Node<Integer> node1 = tree.root.left.left.left;
        tree.remove(node1);
        assertEquals(originSize - 1, tree.size());
        String expected = "" +
                "   ┌─────8─────┐\n" +
                "   │           │\n" +
                "┌──4──┐      ┌─13\n" +
                "│     │      │\n" +
                "2─┐ ┌─6─┐ ┌─10─┐\n" +
                "  │ │   │ │    │\n" +
                "  3 5   7 9  ┌─12\n" +
                "             │\n" +
                "            11";
        // BinaryTrees.println(tree);
        assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        BinarySearchTree.Node<Integer> node12 = tree.root.right.left.right;
        tree.remove(node12);
        assertEquals(originSize - 2, tree.size());
        expected = "┌─────8─────┐\n" +
                "   │           │\n" +
                "┌──4──┐      ┌─13\n" +
                "│     │      │\n" +
                "2─┐ ┌─6─┐ ┌─10─┐\n" +
                "  │ │   │ │    │\n" +
                "  3 5   7 9    11";
        // BinaryTrees.println(tree);
        assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        tree.remove(tree.root);
        assertEquals(originSize - 3, tree.size());
        expected = "" +
                "   ┌────7────┐\n" +
                "   │         │\n" +
                "┌──4──┐    ┌─13\n" +
                "│     │    │\n" +
                "2─┐ ┌─6 ┌─10─┐\n" +
                "  │ │   │    │\n" +
                "  3 5   9    11\n";
        assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());


    }
}
