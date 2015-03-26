/**
 * @test
 * @summary This sample shows different uses of aggregate operator
 * @library ./
 * @(#) Exercise2Test.java
 * @author Tristan Yan
 * @run testng Exercise2Test
 */

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import static org.testng.Assert.assertEquals;
import org.testng.annotations.Test;

public class Exercise2Test extends CompanyDomain {
    /**
      * Get the name of each of the company's customers. This time move the
      * {@link Function} to a  constant on {@link Customer}.
      */
    @Test
    public void getCustomerNames() {
        List<String> customerNames = company.getCustomers().stream().map(Customer.TO_NAME).collect(Collectors.<String>toList());
        List<String> expectedNames = Arrays.asList("Fred", "Mary", "Bill");
        assertEquals(expectedNames, customerNames);
    }

    /**
      * Get the city for each of the company's customers. This time move the {@link Function} to a
      * constant on {@link Customer}.
      */
    @Test
    public void getCustomerCities(){
        List<String> customerCities = company.getCustomers().stream().map(Customer.TO_CITY).collect(Collectors.<String>toList());
        List<String> expectedCities = Arrays.asList("London", "Liphook", "London");
        assertEquals(expectedCities, customerCities);
    }

    @Test
    public void getLondonCustomers() {
        List<Customer> customersFromLondon = company.getCustomers().stream().filter(Customer.sameCityPredicate("London")).collect(Collectors.<Customer>toList());
        assertEquals(2, customersFromLondon.size());
    }
}
