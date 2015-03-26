/**
 * @test
 * @summary 
 * @library ../
 * @(#)AnagramTest.java
 * @author Tristan Yan
 * @run testng AnagramTest
 */

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class AnagramTest {
    private List<String> getWords() {
        return Arrays.asList(
                "alerts", "alters", "artels", "estral", "laster", "ratels", "salter", "slater", "staler", "stelar", "talers",
                "least", "setal", "slate", "stale", "steal", "stela", "taels", "tales", "teals", "tesla"
        );
    }
    
    private static final int SIZE_THRESHOLD = 10;

    @Test
    public void anagramsWithMultimapInlined(){
        List<Collection<String>> results = this.getWords().stream().
               collect(Collectors.groupingBy(ALPHAGRAM_FUNCTION)).values().
               stream().filter(ITERABLE_SIZE_AT_THRESHOLD).
               sorted(DESCENDING_ITERABLE_SIZE).collect(Collectors.<Collection<String>>toList());

        assertEquals(SIZE_THRESHOLD, results.get(results.size() - 1).size());
        
        results.sort(DESCENDING_ITERABLE_SIZE);
        assertEquals(SIZE_THRESHOLD, results.get(results.size() - 1).size());
    }

    @Test
    public void anagramsUsingMapGetIfAbsentPutInsteadOfGroupBy() {
        final Map<Alphagram, List<String>> map = new HashMap<>();
        this.getWords().stream().
            forEach(word -> map.merge(
                new Alphagram(word), Arrays.asList(word), 
                (l1, l2) -> Stream.concat(l1.stream(), l2.stream()).
            collect(Collectors.<String>toList())));
        List<List<String>> results = map.values().stream().filter(ITERABLE_SIZE_AT_THRESHOLD).
                sorted(DESCENDING_ITERABLE_SIZE).collect(Collectors.<List<String>>toList());
        assertTrue(this.listContainsTestGroupAtElementsOneOrTwo(results));
        assertEquals(SIZE_THRESHOLD, results.get(results.size() - 1).size());
    }
    
    private boolean listContainsTestGroupAtElementsOneOrTwo(List<List<String>> list) {
        return list.get(0).containsAll(this.getTestAnagramGroup())
                || list.get(1).containsAll(this.getTestAnagramGroup());
    }
    
    private List<String> getTestAnagramGroup() {
        return Arrays.asList("least", "setal", "slate", "stale", "steal", "stela", "taels", "tales", "teals", "tesla");
    }

    private static final class Alphagram {

        private final char[] key;

        private Alphagram(String string) {
            this.key = string.toCharArray();
            Arrays.sort(this.key);
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            Alphagram alphagram = (Alphagram) o;
            return Arrays.equals(this.key, alphagram.key);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(this.key);
        }

        @Override
        public String toString() {
            return new String(this.key);
        }
    }
    
    private static Function<String, Alphagram> ALPHAGRAM_FUNCTION = 
            t -> new Alphagram(t);
    
    private static final Predicate<Collection<String>> ITERABLE_SIZE_AT_THRESHOLD 
            = l -> l.size() >= SIZE_THRESHOLD;
    
    private static final Function<Collection<String>, Integer> ITERABLE_SIZE_FUNCTION 
            = Collection::size;
    
    private static final Comparator<Collection<String>> ASCENDING_ITERABLE_SIZE 
            = Comparator.comparing(ITERABLE_SIZE_FUNCTION);
    
    private static final Comparator<Collection<String>> DESCENDING_ITERABLE_SIZE 
            = ASCENDING_ITERABLE_SIZE.reversed();
}
