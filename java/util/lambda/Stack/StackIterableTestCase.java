/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)StackIterableTestCase.java
 * @author Tristan Yan
 * @run testng StackIterableTestCase
 */
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class StackIterableTestCase {
    @Test
    public void collect() {
        Stack<Boolean> stack = new Stack<>();
        stack.add(Boolean.TRUE);
        stack.add(Boolean.FALSE);
        stack.add(null);

        Stack<String> expected = new Stack<>();
        expected.add("true");
        expected.add("false");
        expected.add("null");

        Function<Boolean, String> bToStr = b -> (b == null ? "null" : b.toString());
        assertEquals(expected,
                stack.stream().map(bToStr).collect(Collectors.toCollection(Stack::new)));

        assertEquals(Arrays.asList("true", "false", "null"),
                stack.stream().map(bToStr).collect(Collectors.toList()));
    }

    @Test
    public void collectIf() {
        Stack<Integer> stack = new Stack<>();
        for(int i = 1; i <= 5; i++)
            stack.add(i);

        Predicate<Integer> predicate1 = i -> i < 3;
        Function<Integer, String> function1 = i -> i.toString();
        assertEquals(Arrays.asList("1", "2"),
                stack.stream().filter(predicate1).map(function1).collect(Collectors.toList()));

    }

    @Test
    public void collectWith() {
        Stack<Integer> stack = new Stack<>();
        for(int i = 3; i >= 1; i--)
            stack.add(i);
        assertEquals(Arrays.asList(4, 3, 2),
                stack.stream().map(i -> i + 1).collect(Collectors.toList()));
    }

        @Test
    public void flatCollect() {
        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("One");
        stack.push("2");
        stack.push("Two");

        Function<String, Stream<Character>> fm =  s -> {
            List<Character> l = new ArrayList<>();
            for(int i = 0; i < s.length(); i++)
                l.add(s.charAt(i));
            return l.stream();
        };

        Stack<Character> expected = new Stack<>();
        expected.push('1');
        expected.push('O');
        expected.push('n');
        expected.push('e');
        expected.push('2');
        expected.push('T');
        expected.push('w');
        expected.push('o');

        assertEquals(expected, stack.stream().flatMap(fm).
            collect(Collectors.toCollection(Stack<Character>::new)));

        assertEquals(Arrays.asList('1', 'O', 'n', 'e', '2', 'T', 'w', 'o'),
            stack.stream().flatMap(fm).collect(Collectors.<Character>toList()));
    }

    @Test
    public void select() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2); 
        stack.push(3); 
        
        Stack<Integer> expectedOne = new Stack<>();
        expectedOne.push(1);

        assertEquals(expectedOne, stack.stream().filter(i -> i == 1).
                collect(Collectors.toCollection(Stack<Integer>::new)));
        Stack<Integer> expectedTwoThree = new Stack<>();
        expectedTwoThree.push(2);
        expectedTwoThree.push(3);
        assertEquals(expectedTwoThree,stack.stream().
                filter(t -> t > 1).collect(Collectors.toCollection(Stack<Integer>::new)));
        assertEquals(Arrays.asList(2, 3),stack.stream().
                filter(t -> t > 1).collect(Collectors.<Integer>toList()));
    }

    @Test
    public void reject() {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(2);
        stack.push(1);
        Predicate<Integer> predicate = i -> i > 2;

        Stack<Integer> expected = new Stack<>();
        expected.push(2);
        expected.push(1);
        assertEquals(expected, stack.stream().filter(predicate.negate()).
                collect(Collectors.toCollection(Stack<Integer>::new)));
        assertEquals(Arrays.asList(2, 1),stack.stream().filter(predicate.negate()).
                collect(Collectors.toList()));
    }

    @Test
    public void detect() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        Predicate<Integer> predicate = i -> i < 3;
        assertEquals(Integer.valueOf(1), stack.stream().filter(predicate).findFirst().get());
        assertFalse(stack.stream().filter(i -> i == 4).findAny().isPresent());
    }

    @Test
    public void partition() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Predicate<Integer> predicate = i -> i < 3;
        Map<Boolean, List<Integer>> map =
                stack.stream().collect(Collectors.partitioningBy(predicate));

        Stack<Integer> trueStack = new Stack<>();
        trueStack.push(1);
        trueStack.push(2);

        Stack<Integer> falseStack = new Stack<>();
        falseStack.push(3);
        falseStack.push(4);
        falseStack.push(5);
        assertEquals(trueStack, map.get(true));
        assertEquals(falseStack, map.get(false));
    }

    @Test
    public void count() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);

        Predicate<Integer> predicate = i ->  i > 2;
        assertEquals(3, stack.stream().filter(predicate).toArray().length);
        assertEquals(0, stack.stream().filter(i ->  i > 6).toArray().length);
    }

    @Test
    public void anyMatch() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        assertTrue(stack.stream().anyMatch(i -> i == 1));
        assertFalse(stack.stream().anyMatch(i -> i == 4));
    }

    @Test
    public void allMatch() {
        Stack<Integer> stack = new Stack<>();
        stack.push(3);
        stack.push(3);
        stack.push(3);

        assertTrue(stack.stream().allMatch(i -> i == 3));
        assertFalse(stack.stream().allMatch(i -> i == 2));
    }

    @Test
    public void sum() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(10, stack.stream().mapToInt(i -> i.intValue()).sum());
        assertEquals(10l, stack.stream().mapToLong(i -> i.longValue()).sum());
        assertEquals(10.0d, stack.stream().mapToDouble(i -> i.doubleValue()).sum());
        assertEquals(10.0d, stack.stream().mapToDouble(i -> i.floatValue()).sum());
    }

    @Test
    public void max() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(Integer.valueOf(4), stack.stream().max(Comparator.<Integer>naturalOrder()).get());
        assertEquals(Integer.valueOf(1), stack.stream().max(Comparator.<Integer>reverseOrder()).get());
    }

    @Test
    public void maxBy() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Integer.valueOf(3), stack.stream().max(Comparator.<Integer,String>comparing(i -> i.toString())).get());
    }

    @Test
    public void min() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        assertEquals(Integer.valueOf(1), stack.stream().min(Comparator.<Integer>naturalOrder()).get());
        assertEquals(Integer.valueOf(4), stack.stream().min(Comparator.<Integer>reverseOrder()).get()); 
    }

    @Test
    public void minBy() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        assertEquals(Integer.valueOf(1), stack.stream().min(Comparator.<Integer,String>comparing(i -> i.toString())).get());
    }

    @Test
    public void groupBy() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5); 
        stack.push(6);
        stack.push(7);
        stack.push(8);
        stack.push(9);
        stack.push(10);

        Map<Integer, Collection<Integer>> expected = new HashMap<>();
        expected.put(0, Arrays.asList(3, 6, 9));
        expected.put(1, Arrays.asList(1, 4, 7, 10));
        expected.put(2, Arrays.asList(2, 5, 8));

        Map<Integer, List<Integer>> result =
                stack.stream().collect(Collectors.<Integer, Integer>groupingBy(i -> i / 3));
    }

    @Test
    public void forEach() {
        Stack<Integer> stack = new Stack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);
        stack.push(4);
        stack.push(5);
        Appendable builder = new StringBuilder();
        stack.forEach(i -> {
            try{
                builder.append(i.toString());
            }catch(IOException ignore){}
        });
        assertEquals("12345", builder.toString());
    }
}
