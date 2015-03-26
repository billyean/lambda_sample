import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;


/**
 * Customers have a name, city and a list of {@link Order}s
 */
public class Customer {
    public static final Function<Customer, String> TO_NAME = Customer::getName;

    public static final Function<Customer, String> TO_CITY = Customer::getCity;

    public static final Function<Customer, Double> TO_TOTAL_ORDER_VALUE = Customer::getTotalOrderValue;

    public static Predicate<Customer> sameCityPredicate(String city) {
        return c -> city.equals(c.getCity());
    }

    private final String name;
    private final String city;

    private final List<Order> orders = new ArrayList<Order>();

    public Customer(String name, String city) {
        this.name = name;
        this.city = city;
    }

    public String getCity() {
        return this.city;
    }

    public String getName() {
        return this.name;
    }

    public List<Order> getOrders() {
        return this.orders;
    }

    public void addOrder(Order anOrder) {
        this.orders.add(anOrder);
    }

    public double getTotalOrderValue() {
        ToDoubleFunction<Order> valueFunc = o -> o.getValue();
        return this.orders.stream().mapToDouble(valueFunc).sum();
    }
}
