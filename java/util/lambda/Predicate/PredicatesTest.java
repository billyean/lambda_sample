/**
 * @test
 * @summary Function tests with Function and Comparator
 * @library ../domain/
 * @(#)PredicatesTest.java
 * @author Tristan Yan
 * @run testng PredicatesTest
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import static org.testng.Assert.*;
import org.testng.annotations.Test;
public class PredicatesTest {
    private static final Object OBJECT = new Object();
    
    private static final Predicate<Object> alwaysTrue = o -> true;
    
    private static final Predicate<Object> alwaysFalse = o -> false;

    @Test
    public void staticOr() {
       assertTrue(alwaysTrue.or(alwaysFalse).test(OBJECT));
       assertFalse(alwaysFalse.or(alwaysFalse).test(OBJECT));
       assertTrue(alwaysTrue.or(alwaysTrue).test(OBJECT));
       assertNotNull(alwaysTrue.or(alwaysTrue).toString());
       
       Predicate<Object>[] trueOrTrueArr = new Predicate[]{
                alwaysTrue,
                alwaysTrue,
                alwaysTrue
       }; 
       List<Predicate<Object>> trueOrTrue = Arrays.asList(trueOrTrueArr);
       Predicate<Object>[] trueOrFalseArr = new Predicate[]{
                alwaysTrue,
                alwaysTrue,
                alwaysFalse
       };
       List<Predicate<Object>> trueOrFalse = Arrays.asList(trueOrFalseArr);
       Predicate<Object>[] falseOrFalseArr = new Predicate[]{
                alwaysFalse,
                alwaysFalse,
                alwaysFalse
       };
       List<Predicate<Object>> falseOrFalse = Arrays.asList(falseOrFalseArr);
       

       assertTrue(trueOrTrueArr[0].or(trueOrTrueArr[1]).or(trueOrTrueArr[2]).test(OBJECT));
       assertTrue(trueOrFalseArr[0].or(trueOrFalseArr[1]).or(trueOrFalseArr[2]).test(OBJECT));
       assertFalse(falseOrFalseArr[0].or(falseOrFalseArr[1]).or(falseOrFalseArr[2]).test(OBJECT));
    }

    @Test
    public void instanceOr() {
       assertTrue(alwaysTrue.or(alwaysFalse).test(OBJECT));
       assertFalse(alwaysFalse.or(alwaysFalse).test(OBJECT));
       assertTrue(alwaysTrue.or(alwaysTrue).test(OBJECT));
       assertNotNull(alwaysTrue.or(alwaysTrue).toString());
    }

    @Test
    public void staticAnd() {
       assertTrue(alwaysTrue.and(alwaysTrue).test(OBJECT));
       assertFalse(alwaysTrue.and(alwaysFalse).test(OBJECT));
       assertFalse(alwaysFalse.and(alwaysFalse).test(OBJECT));
       assertNotNull(alwaysFalse.and(alwaysFalse).toString());
       
       Predicate<Object>[] trueAndTrueArr = new Predicate[]{
                alwaysTrue,
                alwaysTrue,
                alwaysTrue
       }; 
       List<Predicate<Object>> trueAndTrue = Arrays.asList(trueAndTrueArr);
       Predicate<Object>[] trueAndFalseArr = new Predicate[]{
                alwaysTrue,
                alwaysTrue,
                alwaysFalse
       };
       List<Predicate<Object>> trueAndFalse = Arrays.asList(trueAndFalseArr);
       Predicate<Object>[] falseAndFalseArr = new Predicate[]{
                alwaysFalse,
                alwaysFalse,
                alwaysFalse
       };
       List<Predicate<Object>> falseAndFalse = Arrays.asList(falseAndFalseArr);
 
       assertTrue(trueAndTrueArr[0].and(trueAndTrueArr[1]).and(trueAndTrueArr[2]).test(OBJECT));
       assertFalse(trueAndFalseArr[0].and(trueAndFalseArr[1]).and(trueAndFalseArr[2]).test(OBJECT));
       assertFalse(falseAndFalseArr[0].and(falseAndFalseArr[1]).and(falseAndFalseArr[2]).test(OBJECT));
    }

    @Test
    public void instanceAnd() {
       assertTrue(alwaysTrue.and(alwaysTrue).test(OBJECT));
       assertFalse(alwaysTrue.and(alwaysFalse).test(OBJECT));
       assertFalse(alwaysFalse.and(alwaysFalse).test(OBJECT));
       assertNotNull(alwaysFalse.and(alwaysFalse).toString());
    }


    @Test
    public void equal() {
       assertTrue(Predicate.isEqual(1).test(1));
       assertFalse(Predicate.isEqual(2).test(1));
       assertFalse(Predicate.isEqual(null).test(1));
       assertNotNull(Predicate.isEqual(null).toString());
    }

    @Test
    public void not() {
       assertFalse(alwaysTrue.negate().test(OBJECT));
       assertTrue(alwaysFalse.negate().test(OBJECT));
    }
}
