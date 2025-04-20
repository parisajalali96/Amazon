package Models;

import java.util.ArrayList;
import java.util.HashMap;

public class ShoppingCart {
    HashMap<Product, Integer> productQuantity = new HashMap<>();
    HashMap<Product,Double> productPrice = new HashMap<>();
    public HashMap<Product, Integer> getProductQuantity() {
        return productQuantity;
    }
    public HashMap<Product, Double> getProductPrice() {return productPrice;}
    public void addProduct(Product product, int quantity, double priceBought) {
        productQuantity.put(product, quantity);
        productPrice.put(product,priceBought);
    }
    public void removeProduct(Product product, int quantity) {
        for (Product p : productQuantity.keySet()) {
            if (p.getName().equals(product.getName())) {
                productQuantity.put(p, productQuantity.get(p) - quantity);
                p.removedProduct(quantity);
                if(p.getDiscount() != null && p.getDiscount().getNumberApplied() != 0) {
                    if(quantity > p.getDiscount().getNumberApplied()) {
                        p.getDiscount().addNumberApplied(-p.getDiscount().getNumberApplied());
                    } else {
                        p.getDiscount().addNumberApplied(-quantity);
                    }
                }
            }
        }
    }
    public void addProductQuantity(Product product, int quantity) {
        productQuantity.put(product, productQuantity.get(product) + quantity);
    }
    public void resetProducts(){
        productQuantity.clear();
        productPrice.clear();
    }
}
