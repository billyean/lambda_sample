/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)IntegerPredicatesTest.java
 * @author Tristan Yan
 * @run testng IntegerPredicatesTest
 */

import java.util.function.IntPredicate;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class IntegerPredicatesTest {
    @Test
    public void isOdd() {
        IntPredicate isOdd = i -> ((i & 0x01) == 0x01);
        assertTrue(isOdd.test(1));
        assertFalse(isOdd.test(-2));
    }

    @Test
    public void isEven() {
        IntPredicate isEven = i -> ((i & 0x01) == 0x00);
        assertTrue(isEven.test(-42));
        assertTrue(isEven.test(0));
        assertFalse(isEven.test(1));
    }

    @Test
    public void isZero() {
        IntPredicate isZero = i -> i == 0;
        assertFalse(isZero.test(-42));
        assertTrue(isZero.test(0));
        assertFalse(isZero.test(1));
        assertFalse(isZero.test(-1));
    }

    @Test
    public void isPositive() {
        IntPredicate isPositive = i -> i > 0;
        assertFalse(isPositive.test(0));
        assertTrue(isPositive.test(1));
        assertFalse(isPositive.test(-1));
        assertFalse(isPositive.test(-42));
    }

    @Test
    public void isNegative() {
        IntPredicate isNegative = i -> i < 0;
        assertFalse(isNegative.test(0));
        assertFalse(isNegative.test(1));
        assertTrue(isNegative.test(-1));
        assertTrue(isNegative.test(-42));
    }
}
