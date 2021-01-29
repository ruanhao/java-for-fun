package com.hao.ds.tree;

import com.hao.ds.utils.printer.BinaryTrees;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;


public class RBTreeTest {

    private static final RBTree<Integer> bstA() {
        RBTree<Integer> bst = new RBTree<>();
        Arrays.asList(55, 87, 56, 74, 96, 22, 62, 20, 70, 68, 90, 50).forEach(bst::add);
        return bst;
    }

    @Test
    public void add() {
        String expected = "" +
                "          ┌─────70(B)─────┐\n" +
                "          │               │\n" +
                "      ┌─56(B)─┐       ┌─87(B)─┐\n" +
                "      │       │       │       │\n" +
                "  ┌─22(R)─┐ 62(B)─┐ 74(B) ┌─96(B)\n" +
                "  │       │       │       │\n" +
                "20(B) ┌─55(B)   68(R)   90(R)\n" +
                "      │\n" +
                "    50(R)";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(bstA()).trim());
        BinaryTrees.printlnWithColor(bstA());
    }

    @Test
    public void remove() {
        RBTree<Integer> tree = bstA();
        tree.remove(55);
        String expected = "" +
                "          ┌─────70(B)─────┐\n" +
                "          │               │\n" +
                "      ┌─56(B)─┐       ┌─87(B)─┐\n" +
                "      │       │       │       │\n" +
                "  ┌─22(R)─┐ 62(B)─┐ 74(B) ┌─96(B)\n" +
                "  │       │       │       │\n" +
                "20(B)   50(B)   68(R)   90(R)";


        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        tree.remove(87);
        expected = "" +
                "          ┌─────70(B)─────┐\n" +
                "          │               │\n" +
                "      ┌─56(B)─┐       ┌─90(B)─┐\n" +
                "      │       │       │       │\n" +
                "  ┌─22(R)─┐ 62(B)─┐ 74(B)   96(B)\n" +
                "  │       │       │\n" +
                "20(B)   50(B)   68(R)\n";
        Assert.assertEquals(expected.trim(), BinaryTrees.printString(tree).trim());
        BinaryTrees.printlnWithColor(tree);


    }
}