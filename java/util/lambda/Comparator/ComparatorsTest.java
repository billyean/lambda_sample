/**
 * @test
 * @summary Comparators sanity tests
 * @library ../domain/
 * @(#)ComparatorsTest.java
 * @author Tristan Yan
 * @run testng ComparatorsTest
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class ComparatorsTest {
    @Test
    public void naturalOrder1() {
        List<String> list = Arrays.asList("1", "4", "2", "3");
        list.sort(Comparator.<String>naturalOrder());
        assertEquals(
                Arrays.asList("1", "2", "3", "4"),
                list);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void naturalOrder2() {
        List<String> list = Arrays.asList("1", "2", null, "4");
        list.sort(Comparator.<String>naturalOrder());
    }

    @Test
    public void reverseOrder1() {
        List<String> list = Arrays.asList("1", "4", "2", "3");
        list.sort(Comparator.<String>reverseOrder());
        assertEquals(
                Arrays.asList("4", "3", "2", "1"),
                list);
    }

    @Test
    public void reverseOrder2() {
        List<String> list = Arrays.asList("1", "4", "2", "3");
        list.sort(Comparator.<String>naturalOrder().reversed());
        assertEquals(
                Arrays.asList("4", "3", "2", "1"),
                list);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void reverseOrder3() {
        List<String> list = Arrays.asList("1", "2", null, "4");
        list.sort(Comparator.<String>reverseOrder());
    }

    @Test
    public void chainedComparator(){
        Comparator<Person> byName = Comparator.comparing(Person.FIRST);
        Comparator<Person> byAge = Comparator.comparing(Person.AGE);

        Person fred10 = new Person("Fred", "Smith", 10);
        Person jim10 = new Person("Jim", "Smith", 10);
        Person jim16 = new Person("Jim", "Smith", 16);
        Person sheila12 = new Person("Sheila", "Smith", 12);
        Person sheila14 = new Person("Sheila", "Smith", 14);

        List<Person> people = Arrays.asList(jim16, fred10, sheila14, sheila12, fred10, jim10);

        List<Person> expectedNameThenAgeOrder = Arrays.asList(fred10, fred10, jim10, jim16, sheila12, sheila14);
        List<Person> expectedAgeThenNameOrder = Arrays.asList(fred10, fred10, jim10, sheila12, sheila14, jim16);

        people.sort(byName.thenComparing(byAge));
        assertEquals(expectedNameThenAgeOrder, people);

        people.sort(byAge.thenComparing(byName));
        assertEquals(expectedAgeThenNameOrder, people);
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void chainedNullComparator(){
        Comparator.comparing(Person.FIRST).thenComparing((Comparator<Person>)null);
    }

    @Test
    public void fromFunctions() {
        Person raab = new Person(null, "Raab", 0);
        Person white = new Person(null, "White", 0);

        Comparator<Person> personComparator = Comparator.comparing(Person.LAST);
        assertTrue(personComparator.compare(raab, white) < 0);
        assertTrue(personComparator.compare(white, raab) > 0);
        assertTrue(personComparator.compare(raab, raab) == 0);
    }


    @Test
    public void fromFunctionsWithTwoArgs() {
        Person raab = new Person("Don", "Raab", 0);
        Person white = new Person("Barry", "White", 0);
        Person manilow = new Person("Barry", "Manilow", 0);

        Comparator<Person> personComparator = 
            Comparator.comparing(Person.FIRST).
                thenComparing(Comparator.comparing(Person.LAST));
        assertTrue(personComparator.compare(raab, white) > 0);
        assertTrue(personComparator.compare(white, raab) < 0);
        assertEquals(personComparator.compare(raab, raab), 0);
        assertTrue(personComparator.compare(white, manilow) > 0);
        assertTrue(personComparator.compare(manilow, white) < 0);
    }

    @Test
    public void fromFunctionsWithThreeArgs() {
        Person raab = new Person("Don", "Raab", 21);
        Person white = new Person("Barry", "White", 16);
        Person manilow = new Person("Barry", "Manilow", 60);
        Person manilow2 = new Person("Barry", "Manilow", 61);
        Comparator<Person> personComparator =
            Comparator.comparing(Person.FIRST).
                thenComparing(Comparator.comparing(Person.LAST)).
                thenComparing(Comparator.comparing(Person.AGE));
        assertTrue(personComparator.compare(raab, white) > 0);
        assertTrue(personComparator.compare(white, raab) < 0);
        assertEquals(personComparator.compare(raab, raab), 0);
        assertTrue(personComparator.compare(white, manilow) > 0);
        assertTrue(personComparator.compare(manilow, white) < 0);
        assertTrue(personComparator.compare(manilow, manilow2) < 0);
        assertTrue(personComparator.compare(manilow2, manilow) > 0);
    }

    @Test
    public void descendingCollectionSizeCompare() {
        List<List<Integer>> list = Arrays.asList(1, 2, 3).stream().
                map(i -> IntStream.range(1, i + 1).boxed().collect(Collectors.<Integer>toList())).
                collect(Collectors.<List<Integer>>toList());

        Comparator<List<Integer>> descendingCollectionSizeComparator =
                Comparator.<List<Integer>, Integer>comparing(List::size).reversed();
        list.sort(descendingCollectionSizeComparator);
        assertEquals(
                Arrays.asList(
                    Arrays.asList(1, 2, 3),
                    Arrays.asList(1, 2),
                    Arrays.asList(1)),
                list);
    }

    @Test
    public void ascendingCollectionSizeCompare() {
        List<List<Integer>> list = Arrays.asList(1, 2, 3).stream().
                map(i -> IntStream.range(1, i + 1).boxed().collect(Collectors.<Integer>toList())).
                collect(Collectors.<List<Integer>>toList());

        Comparator<List<Integer>> ascendingCollectionSizeComparator = 
                Comparator.<List<Integer>, Integer>comparing(List::size);
        list.sort(ascendingCollectionSizeComparator);

        assertEquals(
                Arrays.asList(
                    Arrays.asList(1),
                    Arrays.asList(1, 2),
                    Arrays.asList(1, 2, 3)),
                list);
    }

    @Test
    public void byKeyOfEntry() {
        List<HashMap.SimpleEntry<Integer, String>> list 
                = Arrays.asList(new HashMap.SimpleEntry<Integer, String>(3, "B"), 
                new HashMap.SimpleEntry<Integer, String>(1, "C"), 
                new HashMap.SimpleEntry<Integer, String>(2, "A"));
        List<HashMap.SimpleEntry<Integer, String>> sorted 
                = Arrays.asList(new HashMap.SimpleEntry<Integer, String>(1, "C"), 
                new HashMap.SimpleEntry<Integer, String>(2, "A"), 
                new HashMap.SimpleEntry<Integer, String>(3, "B"));
        Comparator<HashMap.SimpleEntry<Integer, String>> comp 
                = Comparator.<HashMap.SimpleEntry<Integer, String>, 
                Integer>comparing(HashMap.SimpleEntry::getKey);
        list.sort(comp);
        assertEquals(sorted, list);
    }

    @Test
    public void byValueOfEntry() {
        List<HashMap.SimpleEntry<Integer, String>> list 
            = Arrays.asList(new HashMap.SimpleEntry<Integer, String>(3, "B"), 
            new HashMap.SimpleEntry<Integer, String>(1, "C"), 
            new HashMap.SimpleEntry<Integer, String>(2, "A"));
        List<HashMap.SimpleEntry<Integer, String>> sorted 
            = Arrays.asList(new HashMap.SimpleEntry<Integer, String>(2, "A"), 
            new HashMap.SimpleEntry<Integer, String>(3, "B"), 
            new HashMap.SimpleEntry<Integer, String>(1, "C"));
        Comparator<HashMap.SimpleEntry<Integer, String>> comp
            = Comparator.<HashMap.SimpleEntry<Integer, String>, String>comparing(HashMap.SimpleEntry::getValue);
        list.sort(comp);
        assertEquals(sorted, list);
    }
}
