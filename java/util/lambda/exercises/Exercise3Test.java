/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise3Test.java
 * @author Tristan Yan
 * @run testng Exercise3Test
 */

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;
import static org.testng.Assert.assertFalse;
import org.testng.annotations.Test;

public class Exercise3Test extends CompanyDomain {
    /**
     * Set up a {@link Predicate} that tests to see if a {@link Customer}'s city
     * is "London"
     */
    private static final Predicate<Customer> CUSTOMER_FROM_LONDON = c -> "London".equals(c.getCity());

    /**
     * Do any customers come from London? Use the Predicate
     * {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAnyCustomersLiveInLondon() {
        boolean anyCustomersFromLondon = company.getCustomers().stream().anyMatch(CUSTOMER_FROM_LONDON);
        assertTrue(anyCustomersFromLondon);
    }

    /**
     * Do all customers come from London? Use the Predicate
     * {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void doAllCustomersLiveInLondon() {
        boolean allCustomersFromLondon = company.getCustomers().stream().allMatch(CUSTOMER_FROM_LONDON);
        assertFalse(allCustomersFromLondon);
    }

    /**
     * How many customers come from London? Use the Predicate
     * {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void howManyCustomersLiveInLondon() {
        int numberOfCustomerFromLondon = company.getCustomers().stream().filter(CUSTOMER_FROM_LONDON).toArray().length;
        assertEquals(2, numberOfCustomerFromLondon, "Should be 2 London customers");
    }

    /**
     * Which customers come from London? Get a collection of those which do. Use
     * the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getLondonCustomers() {
        List<Customer> customersFromLondon = company.getCustomers().stream().filter(CUSTOMER_FROM_LONDON).collect(Collectors.<Customer>toList());
        assertEquals(2, customersFromLondon.size(), "Should be 2 London customers");
    }

    /**
     * Which customers do not come from London? Get a collection of those which
     * don't. Use the Predicate {@link #CUSTOMER_FROM_LONDON}.
     */
    @Test
    public void getCustomersWhoDontLiveInLondon() {
        List<Customer> customersNotFromLondon = company.getCustomers().stream().filter(CUSTOMER_FROM_LONDON.negate()).collect(Collectors.<Customer>toList());
        assertEquals(1, customersNotFromLondon.size(), "customers not from London");
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to
     * pass.
     */
    @Test
    public void findMary() {
        Customer mary = company.getCustomerNamed("Mary");
        assertEquals("Mary", mary.getName(), "customer's name should be Mary");
    }

    /**
     * Implement {@link Company#getCustomerNamed(String)} and get this test to
     * pass.
     */
    @Test
    public void findPete() {
        Customer pete = this.company.getCustomerNamed("Pete");
        assertNull(pete,
                "Should be null as there is no customer called Pete");
    }
}