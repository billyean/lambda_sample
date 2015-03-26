/**
 * @test
 * @summary 
 * @library ../
 * @(#)MultiplyFunctionTest.java
 * @author Tristan Yan
 * @run testng MultiplyFunctionTest
 */
import java.util.function.BiFunction;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class MultiplyFunctionTest {
    private final static BiFunction<Integer, Integer, Long> MultiplyIntegerFunction = (i1, i2) -> (long) i1 * i2;

    private final static BiFunction<Double, Double, Double> MultiplyDoubleFunction = (d1, d2) -> d1 * d2;

    private final static BiFunction<Long, Long, Long> MultiplyLongFunction = (l1, l2) -> l1 * l2;

    @Test
    public void integerBlock() {
        assertEquals(Long.valueOf(20), MultiplyIntegerFunction.apply(2, 10));
    }

    @Test
    public void doubleBlock() {
        assertEquals(new Double(20), MultiplyDoubleFunction.apply(2.0, 10.0));
    }

    @Test
    public void longBlock() {
        assertEquals(Long.valueOf(20), MultiplyLongFunction.apply(2L, 10L));
    }
}