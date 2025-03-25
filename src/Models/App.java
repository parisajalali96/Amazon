package Models;

import Models.enums.Menu;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class App {
    private static final ArrayList<User> users = new ArrayList<>();
    private static final ArrayList<Store> stores = new ArrayList<>();
    private static final ArrayList<Product> products = new ArrayList<>();
    private static final ArrayList<Order> orders = new ArrayList<>();
    private static User loggedinUser = null;
    private static Store loggedinStore = null;
    private static Menu currentMenu = Menu.LoginMenu;

    public static ArrayList<User> getUsers() {
        return users;
    }
    public static ArrayList<Store> getStores() {
        return stores;
    }
    public static ArrayList<Order> getOrders() {
        return orders;
    }
    public static void addOrder(Order order) {
        orders.add(order);
    }
    public static ArrayList<Product> getProducts() {
        return products;
    }
    public static User getLoggedinUser() {
        return loggedinUser;
    }
    public static void setLoggedinUser(User loggedinUser) {
        App.loggedinUser = loggedinUser;
    }
    public static Menu getCurrentMenu() {
        return currentMenu;
    }
    public static void setCurrentMenu(Menu currentMenu) {
        App.currentMenu = currentMenu;
    }
    public static Store getLoggedinStore() {
        return loggedinStore;
    }
    public static void setLoggedinStore(Store loggedinStore) {
        App.loggedinStore = loggedinStore;
    }
}
