package Models;

import java.util.ArrayList;

public class Product {
    String name;
    String brand;
    double rating;
    double sellingPrice;
    double productCost;
    int ID;
    int numberSold = 0;
    int inStock;
    ArrayList<Review> reviews;
    String description;
    Discount discount = null;

    public Product(String name, String brand, double sellingPrice, double productCost, int inStock, String description, int ID) {
        this.name = name;
        this.brand = brand;
        this.sellingPrice = sellingPrice;
        this.productCost = productCost;
        this.rating = 2.5;
        this.inStock = inStock;
        this.description = description;
        this.ID = ID;
    }
    public String getName () {
        return name;
    }
    public int getID () {
        return ID;
    }
    public String getBrand () {
        return brand;
    }
    public double getRating () {
        return rating;
    }
    public double getCost () {
        return productCost;
    }
    public double getPrice () {
        return sellingPrice;
    }
    public void setPrice (double price) {
        this.sellingPrice = price;
    }
    public int getNumberSold () {
        return numberSold;
    }
    public void addNumberSold (int amount) {
        numberSold += amount;
    }
    public int getInStock () {
        return inStock;
    }
    public void addInStock (int amount) {
        inStock += amount;
    }
    public ArrayList<Review> getReviews () {
        return reviews;
    }
    public void addReviews (Review review) {
        reviews.add(review);
    }
    public String getDescription () {
        return description;
    }
    public void setDescription (String description) {
        this.description = description;
    }
    public void addDiscount (Discount discount) {
        this.discount = discount;
    }
    public Discount getDiscount () {
        return discount;
    }

}
