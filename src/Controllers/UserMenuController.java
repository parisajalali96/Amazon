package Controllers;

import Models.*;
import Models.enums.LoginCommands;
import Models.enums.Menu;
import Models.enums.UserCommands;

import java.sql.SQLOutput;
import java.util.*;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

public class UserMenuController {

    public Result listMyOrders() {
        ArrayList<Order> myOrders = App.getLoggedinUser().getOrders();
        if (myOrders.isEmpty()) {
            return new Result(false, "No orders found.");
        }
        System.out.println("order History");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Order order : myOrders) {
            System.out.println("Order ID: " + order.getOrderID());
            System.out.println("Shipping Address: " + addressString(order.getShippingAddress()));
            System.out.println("Total Items Ordered: " + order.getProducts().size());
            System.out.println("Products (Sorted by Name) : ");
            int counter = 1;
            HashMap<Product, Integer> products = order.getProducts();
            List<Map.Entry<Product, Integer>> productList = new ArrayList<>(products.entrySet());
            productList.sort(Comparator.comparing(entry -> entry.getKey().getName()));
            for (Map.Entry<Product, Integer> entry : productList) {
                System.out.println("  " + counter + "-" + entry.getKey().getName());
                counter++;
            }
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result(true, "");
    }

    public String addressString(Address address) {
        return address.getStreet() + ", " + address.getCity()  + ", " + address.getCountry();
    }
    public Result showOrderDetails(String orderId) {
        int id;
        try {
            id = Integer.parseInt(orderId);
        } catch (NumberFormatException e) {
            return new Result(false, "Order not found.");
        }
        Order order = getOrder(id);
        if (order == null) return new Result(false, "Order not found.");
        HashMap<Product, Integer> products = order.getProducts();
        List<Map.Entry<Product, Integer>> productList = new ArrayList<>(products.entrySet());
        productList.sort(Comparator.comparing(entry -> entry.getKey().getID()));
        int counter = 1;
        double totalPrice = 0;
        System.out.println("Products in this order:");
        for (Map.Entry<Product, Integer> entry : productList) {
            System.out.println(counter++ + "-" + " Product Name: " + entry.getKey().getName());
            System.out.println("    ID: " + entry.getKey().getID());
            System.out.println("    Brand: " + entry.getKey().getBrand());
            System.out.printf("    Rating: %.1f/5\n", entry.getKey().getRating());
            System.out.println("    Quantity: " + entry.getValue());
            if(entry.getValue() == 1) {
                System.out.println("    Price: $" + entry.getKey().getPrice() + " each");
            } else System.out.println("    Price: $" + entry.getKey().getPrice());
            System.out.println();
            totalPrice += entry.getKey().getPrice()*entry.getValue();
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
        if(!isPassCorrect(oldPassword)) {
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
        if((matcher = UserCommands.Postal.getMatcher(postal.trim())) == null) {
            return new Result(false, "Invalid postal code. It should be a 10-digit number.");
        } else if (addressExists(postal)){
            return new Result(false, "This postal code is already associated with an existing address.");
        }
        App.getLoggedinUser().addAddress(country, city, street, postal);
        return new Result(true, "Address successfully added with id " + App.getLoggedinUser().getAddresses().size() + ".");
    }

    public Result deleteAddress(String addressId) {
        Matcher matcher = null;
        int ID;
        try {
            ID = Integer.parseInt(addressId);
        } catch (NumberFormatException e) {
            return new Result(false, "No address found.");
        }
        if(getAddress(ID) == null) {
            return new Result(false, "No address found.");
        }
        App.getLoggedinUser().removeAddress(getAddress(ID));
        return new Result(true, "Address with id " + ID + " deleted successfully.");
    }

    public Result listAddresses() {
        if(App.getLoggedinUser().getAddresses().isEmpty())
            return new Result(false, "No addresses found. Please add an address first.");
        System.out.println("Saved Addresses");
        System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        for (Address address : App.getLoggedinUser().getAddresses()) {
            System.out.println();
            System.out.println("Address " + address.getAddressId() + ":");
            System.out.println("Postal Code: " + address.getPostalCode());
            System.out.println("Country: " + address.getCountry());
            System.out.println("City: " + address.getCity());
            System.out.println("Street: " + address.getStreet());
            System.out.println();
            System.out.println("━━━━━━━━━━━━━━━━━━━━━━━━━━");
        }
        return new Result(true, "");
    }

    public Result addCreditCard(String cardNumber, String expDate, String cvv, String initialValue) {
        Matcher matcher = null;
        int value;
        try {
            value = Integer.parseInt(initialValue);
        } catch (NumberFormatException e) {
            return new Result(false, "The initial value cannot be negative.");
        }
        if(cardNumber.length() != 16) {
            return new Result(false, "The card number must be exactly 16 digits.");
        } else if ((matcher = UserCommands.ExpirationDate.getMatcher(expDate)) == null) {
            return new Result(false, "Invalid expiration date. Please enter a valid date in MM/YY format.");
        } else if ((matcher = UserCommands.CVV.getMatcher(cvv)) == null) {
            return new Result(false, "The CVV code must be 3 or 4 digits.");
        } else if (value < 0) {
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
        int ID;
        try {
            ID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new Result(false, "No credit card found.");
        }
        double value;
        try {
            value = Double.parseDouble(amount);
        } catch (NumberFormatException e) {
            return new Result(false, "The amount must be greater than zero.");
        }

        if(value <= 0) {
            return new Result(false, "The amount must be greater than zero.");
        } else if (getCreditCard(ID) == null) {
            return new Result(false, "No credit card found.");
        }
        CreditCard creditCard = getCreditCard(ID);
        creditCard.increaseBalance(value);
        return new Result(true, "$" + value + " has been added to the credit card " + ID +
                ". New balance: $" + creditCard.getBalance() + ".");
    }

    public Result creditCardBalance(String id) {
        int ID;
        try {
            ID = Integer.parseInt(id);
        } catch (NumberFormatException e) {
            return new Result(false, "No credit card found.");
        }
        if(getCreditCard(ID) == null) {
            return new Result(false, "No credit card found.");
        }
        return new Result(true, "Current balance : $" + getCreditCard(ID).getBalance());
    }

    public Result showCart() {
        ShoppingCart shoppingCart = App.getLoggedinUser().getShoppingCart();
        if(shoppingCart == null || shoppingCart.getProducts().isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        }
        System.out.println("Your Shopping Cart:");
        System.out.println("------------------------------------");
        HashMap<Product,Integer> products = shoppingCart.getProducts();
        List<Map.Entry<Product, Integer>> productList = new ArrayList<>(products.entrySet());
        productList.sort(Comparator.comparing(entry -> entry.getKey().getName()));
        for (Map.Entry<Product, Integer> entry : productList) {
            System.out.println("Product ID :" + entry.getKey().getID());
            System.out.println("Name :" + entry.getKey().getName());
            System.out.println("Quantity: " + products.get(entry.getKey()));
            if(entry.getValue() > 1) System.out.println("Price :$" + entry.getKey().getPrice() + " each");
            else System.out.println("Price : $" + entry.getKey().getPrice());
            System.out.println("Total Price: $" + entry.getKey().getPrice()*entry.getValue());
            System.out.println("Brand :" + entry.getKey().getBrand());
            System.out.printf("Rating: %.1f/5\n", entry.getKey().getRating());
            System.out.println("------------------------------------");
        }
        return new Result(true, "");
    }

    public Result checkout(String cardID, String addID) {
        Matcher matcher = null;
        int addressID;
        try {
            addressID = Integer.parseInt(addID);
        } catch (NumberFormatException e) {
            return new Result(false, "The provided address ID is invalid.");
        }
        int creditCardID;
        try {
            creditCardID = Integer.parseInt(cardID);
        } catch (NumberFormatException e) {
            return new Result(false, "The provided card ID is invalid.");
        }
        Address address;
        CreditCard creditCard;
        double totalPrice;
        if(App.getLoggedinUser().getShoppingCart() == null || App.getLoggedinUser().getShoppingCart().getProducts().isEmpty() ) {
            return new Result(false, "Your shopping cart is empty.");
        } else if (getAddress(addressID) == null) {
            return new Result(false, "The provided address ID is invalid.");
        } else if (getCreditCard(creditCardID) == null) {
            return new Result(false, "The provided card ID is invalid.");
        } else if (getCreditCard(creditCardID).getBalance() < ( totalPrice = getShoppingCartBalance())) {
            return new Result(false, "Insufficient balance in the selected card.");
        }
        creditCard = getCreditCard(creditCardID);
        address = getAddress(addressID);
        App.getLoggedinUser().addOrder(App.getLoggedinUser().getShoppingCart().getProducts(), address);
        for (Product product : App.getLoggedinUser().getShoppingCart().getProducts().keySet()) {
            sell(product.getID(), App.getLoggedinUser().getShoppingCart().getProducts().get(product), creditCard);
        }
        clearShoppingCart();
        String addressDetails = address.getStreet()+ ", " + address.getCity() + ", " + address.getCountry();
        return new Result(true, "Order has been placed successfully!\n" +
                "Order ID: +" + (App.getLoggedinUser().getOrders().size() + 101) +"\n" +
                "Total Paid: $" + totalPrice+"\n" +
                "Shipping to: " + addressDetails);
    }

    public Result removeFromShoppingCart(String productId, String quantity) {
        int quantityInt;
        try {
            quantityInt = Integer.parseInt(quantity);
        } catch (NumberFormatException e) {
            return new Result(false, "Quantity must be a positive number.");
        }
        int ID;
        try {
            ID = Integer.parseInt(productId);
        } catch (NumberFormatException e) {
            return new Result(false, "The product with ID " + productId + " is not in your cart.");
        }
        Product productToRemove;
        if(App.getLoggedinUser().getShoppingCart() == null ||App.getLoggedinUser().getShoppingCart().getProducts().isEmpty()) {
            return new Result(false, "Your shopping cart is empty.");
        } else if ((productToRemove = getProductFromCart(ID)) == null) {
            return new Result(false, "The product with ID " + ID + " is not in your cart.");
        } else if (quantityInt <= 0) {
            return new Result(false, "Quantity must be a positive number.");
        } else if(App.getLoggedinUser().getShoppingCart().getProducts().get(productToRemove) < quantityInt) {
            int availableQuantity = App.getLoggedinUser().getShoppingCart().getProducts().get(productToRemove);
            return new Result(false, "You only have " + availableQuantity +  " of \"" + productToRemove.getName() + "\" in your cart.");
        }
        App.getLoggedinUser().getShoppingCart().removeProduct(productToRemove, quantityInt);
        int newQuantity = App.getLoggedinUser().getShoppingCart().getProducts().get(productToRemove);
        if (newQuantity == 0) return new Result(true, "\"" + productToRemove.getName() + "\" has been completely removed from your cart.");
        else return new Result(true, "Successfully removed " + quantityInt + " of \"" + productToRemove.getName() + "\" from your cart.");
    }

    public Result goBack() {
      App.setCurrentMenu(Menu.MainMenu);
      return new Result(true, "Redirecting to the MainMenu ...");
    }

    public Product getProductFromCart(int productID) {
        ShoppingCart shoppingCart = App.getLoggedinUser().getShoppingCart();
        for (Product product : shoppingCart.getProducts().keySet()) {
            if(product.getID() == productID) {
                return product;
            }
        }
        return null;
    }

    public void sell(int id, int quantity, CreditCard creditCard) {
        double totalExpense = 0;
        for (Product product : App.getLoggedinUser().getShoppingCart().getProducts().keySet()) {
            double productExpense = 0;
            if(product.getID() == id) {
                if(product.getDiscount() != null) {
                    Discount discount = product.getDiscount();
                    if(quantity >= discount.getQuantity()) {
                        discount.addNumberApplied(discount.getQuantity());
                        productExpense += (product.getPrice()*(quantity - discount.getQuantity()) +
                                                        (product.getPrice()*discount.getQuantity()*(1-(double)discount.getDiscountPercentage()/100)));
                    } else if(quantity < discount.getQuantity()) {
                        discount.addNumberApplied(quantity);
                        productExpense += product.getPrice()*quantity*(1-(double)discount.getDiscountPercentage()/100);

                    }
                    totalExpense += productExpense;
                    product.addInStock(-quantity);
                    product.addNumberSold(quantity);

                } else {
                    productExpense += (product.getPrice()*quantity);
                    product.addInStock(-quantity);
                    product.addNumberSold(quantity);
                }
                product.addToRevenue(productExpense);
            }
        }
        creditCard.increaseBalance(-1*totalExpense);
    }
    public void clearShoppingCart() {
        ShoppingCart shoppingCart = App.getLoggedinUser().getShoppingCart();
        for (Product product : shoppingCart.getProducts().keySet()) {
            shoppingCart.removeProduct(product, shoppingCart.getProducts().get(product));
        }
    }
    public double getShoppingCartBalance() {
        double balance = 0;
        HashMap<Product,Integer> products = App.getLoggedinUser().getShoppingCart().getProducts();
        for (Product product : products.keySet()) {
            balance += product.getPrice()*products.get(product);
        }
        return balance;
    }
    public Address getAddress (int id) {
        for(Address address : App.getLoggedinUser().getAddresses()) {
            if(address.getAddressId() == id) {
                return address;
            }
        }
        return null;
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
    public boolean addressExists(String postal) {
        for (Address address : App.getLoggedinUser().getAddresses()) {
            if (address.getPostalCode().equals(postal)) {
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
        if(App.getLoggedinUser().getPassword().equals(password)) {
            return true;
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

    public Result invalidCommand() {
        return new Result(false, "invalid command");
    }




}
