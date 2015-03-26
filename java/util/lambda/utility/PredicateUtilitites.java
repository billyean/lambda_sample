import java.util.Spliterators;
import java.util.function.Predicate;
import java.util.stream.StreamSupport;

public class PredicateUtilitites {
    public static <T> Predicate<T> in(Iterable<T> iterable) {
        return t1 -> StreamSupport.stream
            (Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), true).
            anyMatch(t2 -> t1.equals(t2));
    }

    public static <T> Predicate<T> notIn(Iterable<T> iterable) {
        return t1 -> StreamSupport.stream
            (Spliterators.spliteratorUnknownSize(iterable.iterator(), 0), true).
            allMatch(t2 -> !t1.equals(t2));
    }
}
