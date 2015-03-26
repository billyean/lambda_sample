/**
 * @test
 * @summary Sanity test for Functions.forPredicate
 * @library ../
 * @(#)ToFunctionTest.java
 * @author Tristan Yan
 * @run testng ToFunctionTest
 */

import java.util.function.ToDoubleFunction;
import java.util.function.ToIntFunction;
import java.util.function.ToLongFunction;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ToFunctionTest {
    private static final Object JUNK = new Object();

    ToDoubleFunction<Object>  tdf = o -> ((o instanceof Double) ? ((Double)o).doubleValue() : 0.0d);

    ToIntFunction<Object>  tif = o -> ((o instanceof Integer) ? ((Integer)o).intValue() : -1);

    ToLongFunction<Object>  tlf = o -> ((o instanceof Long) ? ((Long)o).longValue() : Long.MIN_VALUE);

    @Test
    public void testApplyAsDouble() {
        assertEquals(0.0d, tdf.applyAsDouble(JUNK));
        assertEquals(1.0d, tdf.applyAsDouble(1.0d));
    }

    @Test
    public void testApplyAsInt() {
        assertEquals(-1, tif.applyAsInt(JUNK));
        assertEquals(10, tif.applyAsInt(10));
    }

    @Test
    public void testApplyAsLong() {
        assertEquals(Long.MIN_VALUE, tlf.applyAsLong(JUNK));
        assertEquals(Integer.MAX_VALUE + 1L, tlf.applyAsLong(Integer.MAX_VALUE + 1L));
    }
}