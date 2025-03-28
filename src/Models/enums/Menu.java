package Models.enums;

import Models.Store;
import Models.User;
import Views.*;

public enum Menu {
    MainMenu(new MainMenu()),
    LoginMenu(new LoginMenu()),
    StoreMenu(new StoreMenu()),
    ProductMenu(new ProductMenu()),
    UserMenu(new UserMenu());

    private AppMenu menu;
    Menu(AppMenu menu) {
        this.menu = menu;
    }

    public void check (String input) {
        this.menu.check(input);
    }
}
