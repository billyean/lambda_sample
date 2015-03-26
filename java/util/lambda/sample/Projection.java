/**
 * @test @summary This sample shows different uses of filter
 * @library ./
 * @(#) Projection.java
 * @author Tristan Yan
 * @run testng Projection
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

class Product {

    public int getProductID() {
        return productID;
    }

    public void setProductID(int productID) {
        this.productID = productID;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    public int getUnitsInStock() {
        return unitsInStock;
    }

    public void setUnitsInStock(int unitsInStock) {
        this.unitsInStock = unitsInStock;
    }

    public Product(int productID, String productName, String category, double unitPrice, int unitsInStock) {
        this.productID = productID;
        this.productName = productName;
        this.category = category;
        this.unitPrice = unitPrice;
        this.unitsInStock = unitsInStock;
    }
    private int productID;
    private String productName;
    private String category;
    private double unitPrice;
    private int unitsInStock;
}
            
@XmlRootElement(name = "order")
class Order{
    @XmlElement(name = "id")
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getOrderdate() {
        return orderdate;
    }

    public void setOrderdate(String orderdate) {
        this.orderdate = orderdate;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    private int orderId;
    private String orderdate;
    private double total;
}

@XmlRootElement(name = "customer")
@XmlType(propOrder = {"customerID", "companyName", "address", "city", "region", "postalcode", "country", "phone", "fax", "orders"})
class Customer {
    @XmlElement(name = "id")
    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    @XmlElement(name = "name")
    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFax() {
        return fax;
    }

    public void setFax(String fax) {
        this.fax = fax;
    }

    public ArrayList<Restrictions.Order> getOrders() {
        return orders;
    }

    private String customerID;
    private String companyName;
    private String address;
    private String city;
    private String region;
    private String postalcode;
    private String country;
    private String phone;
    private String fax;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    public ArrayList<Restrictions.Order> orders;
}

@XmlRootElement(name = "customers")
class Customers {
    public ArrayList<Restrictions.Customer> getCustomers() {
        return customers;
    }
    @XmlElement(name = "customer")
    private ArrayList<Restrictions.Customer> customers;
}


public class Projection {
    private List<Product> productList;

    private List<Product> CreateProductList() {
        if(productList == null) {
            productList = new ArrayList<>();

            productList.add(new Product(37, "Gravad lax", "Seafood",
                    26.0000d, 11));
            productList.add(new Product(38, "Côte de Blaye", "Beverages",
                    263.5000d, 17));
            productList.add(new Product(39, "Chartreuse verte", "Beverages",
                    18.0000d, 69));
            productList.add(new Product(40, "Boston Crab Meat", "Seafood",
                    18.4000d, 123));
            productList.add(new Product(41, "Jack's New England Clam Chowder", "Seafood",
                    9.6500d, 85));
            productList.add(new Product(42, "Singaporean Hokkien Fried Mee", "Grains/Cereals",
                    14.0000d, 26));
            productList.add(new Product(43, "Ipoh Coffee", "Beverages",
                    46.0000d, 17));
            productList.add(new Product(44, "Gula Malacca", "Condiments",
                    19.4500d, 27));
            productList.add(new Product(45, "Rogede sild", "Seafood",
                    9.5000d, 5));
            productList.add(new Product(46, "Spegesild", "Seafood",
                    12.0000d, 95));
            productList.add(new Product(47, "Zaanse koeken", "Confections",
                    9.5000d, 36));
            productList.add(new Product(48, "Chocolade", "Confections",
                    12.7500d, 15));
            productList.add(new Product(49, "Maxilaku", "Confections",
                    20.0000d, 10));
            productList.add(new Product(50, "Valkoinen suklaa", "Confections",
                    16.2500d, 65));
            productList.add(new Product(51, "Manjimup Dried Apples", "Produce",
                    53.0000d, 20));
            productList.add(new Product(52, "Filo Mix", "Grains/Cereals",
                    7.0000d, 38));
            productList.add(new Product(53, "Perth Pasties", "Meat/Poultry",
                    32.8000d, 0));
            productList.add(new Product(54, "Tourtière", "Meat/Poultry",
                    7.4500d, 21));
            productList.add(new Product(55, "Pâté chinois", "Meat/Poultry",
                    24.0000d, 115));
            productList.add(new Product(56, "Gnocchi di nonna Alice", "Grains/Cereals",
                    38.0000d, 21));
            productList.add(new Product(57, "Ravioli Angelo", "Grains/Cereals",
                    19.5000d, 36));
            productList.add(new Product(58, "Escargots de Bourgogne", "Seafood",
                    13.2500d, 62));
            productList.add(new Product(59, "Raclette Courdavault", "Dairy Products",
                    55.0000d, 79));
            productList.add(new Product(60, "Camembert Pierrot", "Dairy Products",
                    34.0000d, 19));
            productList.add(new Product(61, "Sirop d'érable", "Condiments",
                    28.5000d, 113));
            productList.add(new Product(62, "Tarte au sucre", "Confections",
                    49.3000d, 17));
            productList.add(new Product(63, "Vegie-spread", "Condiments",
                    43.9000d, 24));
            productList.add(new Product(64, "Wimmers gute Semmelknödel", "Grains/Cereals",
                    33.2500d, 22));
            productList.add(new Product(65, "Louisiana Fiery Hot Pepper Sauce", "Condiments",
                    21.0500d, 76));
            productList.add(new Product(66, "Louisiana Hot Spiced Okra", "Condiments",
                    17.0000d, 4));
            productList.add(new Product(67, "Laughing Lumberjack Lager", "Beverages",
                    14.0000d, 52));
            productList.add(new Product(68, "Scottish Longbreads", "Confections",
                    12.5000d, 6));
            productList.add(new Product(69, "Gudbrandsdalsost", "Dairy Products",
                    36.0000d, 26));
            productList.add(new Product(70, "Outback Lager", "Beverages",
                    15.0000d, 15));
            productList.add(new Product(71, "Flotemysost", "Dairy Products",
                    21.5000d, 26));
            productList.add(new Product(72, "Mozzarella di Giovanni", "Dairy Products",
                    34.8000d, 14));
            productList.add(new Product(73, "Röd Kaviar", "Seafood",
                    15.0000d, 101));
            productList.add(new Product(74, "Longlife Tofu", "Produce",
                    10.0000d, 4));
            productList.add(new Product(75, "Rhönbräu Klosterbier", "Beverages",
                    7.7500d, 125));
            productList.add(new Product(76, "Lakkalikööri", "Beverages",
                    18.0000d, 57));
            productList.add(new Product(77, "Original Frankfurter grüne Soße", "Condiments",
                    13.0000d, 32));
        }
        return productList;
    }

    /*
     * This sample uses select to produce a sequence of ints one higher than 
     * those in an existing array of ints.
     */
    @Test
    public void plusOne() {
        int[] numbers = {5, 4, 1, 3, 9, 8, 6, 7, 2, 0};
        int[] expected = {6, 5, 2, 4, 10, 9, 7, 8, 3, 1};
        int[] plusOne = Arrays.stream(numbers).map(i -> i + 1).toArray();
        assertEquals(expected, plusOne);
    }

    /*
     * This sample uses select to return a sequence of just the names of a list
     * of products.
     */
    @Test
    public void pruductNames() {
        List<Product> products = CreateProductList();
        Object[] productNames = products.stream().map((p) -> p.getProductName()).toArray();
        int index = 0;
        for(Product product : products)
            assertEquals(product.getProductName(), productNames[index++]);
    }
    
    /*
     * This sample uses select to produce a sequence of strings representing the
     * text version of a sequence of ints.
     */
    @Test
    public void numerTexts() {
        String[] texts = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
        String[] expected = { "five", "four", "one", "three", "nine", "eight", "six", "seven", "two", "zero" };
        
        assertEquals(Arrays.stream(numbers).mapToObj(i -> texts[i]).toArray(), expected);
    
    }

    /*
     * This sample uses select to produce a sequence of the uppercase and 
     * lowercase versions of each word in the original array.
     */
    @Test
    public void upperLowerWords() {
        String[] words = { "aPPLE", "BlUeBeRrY", "cHeRry" };
        Object[] upperLowerWords = Arrays.stream(words).map((word) -> new String[]{word.toUpperCase(), word.toLowerCase()}).toArray();
        assertEquals(words.length, upperLowerWords.length);
        for(int index = 0; index < words.length; index++) {
            String[] upperLower = (String[])upperLowerWords[index];
            assertEquals(upperLower.length, 2);
            assertEquals(upperLower[0], words[index].toUpperCase());
            assertEquals(upperLower[1], words[index].toLowerCase());
        }
    }

    /*
     * This sample uses select to produce a sequence of strings representing the
     * text version of a sequence of ints.
     */
    @Test
    public void evenOddNumbers() {
        int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
        Boolean[] expectedEvens = {false, true, false, false, false, true, true, false, true, true};
        Object[] evens = Arrays.stream(numbers).mapToObj(i -> (i & 0x01) == 0).toArray();
        assertEquals(expectedEvens, evens);
    }

    /*
     * This sample uses an indexed Select clause to determine if the value of 
     * ints in an array match their position in the array.
     */
    @Test
    public void numbersInPlace() {
        int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
        Boolean[] expectedInPlaces = {false, false, false, true, false, false, true, true, false, false};
        AtomicInteger index = new AtomicInteger(0);
        Object[] inPlaces = Arrays.stream(numbers).mapToObj(i -> (i == index.getAndIncrement())).toArray();
        assertEquals(expectedInPlaces, inPlaces);
    }
    
    /*
     * This sample combines select and where to make a simple query that returns
     * the text form of each digit less than 5.
     */
    @Test
    public void digitsLessFive() {
        int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
        String[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        String[] expected = {"four", "one", "three", "two", "zero"};
        Object[] lowNums = Arrays.stream(numbers).filter(i -> i < 5).mapToObj(i -> digits[i]).toArray();
        assertEquals(lowNums, expected);
    }

    /*
     * This sample uses a compound from clause to make a query that returns all
     * pairs of numbers from both arrays such that the number from numbersA is
     * less than the number from numbersB.
     */
    @Test
    public void SelectManyLessThan() {
        int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
        int[] numbersB = { 1, 3, 5, 7, 8 };
        
        List<int[]> result = new ArrayList<>();
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{0 ,1});
        expected.add(new int[]{0 ,3});
        expected.add(new int[]{0 ,5});
        expected.add(new int[]{0 ,7});
        expected.add(new int[]{0 ,8});
        expected.add(new int[]{2 ,3});
        expected.add(new int[]{2 ,5});
        expected.add(new int[]{2 ,7});
        expected.add(new int[]{2 ,8});
        expected.add(new int[]{4 ,5});
        expected.add(new int[]{4 ,7});
        expected.add(new int[]{4 ,8});
        expected.add(new int[]{5 ,7});
        expected.add(new int[]{5 ,8}); 
        expected.add(new int[]{6 ,7});
        expected.add(new int[]{6 ,8}); 
        Arrays.stream(numbersA).forEach(t -> {
            for(int index = 0; index < numbersB.length; index++)
                if(t < numbersB[index])
                    result.add(new int[]{t, numbersB[index]});
        });
        assertEquals(expected, result);
    }
    /*
     * This sample uses a compound from clause to select all orders where the
     * order total is less than 500.00.
     */
    @Test
    public void totalLessThan500() {
        int[] numbersA = { 0, 2, 4, 5, 6, 8, 9 };
        int[] numbersB = { 1, 3, 5, 7, 8 };
        
        List<int[]> result = new ArrayList<>();
        List<int[]> expected = new ArrayList<>();
        expected.add(new int[]{0 ,1});
        expected.add(new int[]{0 ,3});
        expected.add(new int[]{0 ,5});
        expected.add(new int[]{0 ,7});
        expected.add(new int[]{0 ,8});
        expected.add(new int[]{2 ,3});
        expected.add(new int[]{2 ,5});
        expected.add(new int[]{2 ,7});
        expected.add(new int[]{2 ,8});
        expected.add(new int[]{4 ,5});
        expected.add(new int[]{4 ,7});
        expected.add(new int[]{4 ,8});
        expected.add(new int[]{5 ,7});
        expected.add(new int[]{5 ,8}); 
        expected.add(new int[]{6 ,7});
        expected.add(new int[]{6 ,8}); 
        Arrays.stream(numbersA).forEach(t -> {
            for(int index = 0; index < numbersB.length; index++)
                if(t < numbersB[index])
                    result.add(new int[]{t, numbersB[index]});
        });
        assertEquals(expected, result);
    }
}
