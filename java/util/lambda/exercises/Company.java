/**
 * A company has a {@link MutableList} of {@link Customer}s.  It has an array of
 * {@link Supplier}s, and a name.
 */

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Company {
    private final String name;
    private final List<Customer> customers = new ArrayList<>();

    // suppliers are array based.
    private Supplier[] suppliers = new Supplier[0];

    public Company(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public void addCustomer(Customer aCustomer) {
        this.customers.add(aCustomer);
    }

    public List<Customer> getCustomers() {
        return this.customers;
    }

    public List<Order> getOrders() {
        List<Order> orders = new ArrayList<>();
        for (Customer customer : this.customers) {
            orders.addAll(customer.getOrders());
        }
        return orders;
    }

    public Customer getMostRecentCustomer() {
        return this.customers.get(customers.size() - 1);
    }

    public void addSupplier(Supplier supplier) {
        // need to replace the current array of suppliers with another, larger array
        // Of course, normally one would not use an array.

        final Supplier[] currentSuppliers = this.suppliers;
        this.suppliers = new Supplier[currentSuppliers.length + 1];
        System.arraycopy(currentSuppliers, 0, this.suppliers, 0, currentSuppliers.length);
        this.suppliers[this.suppliers.length - 1] = supplier;
    }

    public Supplier[] getSuppliers() {
        return this.suppliers;
    }

    /**
     * Use a {@link Predicate} to find a {@link Customer} with the name given.
     */
    public Customer getCustomerNamed(String name) {
        Optional<Customer> optCust = customers.stream().filter(c -> name.equals(c.getName())).findAny();
        if(optCust.isPresent())
            return optCust.get();
        return null;
    }
}
