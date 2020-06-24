package com.hao.notes.stream;


import static java.util.Comparator.comparing;
import static java.util.stream.Collectors.toList;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.Test;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/*
 * 流的使用一般包括三件事:
 * - 一个数据源（如集合）来执行一个查询
 * - 一个中间操作链，形成一条流的流水线
 * - 一个终端操作，执行流水线，并能生成结果
*/

@Slf4j
public class StreamExamples {


    List<Integer> numbers = Arrays.asList(1, 2, 1, 3, 3, 2, 4);

    @Test
    public void testDistinct() {
        numbers.stream()
            .filter(i -> i % 2 == 0)
            .distinct()
            .forEach(System.out::println);
    }

    @Test
    public void testLimit() {
        List<Dish> dishes = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .limit(3)
                .collect(toList());
        log.info("dishes: {}", dishes);
    }

    @Test
    public void testSkip() {
        List<Dish> skippedDishes = Dish.menu.stream()
                .filter(d -> d.getCalories() > 300)
                .skip(2)
                .collect(toList());
        log.info("dishes: {}", skippedDishes);
    }

    @Test
    public void testFilter() {
        List<Dish> vegetarianMenu = Dish.menu.stream()
                .filter(Dish::isVegetarian)
                .collect(toList());
        log.info("vegetarianMenu: {}", vegetarianMenu);
    }


    @Test
    public void testFlatMap() {
        List<String> uniqueCharacters =
            Arrays.asList("hello", "world").stream()
            .map(w -> w.split(""))
            // Arrays.stream() 的方法可以接受一个数组并产生一个流
            // 将各个生成流扁平化为单个流
            .flatMap(Arrays::stream)
            .distinct()
            .collect(Collectors.toList());
        log.info("uniqueCharacters: {}", uniqueCharacters);
    }

    @Test
    public void testMapToNumber() {
        int sum = Dish.menu.stream()
                .mapToInt(Dish::getCalories)
                .sum();
        log.info("sum: {}", sum);
    }

    @Test
    public void testMap() {
        List<String> dishNames = Dish.menu.stream()
            .map(Dish::getName)
            .collect(toList());
        log.info("dishNames: {}", dishNames);
    }

    /*
     * 此类查询需要将流中所有元素反复结合起来，得到一个值。这样的查询可以被归类为归约操作（将流归约成一个值）。
     * 用函数式编程语言的术语来说，这称为折叠（fold），因为可以将这个操作看成把一张长长的纸（流）反复折叠成一个小方块，而这就是折叠操作的结果。
     */
    @Test
    public void testReduce() {
        // BinaryOperator<T> 用来将两个元素结合起来产生一个新值
        int sum0 = numbers.stream().reduce(0, (a, b) -> a + b);
        int sum1 = numbers.stream().reduce(0, Integer::sum);

        // reduce 还有一个重载的变体，它不接受初始值，但是会返回一个Optional对象
        // 因为流中可能没有任何元素的情况，reduce 操作就无法返回其和，因为它没有初始值
        // 这就是为什么结果被包裹在一个 Optional 对象里，以表明和可能不存在
        Optional<Integer> sum2 = numbers.stream().reduce((a, b) -> (a + b));
        log.info("sum0: {}, sum1: {}, sum2: {}", sum0, sum1, sum2.get());

        // 计算最大值
        Optional<Integer> max = numbers.stream().reduce(Integer::max);
        log.info("max: {}", max);
    }

    @Test
    public void testSorted() {
        Dish.menu.stream()
        .sorted(comparing(Dish::getCalories).reversed())
        .limit(3)
        .map(d -> d.getName())
        .forEach(System.out::println);
    }



    /*
     * IntStream 中的 map 方法只能为流中的每个元素返回另一个 int ， 如果这不是你想要的，可以用 IntStream 的 mapToObj 方法改写它，这个方法会返回一个对象值流。
     * 或者使用 boxed 方法先转换为 Stream<Integer> 。
     */
    @Test
    public void testNumberStream() {
        IntStream evenNumbers1 = IntStream.range(1, 100);
        IntStream evenNumbers2 = IntStream.rangeClosed(1, 100);
        log.info("evenNumbers1: {}, evenNumbers2: {}", evenNumbers1, evenNumbers2);

    }

    @SuppressWarnings({ "unused", "resource" })
    @Test
    @SneakyThrows
    public void testBuildingStream() {
        log.info("=== 由值创建 ===");
        Stream<String> stream = Stream.of("Java 8 ", "Lambdas ", "In ", "Action");
        Stream<String> emptyStream = Stream.empty();

        log.info("=== 由数组创建 ===");
        int[] numbers = {2, 3, 5, 7, 11, 13};
        int sum = Arrays.stream(numbers).sum();
        log.info("sum: {}", sum);

        log.info("=== 由文件创建 ===");
        Stream<String> lines = Files.lines(Paths.get("/etc/hosts"), Charset.defaultCharset());

        log.info("=== 由函数创建 (无限流) ===");
        /*
         * Stream API 提供了两个静态方法来从函数生成流: Stream.iterate 和 Stream.generate ，这两个操作可以创建所谓的无限流，
         * 由 iterate 和 generate 产生的流会用给定的函数按需创建值，因此可以无穷无尽地计算下去。
         * 一般来说，应该使用 limit(n) 来对这种流加以限制，以避免打印无穷多个值。
         */
        Stream.iterate(0, n -> n + 2)
            .limit(10)
            .forEach(System.out::println);

        Stream.generate(Math::random)
            .limit(5)
            .forEach(System.out::println);

        IntStream ones = IntStream.generate(() -> 1);


    }

}
