/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise7Test.java
 * @author Tristan Yan
 * @run testng Exercise7Test
 */

import java.util.Arrays;
import java.util.StringJoiner;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.Test;

public class Exercise7Test extends CompanyDomain {
    /**
     * Get a list of the customers' total order values, sorted. Check out the
     * implementation of {@link Customer#getTotalOrderValue()} and
     * {@link Order#getValue()} .
     */
    @Test
    public void sortedTotalOrderValue() {
        double[] sortedTotalValues = company.getCustomers().stream().mapToDouble(c -> c.getTotalOrderValue()).sorted().toArray();

        // Don't forget the handy utility methods getFirst() and getLast()...
        assertEquals(857.0d, sortedTotalValues[sortedTotalValues.length - 1], "Highest total order value");
        assertEquals(71.0d, sortedTotalValues[0], "Lowest total order value");
    }

    /**
     * Find the max total order value across all customers.
     */
    @Test
    public void maximumTotalOrderValue() {
        double maximumTotalOrderValue = company.getCustomers().stream().mapToDouble(c -> c.getTotalOrderValue()).max().getAsDouble();
        assertEquals(857.0d, maximumTotalOrderValue, "max value");
    }

    /**
     * Find the customer with the highest total order value.
     */
    @Test
    public void customerWithMaxTotalOrderValue() {
        Customer customerWithMaxTotalOrderValue = (Customer) company.getCustomers().stream().sorted((c1, c2) -> Double.valueOf(c2.getTotalOrderValue()).compareTo(c1.getTotalOrderValue())).toArray()[0];
        assertEquals(this.company.getCustomerNamed("Mary"), customerWithMaxTotalOrderValue);
    }

    /**
     * Create some code to get the company's supplier names as a tilde delimited
     * string.
     */
    @Test
    public void supplierNamesAsTildeDelimitedString() {
        StringJoiner sj = new StringJoiner("~");
        Arrays.stream(company.getSuppliers()).map(s -> s.getName()).forEach(n -> sj.add(n));
        String tildeSeparatedNames = sj.toString();
        assertEquals(
                "Shedtastic~Splendid Crocks~Annoying Pets~Gnomes 'R' Us~Furniture Hamlet~SFD~Doxins",
                tildeSeparatedNames,
                "tilde separated names");
    }

    /**
     * Deliver all orders going to customers from London.
     *
     * @see Order#deliver()
     */
    @Test
    public void deliverOrdersToLondon() {
        assertTrue(company.getCustomerNamed("Fred").getOrders().stream().allMatch(Order.IS_DELIVERED));
        assertTrue(company.getCustomerNamed("Mary").getOrders().stream().allMatch(Order.IS_DELIVERED.negate()));
        assertTrue(company.getCustomerNamed("Bill").getOrders().stream().allMatch(Order.IS_DELIVERED));
    }
}
