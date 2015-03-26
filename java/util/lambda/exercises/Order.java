/**
 * Has a number, a {@link Customer}, a {@link List} of {@link LineItem}s, and a
 * boolean that states whether or not the order has been delivered. There is a
 * class variable that contains the next order number.
 */
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;

public class Order {
    public static final Function<Order, Double> TO_VALUE = Order::getValue;

    public static final Predicate<Order> IS_DELIVERED = Order::isDelivered;

    public static final Function<Order, Iterable<LineItem>> TO_LINE_ITEMS = Order::getLineItems;

    private static int nextOrderNumber = 1;

    private final int orderNumber;

    private final List<LineItem> lineItems = new ArrayList<LineItem>();

    private boolean isDelivered;

    public Order() {
        this.orderNumber = nextOrderNumber;
        nextOrderNumber += 1;
    }

    public static void resetNextOrderNumber() {
        nextOrderNumber = 1;
    }

    public void deliver() {
        this.isDelivered = true;
    }

    public boolean isDelivered() {
        return this.isDelivered;
    }

    public void addLineItem(LineItem aLineItem) {
        this.lineItems.add(aLineItem);
    }

    public List<LineItem> getLineItems() {
        return this.lineItems;
    }

    @Override
    public String toString() {
        return "order " + this.orderNumber + " items: " + this.lineItems.size();
    }

    public double getValue() {
        ToDoubleFunction<LineItem> valueFunc = li -> li.getValue();
        return this.lineItems.stream().mapToDouble(valueFunc).sum();
    }
}
