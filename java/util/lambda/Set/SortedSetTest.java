/**
 * @test
 * @summary Lambda unit test for SortedSet.
 * @library ../
 * @(#) SortedSetTest.java
 * @author Tristan Yan
 * @run testng SortedSetTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class SortedSetTest {
    protected <T> SortedSet<T> classUnderTest(T... elements){
        SortedSet<T> set = new TreeSet<>();
        Collections.addAll(set, elements);
        return set;
    }

    protected <T> SortedSet<T> classUnderTest(Comparator c, T... elements){
        SortedSet<T> set = new TreeSet<>(c);
        Collections.addAll(set, elements);
        return set;
    }
    
    @Test
    public void select() {
        SortedSet<Integer> integers = this.classUnderTest(3, 2, 1, 4, 5);
        
        assertEquals(new TreeSet<>(Arrays.asList(1,2)), 
                integers.stream().filter(i -> i < 3).
                collect(Collectors.toCollection(() -> new TreeSet<Integer>())));
        assertTrue(integers.stream().filter(i -> i > 6).
                collect(Collectors.toCollection(() -> new TreeSet<Integer>())).isEmpty());
        
        TreeSet<Integer> three_two_one = new TreeSet<>(Collections.reverseOrder());
        three_two_one.addAll(Arrays.asList(3, 2, 1));
        assertEquals(three_two_one, 
                integers.stream().filter(i -> i < 4).
                collect(Collectors.toCollection(() -> new TreeSet<Integer>(Collections.reverseOrder()))));
    }

    @Test
    public void reject() {
        SortedSet<Integer> integers = this.classUnderTest(4, 2, 1, 3, 5, 6);
        assertEquals(new TreeSet<>(Arrays.asList(1, 2, 3, 4)), 
                integers.stream().filter(((Predicate<Integer>)(i -> i > 4)).negate()).
                collect(Collectors.toCollection(() -> new TreeSet<Integer>())));
        assertEquals(integers.stream().filter(((Predicate<Integer>)(i -> i > 0)).negate()).count(), 0);
        TreeSet<Integer> one_to_three = new TreeSet<>(Collections.reverseOrder());
        one_to_three.addAll(Arrays.asList(3, 2, 1));
        assertEquals(one_to_three, integers.stream().filter(((Predicate<Integer>)(i -> i > 3)).negate()).
                collect(Collectors.toCollection(() -> new TreeSet<Integer>(Collections.reverseOrder()))));
    }

    @Test
    public void partition() {
        SortedSet<Integer> integers
                = this.classUnderTest(Collections.<Integer>reverseOrder(), 4, 2, 1, 3, 5, 6);
        Map<Boolean, List<Integer>> partition = 
                integers.stream().collect(Collectors.<Integer>partitioningBy(i -> (i & 0x01) == 0));
        assertEquals(Arrays.asList(6, 4, 2), partition.get(true));
        assertEquals(Arrays.asList(5, 3, 1), partition.get(false));
    }

    @Test
    public void collect() {
        SortedSet<Integer> integers
                = this.classUnderTest(4, 2, 1, 3, 5, 6);
        assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"), 
                integers.stream().map(i -> String.valueOf(i)).collect(Collectors.toList()));
        SortedSet<Integer> revInt = this.classUnderTest(Collections.<Integer>reverseOrder(), 1, 2, 4, 3, 5);
        assertEquals(Arrays.asList("5", "4", "3", "2", "1"), 
                revInt.stream().map(i -> String.valueOf(i)).collect(Collectors.toList()));
    }

    @Test
    public void flatCollect() {
        SortedSet<Integer> collection = this.classUnderTest(Collections.<Integer>reverseOrder(), 2, 4, 2, 1, 3);
        Function<Integer, Stream<String>> function = i -> {
                 List<String> l = new ArrayList<>();
                 l.add(String.valueOf(i));
                 return l.stream();
        };
        assertEquals(Arrays.asList("4", "3", "2", "1"),
                collection.stream().flatMap(function).collect(Collectors.toList()));
    }

    @Test
    public void groupBy() {
        SortedSet<Integer> integers = this.classUnderTest(Comparator.
                reverseOrder(), 1, 2, 3, 4, 5, 6, 7, 8, 9);
        Predicate<Integer> isOddFunction = i -> (i & 0x01) == 0x01;
        Collector<Integer, ?, TreeSet> toTreeSet = 
                Collectors.<Integer,TreeSet>toCollection(TreeSet<Integer>::new);
        Collector<Integer, ?, Map<Boolean, TreeSet>> parationCol =
                Collectors.partitioningBy(isOddFunction, toTreeSet);
        Map<Boolean, TreeSet> map = integers.stream().collect(parationCol);
        assertEquals(this.classUnderTest(Comparator.reverseOrder(), 8, 6, 4, 2),
                map.get(false));
        assertEquals(2, map.keySet().size());
    }

    @Test
    public void forEachWithIndex() {
        SortedSet<Integer> collection = this.classUnderTest(Comparator.<Integer>reverseOrder(), 1, 2, 3, 4);
        AtomicInteger index = new AtomicInteger(0);
        List<Integer> result = new ArrayList<>();
        
        collection.forEach(i -> {
            result.add(i + index.incrementAndGet());
        });
        assertEquals(Arrays.asList(5, 5, 5, 5), result);
    }
}