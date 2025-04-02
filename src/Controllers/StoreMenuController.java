package Controllers;

import Models.App;
import Models.Discount;
import Models.Product;
import Models.Result;
import Models.enums.Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class StoreMenuController {
    public Result addProduct(String name, String cost, String price, String description, String num) {
        double productCost;
        try {
            productCost = Double.parseDouble(cost);
        } catch (NumberFormatException e) {
            return new Result(false, "Selling price must be greater than or equal to the producer cost.");
        }
        double productPrice;
        try {
            productPrice = Double.parseDouble(price);
        } catch (NumberFormatException e) {
            return new Result(false, "Selling price must be greater than or equal to the producer cost.");
        }
        int numberToSell;
        try {
            numberToSell = Integer.parseInt(num);
        } catch (NumberFormatException e) {
            return new Result(false, "Number of products must be a positive number.");
        }

        if(productCost > productPrice) {
            return new Result(false, "Selling price must be greater than or equal to the producer cost.");
        } else if(numberToSell <= 0) {
            return new Result(false, "Number of products must be a positive number.");
        }

        App.getLoggedinStore().addProduct(name, App.getLoggedinStore().getBrand(),
                productPrice, productCost, numberToSell, description);
        int lastId = App.getProducts().size() + 100;
        return new Result(true, "Product \"" + getProduct(lastId).getName() + "\" has been added successfully with ID " + lastId+ ".");
    }

    public Product getProduct(int id) {
        for (Product product : App.getLoggedinStore().getProducts()) {
            if(product.getID() == id) {
                return product;
            }
        }
        return null;
    }

    public Result applyDiscount(String p, String d, String q) {
        int productId = Integer.parseInt(p);
        int discount = Integer.parseInt(d);
        int quantity = Integer.parseInt(q);

        if(discount > 100 || discount < 0) {
            return new Result(false, "Discount percentage must be between 1 and 100.");
        } else if (getProduct(productId) == null) {
            return new Result(false, "No product found.");
        } else if (getProduct(productId).getInStock() < quantity) {
            return new Result(false, "Oops! Not enough stock to apply the discount to that many items.");
        }
        Product product = getProduct(productId);
        product.setDiscount(new Discount(quantity, discount));
        return new Result(true, "A " + discount + "% discount has been applied to " + quantity +
                " units of product ID " + product.getID() + ".");

    }

    public Result showProfit() {
        double revenue = 0;
        double profit = 0;
        double costs = 0;
        int discountNum = 0;
        for (Product product : App.getLoggedinStore().getProducts()) {
            costs += product.getCost();
            revenue += product.getRevenue();
        }
        profit = revenue - costs;
        return new Result(true, "Total Profit: $" + profit + "\n" +
                "(Revenue: $" +revenue + " - Costs: $" + costs + ")");
    }

    public Result showListOfProducts() {
        if(App.getLoggedinStore().getProducts().isEmpty())
            return new Result(false, "No products available in the store.");
        System.out.println("Store Products (Sorted by date added)");
        System.out.println("------------------------------------------------");
        for (Product product : App.getLoggedinStore().getProducts()) {
            System.out.print("ID: " + product.getID());
            if (product.getDiscount() != null) {
                System.out.print("  **(On Sale! " + product.getDiscount().getQuantity() + " units discounted)**");
            } else if (product.getInStock() == 0) {
                System.out.print("  (**Sold out!**) ");
            }
            System.out.println();
            System.out.println("Name: " + product.getName());
            if (product.getDiscount() != null) {
                System.out.println("Price: ~$" + product.getPrice() + "~ → $" +
                        (product.getDiscount().getDiscountPercentage()/100)*product.getPrice() + " (-" +
                        product.getDiscount().getDiscountPercentage() + "%)");
            } else {
                System.out.println("Price: $" + product.getPrice());
            }
            System.out.println("Stock: " + product.getInStock());
            System.out.println("Sold: " + product.getNumberSold());
            System.out.println("------------------------------------------------");
        }
        return new Result(true, "");
    }

    public Result addStock(String p, String a) {
        int productId = Integer.parseInt(p);
        int amount = Integer.parseInt(a);
        if (getProduct(productId) == null) {
            return new Result(false, "No product found.");
        } else if (amount <= 0) {
            return new Result(false, "Amount must be a positive number.");
        }
        Product product = getProduct(productId);
        product.addInStock(amount);
        return new Result(true, amount + " units of \"" +
                product.getName() + "\" have been added to the stock.");
    }

    public Result updatePrice(String np, String id) {
        double newPrice = Double.parseDouble(np);
        int productId = Integer.parseInt(id);
        if (getProduct(productId) == null) {
            return new Result(false, "No product found.");
        } else if (newPrice <= 0) {
            return new Result(false, "Price must be a positive number.");
        }
        Product product = getProduct(productId);
        product.setPrice(newPrice);
        System.out.printf("Price of \"%s\" has been updated to $%.1f.\n", product.getName(), newPrice);
        return new Result(true, "");
    }
    public Result goBack() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Redirecting to the MainMenu ...");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command");
    }
}
