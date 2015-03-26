/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)LongPredicatesTest.java
 * @author Tristan Yan
 * @run testng LongPredicatesTest
 */

import java.util.function.LongPredicate;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author tristan
 */
public class LongPredicatesTest {
    @Test
    public void isOdd() {
        LongPredicate isOdd = l -> ((l & 0x01) == 0x01);
        assertTrue(isOdd.test(1));
        assertFalse(isOdd.test(-2));
    }

    @Test
    public void isEven() {
        LongPredicate isEven = l -> ((l & 0x01) == 0x00);
        assertTrue(isEven.test(-42));
        assertTrue(isEven.test(0));
        assertFalse(isEven.test(1));
    }

    @Test
    public void isZero() {
        LongPredicate isZero = l -> l == 0;
        assertFalse(isZero.test(-42));
        assertTrue(isZero.test(0));
        assertFalse(isZero.test(1));
        assertFalse(isZero.test(-1));
    }

    @Test
    public void isPositive() {
        LongPredicate isPositive = l -> l > 0;
        assertFalse(isPositive.test(0));
        assertTrue(isPositive.test(1));
        assertFalse(isPositive.test(-1));
        assertFalse(isPositive.test(-42));
    }

    @Test
    public void isNegative() {
        LongPredicate isNegative = l -> l < 0;
        assertFalse(isNegative.test(0));
        assertFalse(isNegative.test(1));
        assertTrue(isNegative.test(-1));
        assertTrue(isNegative.test(-42));
    }
}
