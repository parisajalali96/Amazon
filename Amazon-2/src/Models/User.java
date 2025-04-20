package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class User {
    String firstName;
    String lastName;
    String email;
    String password;
    ShoppingCart cart = new ShoppingCart();
    ArrayList<Address> addresses = new ArrayList<>();
    ArrayList<Order> orders = new ArrayList<>();
    ArrayList<CreditCard> creditCards = new ArrayList<>();
    int numberOfOrders;
    int numberOfCreditCards;
    int numberOfAddresses;
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
    public void addAddress(String country, String city, String street, String postalCode) {
        int newId = ++numberOfAddresses;
        this.addresses.add(new Address(country, city, street, postalCode, newId));
    }
    public void removeAddress(Address address) {
        this.addresses.remove(address);
    }
    public ArrayList<Order> getOrders() {
        return orders;
    }
    public void addOrder(HashMap<Product, Integer> products, HashMap<Product,Double> prices,Address address ) {
        int newId = ++numberOfOrders + 100;
        this.orders.add(new Order(App.getLoggedinUser(), new HashMap<>(products), new HashMap<>(prices), address, newId));
    }
    public ArrayList<CreditCard> getCreditCards() {
        return creditCards;
    }
    public void addCreditCard(String cardNumber, String expDate, String cvv, double initialValue) {
        int newId = ++numberOfCreditCards;
        this.creditCards.add(new CreditCard(cardNumber, expDate, cvv, initialValue, newId));
    }
    public ShoppingCart getShoppingCart() {
        return cart;
    }
    public int getNumberOfOrders() {
        return numberOfOrders;
    }
    public int getNumberOfCreditCards() {
        return numberOfCreditCards;
    }
    public int getNumberOfAddresses() {
        return numberOfAddresses;
    }
}
