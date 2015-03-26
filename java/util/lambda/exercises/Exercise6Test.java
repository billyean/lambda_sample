/**
 * @test
 * @summary This sample shows different uses filter
 * @library ./
 * @(#) Exercise6Test.java
 * @author Tristan Yan
 * @run testng Exercise6Test
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class Exercise6Test extends CompanyDomain  {
    @Test
    public void filterOrderValues() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        List<Double> orderValues = orders.stream().map(o -> o.getValue()).collect(Collectors.<Double>toList());
        List<Double> filtered = orderValues.stream().filter(d -> d > 1.5d).collect(Collectors.<Double>toList());
        assertEquals(Arrays.asList(372.5, 1.75), filtered);
   }

    @Test
    public void filterOrders() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        List<Order> filtered = orders.stream().filter(o -> o.getValue() > 2.0d).collect(Collectors.<Order>toList());
        assertEquals(Arrays.asList(this.company.getMostRecentCustomer().getOrders().iterator().next()), filtered);
        this.company.getMostRecentCustomer().getOrders().add(null);
        assertTrue(this.company.getMostRecentCustomer().getOrders().contains(null), "Don't return a copy from Customer.getOrders(). The field should be a MList.");
    }
}
