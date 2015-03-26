/**
 * @test
 * @summary Comparators tests with Function and Comparator
 * @library ../
 * @(#)FunctionComparatorTest.java
 * @author Tristan Yan
 * @run testng FunctionComparatorTest
 */
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class FunctionComparatorTest {
    private static final Band VAN_HALEN = new Band("Van Halen");
    private static final Band BON_JOVI = new Band("Bon Jovi");
    private static final Band METALLICA = new Band("Metallica");
    private static final Band SCORPIONS = new Band("Scorpions");
    private static final Band ACDC = new Band("AC/DC");
    private static final Band ZZTOP = new Band("ZZ Top");

    @Test
    public void comparator() {
        Comparator<Band> comparator = Comparator.<Band, String>comparing(Band.TO_NAME);
        assertEquals(comparator.compare(ACDC, ZZTOP), ACDC.getName().compareTo(ZZTOP.getName()));
        assertEquals(comparator.compare(ZZTOP, ACDC), ZZTOP.getName().compareTo(ACDC.getName()));
    }

    private List<Band> createTestList() {
        return Arrays.asList(VAN_HALEN, SCORPIONS, BON_JOVI, METALLICA);
    }

    @Test
    public void functionComparatorBuiltTheHardWay() {
        Comparator<Band> byName = Comparator.<Band, String>comparing(Band.TO_NAME);
        List<Band> sortedList = this.createTestList();
        sortedList.sort(byName);
        assertEquals(Arrays.asList(BON_JOVI, METALLICA, SCORPIONS, VAN_HALEN), sortedList);
    }

    @Test
    public void functionComparatorBuiltTheEasyWay() {
        Comparator<Band> byName = FunctionComparatorTest.comparing(Band.TO_NAME, Comparator.<String>reverseOrder());
        List<Band> sortedList = this.createTestList();
        sortedList.sort(byName);
        assertEquals(Arrays.asList(VAN_HALEN, SCORPIONS, METALLICA, BON_JOVI), sortedList);
    }
    
    private static final class Band {
        public static final Function<Band, String> TO_NAME = (b) -> b.getName();
        
        private final String name;

        private Band(String name) {
            this.name = name;
        }

        public String getName() {
            return this.name;
        }

        @Override
        public String toString() {
            return this.name;
        }

        @Override
        public boolean equals(Object other) {
            return this == other || other instanceof Band && this.name.equals(((Band) other).name);
        }

        @Override
        public int hashCode() {
            return this.name.hashCode();
        }
    }
    
    public static <T, U extends Comparable<? super U>> Comparator<T> comparing(Function<? super T, ? extends U> keyExtractor, Comparator<? super U> comparator) {
        Objects.requireNonNull(keyExtractor);
        return (c1, c2) -> comparator.compare(keyExtractor.apply(c1), keyExtractor.apply(c2));
    }
}
