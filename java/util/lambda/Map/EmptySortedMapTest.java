/**
 * @test
 * @summary Lambda support test for List;
 * @library ../
 * @(#)EmptySortedMapTest.java
 * @author Tristan Yan
 * @run testng EmptySortedMapTest
 */

import java.util.Comparator;
import java.util.SortedMap;
import java.util.TreeMap;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class EmptySortedMapTest {
    private SortedMap<Integer, String> classUnderTest() {
        return new TreeMap<>();
    }

    protected SortedMap<Integer, String> 
            classUnderTest(Comparator<? super Integer> comparator) {
        return new TreeMap<>(comparator);
    }

    @Test
    public void computeIfAbsent() {
        // Absent key behavior
        SortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Integer absentKey = classUnderTest.size() + 1;
        String absentValue = String.valueOf(absentKey);
        assertEquals(absentValue, classUnderTest.computeIfAbsent(absentKey, k -> k.toString()));

        // Put 1 element
        assertEquals(classUnderTest.size(), 1);
        assertEquals(classUnderTest.get(absentKey), absentKey.toString());
    }

    @Test
    public void computeIfPresent() {
        // Absent key behavior
        SortedMap<Integer, String> classUnderTest = this.classUnderTest();
        Integer absentKey = classUnderTest.size() + 1;
        String absentValue = String.valueOf(absentKey);
        assertNull(classUnderTest.computeIfPresent(absentKey, (k ,v) -> String.valueOf(k)));

        // Put 1 element
        classUnderTest.put(absentKey, absentValue);
        assertEquals(classUnderTest.computeIfPresent(absentKey, (k ,v) -> String.valueOf(k + 1)),
                String.valueOf(absentKey + 1));
        assertEquals(classUnderTest.size(), 1);
        assertEquals(classUnderTest.get(absentKey), String.valueOf(absentKey + 1));

        assertNull(classUnderTest.computeIfPresent(absentKey, (k ,v) -> null));
        assertTrue(classUnderTest.isEmpty());
    }

    @Test
    public void replaceAll() {
        SortedMap<Integer, String> classUnderTest = this.classUnderTest();
        classUnderTest.replaceAll((k, v) -> String.valueOf(k+1));
        assertTrue(classUnderTest.isEmpty());
    }
}