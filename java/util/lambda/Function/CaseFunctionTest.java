/**
 * @test
 * @summary 
 * @library ../
 * @(#)CaseFunctionTest.java
 * @author Tristan Yan
 * @run testng CaseFunctionTest
 */

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CaseFunctionTest {
    static class CaseFunction<T, R, U> implements Function<T, R>{
        private Map<U, R> cases = new HashMap<>();
        
        private R r;
        
        private Function<T, U> f;
        
        CaseFunction(R defaultValue, Function<T, U> function){
            r = defaultValue;
            f = function;
        }
        
        public void addCase(U t, R r) {
            cases.put(t, r);
        }

        @Override
        public R apply(T t) {
            return cases.getOrDefault(f.apply(t), r);
        }
    }
    
    static class CaseIdenticalFunction<T, R> extends CaseFunction<T, R, T>{
        CaseIdenticalFunction(R defaultValue){
            super(defaultValue, Function.identity());
        }
        
        CaseIdenticalFunction(){
            this(null);
        }
    }
            
    @Test
    public void basicCase() {
        Integer fortyTwo = 42;
        Predicate<Integer> p = i -> i == 42;
        CaseIdenticalFunction<Integer,Integer> function 
                = new CaseIdenticalFunction<>();
        function.addCase(fortyTwo, fortyTwo);
        assertEquals(fortyTwo, function.apply(fortyTwo));
        assertNull(function.apply(fortyTwo + 1));
    }

    @Test
    public void defaultValue() {
        CaseFunction<Foo, String, Double> function
                = new CaseFunction<>("Yow!", Foo.TO_VALUE);
        function.addCase(5.0, "Patience, grasshopper");
        assertEquals("Yow!", function.apply(new Foo("", 1.0D)));
    }

    public static final class Foo implements Comparable<Foo> {
        public static final Function<Foo, Double> TO_VALUE = Foo::getValue;

        private final String description;
        private final double value;

        private Foo(String description, double value) {
            this.description = description;
            this.value = value;
        }
        
        public double getValue(){
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }

            Foo foo = (Foo) o;

            if (Double.compare(foo.value, this.value) != 0) {
                return false;
            }
            return this.description.equals(foo.description);
        }

        @Override
        public int hashCode() {
            int result = this.description == null ? 0 : this.description.hashCode();
            long l = Double.doubleToLongBits(this.value);
            result = 31 * result + (int) (l ^ l >>> 32);
            return result;
        }

        public int compareTo(Foo o) {
            throw new RuntimeException("compareTo not implemented");
        }
    }
}
