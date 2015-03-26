/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise4Test.java
 * @author Tristan Yan
 * @run testng Exercise4Test
 */

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.testng.annotations.Test;
import static org.testng.Assert.assertEquals;

public class Exercise4Test extends CompanyDomain {
    /**
     * Improve {@link Company#getOrders()} without breaking this test.
     */
    @Test
    public void improveGetOrders(){
        assertEquals(5, this.company.getOrders().size());
    }

    /**
     * Get all items that have been ordered by anybody.
     */
    @Test
    public void findItemNames()
    {
        Function<Order, Stream<LineItem>> fm = od -> od.getLineItems().stream();
        List<LineItem> allOrderedLineItems = company.getOrders().stream().flatMap(fm).collect(Collectors.<LineItem>toList());
        Set<String> actualItemNames = allOrderedLineItems.stream().map(li -> li.getName()).collect(Collectors.<String>toSet());

        Set<String> expectedItemNames = Arrays.stream(new String[]{
                "Shed", "big shed", "bowl", "cat", "cup", "chair", "dog",
                "goldfish", "gnome", "saucer", "shed", "sofa", "table"}).collect(Collectors.<String>toSet());
        assertEquals(expectedItemNames, actualItemNames);
    }

    @Test
    public void findCustomerNames()
    {
        List<String> names = company.getCustomers().stream().map(c -> c.getName()).collect(Collectors.<String>toList());
        List<String> expectedNames = Arrays.asList("Fred", "Mary", "Bill");
        assertEquals(expectedNames, names);
    }
}
