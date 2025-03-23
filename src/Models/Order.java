package Models;

import java.util.ArrayList;

public class Order {
    User customer;
    ArrayList<Product> products;
    Address shippingAddress;
    int orderID;

    public Order(User customer, ArrayList<Product> products, Address shippingAddress, int id) {
        this.customer = customer;
        this.products = products;
        this.shippingAddress = shippingAddress;
        this.orderID = id;
    }

    public User getCustomer() {
        return customer;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public Address getShippingAddress() {
        return shippingAddress;
    }
    public int getOrderID() {
        return orderID;
    }
}
