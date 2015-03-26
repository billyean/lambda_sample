/**
 * @test
 * @summary Lambda support test for Arrays.asList();
 * @library ../
 * @(#)EmptyListTest.java
 * @author Tristan Yan
 * @run testng EmptyListTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class EmptyListTest {
    @Test
    public void max() {
        assertFalse(Arrays.<Integer>asList().stream().
                max(Comparator.naturalOrder()).isPresent());
    }

    @Test
    public void min() {
        assertFalse(Arrays.<Integer>asList().stream().
                min(Comparator.reverseOrder()).isPresent());
    }

    @Test
    public void maxBy() {
        assertFalse(Arrays.<Integer>asList().stream().map(i -> i.toString()).
                max(Comparator.naturalOrder()).isPresent());
    }

    @Test
    public void minBy() {
        assertFalse(Arrays.<Integer>asList().stream().map(i -> i.toString()).
                min(Comparator.reverseOrder()).isPresent());
    }

    @Test
    public void sortThis() {
        List<Object> expected = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<Object> sortedList = list.stream().sorted()
                .collect(Collectors.toList());
        assertEquals(expected, sortedList);
        assertEquals(sortedList, list);
    }

    @Test
    public void sortThisBy() {
        List<Object> expected = new ArrayList<>();
        List<Object> list = new ArrayList<>();
        List<Object> sortedList = list.stream()
                .sorted((o1, o2) -> o1.toString().compareTo(o2.toString()))
                .collect(Collectors.toList());
        assertEquals(expected, sortedList);
        assertEquals(sortedList, list);
    }
}