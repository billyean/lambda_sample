/**
 * @test
 * @summary Lambda support test for empty TreeSet;
 * @library ../
 * @(#)EmptySortedSetTest.java
 * @author Tristan Yan
 * @run testng EmptySortedSetTest
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.testng.Assert.*;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

public class EmptySortedSetTest {
    private final SortedSet<Integer> emptySet;

    EmptySortedSetTest(SortedSet<Integer> emptySet){
        this.emptySet = emptySet;
    }

    @Factory
    public static Object[] createTest() {
        List<EmptySortedSetTest> tests = new ArrayList<>();
        SortedSet<Integer> emptySet1 = new TreeSet<>();
        tests.add(new EmptySortedSetTest(emptySet1));
        SortedSet<Integer> emptySet2 = new TreeSet<>(Comparator.<Integer>naturalOrder());
        tests.add(new EmptySortedSetTest(emptySet2));
        return tests.toArray();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testFindAny() {
        emptySet.stream().filter(Predicate.isEqual(1)).findAny().get();
    }

    @Test
    public void testAllMatch() {
        assertTrue(emptySet.stream().allMatch(i -> i instanceof Integer) );
    }

    @Test
    public void testAnyMatch() {
        assertFalse(emptySet.stream().anyMatch(i -> i instanceof Integer));
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testGetFirst() {
        emptySet.stream().findFirst().get();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void min() {
        emptySet.stream().min(Comparator.<Integer>naturalOrder()).get();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void max() {
        emptySet.stream().max(Comparator.<Integer>naturalOrder()).get();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void minBy() {
        Function<Integer,String> func =  i -> i.toString();
        emptySet.stream().min(Comparator.comparing(func)).get();
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void maxBy(){
        Function<Integer,String> func =  i -> i.toString();
        emptySet.stream().max(Comparator.comparing(func)).get();
    }
}