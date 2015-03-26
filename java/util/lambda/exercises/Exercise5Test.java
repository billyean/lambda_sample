/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise5Test.java
 * @author Tristan Yan
 * @run testng Exercise5Test
 */

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import org.testng.annotations.Test;

public class Exercise5Test extends CompanyDomain {
    /**
     * Find company all Supplier's name.
     */
    @Test
    public void findSupplierNames() {
        List<String> supplierNames = Arrays.stream(company.getSuppliers()).map(s -> s.getName()).collect(Collectors.<String>toList());

        List<String> expectedSupplierNames = Arrays.asList(
                "Shedtastic",
                "Splendid Crocks",
                "Annoying Pets",
                "Gnomes 'R' Us",
                "Furniture Hamlet",
                "SFD",
                "Doxins");
        assertEquals(expectedSupplierNames, supplierNames);
    }

    /**
     * Create a {@link Predicate} for Suppliers that supply more than 2 items.
     * Find the number of suppliers that satisfy that Predicate.
     */
    @Test
    public void countSuppliersWithMoreThanTwoItems() {
        Predicate<Supplier> moreThanTwoItems = s -> s.getItemNames().length > 2;
        int suppliersWithMoreThanTwoItems = Arrays.stream(company.getSuppliers()).filter(moreThanTwoItems).toArray().length;
        assertEquals(5, suppliersWithMoreThanTwoItems, "suppliers with more than 2 items");
    }

    /**
     * Find the supplier who can supplies sandwich toaster.
     */
    @Test
    public void whoSuppliesSandwichToaster() {
        // Create a Predicate that will check to see if a Supplier supplies a "sandwich toaster".
        Predicate<Supplier> suppliesToaster = s -> Arrays.stream(s.getItemNames()).filter(i -> "sandwich toaster".equals(i)).findAny().isPresent();

        // Find one supplier that supplies toasters.
        Supplier toasterSupplier = Arrays.stream(company.getSuppliers()).filter(suppliesToaster).findAny().get();
        assertNotNull(toasterSupplier, "toaster supplier");
        assertEquals("Doxins", toasterSupplier.getName());
    }

    /**
     * Find the order that value is greater than 1.5.
     */
    @Test
    public void filterOrderValues() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        /**
         * Get the order values that are greater than 1.5.
         */
        List<Double> orderValues = orders.stream().map(order -> order.getValue()).collect(Collectors.<Double>toList());
        List<Double> filtered = orderValues.stream().filter(o -> o > 1.5).collect(Collectors.<Double>toList());
        assertEquals(Arrays.asList(372.5, 1.75), filtered);
    }

    @Test
    public void filterOrders() {
        List<Order> orders = this.company.getMostRecentCustomer().getOrders();
        /**
         * Get the actual orders (not their double values) where those orders have a value greater than 2.0.
         */
        List<Order> filtered = orders.stream().filter(order -> order.getValue() > 2.0).collect(Collectors.<Order>toList());
        assertEquals(Arrays.asList(company.getMostRecentCustomer().getOrders().iterator().next()), filtered);
    }
}