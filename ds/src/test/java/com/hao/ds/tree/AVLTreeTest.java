package com.hao.ds.tree;


import com.hao.ds.utils.printer.BinaryTrees;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
@RunWith(JUnit4.class)
public class AVLTreeTest {

    private static final AVLTree<Integer> bstA() {
        AVLTree<Integer> bst = new AVLTree<>();
        Arrays.asList(8, 2, 4, 3, 1, 10, 9, 11, 7, 12, 13, 14).forEach(bst::add);
        return bst;
    }

    private static final AVLTree<Integer> bstB() {
        AVLTree<Integer> bst = new AVLTree<>();
        Arrays.asList(85, 19, 69, 3, 7, 99, 95).forEach(bst::add);
        return bst;
    }

    @Test
    public void remove() {
        AVLTree<Integer> tree = bstB();

        tree.remove(99);
        String expected = "" +
                "      ┌───69(0)────┐\n" +
                "      │            │\n" +
                "  ┌─7(0)─┐     ┌─95(1)\n" +
                "  │      │     │\n" +
                "3(0)   19(0) 85(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        tree.remove(85);
        tree.remove(95);
        expected = "" +
                "  ┌─7(1)─┐\n" +
                "  │      │\n" +
                "3(0) ┌─69(1)\n" +
                "     │\n" +
                "   19(0)\n";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        tree.remove(3);
        expected = "" +
                "  ┌─19(0)─┐\n" +
                "  │       │\n" +
                "7(0)    69(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
    }

    @Test
    public void add() {
        AVLTree<Integer> tree = new AVLTree<>();
        tree.add(8);
        tree.add(9);
        tree.add(10);
        String expected = "" +
                "  ┌─9(0)─┐\n" +
                "  │      │\n" +
                "8(0)   10(0)";
        Assert.assertEquals(expected, BinaryTrees.printString(tree)); // right rotate
        tree.add(11);
        tree.add(12);
        expected = "" +
                "  ┌─9(1)─┐\n" +
                "  │      │\n" +
                "8(0) ┌─11(0)─┐\n" +
                "     │       │\n" +
                "   10(0)   12(0)\n";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim()); // right rotate

        tree.add(7);
        tree.add(6);
        expected = "" +
                "      ┌────9(0)────┐\n" +
                "      │            │\n" +
                "  ┌─7(0)─┐     ┌─11(0)─┐\n" +
                "  │      │     │       │\n" +
                "6(0)    8(0) 10(0)   12(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim()); // left rotate
        // BinaryTrees.println(tree);


        AVLTree<Integer> tree2 = new AVLTree<>();
        tree2.add(10);
        tree2.add(20);
        tree2.add(15);
        expected = "" +
                "  ┌─15(0)─┐\n" +
                "  │       │\n" +
                "10(0)   20(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree2).trim()); // right rotate and left rotate
        tree2.add(30);
        tree2.add(25);
        expected = "" +
                "  ┌─15(1)─┐\n" +
                "  │       │\n" +
                "10(0) ┌─25(0)─┐\n" +
                "      │       │\n" +
                "    20(0)   30(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree2).trim()); // right rotate and left rotate

        AVLTree<Integer> tree3 = new AVLTree<>();
        tree3.add(10);
        tree3.add(5);
        tree3.add(6);
        expected = "" +
                "  ┌─6(0)─┐\n" +
                "  │      │\n" +
                "5(0)   10(0)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree3).trim()); // left rotate and right rotate

        AVLTree<Integer> tree4 = new AVLTree<>();
        Arrays.asList(14, 6, 80, 81, 12, 17, 77, 53, 2, 51, 34, 42, 45, 24, 59, 23, 31, 90, 73).forEach(tree4::add);

        expected = "" +
                "            ┌─────────────34(0)────────────┐\n" +
                "            │                              │\n" +
                "      ┌───14(1)────┐               ┌─────53(1)─────┐\n" +
                "      │            │               │               │\n" +
                "  ┌─6(0)─┐     ┌─23(1)─┐       ┌─45(0)─┐       ┌─80(0)─┐\n" +
                "  │      │     │       │       │       │       │       │\n" +
                "2(0)   12(0) 17(0)   24(1)─┐ 42(0)   51(0) ┌─73(0)─┐ 81(1)─┐\n" +
                "                           │               │       │       │\n" +
                "                         31(0)           59(0)   77(0)   90(0)";
         Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree4).trim());

        BinaryTrees.println(tree4);
    }
}
