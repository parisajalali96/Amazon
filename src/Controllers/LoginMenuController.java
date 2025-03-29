package Controllers;

import Models.*;
import Models.enums.LoginCommands;
import Models.enums.Menu;

import java.util.ArrayList;
import java.util.regex.Matcher;

public class LoginMenuController {

    public Result createUserAccount (String firstName, String lastName, String password, String rePassword, String email) {
        Matcher matcher = null;
        if(firstName.length() < 3 || lastName.length() < 3 ) {
            return new Result(false, "Name is too short.");
        } else if((matcher = LoginCommands.Name.getMatcher(firstName)) == null || LoginCommands.Name.getMatcher(lastName) == null) {
            return new Result(false, "Incorrect name format.");
        } else if((matcher = LoginCommands.Password.getMatcher(password)) == null) {
            return new Result(false, "Incorrect password format.");
        } else if(!rePassword.equals(password)) {
            return new Result(false, "Re-entered password is incorrect.");
        } else if ((matcher = LoginCommands.Email.getMatcher(email)) == null) {
            return new Result(false, "Incorrect email format.");
        } else if(emailExists(email)) {
            return new Result(false, "Email already exists.");
        }
        App.getUsers().add(new User(firstName, lastName, email, password));
        return new Result(true, "User account for " + firstName + " " + lastName +" created successfully.");
    } //correct

    public Result createStoreAccount (String brand, String password, String rePassword, String email) {
        Matcher matcher = null;
        if(brand.length() < 3) {
            return new Result(false, "Brand name is too short.");
        } else if((matcher = LoginCommands.Password.getMatcher(password)) == null) {
            return new Result(false, "Incorrect password format.");
        } else if(!rePassword.equals(password)) {
            return new Result(false, "Re-entered password is incorrect.");
        } else if((matcher = LoginCommands.Email.getMatcher(email)) == null) {
            return new Result(false, "Incorrect email format.");
        } else if(emailExists(email)) {
            return new Result(false, "Email already exists.");
        }
        App.getStores().add(new Store(brand, password, email));
        return new Result(true, "Store account for " + brand + " created successfully.");
    }//correct

    public Result loginAsUser (String email, String password) {
        if(getUser(email) == null) {
            return new Result(false, "No user account found with the provided email.");
        } else if(!userPasswordMatchesEmail(password, email)) {
            return new Result(false, "Password is incorrect.");
        }
        App.setLoggedinStore(null);
        App.setLoggedinUser(getUser(email));
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "User logged in successfully. Redirecting to the MainMenu ...");
    }

    public Result loginAsStore (String email, String password) {
        if(!storeExists(email)) {
            return new Result(false, "No store account found with the provided email.");
        } else if(!storePasswordMatchesEmail(password, email)) {
            return new Result(false, "Password is incorrect.");
        }
        App.setLoggedinUser(null);
        App.setLoggedinStore(getStore(email));
        App.setCurrentMenu(Menu.MainMenu);
        return new Result (true, "Store logged in successfully. Redirecting to the MainMenu ...");
    }

    public Result logout() {
        if(App.getLoggedinUser() != null) {
            App.setLoggedinUser(null);
        } else if(App.getLoggedinStore() != null) {
            App.setLoggedinStore(null);
        } else {
            return new Result(false, "You should login first.");
        }
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "Logged out successfully. Redirecting to the MainMenu ...");
    }

    public void emptyCart() {
        ShoppingCart shoppingCart = App.getLoggedinUser().getShoppingCart();
        if(shoppingCart == null) return;
        for (Product product : shoppingCart.getProducts().keySet()) {
            product.addInStock(shoppingCart.getProducts().get(product));
            shoppingCart.getProducts().remove(product);
        }
    }

    public void removeFromCarts() {
        if (App.getLoggedinStore().getProducts().isEmpty()) return;
        ArrayList<Product> products = App.getLoggedinStore().getProducts();
        App.getProducts().removeAll(products);
        for (User user : App.getUsers()) {
            user.getShoppingCart().getProducts().keySet().removeAll(products);
        }
    }
    public Result deleteAccount(String password, String rePassword) { //extra stuff
        if(App.getLoggedinUser() == null && App.getLoggedinStore() == null) {
            return new Result(false, "You should login first.");
        } else if (App.getLoggedinUser() != null) {
            if(!rePassword.equals(password)) {
                return new Result(false, "Re-entered password is incorrect.");
            } else if(!userPasswordMatchesEmail(password, App.getLoggedinUser().getEmail())) {
                return new Result(false, "Password is incorrect.");
            }
            emptyCart();
            App.getUsers().remove(App.getLoggedinUser());
            App.setLoggedinUser(null);
            App.setCurrentMenu(Menu.MainMenu);
            return new Result(true, "Account deleted successfully. Redirecting to the MainMenu ...");
        } else {
            if(!rePassword.equals(password)) {
                return new Result(false, "Re-entered password is incorrect.");
            } else if(!userPasswordMatchesEmail(password, App.getLoggedinStore().getEmail())) {
                return new Result(false, "Password is incorrect.");
            }
            removeFromCarts();
            App.getStores().remove(App.getLoggedinStore());
            App.setLoggedinUser(null);
            App.setCurrentMenu(Menu.MainMenu);
            return new Result(true, "Account deleted successfully. Redirecting to the MainMenu ...");
        }
    }

    public Result goBack() {
        App.setCurrentMenu(Menu.MainMenu);
        return new Result(true, "");
    }
    public Store getStore (String email) {
        for(Store store : App.getStores()) {
            if(store.getEmail().equals(email)) {
                return store;
            }
        }
        return null;
    }
    public boolean storeExists (String email) {
        for(Store store : App.getStores()) {
            if(store.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }
    public User getUser (String email) {
        for(User user : App.getUsers()) {
            if(user.getEmail().equals(email)) {
                return user;
            }
        }
        return null;
    }

    public boolean storePasswordMatchesEmail (String password, String email) {
        for(Store store : App.getStores()) {
            if(store.getEmail().equals(email) && store.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean userPasswordMatchesEmail (String password, String email) {
        for(User user : App.getUsers()) {
            if(user.getEmail().equals(email) && user.getPassword().equals(password)) {
                return true;
            }
        }
        return false;
    }
    public boolean emailExists(String email) {
        for(User user : App.getUsers()) {
            if(user.getEmail().equals(email)) {
                return true;
            }
        }

        for (Store store : App.getStores()) {
            if(store.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }



    public Result invalidCommand() {
        return new Result(false, "Invalid command.");
    }
}
