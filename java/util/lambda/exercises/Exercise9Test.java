/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise9Test.java
 * @author Tristan Yan
 * @run testng Exercise9Test
 */

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

public class Exercise9Test extends CompanyDomain {
    /**
     * Figure out which customers ordered saucers (in any of their orders).
     */
    @Test
    public void whoOrderedSaucers() {
        List<Customer> customersWithSaucers = company.getCustomers().stream().
                filter(cust -> cust.getOrders().stream().anyMatch(order -> order.getLineItems().stream().anyMatch(li -> "saucer".equals(li.getName())))).
                collect(Collectors.<Customer>toList());
        assertEquals(2, customersWithSaucers.size(), "customers with saucers");
    }

    @Test
    public void ordersByCustomerUsingAsMap() {
        Map<String, List<Order>> customerNameToOrders = new HashMap<>();

        this.company.getCustomers().forEach(cust
                    -> {
            customerNameToOrders.put(cust.getName(), cust.getOrders());
                });
        assertNotNull(customerNameToOrders, "customer name to orders");
        assertEquals(3, customerNameToOrders.size(), "customer names");
        List<Order> ordersForBill = customerNameToOrders.get("Bill");
        assertEquals(3, ordersForBill.size(), "Bill orders");
    }

    /**
     * Create a map where the keys are customers and the values is the price of
     * the most expensive item that the customer ordered.
     */
    @Test
    public void mostExpensiveItem() {
        Map<Customer, Double> mostExpensiveMap = new HashMap<>();
        this.company.getCustomers().forEach(c
            -> { 
                Function<Order, Stream<LineItem>> fm = or -> or.getLineItems().stream();
                Double mostExpensive = c.getOrders().stream().flatMap(fm).map(li -> li.getValue()).max(Comparator.naturalOrder()).get();
                mostExpensiveMap.put(c, mostExpensive);
                });
        assertEquals(3, mostExpensiveMap.size());
        assertEquals(2, mostExpensiveMap.values().stream().distinct().toArray().length);

        Map fiftyMap = new HashMap(mostExpensiveMap);
        mostExpensiveMap.forEach((k,v
                    ) -> { if (v != 50.0d) {
                        fiftyMap.remove(k);
                    }
                });
        assertEquals(Arrays.asList(
                this.company.getCustomerNamed("Bill"),
                this.company.getCustomerNamed("Fred")),
                (List<Customer>) fiftyMap.keySet().stream().sorted(Comparator.comparing(Customer.TO_NAME)).collect(Collectors.<Customer>toList()));
    }
}
