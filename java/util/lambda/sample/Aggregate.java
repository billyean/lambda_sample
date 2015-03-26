/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Aggregate.java
 * @author Tristan Yan
 * @run testng Aggregate
 */

import java.util.Arrays;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class Aggregate {

    /*
     * This sample uses Count to get the number of unique prime factors of 300.
     */
    @Test
    public void uniqueFactor() {
        int[] primeFactorsOf300 = { 2, 2, 3, 5, 5 };
        
        assertEquals(Arrays.stream(primeFactorsOf300).distinct().toArray().length, 3);
    }
}
