package com.hao.ds.sort;

import com.google.common.collect.Sets;
import com.hao.ds.utils.Integers;
import java.util.Arrays;
import org.junit.Assert;
import org.junit.Test;

public class SortTest {


    @Test
    public void testSorts() {
        int count = 10000;
        // Integer[] array = {334, 25, 23};
        Integer[] array = Integers.random(count, -count , count);
        testSorts(array,
                new BubbleSort(),
                new BubbleSortV2(),
                new BubbleSortV3(),
                new SelectionSort(),
                new InsertionSortV2(),
                new InsertionSortV3(),
                new InsertionSort(),
                new MergeSort(),
                new QuickSort(),
                new QuickSortV2(),
                new ShellSort(),
                new ShellSortV2(),
                // new CountingSort(),
                new CountingSortV2(),
                new CountingSortV3(),
                new RadixSort(),
                new HeapSort()
        );
    }

    static void testSorts(Integer[] array, Sort... sorts) {
        for (Sort sort : sorts) {
            Integer[] newArray = Integers.copy(array);
            int totalNumber0 = Sets.newHashSet(newArray).size();
            sort.sort(newArray);
            Assert.assertEquals(String.format("排序前后数据不一致 (%s)", sort.getClass().getSimpleName()), totalNumber0, Sets.newHashSet(newArray).size());
            // Integers.println(newArray);
            Assert.assertTrue(String.format("排序顺序错误 (%s)", sort.getClass().getSimpleName()),
                    Integers.isAscOrder(newArray));
        }
        Arrays.sort(sorts);
        for (Sort sort : sorts) {
            System.out.println(sort.info());
        }
    }

}