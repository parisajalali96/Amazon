package Views;

import Controllers.StoreMenuController;

import java.util.regex.Matcher;

public class StoreMenu implements AppMenu{
    StoreMenuController controller = new StoreMenuController();

    @Override
    public void check (String input) {
        Matcher matcher = null;
    }
}
