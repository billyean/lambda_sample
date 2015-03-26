/**
 * @test
 * @summary 
 * @library ../
 * @(#)MinAndMaxBlocksTest.java
 * @author Tristan Yan
 * @run testng MinAndMaxBlocksTest
 */

import java.util.function.BiFunction;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MinAndMaxBlocksTest {
    private static class MinFunction {
        private static final BiFunction<Double, Double, Double> DOUBLE = (d1, d2) -> Math.min(d1, d2);

        private static final BiFunction<Long, Long, Long> LONG = (l1, l2) -> Math.min(l1, l2);

        private static final BiFunction<Integer, Integer, Integer> INTEGER = (i1, i2) -> Math.min(i1, i2);
    }

    private static class MaxFunction {
        private static final BiFunction<Double, Double, Double> DOUBLE = (d1, d2) -> Math.max(d1, d2);

        private static final BiFunction<Long, Long, Long> LONG = (l1, l2) -> Math.max(l1, l2);

        private static final BiFunction<Integer, Integer, Integer> INTEGER = (i1, i2) -> Math.max(i1, i2);
    }

    @Test
    public void minBlocks() {
        assertEquals(new Double(1.0), MinFunction.DOUBLE.apply(1.0, 2.0));
        assertEquals(new Double(0.0), MinFunction.DOUBLE.apply(0.0, 1.0));
        assertEquals(new Double(-1.0), MinFunction.DOUBLE.apply(1.0, -1.0));

        assertEquals(Integer.valueOf(1), MinFunction.INTEGER.apply(1, 2));
        assertEquals(Integer.valueOf(0), MinFunction.INTEGER.apply(0, 1));
        assertEquals(Integer.valueOf(-1), MinFunction.INTEGER.apply(1, -1));

        assertEquals(Long.valueOf(1L), MinFunction.LONG.apply(1L, 2L));
        assertEquals(Long.valueOf(0L), MinFunction.LONG.apply(0L, 1L));
        assertEquals(Long.valueOf(-1L), MinFunction.LONG.apply(1L, -1L));
    }

    @Test
    public void maxBlocks() {
        assertEquals(new Double(2.0), MaxFunction.DOUBLE.apply(1.0, 2.0));
        assertEquals(new Double(1.0), MaxFunction.DOUBLE.apply(0.0, 1.0));
        assertEquals(new Double(1.0), MaxFunction.DOUBLE.apply(1.0, -1.0));

        assertEquals(Integer.valueOf(2), MaxFunction.INTEGER.apply(1, 2));
        assertEquals(Integer.valueOf(1), MaxFunction.INTEGER.apply(0, 1));
        assertEquals(Integer.valueOf(1), MaxFunction.INTEGER.apply(1, -1));

        assertEquals(Long.valueOf(2L), MaxFunction.LONG.apply(1L, 2L));
        assertEquals(Long.valueOf(1L), MaxFunction.LONG.apply(0L, 1L));
        assertEquals(Long.valueOf(1L), MaxFunction.LONG.apply(1L, -1L));
    }
}