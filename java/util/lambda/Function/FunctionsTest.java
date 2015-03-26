/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)FunctionsTest.java
 * @author Tristan Yan
 * @run testng FunctionsTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

/**
 *
 * @author tristan
 */
public class FunctionsTest {
    private static final Function<String, Integer> STRING_LENGTH = s -> s.length();

    private static final Function<Integer, Boolean> IS_ODD = i -> i % 2 != 0;

    private static final Function<Boolean, String> BOOLEAN_STRING = b -> b.toString();

    @Test
    public void getIdentity() {
        Object object = new Object();
        assertTrue(object == Function.identity().apply(object));
    }

    @Test
    public void getConst(){
        ToIntFunction<Object> iff = p -> 5;
        assertEquals(5, iff.applyAsInt(null));
    }

    @Test
    public void getMathSinFunction() {
        Function<Double, Double> function = d -> Math.sin(d);
        assertEquals(Math.sin(1.0), function.apply(1.0));
    }

    @Test
    public void getNumberIdentity() {
        Function<Number, Number> function = Function.identity();
        assertEquals(1, function.apply(1));
    }

    @Test
    public void getIntegerIdentity() {
        Function<Integer, Integer> function = Function.identity();
        assertTrue(1 == function.apply(1));
    }

    @Test
    public void getLongIdentity() {
        Function<Long, Long> function = Function.identity();
        assertTrue(1l == function.apply(1l));
    }

    @Test
    public void getDoubleIdentity() {
        Function<Double, Double> function = Function.identity();
        assertTrue(1.0 == function.apply(1.0));
    }

    @Test
    public void getStringIdentity() {
        Function<String, String> function = Function.identity();
        assertEquals("hello", function.apply("hello"));
    }

    @Test
    public void getStringTrim() {
        Function<String, String> function = t -> t.trim();
        assertEquals("hello", function.apply(" hello  "));
    }

    @Test
    public void getToString() {
        Function<Object, String> function = t -> String.valueOf(t);
        assertEquals("1", function.apply(1));
        assertEquals("null", function.apply(null));
    }

    @Test
    public void getStringToInteger() {
        Function<String, Integer> function = s -> Integer.valueOf(s);
        assertEquals(Integer.valueOf(1), function.apply("1"));
    }

    @Test
    public void chains() {
        Function<String, Integer> toInteger = s -> Integer.valueOf(s);
        Function<Object, String> toString = o -> o.toString();

        assertEquals("42", toInteger.andThen(toString).apply("42"));
        assertEquals(Integer.valueOf(42), toString.andThen(toInteger).apply(42));

        Function<String, Integer> chain = toInteger.andThen(toString).andThen(toInteger);
        assertEquals(Integer.valueOf(42), chain.apply("42"));
        assertEquals("42", toString.andThen(toInteger).andThen(toString).apply(42));

        assertEquals("42", toInteger.andThen(toString).andThen(toInteger).andThen(toString).apply("42"));
        assertEquals(Integer.valueOf(42), toString.andThen(toInteger).andThen(toString).andThen(toInteger).apply(42));

        assertEquals(Integer.valueOf(42), toInteger.andThen(toString).andThen(toInteger).andThen(toString).andThen(toInteger).apply("42"));
        assertEquals(Integer.valueOf(42), toString.andThen(toInteger).andThen(toString).andThen(toInteger).andThen(toString).andThen(toInteger).apply(42));
    }

    @Test
    public void chain_two() {
        Function<Boolean, Integer> chain = BOOLEAN_STRING.andThen(STRING_LENGTH);
        assertEquals(Integer.valueOf(5), chain.apply(Boolean.FALSE));
    }

    @Test
    public void chain_three() {
        Function<String, String> chain = STRING_LENGTH.andThen(IS_ODD).andThen(BOOLEAN_STRING);
        assertEquals("true", chain.apply("foo"));
    }

    @Test
    public void chain_four() {
        Function<Integer, Boolean> chain = IS_ODD.andThen(BOOLEAN_STRING).andThen(STRING_LENGTH).andThen(IS_ODD);
        assertEquals(Boolean.TRUE, chain.apply(Integer.valueOf(4)));
    }

    @Test
    public void intValueFunctionToComparator() {
        List<Integer> list1 = IntStream.range(1, 101).boxed().collect(Collectors.<Integer>toList());
        List<Integer> list2 = new ArrayList(list1);
        Collections.shuffle(list1);
        list1.sort(Comparator.naturalOrder());
        assertEquals(list1, list2);
    }

    @Test
    public void doubleValueFunctionToComparator() {
        List<Double> list = Arrays.asList(5.0, 4.0, 3.0, 2.0, 1.0);
        Collections.shuffle(list);
        list.sort(Comparator.naturalOrder());
        assertEquals(Arrays.asList(1.0, 2.0, 3.0, 4.0, 5.0), list);
    }

    @Test
    public void longValueFunctionToComparator() {
        List<Long> list = Arrays.asList(Long.MAX_VALUE, 1L, 0L, -1L, Long.MIN_VALUE);
        Collections.shuffle(list);
        list.sort(Comparator.<Long>naturalOrder());;
        assertEquals(Arrays.asList(Long.MIN_VALUE, -1L, 0L, 1L, Long.MAX_VALUE), list);
    }

    @Test
    public void pair() {
        Person john = new Person("John", "Smith");
        Person jane = new Person("Jane", "Smith");
        Person johnDoe = new Person("John", "Doe");
        List<Person> people = Arrays.asList(john, jane, johnDoe);
        people.sort(Comparator.comparing(Person.LAST).thenComparing(Comparator.comparing(Person.FIRST)));
        assertEquals(Arrays.asList(johnDoe, jane, john), people);
    }

    @Test
    public void key() {
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Function<Map.Entry<String, Integer>, String> keyFunc = me -> me.getKey();
        Set<String> keys = entries.stream().map(keyFunc).collect(Collectors.<String>toSet());
        Set<String> oneSet = new HashSet<>();
        oneSet.add("One");
        assertEquals(oneSet, keys);
    }

    @Test
    public void value() {
        Map<String, Integer> map = new HashMap<>();
        map.put("One", 1);
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        Function<Map.Entry<String, Integer>, Integer> valueFunc = me -> me.getValue();
        Set<Integer> keys = entries.stream().map(valueFunc).collect(Collectors.<Integer>toSet());
        Set<Integer> oneSet = new HashSet<>();
        oneSet.add(1);
        assertEquals(oneSet, keys);
    }

    @Test
    public void size() {
        List<List<Integer>> list = Arrays.asList(Arrays.asList(1), Arrays.asList(1, 2), Arrays.asList(1, 2, 3));
        List<Integer> sizes = list.stream().map(c -> c.size()).collect(Collectors.<Integer>toList());
        assertEquals(Arrays.asList(1, 2, 3), sizes);
    }

    @Test
    public void squaredCollection() {
        int[] square = Arrays.asList(1, 2, 3, 4, 5).stream().mapToInt(t -> t * t).toArray();
        assertEquals(square, new int[]{1, 4, 9, 16, 25});
    }
}
