package Models;

import java.util.ArrayList;

public class ShoppingCart {
    ArrayList<Product> products;
    boolean checkout;

    public ArrayList<Product> getProducts() {
        return products;
    }
    public void addProduct(Product product) {
        products.add(product);
    }
}
