package Controllers;

import Models.App;
import Models.Product;
import Models.Result;
import Models.Review;
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
        ArrayList<Product> products = App.getProducts();
        if (SortByTypes.Rate.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getRating).reversed());
        } else if (SortByTypes.HigherPriceToLower.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice).reversed());
        } else if (SortByTypes.LowerPriceToHigher.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getPrice));
        } else if (SortByTypes.NumberSold.matches(sortBy)) {
            products.sort(Comparator.comparing(Product::getNumberSold).reversed());
        }
        return products;
    }

    public Result showNext10Products() {
        showProducts(currentSortBy, startPoint);
        return new Result(true, "");
    }

    public Result showPrevious10Products() {
        showProducts(currentSortBy, startPoint - 19);
        return new Result(true, "");
    }
    public Result showProducts(String sortBy, int start) { //show next in menu
        ArrayList<Product> products = sortBy(sortBy);
        System.out.println("Product List (Sorted by: " + sortBy.trim() + ")");
        Product lastProductShown = null;
        for (int i = start; i < start + 10; i++) {
            System.out.println("------------------------------------------------");
            System.out.print("ID : " + products.get(i).getID());
            if(products.get(i).getDiscount() != null) {
                System.out.print("  **(On Sale! " + products.get(i).getDiscount().getQuantity() + " units discounted)** ");
            } else if(products.get(i).getInStock() == 0) {
                System.out.print("  **(Sold out!)**");
            }
            System.out.println();
            System.out.println("Name : " + products.get(i).getName());
            System.out.printf("Rate : %.1f/5\n", products.get(i).getRating());
            if(products.get(i).getDiscount() == null) {
                System.out.println("Price : $" + products.get(i).getPrice());
            } else {
                System.out.println("Price : ~$" + products.get(i).getPrice() + "~ → $" +
                        products.get(i).getPrice()*(1-products.get(i).getDiscount().getDiscountPercentage()/100) +
                        "(-" + products.get(i).getDiscount().getDiscountPercentage() + "%)");
            }
            System.out.println("Brand : " + products.get(i).getBrand());
            System.out.println("Stock : " + products.get(i).getInStock());
            System.out.println("------------------------------------------------");
            if(i == 9) lastProductShown = products.get(i);
        }
        if (products.size() > 10) {
            System.out.println("(Showing " + (start + 1) + "-" + (start + 10) + "out of "
                    + products.size() + ")" +
                    "Use `show next 10 products' to see more.");
        }
        startPoint = start + Math.min(10, products.indexOf(lastProductShown) + 1);
        currentSortBy = sortBy.trim();
        return new Result(true, "");
    }

    public Result showProductInfo(String productId) {
        int ID = Integer.parseInt(productId);
        if (getProduct(ID) == null) {
            return new Result(false, "No product found.");
        }
        System.out.println("Product Details");
        System.out.println("------------------------------------------------");
        System.out.print("Name : " + getProduct(ID).getName());
        if(getProduct(ID).getDiscount() != null) System.out.println("  **(On Sale! " +
                getProduct(ID).getDiscount().getQuantity() + " units discounted)**");
        else if (getProduct(ID).getInStock() == 0) System.out.print(" **(Sold out!)**");
        System.out.println();
        if(getProduct(ID).getDiscount() == null) {
            System.out.println("Price : $" + getProduct(ID).getPrice());
        } else {
            System.out.println("Price : ~$" + getProduct(ID).getPrice() + "~ → $" +
                    getProduct(ID).getPrice()*(1-getProduct(ID).getDiscount().getDiscountPercentage()/100) +
                    "(-" + getProduct(ID).getDiscount().getDiscountPercentage() + "%)");
        }
        System.out.println("Brand : " + getProduct(ID).getBrand());
        System.out.println("Number of Products Remaining : " + getProduct(ID).getInStock());
        System.out.println("About this item : ");
        System.out.println(getProduct(ID).getDescription());
        System.out.println();
        if (!getProduct(ID).getReviews().isEmpty()) {
            System.out.println("Customer Reviews : ");
            System.out.println("------------------------------------------------");
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
        int ID = Integer.parseInt(id);
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
        int ID = Integer.parseInt(productId);
        int quantity = Integer.parseInt(number);
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
        return new Result(true, "");
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
        return new Result(false, "invalid command.");
    }

}
