
/**
 * @test
 * @summary Basic test for Stream<Integer>
 * @library ../utility/
 * @(#) CollectionTestCase.java
 * @author Tristan Yan
 * @run testng CollectionTestCase
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.DoubleAdder;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CollectionTestCase {
    protected Collection<Integer> integers =  
            IntStream.range(1, 11).boxed().collect(Collectors.toCollection(() -> new ArrayList<Integer>()));

    @Test
    public void selectWith() {
        Collection<Integer> c1 = new ArrayList<>();
        c1.add(101);
        c1.addAll(integers);
        c1 = c1.stream().filter(t -> t % 2 == 1).collect(Collectors.toCollection(() -> new ArrayList<Integer>()));

        Collection<Integer> c2 = new ArrayList<>();
        c2.add(101);
        Collection<Integer> c3 = integers.stream().filter(PredicateUtilitites.in(Arrays.asList(1,3,5,7,9))).collect(Collectors.toCollection(() -> c2));

        assertEquals(c1, c3);
    }

    @Test
    public void rejectWith() {
        Collection<Integer> c1 = new ArrayList<>();
        c1.add(100);
        c1.addAll(integers);
        c1 = c1.stream().filter(t -> t % 2 == 0).collect(Collectors.toCollection(() -> new ArrayList<Integer>()));
  
        Collection<Integer> c2 = new ArrayList<>();
        c2.add(100);
        Collection<Integer> c3 = integers.stream().filter(
                PredicateUtilitites.notIn(Arrays.asList(1,3,5,7,9))).
                collect(Collectors.toCollection(() -> c2));

        assertEquals(c1, c3);
    }

    @Test
    public void partition() {
        Map<Boolean, List<Integer>> partitions =
                integers.stream().collect(Collectors.partitioningBy((Integer i) -> i % 2 == 1));
        Collection<Integer> cEven = integers.stream().filter(t -> t % 2 == 0).
                collect(Collectors.toCollection(() -> new ArrayList<Integer>()));
        Collection<Integer> cOdd = integers.stream().filter(t -> t % 2 == 1).
                collect(Collectors.toCollection(() -> new ArrayList<Integer>()));
        assertEquals(
                cEven,
                partitions.get(Boolean.FALSE));
        assertEquals(
                cOdd,
                partitions.get(Boolean.TRUE));
    }
    
    @Test
    public void collectWith() {
        Collection<String> expected = new ArrayList<>();
        expected.add("?");
        expected.addAll(integers.stream().map(i -> i + "!").collect(Collectors.<String>toList()));

        Collection<String> actualTemp = new ArrayList<>();
        actualTemp.add("?");
        Collection<String> actual = integers.stream().<Collection<String>>collect(() -> actualTemp, (r, t) -> r.add(t + "!"), (r1, r2) -> r1.addAll(r2));
        assertEquals(expected, actual);
    }

    @Test
    public void testReduceInt() {
        Integer result = integers.stream().reduce(new Integer(0), (i1, i2) -> i1 + i2);
        assertEquals(integers.stream().mapToInt(i -> i.intValue()).sum(), result.intValue());
    }

    @Test
    public void testReduceLong() {
        Long result = integers.stream().<Long>reduce(0l, (i1, i2) -> i1 + i2, (l1, l2) -> l1 + l2);
        assertEquals(integers.stream().mapToLong(i -> i.longValue()).sum(), result.longValue());
    }

    @Test
    public void testReduceDouble() {
        Supplier<DoubleAdder> s = () -> new DoubleAdder();
        BiConsumer<DoubleAdder, Integer> bc = (d, i) -> d.add(i);
        BiConsumer<DoubleAdder, DoubleAdder> bd = (d1, d2) -> d1.add(d2.doubleValue());
        DoubleAdder result = integers.stream().collect(s, bc, bd);
        assertEquals(integers.stream().mapToDouble(i -> i.doubleValue()).sum(), result.doubleValue());
    }

    @Test
    public void testReduceFloat() {
        Supplier<AtomicInteger> s = () -> new AtomicInteger(0);
        BiConsumer<AtomicInteger, Integer> bc = (ai, i) -> ai.getAndAdd(i.intValue());
        BiConsumer<AtomicInteger, AtomicInteger> bo = (ai1, ai2) -> new AtomicInteger(ai1.addAndGet(ai2.get()));
        AtomicInteger result = integers.stream().collect(s, bc, bo);
        assertEquals(integers.stream().mapToDouble(i -> i.floatValue()).sum(), (double)result.get());
    }
    
    @Test
    public void testFilter() {
        assertTrue(integers.stream().allMatch(t -> t < integers.size() + 1));
        assertEquals(integers, integers.stream().filter(t -> t < integers.size() + 1).collect(Collectors.toList()));
    }

    @Test
    public void testCollect() {
        assertEquals(integers, integers.stream().map(Function.identity()).collect(Collectors.toList()));
    }

    @Test
    public void flatCollect() {
        List<String> actual = integers.stream().
                flatMap(i -> IntStream.range(1, i).mapToObj(i1 -> String.valueOf(i1))).
                collect(Collectors.<String>toList());
        Collections.sort(actual);

        List<String> expected = integers.stream().<List<String>>collect(() -> new ArrayList<String>(), 
                (s, e) -> {
                    for(int i = 1; i < e; i++)
                        s.add(String.valueOf(i));
                }, (s1, s2) -> s1.addAll(s2));
        Collections.sort(expected);

        assertEquals(expected, actual);
    }
    
    @Test
    public void testFindAny() {
        assertEquals(Integer.valueOf(1), integers.stream().filter(t -> t.intValue() == 1).findAny().get());
        assertFalse(integers.stream().filter(t -> t == integers.size() + 1).findAny().isPresent());
        assertEquals(Integer.valueOf(1), integers.stream().filter(t -> t == 1).findAny().orElse(integers.size() + 1));
        assertEquals(Integer.valueOf(integers.size() + 1), integers.stream().filter(t -> t == integers.size() + 1).findAny().orElse(integers.size() + 1));
    }

    @Test
    public void testAllMatch() {
        assertTrue(integers.stream().allMatch(i -> (Object)i instanceof Integer));
        assertFalse(integers.stream().allMatch(i -> i == 0));
    }

    @Test
    public void testAnyMatch() {
        assertFalse(integers.stream().anyMatch(i -> (Object)i instanceof String));
        assertTrue(integers.stream().anyMatch(i -> (Object)i instanceof Integer));
    }

    @Test
    public void testCount() {
        assertEquals(integers.size(), integers.stream().filter(i -> (Object)i instanceof Integer).toArray().length);
        assertEquals(0, integers.stream().filter(i -> (Object)i instanceof String).toArray().length);
    }

    @Test
    public void testGetFirst() {
        assertEquals(Integer.valueOf(1), integers.stream().findFirst().get());
    }

    @Test
    public void testGetLast() {
        assertEquals(Integer.valueOf(integers.size()), integers.stream().sorted(Comparator.<Integer>reverseOrder()).findFirst().get());
    }

    @Test
    public void testIterator() {
        final Iterator<Integer> iterator = integers.stream().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Integer integer = iterator.next();
            assertEquals(i + 1, integer.intValue());
        }
    }

    @Test(expectedExceptions = NoSuchElementException.class)
    public void testIteratorException() {
        final Iterator<Integer> iterator = integers.stream().iterator();
        for (int i = 0; iterator.hasNext(); i++) {
            Integer integer = iterator.next();
            assertEquals(i + 1, integer.intValue());
        }
        iterator.next();
    }

    @Test
    public void testToArray() {
        List<Integer> copy = new ArrayList(integers);
        assertEquals(integers.stream().toArray(), copy.toArray());
        assertEquals(integers.stream().toArray(), copy.toArray(new Integer[integers.size()]));
    }

    @Test
    public void toSortedList() {
        List<Integer> copy = new ArrayList(integers);
        List<Integer> list = new ArrayList(integers);
        Collections.sort(list, Collections.<Integer>reverseOrder());
        assertEquals(copy.stream().sorted(Collections.<Integer>reverseOrder()).collect(Collectors.<Integer>toList()), list);
        Collections.sort(list);
        assertEquals(copy.stream().sorted().collect(Collectors.<Integer>toList()), list);
    }

    @Test
    public void toSortedSet() {
        SortedSet<Integer> set = new TreeSet(integers);
        assertEquals(integers.stream().sorted().collect(Collectors.<Integer>toList()), new ArrayList(set));
    }

    @Test
    public void toSortedSetWithComparator() {
        SortedSet<Integer> set = new TreeSet(Comparator.reverseOrder());
        set.addAll(integers);
        assertEquals(integers.stream().sorted(Comparator.reverseOrder()).collect(Collectors.<Integer>toList()), new ArrayList(set));
    }

    @Test
    public void toSortedSetBy() {
        Function<Integer, String> itoa = String::valueOf;
        Comparator<Integer> cmpByString = Comparator.comparing(itoa);
        SortedSet<Integer> set = new TreeSet(cmpByString);
        set.addAll(integers);
        assertEquals(integers.stream().map(String::valueOf).collect(Collectors.
                <String, TreeSet>toCollection(() -> new TreeSet(cmpByString))), 
                set);
    }

    @Test
    public void testForLoop() {
        integers.stream().forEach(each -> assertNotNull(each));
    }

    private Collection<Integer> classUnderTestWithNull() {
        List<Integer> list = integers.stream().filter(Predicate.isEqual(1).negate()).collect(Collectors.<Integer>toList());
        list.add(null);
        return list;
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void map_throws() {
        this.classUnderTestWithNull().stream().map(i -> i.intValue()).toArray();
    }

    @Test
    public void min() {
        assertEquals(Integer.valueOf(1), integers.stream().min(Comparator.naturalOrder()).get());
    }

    @Test
    public void max() {
        assertEquals(Integer.valueOf(1), integers.stream().max(Comparator.reverseOrder()).get());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void min_null_throws_with_comparator() {
        this.classUnderTestWithNull().stream().min(Comparator.naturalOrder());
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void max_null_throws_with_comparator() {
        this.classUnderTestWithNull().stream().max(Comparator.reverseOrder());
    }

    @Test
    public void minByFuncComp() {
        assertEquals(Integer.valueOf(1), integers.stream().min((i1, i2) -> String.valueOf(i1).compareTo(String.valueOf(i2))).get());
    }

    @Test
    public void maxByFuncComp() {
        assertEquals(Integer.valueOf(integers.size() - 1), integers.stream().max((i1, i2) -> String.valueOf(i1).compareTo(String.valueOf(i2))).get());
    }

    @Test(expectedExceptions = UnsupportedOperationException.class)
    public void testIteratorRemove() {
        integers.stream().iterator().remove();
    }
}
