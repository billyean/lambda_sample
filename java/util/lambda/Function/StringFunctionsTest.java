/**
 * @test
 * @summary 
 * @library ../
 * @(#)StringFunctionsTest.java
 * @author Tristan Yan
 * @run testng StringFunctionsTest
 */

import java.util.function.BiFunction;
import java.util.function.Function;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class StringFunctionsTest {
    @Test
    public void toUpperCase() {
        Function<String, String> function = String::toUpperCase;
        assertEquals("UPPER", function.apply("upper"));
        assertEquals("UPPER", function.apply("Upper"));
        assertEquals("UPPER", function.apply("UPPER"));
        assertSame("UPPER", function.apply("UPPER"));
    }

    @Test
    public void toLowerCase() {
        Function<String, String> function = String::toLowerCase;
        assertEquals("lower", function.apply("LOWER"));
        assertEquals("lower", function.apply("Lower"));
        assertEquals("lower", function.apply("lower"));
        assertSame("lower", function.apply("lower"));
    }

    @Test
    public void length() {
        Function<String, Integer> function = String::length;
        assertEquals(Integer.valueOf(6), function.apply("string"));
        assertEquals(Integer.valueOf(0), function.apply(""));
    }

    @Test
    public void trim() {
        Function<String, String> function = String::trim;
        assertEquals("trim", function.apply("trim "));
        assertEquals("trim", function.apply(" trim"));
        assertEquals("trim", function.apply("  trim  "));
        assertEquals("trim", function.apply("trim"));
        assertSame("trim", function.apply("trim"));
    }

    @Test
    public void firstLetter() {
        Function<String, Character> function1 = s -> s.charAt(0);
        assertEquals('A', function1.apply("Autocthonic").charValue());
        Function<Integer, Character> function2 = "Autocthonic"::charAt;
        assertEquals('A', function2.apply(0).charValue());
    }

    private Function<String, String> subStringFunc(int beginIndex, int endIndex){
        return (s) -> s.substring(beginIndex, endIndex);
    }


    @Test
    public void subString() {
        assertEquals("bS", subStringFunc(2, 4).apply("subString"));
        BiFunction<Integer, Integer, String> subStringFunc1
                = "subString"::substring;
        assertEquals("bS", subStringFunc1.apply(2, 4));
    }

    @Test(expectedExceptions = StringIndexOutOfBoundsException.class)
    public void subString_throws_on_short_string() {
        subStringFunc(2, 4).apply("hi");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void subString_throws_on_null() {
        subStringFunc(2, 4).apply(null);
    }
}
