package com.hao.notes.stream;

import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.averagingInt;
import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.mapping;
import static java.util.stream.Collectors.maxBy;
import static java.util.stream.Collectors.partitioningBy;
import static java.util.stream.Collectors.summarizingInt;
import static java.util.stream.Collectors.summingInt;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toSet;

import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

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
        long total = Dish.menu.stream().collect(counting());
        log.info("total: {}", total);

    }

    @Test
    public void testAggregationMaxBy() {
        Optional<Dish> maxOp = Dish.menu.stream().collect(maxBy(comparingInt(Dish::getCalories)));
        maxOp.ifPresent(System.out::println);
    }

    @Test
    public void testAggregationSumming() {
        int sum = Dish.menu.stream().collect(summingInt(Dish::getCalories));
        log.info("sum: {}", sum);
    }

    @Test
    public void testAggregationAveraging() {
        double avg = Dish.menu.stream().collect(averagingInt(Dish::getCalories));
        log.info("avg: {}", avg);
    }

    @Test
    public void testAggregationSummarizing() {
        IntSummaryStatistics s = Dish.menu.stream().collect(summarizingInt(Dish::getCalories));
        log.info("Summary statistics: {}", s);
    }

    @Test
    public void testAggregationJoining() {
        String j = Dish.menu.stream().map(Dish::getName).collect(joining(", "));
        log.info("Joined string: {}", j);
    }

    @Test
    public void testGroupingBy() {
        Map<Dish.CaloricLevel, List<Dish>> dishesByCaloricLevel = Dish.menu.stream().collect(groupingBy(dish -> {
            if (dish.getCalories() <= 400)
                return Dish.CaloricLevel.DIET;
            else if (dish.getCalories() <= 700)
                return Dish.CaloricLevel.NORMAL;
            else
                return Dish.CaloricLevel.FAT;
        }));
        log.info("Group by caloric level: {}", dishesByCaloricLevel);
    }

    @Test
    public void testGroupingByMultipleLevels() {
        Map<Dish.Type, Map<Dish.CaloricLevel, List<Dish>>> dishesByTypeCaloricLevel = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, groupingBy(dish -> {
                    if (dish.getCalories() <= 400)
                        return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700)
                        return Dish.CaloricLevel.NORMAL;
                    else
                        return Dish.CaloricLevel.FAT;
                })));
        log.info("Grouped by type and caloric level: {}", dishesByTypeCaloricLevel);
    }

    @Test
    public void testGroupingThenAggregationOnSubStream() {
        Map<Dish.Type, Long> typesCount = Dish.menu.stream().collect(groupingBy(Dish::getType, counting()));
        log.info("Count for types: {}", typesCount);

        Map<Dish.Type, Optional<Dish>> mostCaloricByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, maxBy(comparingInt(Dish::getCalories))));
        log.info("Max caloric level by type: {}", mostCaloricByType);

        Map<Dish.Type, Dish> mostCaloricByType2 = Dish.menu.stream().collect(
                groupingBy(Dish::getType, collectingAndThen(maxBy(comparingInt(Dish::getCalories)), Optional::get)));
        log.info("Max caloric level by type (collectingAndThen): {}", mostCaloricByType2);

        Map<Dish.Type, Integer> totalCaloriesByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, summingInt(Dish::getCalories)));
        log.info("Total calories by type: {}", totalCaloriesByType);

        Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400)
                        return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700)
                        return Dish.CaloricLevel.NORMAL;
                    else
                        return Dish.CaloricLevel.FAT;
                }, toSet())));
        log.info("Mapping on substream and collect in set: {}", caloricLevelsByType);

        Map<Dish.Type, Set<Dish.CaloricLevel>> caloricLevelsByType1 = Dish.menu.stream()
                .collect(groupingBy(Dish::getType, mapping(dish -> {
                    if (dish.getCalories() <= 400)
                        return Dish.CaloricLevel.DIET;
                    else if (dish.getCalories() <= 700)
                        return Dish.CaloricLevel.NORMAL;
                    else
                        return Dish.CaloricLevel.FAT;
                }, toCollection(HashSet::new))));
        log.info("Mapping on substream and collect in hash set: {}", caloricLevelsByType1);
    }

    @Test
    public void testPartitioningBy() {
        Map<Boolean, List<Dish>> partitionedMenu = Dish.menu.stream().collect(partitioningBy(Dish::isVegetarian));
        log.info("{}", partitionedMenu.get(true));
    }

    @Test
    public void testPartitioningThenAggregationOnSubStream() {
        Map<Boolean, String> mostCaloricPartitionedByVegetarian = Dish.menu.stream()
                .collect(partitioningBy(Dish::isVegetarian,
                        collectingAndThen(maxBy(comparingInt(Dish::getCalories)), op -> op.get().getName())));
        log.info("{}", mostCaloricPartitionedByVegetarian);
    }

}
