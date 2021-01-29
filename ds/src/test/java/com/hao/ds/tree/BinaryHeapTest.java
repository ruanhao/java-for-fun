package com.hao.ds.tree;

import com.hao.ds.utils.printer.BinaryTrees;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class BinaryHeapTest {



    @Test
    public void add() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        Arrays.asList(72, 68, 50, 43, 38, 47, 21, 14, 40, 3).forEach(heap::add);
        heap.add(80);
        String expected = "" +
                "        ┌────80────┐\n" +
                "        │          │\n" +
                "    ┌──72──┐     ┌─50─┐\n" +
                "    │      │     │    │\n" +
                " ┌─43─┐  ┌─68─┐ 47    21\n" +
                " │    │  │    │\n" +
                "14    40 3    38\n";
        BinaryTrees.println(heap);
        assertEquals(expected.trim(), BinaryTrees.printString(heap).trim());
    }

    @Test
    public void remove() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        Arrays.asList(72, 68, 50, 43, 38, 47, 21, 14, 40, 3).forEach(heap::add);

        BinaryTrees.println(heap);
        heap.remove();
        BinaryTrees.println(heap);
        String expected = "" +
                "       ┌───68──┐\n" +
                "       │       │\n" +
                "    ┌─43─┐   ┌─50─┐\n" +
                "    │    │   │    │\n" +
                " ┌─40─┐  38 47    21\n" +
                " │    │\n" +
                "14    3";
        assertEquals(expected.trim(), BinaryTrees.printString(heap).trim());
    }

    @Test
    public void replace() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        Arrays.asList(72, 68, 50, 43, 38, 47, 21, 14, 40, 3).forEach(heap::add);

        BinaryTrees.println(heap);
        heap.replace(1);
        BinaryTrees.println(heap);
        String expected = "" +
                "        ┌───68──┐\n" +
                "        │       │\n" +
                "    ┌──43─┐   ┌─50─┐\n" +
                "    │     │   │    │\n" +
                " ┌─40─┐ ┌─38 47    21\n" +
                " │    │ │\n" +
                "14    1 3";

        assertEquals(expected.trim(), BinaryTrees.printString(heap).trim());
    }

    @Test
    public void heapify() {

        List<Integer> ints = Arrays.asList(72, 68, 50, 43, 38, 47, 21, 14, 40, 3);
        BinaryHeap<Integer> heap = new BinaryHeap<>(ints);

        BinaryTrees.println(heap);
        String expected = "" +
                "        ┌───72───┐\n" +
                "        │        │\n" +
                "    ┌──68──┐   ┌─50─┐\n" +
                "    │      │   │    │\n" +
                " ┌─43─┐  ┌─38 47    21\n" +
                " │    │  │\n" +
                "14    40 3";
        assertEquals(expected.trim(), BinaryTrees.printString(heap).trim());

    }

    @Test
    public void smallHeap() {


        BinaryHeap<Integer> heap = new BinaryHeap<>(false);
        Arrays.asList(72, 68, 50, 43, 38, 47, 21, 14, 40, 3).forEach(heap::add);
        BinaryTrees.println(heap);

    }


}