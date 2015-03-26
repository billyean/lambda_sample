/**
 * @test
 * @summary This sample shows different uses filter
 * @library ./
 * @(#) CaseConsumerTest.java
 * @author Tristan Yan
 * @run testng CaseConsumerTest
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CaseConsumerTest {
    
    @Test
    public void procedure() {
        List<String> ifOneList = new ArrayList<>();
        List<String> defaultList = new ArrayList<>();

        Consumer<String> ifOneConsumer = s
                    -> {
            if (s.equals("1")) {
                        ifOneList.add(s);
                    } else {
                        defaultList.add(s);
                    }
                };
        List<String> list = Arrays.asList("1", "2");
        list.stream().forEach(ifOneConsumer);

        assertEquals(Arrays.asList("1"), ifOneList);
        assertEquals(Arrays.asList("2"), defaultList);
    }
}
