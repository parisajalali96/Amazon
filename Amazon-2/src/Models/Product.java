package Models;

import java.util.ArrayList;

public class Product {
    String name;
    String brand;
    double rating = 0;
    double sellingPrice;
    double productCost;
    int ID;
    int numberSold = 0;
    int inStock;
    double revenue;
    double totalCost;
    ArrayList<Review> reviews = new ArrayList<>();
    String description;
    Discount discount = null;

    public Product(String name, String brand, double sellingPrice, double productCost, int inStock, String description, int ID) {
        this.name = name;
        this.brand = brand;
        this.sellingPrice = sellingPrice;
        this.productCost = productCost;
        this.inStock = inStock;
        this.totalCost = productCost * inStock;
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
        if(this.rating == 0) return 2.5;
        return rating/reviews.size();
    }
    public void addRating (double rating) {
        this.rating += rating;
    }
    public double getCost () {
        return productCost;
    }
    public double getPrice () {
        return sellingPrice;
    }
    public double getSellingPrice () {
        if(discount != null) {
            return this.sellingPrice*(1-(double)discount.getDiscountPercentage()/100);
        } else {
            return this.sellingPrice;
        }
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
        if(amount >0) {
            totalCost += amount * productCost;
        }
    }
    public void removedProduct(int num) {
        inStock += num;
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
    public void setDiscount (Discount discount) {
        this.discount = discount;
    }
    public Discount getDiscount () {
        return discount;
    }
    public void addToRevenue (double amount) {
        revenue += amount;
    }
    public double getRevenue () {
        return revenue;
    }
    public double getTotalCost () {
        return totalCost;
    }

}
