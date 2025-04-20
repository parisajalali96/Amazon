package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    User customer;
    HashMap<Product, Integer> products;
    HashMap<Product, Double> prices;
    Address shippingAddress;
    int orderID;
    private static int numberOfOrders;

    public Order(User customer, HashMap<Product, Integer> products, HashMap<Product,Double> prices, Address shippingAddress, int newID) {
        this.customer = customer;
        this.products = products;
        this.prices = prices;
        this.shippingAddress = shippingAddress;
        this.orderID = newID;
        numberOfOrders++;
    }

    public User getCustomer() {
        return customer;
    }
    public HashMap<Product, Integer> getProducts() {
        return products;
    }
    public Address getShippingAddress() {
        return shippingAddress;
    }
    public int getOrderID() {
        return orderID;
    }
    public HashMap<Product, Double> getPrices() {
        return prices;
    }
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
}
