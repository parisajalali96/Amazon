package Controllers;

import Models.*;
import Models.enums.Menu;
import Models.enums.ProductCommands;
import Models.enums.SortByTypes;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Scanner;
import java.util.regex.Matcher;

public class ProductMenuController {

    private static int startPoint;
    private static String currentSortBy;

    public ArrayList<Product> sortBy (String sortBy) {
        ArrayList<Product> products = new ArrayList<>(App.getProducts());
        if (SortByTypes.Rate.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getRating).reversed());
        } else if (SortByTypes.HigherPriceToLower.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getSellingPrice).reversed());
        } else if (SortByTypes.LowerPriceToHigher.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if (SortByTypes.NumberSold.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getNumberSold).reversed());
        }
        return products;
    }



    public Result showNext10Products() {
        if (startPoint + 10 >= App.getProducts().size() || startPoint + 10 < 0) return new Result(false, "No more products available.");
        startPoint += 10;
        showProducts(currentSortBy, startPoint);
        return new Result(true, "");
    }

    public Result showPrevious10Products() {
        if (startPoint - 10 >= App.getProducts().size() || startPoint - 10 < 0) return new Result(false, "No more products available.");
        startPoint = Math.max(0, startPoint - 10);
        showProducts(currentSortBy, startPoint);
        return new Result(true, "");
    }


    public Result showProducts(String sortBy, int start) {
        ArrayList<Product> products = sortBy(sortBy);
        if(!sortBy.equals(currentSortBy)) startPoint = 0;
        if (start >= products.size() || start < 0) return new Result(false, "No more products available.");

        String sortByCapital = sortBy.substring(0, 1).toUpperCase() + sortBy.substring(1);
        System.out.println("Product List (Sorted by: " + sortByCapital.trim() + ")");
        System.out.println("------------------------------------------------");
        Product lastProductShown = null;
        for (int i = start; i < Math.min(start + 10, products.size()); i++) {
            System.out.print("ID: " + products.get(i).getID());
            if(products.get(i).getDiscount() != null) {
                System.out.print("  **(On Sale! " + products.get(i).getDiscount().getQuantity() + " units discounted)**");
            } else if(products.get(i).getInStock() == 0) {
                System.out.print("  **(Sold out!)**");
            }
            System.out.println();
            System.out.println("Name: " + products.get(i).getName());
            System.out.printf("Rate: %.1f/5\n", products.get(i).getRating());
            if(products.get(i).getDiscount() == null) {
                System.out.printf("Price: $%.1f\n", products.get(i).getPrice());
            } else {
                Product currentProduct = products.get(i);
                double price = products.get(i).getPrice();
                Discount discount = products.get(i).getDiscount();
                int discountPercentage = discount.getDiscountPercentage();
                double discountedPrice = price * (1-(double)discountPercentage/100);

                System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n",price,discountedPrice, discountPercentage);
            }
            System.out.println("Brand: " + products.get(i).getBrand());
            System.out.println("Stock: " + products.get(i).getInStock());
            System.out.println("------------------------------------------------");
            lastProductShown = products.get(i);
        }
        System.out.println("(Showing " + (start + 1) + "-" + (start + 10) + " out of "
                + products.size() + ")");
        if (products.size() > 10 && start + 10 < products.size()) {
            System.out.println("Use `show next 10 products' to see more.");
        }
        currentSortBy = sortBy;
        return new Result(true, "");
    }

    public Result showProductInfo(String productId) {
        int ID;
        try {
            ID = Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            return new Result(false, "No product found.");
        }
        if (getProduct(ID) == null) {
            return new Result(false, "No product found.");
        }
        System.out.println("Product Details");
        System.out.println("------------------------------------------------");
        System.out.print("Name: " + getProduct(ID).getName());
        if(getProduct(ID).getDiscount() != null) System.out.println("  **(On Sale! " +
                getProduct(ID).getDiscount().getQuantity() + " units discounted)**");
        else if (getProduct(ID).getInStock() == 0) System.out.print(" **(Sold out!)**");
        System.out.println();
        System.out.println("ID: " + ID);
        System.out.printf("Rating: %.1f/5\n", getProduct(ID).getRating());
        if(getProduct(ID).getDiscount() == null) {
            System.out.printf("Price: $%.1f\n", getProduct(ID).getPrice());
        } else {
            Product product = getProduct(ID);
            double price = product.getPrice();
            double discountPercentage = product.getDiscount().getDiscountPercentage();
            double discountedPrice = price* (1 - discountPercentage / 100);
            System.out.printf("Price: ~$%.1f~ → $%.1f (-%d%%)\n",price,discountedPrice, discountPercentage);
        }
        System.out.println("Brand: " + getProduct(ID).getBrand());
        System.out.println("Number of Products Remaining: " + getProduct(ID).getInStock());
        System.out.println("About this item: ");
        System.out.println(getProduct(ID).getDescription());
        System.out.println();
        System.out.println("Customer Reviews: ");
        System.out.println("------------------------------------------------");
        if (!getProduct(ID).getReviews().isEmpty()) {
            for (Review review : getProduct(ID).getReviews()) {
                System.out.println(review.getCustomer() + " (" + review.getRating() + "/5)");
                if(!review.getComment().isEmpty()) {
                    System.out.println("\"" + review.getComment() + "\"");
                }
                System.out.println("------------------------------------------------");
            }
        }
        return new Result(true, "");
    }

    public Result rateProduct (String rating, String message, String id) {
        int ID;
        try {
            ID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new Result(false, "No product found.");
        }
        int rate = Integer.parseInt(rating);
        if (getProduct(ID) == null) {
            return new Result(false, "No product found.");
        } else if (rate > 5 || rate < 1) {
            return new Result(false, "Rating must be between 1 and 5.");
        } else if (App.getLoggedinUser() == null) {
            return new Result(false, "You must be logged in to rate a product.");
        }

        Product product = getProduct(ID);
        product.addReviews(new Review(App.getLoggedinUser(), message, rate));
        return new Result(true, "Thank you! Your rating and review have been submitted successfully.");
    }

    public Result addToCart(String productId, String number) {
        int ID;
        try {
            ID = Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            return new Result(false, "No product found.");
        }
        int quantity;
        try {
            quantity = Integer.parseInt(number);
        } catch (NumberFormatException e) {
            return new Result(false, "Quantity must be a positive number.");
        }

        if (App.getLoggedinUser() == null) {
            return new Result(false, "You must be logged in to add items to your cart.");
        } else if (getProduct(ID) == null) {
            return new Result(false, "No product found.");
        } else if (quantity <= 0) {
            return new Result(false, "Quantity must be a positive number.");
        } else if (getProduct(ID).getInStock() < quantity) {
            return new Result(false, "Only " + getProduct(ID).getInStock() + " units of \"" +
                    getProduct(ID).getName() + "\" are available.");
        }
        Product product = getProduct(ID);
        App.getLoggedinUser().getShoppingCart().addProduct(product, quantity);
        return new Result(true, "\"" + getProduct(ID).getName() +"\" "
                + quantity +" has been added to your cart.");
    }

    public Result goBack() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to the MainMenu ...");
    }

    public Product getProduct (int ID) {
        for (Product product : App.getProducts()) {
            if (product.getID() == ID) {
                return product;
            }
        }
        return null;
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command");
    }


}
