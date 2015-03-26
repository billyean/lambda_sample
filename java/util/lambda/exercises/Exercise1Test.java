/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise1Test.java
 * @author Tristan Yan
 * @run testng Exercise1Test
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class Exercise1Test extends CompanyDomain {
    /**
      * Get the name of each of the company's customers.
      */
    @Test
    public void getCustomerNames() {
        List<String> customerNames = company.getCustomers().stream().map(c -> c.getName()).collect(Collectors.<String>toList());
        List<String> expectedNames = Arrays.asList("Fred", "Mary", "Bill");
        assertEquals(expectedNames, customerNames);
    }

    /**
     * Get the city for each of the company's customers.
     */
    @Test
    public void getCustomerCities() {
        List<String> customerCities = company.getCustomers().stream().map(c -> c.getCity()).collect(Collectors.<String>toList());
        List<String> expectedCities = Arrays.asList("London", "Liphook", "London");
        assertEquals(expectedCities, customerCities);
    }

    /**
      * Which customers come from London.
      */
    @Test
    public void getLondonCustomers() {
        List<Customer> customersFromLondon = company.getCustomers().stream().filter(c -> "London".equals(c.getCity())).collect(Collectors.<Customer>toList());
        assertEquals(2, customersFromLondon.size());
    }
}