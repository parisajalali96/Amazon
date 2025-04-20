package Controllers;

import Models.App;
import Models.Result;
import Models.enums.MainMenuCommands;
import Models.enums.Menu;

import java.util.regex.Matcher;

public class MainMenuController {
    public Result goToMenu(String menu) {
        Matcher matcher = null;
        if ((matcher = MainMenuCommands.LoginMenu.getMatcher(menu)) != null) {
            App.setCurrentMenu(Menu.LoginMenu);
        } else if ((matcher = MainMenuCommands.UserMenu.getMatcher(menu)) != null) {
            if(App.getLoggedinUser() == null)
                return new Result(false, "You need to login as a user before accessing the user menu.");
            App.setCurrentMenu(Menu.UserMenu);
        } else if ((matcher = MainMenuCommands.ProductMenu.getMatcher(menu)) != null) {
            App.setCurrentMenu(Menu.ProductMenu);
        } else if ((matcher = MainMenuCommands.StoreMenu.getMatcher(menu)) != null) {
            if(App.getLoggedinStore() == null) return new Result(false, "You should login as store before accessing the store menu.");
            App.setCurrentMenu(Menu.StoreMenu);
        }
        return new Result(true, "Redirecting to the " + menu + " ...");
    }

    public Result invalidCommand() {
        return new Result(false, "invalid command");
    }
}
