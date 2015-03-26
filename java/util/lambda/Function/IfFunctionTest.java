/**
 * @test
 * @summary Sanity test for Functions.forPredicate
 * @library ../
 * @(#)IfFunctionTest.java
 * @author Tristan Yan
 * @run testng IfFunctionTest
 */

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class IfFunctionTest {
    @Test
    public void iterate() {
        Map<Integer, Integer> map = new HashMap<>();
        map.put(1, 1);
        map.put(2, 2);
        map.put(3, 3);
        map.put(4, 4);
        map.put(5, 5);
        Function<Integer, Integer> ifFunction = i -> (i & 0x01) == 0 ?  1 : 0;

        List<Integer> result = map.values().stream().sorted().map(ifFunction).collect(Collectors.<Integer>toList());
        assertEquals(Arrays.asList(0, 1, 0, 1, 0), result);
    }

    @Test
    public void testIf() {
        Function<Integer, Boolean> function = i -> i > 5 ? true : false;
        assertTrue(function.apply(10));
    }

    @Test
    public void ifElse() {
        Function<Integer, Boolean> function = i -> i > 5 ? true : false;
        assertFalse(function.apply(1));
    }
}