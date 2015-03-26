/**
 * @test
 * @summary 
 * @library ../
 * @(#)AddFunctionTest.java
 * @author Tristan Yan
 * @run testng AddFunctionTest
 */

import java.util.function.BiFunction;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class AddFunctionTest {
    
    @Test
    public void addStringBlockHandlesNulls() {
        BiFunction<String, String, String> undertest = (s1, s2) -> 
                ((s1 == null) ? "" : s1) + ((s2 == null) ? "" : s2) ;
        assertEquals("two", undertest.apply(null, "two"));
        assertEquals("one", undertest.apply("one", null));
    }
}
