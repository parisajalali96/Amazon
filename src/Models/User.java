package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    String firstName;
    String lastName;
    String email;
    String password;
    ShoppingCart cart;
    ArrayList<Address> addresses = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<CreditCard> creditCards = new ArrayList<>();
    int addressID = 1;

    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public ArrayList<Address> getAddresses() {
        return addresses;
    }
    public void addAddress(String country, String city, String street, int postalCode) {
        int newId = addresses.size() + 1;
        this.addresses.add(new Address(country, city, street, postalCode, newId));
    }
    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public void addOrder(HashMap<Product, Integer> products, Address address ) {
        int newId = orders.size() + 101;
        this.orders.add(new Order(App.getLoggedinUser(), products, address, newId));
    }
    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }
    public void addCreditCard(String cardNumber, String expDate, String cvv, int initialValue) {
        int newId = creditCards.size() + 1;
        this.creditCards.add(new CreditCard(cardNumber, expDate, cvv, initialValue, newId));
    }
    public ShoppingCart getShoppingCart() {
        return cart;
    }
}
