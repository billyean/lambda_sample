/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise8Test.java
 * @author Tristan Yan
 * @run testng Exercise8Test
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class Exercise8Test extends CompanyDomain {
    /**
     * Create a Map where the values are the names of cities and the keys
     * are the customers from those cities.
     */
    @Test
    public void customersByCity() {
        // Notice that the second generic type is Customer, not List<Customer>
        Function<Customer, String> cityFunc = c -> c.getCity();
        Map<String, List<Customer>> multimap = company.getCustomers().stream().
                collect(Collectors.groupingBy(cityFunc));

        assertEquals(Arrays.asList(this.company.getCustomerNamed("Mary")), multimap.get("Liphook"));
        assertEquals(
                Arrays.asList(
                this.company.getCustomerNamed("Fred"),
                this.company.getCustomerNamed("Bill")),
                multimap.get("London"));
    }

    @Test
    public void mapOfItemsToSuppliers() {
        /**
         * Change itemsToSuppliers to a Map<String, List<Supplier>>
         */
        final Map<String, List<Supplier>> itemsToSuppliers = new HashMap<>();

        Arrays.stream(company.getSuppliers()).forEach(supplier
                    -> {
            Arrays.stream(supplier.getItemNames()).forEach(itemName
                        -> {
                List<Supplier> suppliersForItem;
                        if (itemsToSuppliers.containsKey(itemName)) {
                            suppliersForItem = itemsToSuppliers.get(itemName);
                        } else {
                            suppliersForItem = new ArrayList<>();
                            itemsToSuppliers.put(itemName, suppliersForItem);
                        }
                        suppliersForItem.add(supplier);
                    });
                });
        assertEquals(2, itemsToSuppliers.get("sofa").size(), "should be 2 suppliers for sofa");
    }
}
