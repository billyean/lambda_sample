/**
 * @test
 * @summary Lambda test for ArrayList
 * @library ../..
 * @(#) MutableListTest.java
 * @author Tristan Yan
 * @run testng MutableListTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;
import java.util.StringJoiner;
import java.util.Vector;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.testng.Assert.*;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class MutableListTest {
    static enum LIST_TYPE {ARRAYLIST, LINKEDLIST,COPYONWRITEARRAYLIST, STACK, VECTOR};
    public <T> List<T> classUnderTest(LIST_TYPE type, T... elements){
        switch(type) {
            case ARRAYLIST:
                return new ArrayList<T>(Arrays.asList(elements));
            case LINKEDLIST:
                return new LinkedList<T>(Arrays.asList(elements));
            case COPYONWRITEARRAYLIST:
                return new CopyOnWriteArrayList<T>(Arrays.asList(elements));
            case STACK:
                Stack<T> stack = new Stack<T>();
                stack.addAll(Arrays.asList(elements));
                return stack;
            default:
                return new Vector<T>(Arrays.asList(elements));
        } 
    }
    
    @DataProvider
    public Object[][] types() {
        return new Object[][] {
            {LIST_TYPE.ARRAYLIST}, {LIST_TYPE.LINKEDLIST}, {LIST_TYPE.VECTOR},
        };
    }
    
    @Test(dataProvider="types") 
    public void testForEachFromTo(LIST_TYPE type) {
        List<Integer> result = this.classUnderTest(type);
        List<Integer> collection = new ArrayList(Arrays.asList(1, 2, 3, 4));
        collection.forEach(i -> { if(i > 2) result.add(i);});
        assertEquals(2, result.size());
        assertTrue(result.containsAll(Arrays.asList(3, 4)));
    }

    @Test(dataProvider="types")
    public void reverseForEach(LIST_TYPE type) {
        List<Integer> result = this.classUnderTest(type);
        List<Integer> collection = this.classUnderTest(type, 1, 2, 3, 4);
        collection.sort(Comparator.reverseOrder());
        collection.forEach(i -> result.add(i));
        assertEquals(Arrays.asList(4, 3, 2, 1), result);
    }

    @Test(dataProvider="types")
    public void reverseForEach_emptyList(LIST_TYPE type) {
        List<Integer> integers = this.classUnderTest(type, 1, 2, 3, 4);
        List<Integer> result = this.classUnderTest(type);
        integers.sort(Comparator.reverseOrder());
        integers.forEach(i -> result.add(i));
        assertEquals(Arrays.asList(4, 3, 2, 1), result);;
    }

    @Test(dataProvider="types")
    public void removeIf(LIST_TYPE type) {
        List<Integer> objects = this.classUnderTest(type, 1, 2, 3, null);
        objects.removeIf(i -> i == null);
        assertEquals(Arrays.asList(1, 2, 3), objects);
    }

    @Test(dataProvider="types")
    public void sortThis_with_null(LIST_TYPE type) {
        Comparator<Integer> comp_with_null = (i1, i2) -> 
                i1 == null ? -1 : (i2  == null ? 1 : i1.compareTo(i2));
        List<Integer> integers = this.classUnderTest(type, 2, null, 3, 4, 1);
        integers.sort(comp_with_null);
        assertEquals(integers, Arrays.asList(null, 1, 2, 3, 4));
    }

    @Test(dataProvider="types")
    public void sortThis_small(LIST_TYPE type) {
        List<Integer> actual = this.classUnderTest(type, 1, 2, 3);
        Collections.shuffle(actual);
        actual.sort(Comparator.naturalOrder());
        assertEquals(this.classUnderTest(type, 1, 2, 3), actual);
    }

    @Test(dataProvider="types")
    public void sortThis(LIST_TYPE type) {
        List<Integer> actual = this.classUnderTest(type, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collections.shuffle(actual);
        actual.sort(Comparator.naturalOrder());
        assertEquals(this.classUnderTest(type, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10), actual);
    }

    @Test(dataProvider="types")
    public void sortThis_large(LIST_TYPE type) {
        List<Integer> actual = IntStream.range(1, 1001).boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));
        Collections.shuffle(actual);
        actual.sort(Comparator.naturalOrder());
        assertEquals(IntStream.range(1, 1001).boxed().toArray(), actual.toArray());
    }

    @Test(dataProvider="types")
    public void sortThis_with_reverse_comparator_small(LIST_TYPE type){
        List<Integer> actual = this.classUnderTest(type, 1, 2, 3);
        Collections.shuffle(actual);
        actual.sort(Comparator.reverseOrder());
        assertEquals(this.classUnderTest(type, 3, 2, 1), actual);
    }

    @Test(dataProvider="types")
    public void sortThis_with_reverse_comparator(LIST_TYPE type) {
        List<Integer> actual = this.classUnderTest(type, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collections.shuffle(actual);
        actual.sort(Comparator.reverseOrder());
        assertEquals(this.classUnderTest(type, 10, 9, 8, 7, 6, 5, 4, 3, 2, 1), actual);
    }

    @Test(dataProvider="types")
    public void sortThis_with_reverse_comparator_large(LIST_TYPE type) {
        List<Integer> actual = IntStream.range(1, 1001).boxed().
                collect(Collectors.toCollection(ArrayList<Integer>::new));
        Collections.shuffle(actual);
        actual.sort(Comparator.reverseOrder());
        assertEquals(IntStream.iterate(1000, i -> --i).limit(1000).
                boxed().toArray(), actual.toArray());
    }

    @Test(dataProvider="types")
    public void sortThisBy(LIST_TYPE type) {
        List<Integer> actual = this.classUnderTest(type, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        Collections.shuffle(actual);
        actual.sort(Comparator.<Integer, String>comparing(i -> String.valueOf(i)));
        assertEquals(Arrays.asList(1, 10, 2, 3, 4, 5, 6, 7, 8, 9), actual);
    }

    @Test(dataProvider="types")
    public void appendString(LIST_TYPE type) {
        List<Integer> list = this.classUnderTest(type, 1, 2, 3);

        String joined = list.stream().map(i -> String.valueOf(i)).
                collect(Collectors.joining());
        assertEquals("123", joined);

    }

    @Test(dataProvider="types")
    public void appendStringWithSeparator(LIST_TYPE type){
        List<Integer> list = this.classUnderTest(type, 1, 2, 3);

        String joined = list.stream().map(i -> String.valueOf(i)).
                collect(Collectors.joining("/"));
        assertEquals("1/2/3", joined);
    }

    @Test(dataProvider="types")
    public void forEachWithIndexWithFromTo(LIST_TYPE type) {
        List<Integer> list = this.classUnderTest(type, 1, 2, 3);
        List<Integer> result = this.classUnderTest(type);
        AtomicInteger index = new AtomicInteger();
        list.forEach(e -> {
                if(Arrays.asList(1, 2).contains(index.getAndIncrement())) 
                    result.add(e); 
        });
        assertEquals(Arrays.asList(2, 3), result);
    }

    @Test(dataProvider="types")
    public void forEachWithIndexWithFromToInReverse(LIST_TYPE type) {
        List<Integer> list = this.classUnderTest(type, 1, 2, 3);
        List<Integer> result = this.classUnderTest(type);
        AtomicInteger index = new AtomicInteger();
        list.forEach(e -> {
                if(Arrays.asList(1, 2).contains(index.getAndIncrement())) 
                    result.add(0, e); 
        });
        assertEquals(Arrays.asList(3, 2), result);
    }

    @Test(dataProvider="types",expectedExceptions = NullPointerException.class)
    public void sortThisWithNull(LIST_TYPE type) {
        List<Integer> integers = this.classUnderTest(type, 2, null, 3, 4, 1);
        integers.sort(Comparator.naturalOrder());
    }

    @Test(dataProvider="types",expectedExceptions = NullPointerException.class)
    public void sortThisWithNullOnListWithMoreThan10Elements(LIST_TYPE type) {
        List<Integer> integers = this.classUnderTest(type, 2, null, 3, 4, 1, 5, 6, 7, 8, 9, 10, 11);
        integers.sort(Comparator.naturalOrder());
    }
}