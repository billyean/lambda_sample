/**
 * @test
 * @summary Comparators sanity tests
 * @library ../domain/
 * @(#)IntStreamTest.java
 * @author Tristan Yan
 * @run testng IntStreamTest
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.IntPredicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class IntStreamTest {
    @Test
    public void into() {
        long[] oneToSix = LongStream.range(1, 10).filter(t -> t < 7).toArray();
        assertEquals(oneToSix, new long[]{1, 2, 3, 4, 5, 6});
    }
        
    @Test
    public void sum() {
        long sum = IntStream.range(1, 5).filter(t -> t < 5).sum();
        assertEquals(10, sum);
    }
    
    @Test
    public void fromAndToAndBy() {
        IntPredicate isOdd = i -> 1 == (i & 1);
        int[] oneToNineByTwo = IntStream.rangeClosed(1, 9).filter(isOdd).toArray();
        int[] oneToTenByTwo = IntStream.range(1, 10).filter(isOdd).toArray();
        assertEquals(new int[]{1 ,3, 5, 7, 9}, oneToNineByTwo);
        assertEquals(oneToTenByTwo, oneToNineByTwo);
    }

    @Test
    public void equals() {
        int[] oneToFive1 = IntStream.range(1, 6).toArray();
        int[] oneToFive2 = IntStream.range(1, 6).toArray();
        int[] zeroToFive = IntStream.range(0, 6).toArray();
        assertEquals(oneToFive1, oneToFive2);
        assertNotEquals(oneToFive1, zeroToFive);
        assertNotEquals(zeroToFive, oneToFive1);
        
        IntPredicate isOdd = i -> 1 == (i & 1);

        assertEquals(IntStream.rangeClosed(1, 6).filter(isOdd).toArray(), 
                IntStream.range(1, 7).filter(isOdd).toArray());
        assertEquals(new int[]{1, 2, 3}, IntStream.range(1, 4).toArray());
        assertEquals(new int[]{3, 2, 1}, IntStream.iterate(3, i -> --i).limit(3).toArray());

        assertNotEquals(new int[]{1, 2, 3, 4}, IntStream.range(1, 4).toArray());
        assertNotEquals(new int[]{1, 2, 4}, IntStream.range(1, 4).toArray());
        assertNotEquals(new int[]{3, 2, 0}, IntStream.iterate(3, i -> --i).limit(3).toArray());

        assertEquals(new int[]{-1, -2, -3}, IntStream.iterate(-1, i -> --i).limit(3).toArray());
    }

    @Test
    public void forEachOnFromToStream() {
        List<Integer> result = new ArrayList<>();
        IntStream stream = IntStream.range(1, 6);
        stream.forEach(i -> result.add(i));
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
    }
 
    @Test
    public void reverseForEachOnFromToStream() {
        IntStream stream = IntStream.range(1, 6);
        List<Integer> result = stream.boxed().sorted(Comparator.<Integer>reverseOrder()).collect(Collectors.<Integer>toList());
        assertEquals(Arrays.asList(5, 4, 3, 2, 1), result);

        result = result.stream().sorted().collect(Collectors.<Integer>toList());
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
    }

    @Test
    public void forEachOnFromToByStream() {
        List<Integer> result = new ArrayList<Integer>();
        IntStream stream = IntStream.iterate(1, i -> i + 2).limit(3);
        stream.forEach(i -> result.add(i));
        assertEquals(Arrays.asList(1, 3, 5), result);
    }

    @Test
    public void forEachOnFromToByInterval2() {
        List<Integer> result = new ArrayList<Integer>();
        IntStream stream = IntStream.iterate(5, i -> i - 2).limit(3);
        stream.forEach(i -> result.add(i));
        assertEquals(Arrays.asList(5, 3, 1), result);
    }

    @Test
    public void sumIntStream() {
        int sum = IntStream.range(1, 6).sum();
        assertEquals(15, sum);
    }

    @Test
    public void maxIntStream() {
        int max = IntStream.range(1, 6).max().getAsInt();
        assertEquals(5, max);
    }

    @Test
    public void collectOnFromToByIntStream() {
        IntStream stream = IntStream.iterate(1, i -> i + 2).limit(3);
        List<String> result = stream.mapToObj(i -> String.valueOf(i)).collect(Collectors.<String>toList());
        assertEquals(Arrays.asList("1", "3", "5"), result);
    }

    @Test
    public void collectOnFromToIntStream() {
        IntStream stream = IntStream.range(1, 6);
        List<String> result = stream.mapToObj(i -> String.valueOf(i)).collect(Collectors.<String>toList());
        assertEquals(Arrays.asList("1", "2", "3", "4", "5"), result);
    }

    @Test
    public void selectOnFromToIntStream() {
        IntStream stream = IntStream.range(1, 6);
        assertEquals(new int[]{2, 4}, stream.filter(i -> (i & 0x01) == 0).toArray());
    }

    @Test
    public void reverseThis() {
        List<Integer> result = new ArrayList<Integer>();
        IntStream.iterate(5, i -> --i).limit(5).sorted().
                forEachOrdered(i -> result.add(i));
        assertEquals(Arrays.asList(1, 2, 3, 4, 5), result);
    }

    @Test
    public void intStreamAsArray() {
        assertEquals(new Integer[]{1, 2, 3, 4, 5}, IntStream.range(1, 6).boxed().toArray());
    }

    @Test
    public void intStreamAsIntArray() {
        assertEquals(new int[]{1, 2, 3, 4, 5}, IntStream.range(1, 6).toArray());
    }

    @Test
    public void toSet() {
        IntStream stream = IntStream.range(0, 11);
        Set<Integer> set = stream.boxed().collect(Collectors.<Integer>toSet());
        set.containsAll(Arrays.asList(0, 2, 4, 6, 8, 10));
        assertTrue(11 == set.size());
    }

    @Test
    public void size() {
        assertEquals(100, IntStream.range(1, 101).toArray().length);
        assertEquals(50, IntStream.range(1, 101).filter(i -> (i - 1) % 2 == 0).toArray().length);
        assertEquals(34, IntStream.range(1, 101).filter(i -> (i - 1) % 3 == 0).toArray().length);
        assertEquals(25, IntStream.range(1, 101).filter(i -> (i - 1) % 4 == 0).toArray().length);
        assertEquals(20, IntStream.range(1, 101).filter(i -> (i - 1) % 5 == 0).toArray().length);
        assertEquals(17, IntStream.range(1, 101).filter(i -> (i - 1) % 6 == 0).toArray().length);
        assertEquals(15, IntStream.range(1, 101).filter(i -> (i - 1) % 7 == 0).toArray().length);
        assertEquals(13, IntStream.range(1, 101).filter(i -> (i - 1) % 8 == 0).toArray().length);
        assertEquals(12, IntStream.range(1, 101).filter(i -> (i - 1) % 9 == 0).toArray().length);
        assertEquals(10, IntStream.range(1, 101).filter(i -> (i - 1) % 10 == 0).toArray().length);
        assertEquals(11, IntStream.range(0, 11).toArray().length);
        assertEquals(1, IntStream.range(0, 1).toArray().length);
        assertEquals(11, IntStream.iterate(0, i -> i--).limit(11).toArray().length);
    }
 
    @Test
    public void generateInt() {
        int[]  ia = IntStream.generate(() -> 1).limit(10).toArray();
        assertEquals(new int[]{1, 1, 1, 1, 1,1, 1, 1, 1, 1}, ia);
    }
 
    @Test
    public void iterateInt() {
        int[] ia = IntStream.iterate(0, i -> i +2).limit(5).toArray();
        assertEquals(new int[]{0, 2, 4, 6, 8}, ia);
    }
}
