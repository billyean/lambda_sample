/**
 * @test
 * @summary Lambda support test for TreeMap;
 * @library ../
 * @(#)DataSetTest.java
 * @author Tristan Yan
 * @run testng DataSetTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class DataSetTest {

    @Test
    public void forEach() {
        List<String> result1 = new ArrayList<>();
        Set<String> intSet1 = new HashSet<>();
        intSet1.add("1");
        intSet1.forEach(i -> result1.add(i));
        assertEquals(Arrays.asList("1"), result1);

        List<String> result2 = new ArrayList<>();
        Set<String> intSet2 = new HashSet<>();
        intSet2.add("1");
        intSet2.add("2");
        intSet2.forEach(i -> result2.add(i));
        Collections.sort(result2);
        assertEquals(Arrays.asList("1", "2"), result2);

        List<String> result3 = new ArrayList<>();
        Set<String> intSet3 = new HashSet<>();
        intSet3.add("1");
        intSet3.add("2");
        intSet3.add("3");
        intSet3.forEach(i -> result3.add(i));
        Collections.sort(result3);
        assertEquals(Arrays.asList("1", "2", "3"), result3);

        List<String> result4 = new ArrayList<>();
        Set<String> intSet4 = new HashSet<>();
        intSet4.add("1");
        intSet4.add("2");
        intSet4.add("3");
        intSet4.add("4");
        intSet4.forEach(i -> result4.add(i));
        Collections.sort(result4);
        assertEquals(Arrays.asList("1", "2", "3","4"), result4);

        List<String> result5 = new ArrayList<>();
        Set<String> intSet5= new HashSet<>();
        intSet5.add("1");
        intSet5.add("2");
        intSet5.add("3");
        intSet5.add("4");
        intSet5.add("5");
        intSet5.forEach(i -> result5.add(i));
        Collections.sort(result5);
        assertEquals(Arrays.asList("1", "2", "3","4", "5"), result5);
    }

    @Test
    public void forEachWith() {
        List<Integer> result1 = new ArrayList<>();
        Set<Integer> intSet1 = new HashSet<>();
        intSet1.add(1);
        intSet1.forEach(i -> result1.add(i));
        assertEquals(1, result1.size());
        assertTrue(result1.contains(1)); 
    }

//    @Test
//    public void filter() {
//        assertTrue(intSet.select(Predicates.lessThan(3)), 1);
//        Verify.assertEmpty(this.intSet.select(Predicates.greaterThan(3)));
//    }
}