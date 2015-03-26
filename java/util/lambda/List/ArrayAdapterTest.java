/**
 * @test
 * @summary Lambda support test for Arrays.asList();
 * @library ../
 * @(#)ArrayAdapterTest.java
 * @author Tristan Yan
 * @run testng ArrayAdapterTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ArrayAdapterTest {
    protected <T> List<T> classUnderTest() {
        return Arrays.asList();
    }

    protected <T> List<T> newWith(T... littleElements) {
        return Arrays.asList(littleElements);
    }

    @Test
    public void testForEach() {
        List<Integer> result = new ArrayList<Integer>();
        List<Integer> collection = this.newWith(1, 2, 3, 4);
        collection.forEach(t -> result.add(t));
        assertEquals(result, Arrays.asList(1, 2, 3, 4));
    }

    @Test
    public void testForEachFromTo() {
        List<Integer> result = new ArrayList<Integer>();
        List<Integer> collection = this.newWith(1, 2, 3, 4);
        collection.stream().substream(2, 4).forEach(t -> result.add(t));
        assertEquals(result, Arrays.asList(3, 4));
    }

    @Test
    public void testForEachWithIndex(){
        List<Integer> result = new ArrayList<Integer>();
        List<Integer> collection = this.newWith(1, 2, 3, 4);
        collection.forEach(t -> result.add(t + collection.indexOf(t)));
        assertEquals(result, Arrays.asList(1, 3, 5, 7));
    }

    @Test
    public void testAllMatch() {
        assertTrue(this.newWith(1, 2, 3).stream().allMatch(t -> t instanceof Integer));
        assertFalse(this.newWith(1, 2, 3).stream().allMatch(Predicate.isEqual(1)));
    }

    @Test
    public void testAnyMatch() {
        assertTrue(this.newWith(1, 2, 3).stream().anyMatch(Predicate.isEqual(1)));
    }

    @Test
    public void testCollectIf() {
        assertEquals(this.newWith(1, 2, 3).stream().filter(Predicate.isEqual(1))
                .map(t -> t.toString()).collect(Collectors.toList()),
                Arrays.asList("1"));
    }

    @Test
    public void testSelectWith() {
        assertEquals(newWith(1, 2, 3, 4, 5).stream().
                filter(t -> t < 3).toArray(), new int[]{1, 2});
        assertEquals(newWith(-1, 2, 3, 4, 5).stream().
                filter(t -> t >= 3).toArray(), new int[]{3, 4, 5});
    }

    @Test
    public void testFindAny() {
        assertEquals(Integer.valueOf(3), newWith(1, 2, 3, 4, 5).stream()
                .filter(t -> t == 3).findAny().get());
        assertFalse(newWith(1, 2, 3, 4, 5).stream().filter(t -> t == 6)
                .findAny().isPresent());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void removeIf() {
        assertTrue(newWith(1, 2, 3, 4, 5).removeIf(t -> t < 3));
    }

    @Test
    public void replaceAll() {
        List<Integer> l = newWith(1, 2, 3, 4, 5);
        l.replaceAll(t -> t * t);
        assertEquals(l, Arrays.asList(1, 4, 9, 16, 25));
    }
}