/**
 * @test
 * @summary This sample shows different uses of filter
 * @library .
 * @(#) Restrictions.java
 * @author Tristan Yan
 * @run testng Restrictions
 */

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import static org.testng.Assert.*;
import org.testng.annotations.Test;

public class Restrictions {
    private final static String CUSTOMERS_XML = "Customers.xml";

    private List<Product> productList;
    
    private List<Customer> customerList;
    
    static class Product {
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
    static class Order{
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
    static class Customer {
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

        public ArrayList<Order> getOrders() {
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
        public ArrayList<Order> orders;
    }
    
    @XmlRootElement(name = "customers")
    static class Customers {
        public ArrayList<Customer> getCustomers() {
            return customers;
        }
        @XmlElement(name = "customer")
        private ArrayList<Customer> customers;
    }

    public List<Product> GetProductList()
    {
        if (productList == null)
            createLists();
        return productList;
    }

    public List<Customer> GetCustomerList()
    {
        if (customerList == null)
            createLists();
        return customerList;
    } 
    
    private void createLists() {
        // Product data created in-memory using collection initializer:
        productList = new ArrayList<>();
        productList.add(new Product(1, "Chai", "Beverages", 18.0000d, 39));
        productList.add(new Product(2, "Chang", "Beverages", 19.0000d, 17));
        productList.add(new Product(3, "Aniseed Syrup", "Condiments", 10.0000d, 13));
        productList.add(new Product(4, "Chef Anton's Cajun Seasoning", "Condiments", 22.0000d, 53));
        productList.add(new Product(5, "Chef Anton's Gumbo Mix", "Condiments", 21.3500d, 0));
        productList.add(new Product(6, "Grandma's Boysenberry Spread", "Condiments", 25.0000d, 120));
        productList.add(new Product(7, "Uncle Bob's Organic Dried Pears", "Produce", 30.0000d, 15));
        productList.add(new Product(8, "Northwoods Cranberry Sauce", "Condiments", 40.0000d, 6));
        productList.add(new Product(9, "Mishi Kobe Niku", "Meat/Poultry", 97.0000d, 29));
        productList.add(new Product(10, "Ikura", "Seafood", 31.0000d, 31));
        productList.add(new Product(11, "Queso Cabrales", "Dairy Products", 21.0000d, 22));
        productList.add(new Product(12, "Queso Manchego La Pastora", "Dairy Products", 38.0000d, 86));
        productList.add(new Product(13, "Konbu", "Seafood", 6.0000d, 24));
        productList.add(new Product(14, "Tofu", "Produce", 23.2500d, 35));
        productList.add(new Product(15, "Genen Shouyu", "Condiments", 15.5000d, 39));
        productList.add(new Product(16, "Pavlova", "Confections", 17.4500d, 29));
        productList.add(new Product(17, "Alice Mutton", "Meat/Poultry", 39.0000d, 0));
        productList.add(new Product(18, "Carnarvon Tigers", "Seafood", 62.5000d, 42));
        productList.add(new Product(19, "Teatime Chocolate Biscuits", "Confections", 9.2000d, 25));
        productList.add(new Product(20, "Sir Rodney's Marmalade", "Confections", 81.0000d, 40));
        productList.add(new Product(21, "Sir Rodney's Scones", "Confections", 10.0000d, 3));
        productList.add(new Product(22, "Gustaf's Knäckebröd", "Grains/Cereals", 21.0000d, 104));
        productList.add(new Product(23, "Tunnbröd", "Grains/Cereals", 9.0000d, 61));
        productList.add(new Product(24, "Guaraná Fantástica", "Beverages", 4.5000d, 20));
        productList.add(new Product(25, "NuNuCa Nuß-Nougat-Creme", "Confections", 14.0000d, 76));
        productList.add(new Product(26, "Gumbär Gummibärchen", "Confections", 31.2300d, 15));
        productList.add(new Product(27, "Schoggi Schokolade", "Confections", 43.9000d, 49));
        productList.add(new Product(28, "Rössle Sauerkraut", "Produce", 45.6000d, 26));
        productList.add(new Product(29, "Thüringer Rostbratwurst", "Meat/Poultry", 123.7900d, 0));
        productList.add(new Product(30, "Nord-Ost Matjeshering", "Seafood", 25.8900d, 10));
        productList.add(new Product(31, "Gorgonzola Telino", "Dairy Products", 12.5000d, 0));
        productList.add(new Product(32, "Mascarpone Fabioli", "Dairy Products", 32.0000d, 9));
        productList.add(new Product(33, "Geitost", "Dairy Products", 2.5000d, 112));
        productList.add(new Product(34, "Sasquatch Ale", "Beverages", 14.0000d, 111));
        productList.add(new Product(35, "Steeleye Stout", "Beverages", 18.0000d, 20));
        productList.add(new Product(36, "Inlagd Sill", "Seafood", 19.0000d, 112));
        productList.add(new Product(37, "Gravad lax", "Seafood", 26.0000d, 11));
        productList.add(new Product(38, "Côte de Blaye", "Beverages", 263.5000d, 17));
        productList.add(new Product(39, "Chartreuse verte", "Beverages", 18.0000d, 69));
        productList.add(new Product(40, "Boston Crab Meat", "Seafood", 18.4000d, 123));
        productList.add(new Product(41, "Jack's New England Clam Chowder", "Seafood", 9.6500d, 85));
        productList.add(new Product(42, "Singaporean Hokkien Fried Mee", "Grains/Cereals", 14.0000d, 26));
        productList.add(new Product(43, "Ipoh Coffee", "Beverages", 46.0000d, 17));
        productList.add(new Product(44, "Gula Malacca", "Condiments", 19.4500d, 27));
        productList.add(new Product(45, "Rogede sild", "Seafood", 9.5000d, 5));
        productList.add(new Product(46, "Spegesild", "Seafood", 12.0000d, 95));
        productList.add(new Product(47, "Zaanse koeken", "Confections", 9.5000d, 36));
        productList.add(new Product(48, "Chocolade", "Confections", 12.7500d, 15));
        productList.add(new Product(49, "Maxilaku", "Confections", 20.0000d, 10));
        productList.add(new Product(50, "Valkoinen suklaa", "Confections", 16.2500d, 65));
        productList.add(new Product(51, "Manjimup Dried Apples", "Produce", 53.0000d, 20));
        productList.add(new Product(52, "Filo Mix", "Grains/Cereals", 7.0000d, 38));
        productList.add(new Product(53, "Perth Pasties", "Meat/Poultry", 32.8000d, 0));
        productList.add(new Product(54, "Tourtière", "Meat/Poultry", 7.4500d, 21));
        productList.add(new Product(55, "Pâté chinois", "Meat/Poultry", 24.0000d, 115));
        productList.add(new Product(56, "Gnocchi di nonna Alice", "Grains/Cereals", 38.0000d, 21));
        productList.add(new Product(57, "Ravioli Angelo", "Grains/Cereals", 19.5000d, 36));
        productList.add(new Product(58, "Escargots de Bourgogne", "Seafood", 13.2500d, 62));
        productList.add(new Product(59, "Raclette Courdavault", "Dairy Products", 55.0000d, 79));
        productList.add(new Product(60, "Camembert Pierrot", "Dairy Products", 34.0000d, 19));
        productList.add(new Product(61, "Sirop d'érable", "Condiments", 28.5000d, 113));
        productList.add(new Product(62, "Tarte au sucre", "Confections", 49.3000d, 17));
        productList.add(new Product(63, "Vegie-spread", "Condiments", 43.9000d, 24));
        productList.add(new Product(64, "Wimmers gute Semmelknödel", "Grains/Cereals", 33.2500d, 22));
        productList.add(new Product(65, "Louisiana Fiery Hot Pepper Sauce", "Condiments", 21.0500d, 76));
        productList.add(new Product(66, "Louisiana Hot Spiced Okra", "Condiments", 17.0000d, 4));
        productList.add(new Product(67, "Laughing Lumberjack Lager", "Beverages", 14.0000d, 52));
        productList.add(new Product(68, "Scottish Longbreads", "Confections", 12.5000d, 6));
        productList.add(new Product(69, "Gudbrandsdalsost", "Dairy Products", 36.0000d, 26));
        productList.add(new Product(70, "Outback Lager", "Beverages", 15.0000d, 15));
        productList.add(new Product(71, "Flotemysost", "Dairy Products", 21.5000d, 26));
        productList.add(new Product(72, "Mozzarella di Giovanni", "Dairy Products", 34.8000d, 14));
        productList.add(new Product(73, "Röd Kaviar", "Seafood", 15.0000d, 101));
        productList.add(new Product(74, "Longlife Tofu", "Produce", 10.0000d, 4));
        productList.add(new Product(75, "Rhönbräu Klosterbier", "Beverages", 7.7500d, 125));
        productList.add(new Product(76, "Lakkalikööri", "Beverages", 18.0000d, 57));
        productList.add(new Product(77, "Original Frankfurter grüne Soße", "Condiments", 13.0000d, 32));

        try {
            JAXBContext context = JAXBContext.newInstance(Customers.class);
            Unmarshaller um = context.createUnmarshaller();
            Customers cs = (Customers) um.unmarshal(new File(System.getProperty("test.src", "."), CUSTOMERS_XML));
            customerList = cs.getCustomers();
        } catch (JAXBException ex) {
            throw new RuntimeException(ex);
        }
    }

    /*
     * This sample  uses the where clause  to find all elements  of an array  
     * with a value less than 5
     */
    @Test 
    public void where1(){
        int[] numbers = { 5, 4, 1, 3, 9, 8, 6, 7, 2, 0 };
        int[] lowNums = Arrays.stream(numbers).filter((n) -> n < 5).toArray();
        for(int i: lowNums)
            assertTrue(i < 5);
    }

    /*
     * This sample uses the where clause to find all products that are out of 
     * stock 
     */
    @Test 
    public void where2(){
        List<Product> products = GetProductList();
        Iterator<Product> iter = products.stream().filter((p) -> p.unitsInStock == 0).iterator();
        while(iter.hasNext())
            assertEquals(iter.next().unitsInStock, 0);
    }

    /*
     * This sample uses the where clause to find all products that are in  stock 
     *  and cost more than 3.00 per unit
     */
    @Test 
    public void where3(){
        List<Product> products = GetProductList();
        Iterator<Product> iter = products.stream().filter((p) -> p.unitsInStock > 0 && p.unitPrice > 3.00d).iterator();
        System.out.println("In-stock products that cost more than 3.00:");
        while(iter.hasNext()) {
            Product p = iter.next();
            assertTrue(p.unitsInStock > 0);
            assertTrue(p.unitPrice > 3.00d);
            System.out.println(p.productName + " is in stock and costs more than 3.00.");
        }
    }
    
    /*
     * This sample uses the where  clause to find all customers in Washington 
     * and then it uses a foreach loop to iterate over the orders collection 
     * that belongs to each customer
     */
    @Test
    public void where4(){
        List<Customer> customers = GetCustomerList();
        customers.stream().filter((c) -> "WA".equals(c.region)).forEach(
            (c) -> {
               assertEquals(c.region, "WA");
               System.out.println("Customer " + c.customerID + ": " + c.companyName );
               List<Order> orders = c.getOrders();
               for(Order order : orders)
                  System.out.println("\tOrder " + order.orderId + ": " + order.orderdate); 
            });

    }

    /*
     * This sample demonstrates an indexed where clause that returns digits 
     * whose name is shorter than their value
     */
    @Test
    public void where5(){
        String[] digits = { "zero", "one", "two", "three", "four", "five", "six", "seven", "eight", "nine" };
        String[] expected = { "five", "six", "seven", "eight", "nine" };
        AtomicInteger index = new AtomicInteger(0);
        Object[] shortDigits = Arrays.stream(digits).filter((s) -> s.length() < index.getAndIncrement()).toArray();
        
        assertEquals(expected, shortDigits);
    }
}
