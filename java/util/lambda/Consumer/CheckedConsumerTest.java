/**
 * @test
 * @summary Sanity test for Consumer
 * @library ../
 * @(#)CheckedConsumerTest.java
 * @author Tristan Yan
 * @run testng CheckedConsumerTest
 */

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class CheckedConsumerTest {
    @Test
    public void dateConsumer() {
        Consumer<Date> consumer = d -> { assertNotNull(d.toString()); };
        consumer.accept(new Date());
    }

    @Test
    public void collectionConsumer() {
        Consumer<Collection<String>> consumer = c -> { assertFalse(c.isEmpty()); };
        consumer.accept(Arrays.asList("1"));
    }

    @Test
    public void mapConsumer()
    {
        Consumer<Map<String, String>> consumer = m -> { assertTrue(m.containsKey("1")); };
        Map<String, String> m = new HashMap<>();
        m.put("1", "1");
        consumer.accept(m);
    }

    @Test(expectedExceptions = RuntimeException.class)
    public void checkedObjectIntConsumer() {
        Consumer<Object> consumer = o -> { throw new RuntimeException(); };
        consumer.accept(null);
    }

    @Test
    public void numberConsumer()  {
        Consumer<Integer> consumer = i -> { assertEquals(Integer.valueOf(1), i); };
        consumer.accept(1);
    }

    @Test
    public void timestampConsumer() {
        Consumer<Timestamp> consumer = t -> { assertNotNull(t.toString()); };
        consumer.accept(new Timestamp(0));
    }
}