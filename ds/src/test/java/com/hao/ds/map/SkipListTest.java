package com.hao.ds.map;

import com.google.common.primitives.Ints;
import lombok.NonNull;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class SkipListTest {

    @Test
    public void remove() {
        SkipList<Integer, String> skipList = new SkipList<>();
        skipList.put(21, "21");
        skipList.put(19, "19");
        skipList.put(17, "17");
        skipList.put(12, "12");
        skipList.put(9, "9");
        skipList.put(7, "7");
        skipList.put(6, "6");
        skipList.put(3, "3");

        assertEquals("21", skipList.remove(21));
        assertNull(skipList.get(21));
        assertEquals("19", skipList.remove(19));
        assertNull(skipList.get(19));
        assertEquals("17", skipList.remove(17));
        assertNull(skipList.get(17));
        assertEquals("9", skipList.remove(9));
        assertNull(skipList.get(9));
        assertEquals("7", skipList.remove(7));
        assertNull(skipList.get(7));

        skipList = new SkipList<>();
        skipList.put(7, "7");
        skipList.put(6, "6");
        SkipList.Node<Integer, String> node6 = getNode(skipList.head, 6);
        SkipList.Node<Integer, String> node7 = getNode(skipList.head, 7);
        int levelOfNode6 = node6.nexts.length;
        int levelOfNode7 = node7.nexts.length;
        System.err.println("level: " + skipList.level);
        System.err.println("levelOfNode6: " + levelOfNode6);
        System.err.println("levelOfNode7: " + levelOfNode7);
        assertEquals(skipList.level, Ints.max(levelOfNode6, levelOfNode7));
        if (levelOfNode6 > levelOfNode7) {
            skipList.remove(6);
            assertEquals(skipList.level, levelOfNode7);
        }
        if (levelOfNode6 < levelOfNode7) {
            skipList.remove(7);
            assertEquals(skipList.level, levelOfNode6);
        }

    }

    private <K, V> SkipList.Node<K, V> getNode(@NonNull SkipList.Node<K, V> head, @NonNull K key) {
        SkipList.Node node = head;
        while (node.nexts[0] != null && node.nexts[0].key != key) {
            node = node.nexts[0];
        }
        return node.nexts[0];
    }

    @Test
    public void put() {
        SkipList<Integer, String> skipList = new SkipList<>();
        skipList.put(21, "21");
        skipList.put(19, "19");
        skipList.put(17, "17");
        skipList.put(12, "12");
        skipList.put(9, "9");
        skipList.put(7, "7");
        skipList.put(6, "6");
        skipList.put(3, "3");

        assertEquals("3", skipList.get(3));
        assertEquals("6", skipList.get(6));
        assertEquals("7", skipList.get(7));
        assertEquals("9", skipList.get(9));
        assertEquals("12", skipList.get(12));
        assertEquals("17", skipList.get(17));
        assertEquals("19", skipList.get(19));
        assertEquals("21", skipList.get(21));
        assertNull(skipList.get(1));
        assertNull(skipList.get(8));
        assertNull(skipList.get(22));

    }

    @Test
    public void get() {
        SkipList<Integer, String> skipList = new SkipList<>();
        skipList.level = 4;

        SkipList.Node<Integer, String> node21 = new SkipList.Node<>(21, "21", new SkipList.Node[]{null, null, null, null});
        SkipList.Node<Integer, String> node19 = new SkipList.Node<>(19, "19", new SkipList.Node[]{node21});
        SkipList.Node<Integer, String> node17 = new SkipList.Node<>(17, "17", new SkipList.Node[]{node19, node21});
        SkipList.Node<Integer, String> node12 = new SkipList.Node<>(12, "12", new SkipList.Node[]{node17});
        SkipList.Node<Integer, String> node9 = new SkipList.Node<>(9, "9", new SkipList.Node[]{node12, node17, node21});
        SkipList.Node<Integer, String> node7 = new SkipList.Node<>(7, "7", new SkipList.Node[]{node9});
        SkipList.Node<Integer, String> node6 = new SkipList.Node<>(6, "6", new SkipList.Node[]{node7, node9});
        SkipList.Node<Integer, String> node3 = new SkipList.Node<>(3, "3", new SkipList.Node[]{node6});
        skipList.head = new SkipList.Node<>();
        skipList.head.nexts = new SkipList.Node[]{node3, node6, node9, node21};

        assertEquals("3", skipList.get(3));
        assertEquals("6", skipList.get(6));
        assertEquals("7", skipList.get(7));
        assertEquals("9", skipList.get(9));
        assertEquals("12", skipList.get(12));
        assertEquals("17", skipList.get(17));
        assertEquals("19", skipList.get(19));
        assertEquals("21", skipList.get(21));
        assertNull(skipList.get(1));
        assertNull(skipList.get(8));
        assertNull(skipList.get(22));


    }
}