package Controllers;

import Models.*;
import Models.enums.LoginCommands;
import Models.enums.UserCommands;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class UserMenuController {

    public Result listMyOrders() {
        ArrayList<Order> myOrders = new ArrayList<>();
        for (Order order : App.getLoggedinUser().getOrders()) {
            if (order.getCustomer().getEmail().equals(App.getLoggedinUser().getEmail())) {
                myOrders.add(order);
            }
        }
        if (myOrders.isEmpty()) {
            return new Result(false, "No orders found");
        }
        System.out.println("order History");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Order order : myOrders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Shipping Address: " + order.getShippingAddress());
            System.out.println("Total Items Ordered: " + myOrders.size());
            System.out.println("Products (Sorted by Name) : ");
            int counter = 1;
            ArrayList<Product> products = order.getProducts();
            products.sort(Comparator.comparing(Product::getName));
            for (Product product : products) {
                System.out.println(counter + "-" + product.getName());
                counter++;
            }
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result(true, "");
    }

    public Result showOrderDetails(String orderId) {
        int id = Integer.parseInt(orderId);
        System.out.println("Products in this order:");
        Order order = getOrder(id);
        if (order == null) return new Result(false, "Order not found");
        ArrayList<Product> products = order.getProducts();
        products.sort(Comparator.comparing(Product::getID));
        int counter = 1;
        double totalPrice = 0;
        for (Product product : products) {
            System.out.println(counter + "-" + " Product Name: " + product.getName());
            System.out.println("ID: " + product.getID());
            System.out.println("Brand: " + product.getBrand());
            System.out.printf("Rating: %.1f/5\n", product.getRating());
            System.out.println("Price: $" + product.getPrice().getNum());
            System.out.println();
            totalPrice += product.getPrice().getNum();
        }
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        System.out.println("**Total Cost: $" + totalPrice + "**");
        return new Result(true, "");
    }

    public Result editName(String newFirstName, String newLastName, String password) {
        Matcher matcher = null;
        if (!isPassCorrect(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        }
        if (newFirstName.equals(App.getLoggedinUser().getFirstName()) ||
                newLastName.equals(App.getLoggedinUser().getLastName())) {
            return new Result(false, "The new name must be different from the current name.");
        }
        if (newFirstName.length() < 3 || newLastName.length() < 3) {
            return new Result(false, "Name is too short.");
        }
        if ((matcher = LoginCommands.Name.getMatcher(newFirstName)) == null ||
                (matcher = LoginCommands.Name.getMatcher(newLastName)) == null) {
            return new Result(false, "Incorrect name format.");
        }

        App.getLoggedinUser().setFirstName(newFirstName);
        App.getLoggedinUser().setLastName(newLastName);
        return new Result(true, "Name updated successfully.");
    }

    public Result editEmail(String newEmail, String password) {
        Matcher matcher = null;
        if (!isPassCorrect(password)) {
            return new Result(false, "Incorrect password. Please try again.");
        } else if (newEmail.equals(App.getLoggedinUser().getEmail())) {
            return new Result(false, "The new email must be different from the current email.");
        } else if ((matcher = LoginCommands.Email.getMatcher(newEmail)) == null) {
            return new Result(false, "Incorrect email format.");
        } else if (emailExists(newEmail)) {
            return new Result(false, "Email already exists.");
        }
        App.getLoggedinUser().setEmail(newEmail);
        return new Result(true, "Email updated successfully.");
    }

    public Result editPassword(String newPassword, String oldPassword) {
        Matcher matcher = null;
        if(isPassCorrect(oldPassword)) {
            return new Result(false, "Incorrect password. Please try again.");
        } else if (newPassword.equals(oldPassword)) {
            return new Result(false, "The new password must be different from the old password.");
        } else if ((matcher = LoginCommands.Password.getMatcher(newPassword)) == null) {
            return new Result(false, "The new password is weak.");
        }
        App.getLoggedinUser().setPassword(newPassword);
        return new Result(true, "Password updated successfully.");
    }

    public void showMyInfo() {
        System.out.println("Name: " + App.getLoggedinUser().getFirstName() + " " + App.getLoggedinUser().getLastName());
        System.out.println("Email: " + App.getLoggedinUser().getEmail());
    }

    public Result addAddress(String country, String city, String street, String postal) {
        Matcher matcher = null;
        int postalCode = Integer.parseInt(postal);
        if((matcher = UserCommands.Postal.getMatcher(postal)) == null) {
            return new Result(false, "Invalid postal code. It should be a 10-digit number.");
        } else if (addressExists(postalCode)) {
            return new Result(false, "This postal code is already associated with an existing address.");
        }
        App.getLoggedinUser().addAddress(country, city, street, postalCode);
        return new Result(true, "Address successfully added with id " + App.getLoggedinUser().getAddresses().size());
    }

    public Result addCreditCard(String cardNumber, String expDate, String cvv, String initialValue) {
        Matcher matcher = null;
        int value = Integer.parseInt(initialValue);
        if(cardNumber.length() != 16) {
            return new Result(false, "The card number must be exactly 16 digits.");
        } else if ((matcher = UserCommands.ExpirationDate.getMatcher(expDate)) == null) {
            return new Result(false, "Invalid expiration date. Please enter a valid date in MM/YY format.");
        } else if ((matcher = UserCommands.CVV.getMatcher(cvv)) == null) {
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        } else if ((matcher = UserCommands.isValueNeg.getMatcher(cardNumber)) != null) {
            return new Result(false, "The initial value cannot be negative.");
        } else if (creditCardExists(cardNumber)) {
            return new Result(false, "This card is already saved in your account.");
        }
        App.getLoggedinUser().addCreditCard(cardNumber, expDate, cvv, value);
        return new Result(true, "Credit card with Id " +
                App.getLoggedinUser().getCreditCards().size() + " has been added successfully.");
    }

    public Result chargeCreditCard(String amount, String id) {
        Matcher matcher = null;
        double value = Double.parseDouble(amount);
        int ID = Integer.parseInt(id);
        if(value <= 0) {
            return new Result(false, "The amount must be greater than zero.");
        } else if (getCreditCard(ID) == null) {
            return new Result(false, "No credit card found.");
        }
        CreditCard creditCard = getCreditCard(ID);
        creditCard.increaseValue(value);
        return new Result(true, "$" + value + " has been added to the credit card " + ID +
                ". New balance: $" + creditCard.getValue() + ".");
    }

    public Result creditCardBalance(String id) {
        int ID = Integer.parseInt(id);
        if(getCreditCard(ID) == null) {
            return new Result(false, "No credit card found.");
        }
        return new Result(true, "Current balance : $" + getCreditCard(ID).getValue());
    }

    public Result showCart() {
        ShoppingCart shoppingCart = App.getLoggedinUser().getShoppingCart();
        if(shoppingCart.getProducts().isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        }
        System.out.println("Your Shopping Cart:");
        System.out.println("------------------------------------");
        for (Product product : shoppingCart.getProducts()) {
            System.out.println("Product ID :" + product.getID());
            System.out.println("Name :" + product.getName());
        }
        return new Result(true, "");
    }
    public CreditCard getCreditCard(int id) {
        for (CreditCard creditCard : App.getLoggedinUser().getCreditCards()) {
            if(creditCard.getCardID() == id) {
                return creditCard;
            }
        }
        return null;
    }
    public boolean creditCardExists(String cardNumber) {
        for( CreditCard creditCard : App.getLoggedinUser().getCreditCards()) {
            if(creditCard.getCardNumber().equals(cardNumber)) {
                return true;
            }
        }
        return false;
    }
    public boolean addressExists(int postal) {
        for (Address address : App.getLoggedinUser().getAddresses()) {
            if (address.getPostalCode() == postal) {
                return true;
            }
        }
        return false;
    }
    public Order getOrder(int id) {
        for (Order order : App.getLoggedinUser().getOrders()) {
            if (order.getCustomer().getEmail().equals(App.getLoggedinUser().getEmail()) &&
                    order.getOrderID() == id) {
                return order;
            }
        }
        return null;
    }

    public boolean isPassCorrect(String password) {
        for (User user : App.getUsers()) {
            if (user.getPassword().equals(App.getLoggedinUser().getPassword())) {
                return true;
            }
        }
        return false;
    }

    public boolean emailExists(String email) {
        for (User user : App.getUsers()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

}
