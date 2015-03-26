/**
 * @test
 * @summary 
 * @library ../
 * @(#)ParallelArrayIterateTest.java
 * @author Tristan Yan
 * @run testng ParallelArrayIterateTest
 */

import java.util.Arrays;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ParallelArrayIterateTest {
    @Test
    public void parallelForEach() {
        AtomicInteger sum1 = new AtomicInteger(0);
        Integer[] array1 = this.createIntegerArray(16);
        
        Arrays.stream(array1).parallel().forEach(t -> sum1.getAndAdd(t));
        assertEquals(16, sum1.get());

        AtomicInteger sum2 = new AtomicInteger(0);
        Integer[] array2 = this.createIntegerArray(7);
        Arrays.stream(array2).parallel().forEach(t -> sum2.getAndAdd(t));
        assertEquals(7, sum2.get());

        Integer[] array3 = this.createIntegerArray(15);
        int sum3 = Arrays.stream(array3).parallel().reduce(0, (t1, t2) -> t1 + t2);
        assertEquals(15, sum3);

        Integer[] array4 = this.createIntegerArray(35);
        Optional<Integer> sum4 = Arrays.stream(array4).parallel().reduce((t1, t2) -> t1 + t2);
        assertEquals(35, sum4.get().intValue());

        Integer[] array5 = this.createIntegerArray(40);
        Arrays.parallelPrefix(array5, (t1, t2) -> t1 + t2);
        assertEquals(40, array5[array5.length -1].intValue());
    }

    private Integer[] createIntegerArray(int size) {
        Integer[] array = new Integer[size];
        for (int i = 0; i < size; i++) {
            array[i] = 1;
        }
        return array;
    }

    @Test(expectedExceptions = ArithmeticException.class)
    public void parallelForEachException() {
        Integer[] oneToFive = createIntegerArray(5);
        Arrays.parallelPrefix(oneToFive, (t1,t2) -> t1-t2);
        
        AtomicInteger sum = new AtomicInteger(100);
        Arrays.stream(oneToFive).parallel().forEach(t -> sum.updateAndGet(s -> s/t));
    }
}
