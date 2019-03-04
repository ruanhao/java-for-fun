package com.hao.notes.stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;

import java.util.IntSummaryStatistics;
import java.util.Optional;

import org.junit.Test;

import lombok.extern.slf4j.Slf4j;



/*
 * Collectors 类提供的工厂方法（例如 groupingBy )所创建的对象。主要提供三个功能:
 * - 将流元素归约和汇总为一个值 (Aggregation)
 * - 元素分组 (Group)
 * - 元素分区 (Partition)
 */
@Slf4j
public class CollectionExamples {

    @Test
    public void testAggregationCounting() {
        long total = Dish.menu.stream()
                .collect(counting());
        log.info("total: {}", total);

    }

    @Test
    public void testAggregationMaxBy() {
        Optional<Dish> maxOp = Dish.menu.stream()
                .collect(maxBy(comparingInt(Dish::getCalories)));
        maxOp.ifPresent(System.out::println);
    }

    @Test
    public void testAggregationSumming() {
        int sum = Dish.menu.stream()
            .collect(summingInt(Dish::getCalories));
        log.info("sum: {}", sum);
    }

    @Test
    public void testAggregationAveraging() {
        double avg = Dish.menu.stream()
            .collect(averagingInt(Dish::getCalories));
        log.info("avg: {}", avg);
    }

    @Test
    public void testAggregationSummarizing() {
        IntSummaryStatistics s = Dish.menu.stream()
            .collect(summarizingInt(Dish::getCalories));
        log.info("Summary statistics: {}", s);
    }

    @Test
    public void testAggregationJoining() {
        String j = Dish.menu.stream()
            .map(Dish::getName)
            .collect(joining(", "));
        log.info("Joined string: {}", j);
    }

}
