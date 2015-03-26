/**
 * @test
 * @summary Lambda support test for List;
 * @library ../
 * @(#)DataListTest.java
 * @author Tristan Yan
 * @run testng DataListTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class DataListTest {
    @Test
    public void testForEachSingleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1"), result);
    }

    @Test
    public void testForEachWithIndexSingleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1"), result);
        assertEquals(0, indexSum[0]);
    }

    @Test
    public void testForEachDoubleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1", "2"), result);
    }

    @Test
    public void testForEachWithIndexDoubleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1", "2"), result);
        assertEquals(1, indexSum[0]);
    }

    @Test
    public void testForEachTripleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1", "2", "3"), result);
    }

    @Test
    public void testForEachWithIndexTripleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1", "2", "3"), result);
        assertEquals(3, indexSum[0]);
    }

    @Test
    public void testForEachQuadrupleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1", "2", "3", "4"), result);
    }

    @Test
    public void testForEachWithIndexQuadrupleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1", "2", "3", "4"), result);
        assertEquals(6, indexSum[0]);
    }
    @Test
    public void testForEachQuintupleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4", "5");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), result);
    }

    @Test
    public void testForEachWithIndexQuintupleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4", "5");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), result);
        assertEquals(10, indexSum[0]);
    }
    @Test
    public void testForEachSextupleton() {
        List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4", "5", "6");
        source.forEach(t -> result.add(t));
        assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"), result);
    }

    @Test
    public void testForEachWithIndexSextupleton() {
        final int[] indexSum = new int[1];
        final List<String> result = new ArrayList<>();
        List<String> source = Arrays.asList("1", "2", "3", "4", "5", "6");
        source.forEach(t -> {
            result.add(t);
            indexSum[0] += result.indexOf(t);
        });
        assertEquals(Arrays.asList("1", "2", "3", "4", "5", "6"), result);
        assertEquals(15, indexSum[0]);
    }
}