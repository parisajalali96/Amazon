package Models;

import java.util.ArrayList;

public class Store {
    String brand;
    String password;
    String email;
    int id = 0;
    ArrayList<Product> products = new ArrayList<>();

    public Store(String brand, String password, String email) {
        this.brand = brand;
        this.password = password;
        this.email = email;
        id++;
    }

    public String getBrand() {
        return brand;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
    public ArrayList<Product> getProducts() {
        return products;
    }
    public void addProduct(String name, String brand, double sellingPrice, double productCost, int inStock, String description) {
        int ID = App.getProducts().size() + 101;
        Product product = new Product(name, brand, sellingPrice, productCost, inStock, description, ID);
        products.add(product);
        App.getProducts().add(product);
    }
}
