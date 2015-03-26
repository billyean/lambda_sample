/**
 * @test
 * @summary 
 * @library ../
 * @(#)Functions2Test.java
 * @author Tristan Yan
 * @run testng Functions2Test
 */

import java.util.function.Function;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class Functions2Test {
    @Test
    public void asFunction2Function() {
        Function<Object, String> getToString = o -> o.toString();
        Function<Integer, Object> intToObject = i -> i + 2;
        assertEquals("3", getToString.apply(intToObject.apply(1)));
        
        assertEquals("3", intToObject.andThen(getToString).apply(1));
    }
}
