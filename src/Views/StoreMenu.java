package Views;

import Controllers.StoreMenuController;
import Models.App;
import Models.enums.StoreCommands;

import java.util.regex.Matcher;

public class StoreMenu implements AppMenu{
    StoreMenuController controller = new StoreMenuController();

    @Override
    public void check (String input) {
        Matcher matcher = null;
        if ((matcher = StoreCommands.AddProduct.getMatcher(input)) != null) {
            controller.addProduct(matcher.group("name"), matcher.group("productCost"),
                    matcher.group("price"), matcher.group("about"),
                    matcher.group("numberToSell") );
        } else if ((matcher = StoreCommands.ApplyDiscount.getMatcher(input)) != null) {
            controller.applyDiscount(matcher.group("productId"),
                    matcher.group("discountPercentage"), matcher.group("quantity"));
        } else if ((matcher = StoreCommands.ShowProfit.getMatcher(input)) != null) {
            controller.showProfit();
        } else if ((matcher = StoreCommands.ShowListOfProducts.getMatcher(input)) != null) {
            controller.showListOfProducts();
        } else if ((matcher = StoreCommands.AddStock.getMatcher(input)) != null) {
            controller.addStock(matcher.group("productId"), matcher.group("amount"));
        } else if ((matcher = StoreCommands.UpdatePrice.getMatcher(input)) != null) {
            controller.updatePrice(matcher.group("productId"), matcher.group("newPrice"));
        } else if ((matcher = StoreCommands.GoBack.getMatcher(input)) != null) {
            controller.goBack();
        }
    }
}
