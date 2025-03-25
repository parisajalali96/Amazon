package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class Order {
    User customer;
    HashMap<Product, Integer> products;
    Address shippingAddress;
    int orderID;

    public Order(User customer, HashMap<Product, Integer> products, Address shippingAddress, int newID) {
        this.customer = customer;
        this.products = products;
        this.shippingAddress = shippingAddress;
        this.orderID = newID;
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
}
