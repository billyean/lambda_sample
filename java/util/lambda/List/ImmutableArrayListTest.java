/**
 * @test
 * @summary Lambda support test for Arrays.asList();
 * @library ../
 * @(#)ImmutableArrayListTest.java
 * @author Tristan Yan
 * @run testng ImmutableArrayListTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ImmutableArrayListTest {
    @Test
    public void forEach() {
        List<Integer> result = new ArrayList<>();
        List<Integer> collection = Arrays.asList(1, 2, 3, 4);
        collection.forEach(i -> result.add(i));
        assertEquals(4, result.size());
        assertEquals(result, Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void forEachFromTo() {
        List<Integer> result = new ArrayList<>();
        List<Integer> collection = Arrays.asList(1, 2, 3, 4);
        AtomicInteger index = new AtomicInteger(0);
        collection.forEach(i -> { if(index.getAndIncrement() > 1) 
            result.add(i);
        });
        assertEquals(2, result.size());
        assertEquals(result, Arrays.asList(3, 4));
    }

    @Test
    public void forEachWithIndex(){
        List<Integer> result = new ArrayList<>();
        List<Integer> collection = Arrays.asList(1, 2, 3, 4);
        AtomicInteger index = new AtomicInteger(0);
        collection.forEach(i -> result.add(i + index.getAndIncrement()));
        assertEquals(result, Arrays.asList(1, 3, 5, 7));
    }

    @Test
    public void filter(){
        assertEquals(Arrays.asList(1, 2, 3, 4, 5).stream().filter(i -> i < 3).toArray(), new Integer[]{1,2});
        assertNotEquals(Arrays.asList(-1, 2, 3, 4, 5).stream().filter(i -> i < 3).toArray(), new Integer[]{3,4,5});
    }

    @Test
    public void reject(){
        Predicate<Integer> p = i -> i < 3;
        assertNotEquals(Arrays.asList(1, 2, 3, 4, 5).stream().filter(p.negate()).toArray(), new Integer[]{1,2});
    }

    @Test
    public void partitionBy() {
        Map<Boolean, List<Integer>> partition = Arrays.asList(1, 2, 3, 4, 5).stream().collect(Collectors.partitioningBy(i -> i < 3));
        assertEquals(Arrays.asList(1, 2), partition.get(true));
        assertEquals(Arrays.asList(3, 4, 5), partition.get(false));
    }

    @Test
    public void collect() {
        assertEquals(Arrays.asList(1, 2, 3, 4).stream().
                collect(Collectors.mapping(i -> String.valueOf(i), Collectors.toList())), Arrays.asList("1", "2", "3", "4"));
    }
    @Test
    public void detect() {
        assertEquals(Integer.valueOf(3), Arrays.asList(1, 2, 3, 4, 5).stream().filter(i -> i ==3).findAny().get());
        assertFalse(Arrays.asList(1, 2, 3, 4, 5).stream().filter(i -> i == 6).findAny().isPresent());
    }

    @Test
    public void allMatch() {
        assertTrue(Arrays.asList(1, 2, 3).stream().allMatch(i -> i instanceof Integer));
        assertFalse(Arrays.asList(1, 2, 3).stream().allMatch(i -> i == 1));
    }

    @Test
    public void anyMatch() {
        assertTrue(Arrays.asList(1, 2, 3).stream().anyMatch(i -> i instanceof Integer));
        assertFalse(Arrays.asList(1, 2, 3).stream().anyMatch( i -> i > 10));
    }

    @Test
    public void count() {
        assertEquals(3, Arrays.asList(1, 2, 3).stream().count());
    }

    @Test
    public void getFirst() {
        assertEquals(Integer.valueOf(1), Arrays.asList(1, 2, 3).stream().findFirst().get());
        assertNotEquals(Integer.valueOf(3), Arrays.asList(1, 2, 3).stream().findFirst().get());
    }
}