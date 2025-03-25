package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    HashMap<Product, Integer> products = new HashMap<>();

    public HashMap<Product, Integer> getProducts() {
        return products;
    }
    public void addProduct(Product product, int quantity) {
        products.put(product, quantity);
    }
    public void removeProduct(Product product, int quantity) {
        for (Product p : products.keySet()) {
            if (quantity == 0) break;
            if (p.getName().equals(product.getName())) {
                products.remove(p);
                quantity--;
            }
        }
    }
    public void addProductQuantity(Product product, int quantity) {
        products.put(product, products.get(product) + quantity);
    }
}
