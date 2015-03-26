/**
 * Suppliers have a name and an array of itemNames. Suppliers don't like lists - they prefer arrays....
 */

import java.util.function.Function;

public class Supplier {
    public static final Function<Supplier, String> TO_NAME = supplier -> supplier.name;

    public static final Function<Supplier, Integer> TO_NUMBER_OF_ITEMS = supplier -> supplier.itemNames.length;

    private final String name;
    private final String[] itemNames;

    public Supplier(String name, String[] itemNames) {
        this.name = name;
        this.itemNames = itemNames;
    }

    public String getName() {
        return this.name;
    }

    public String[] getItemNames() {
        return this.itemNames;
    }
}
