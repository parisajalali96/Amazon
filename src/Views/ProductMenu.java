package Views;

import Controllers.ProductMenuController;
import Models.App;
import Models.enums.ProductCommands;

import java.util.regex.Matcher;

public class ProductMenu implements AppMenu {
    ProductMenuController controller = new ProductMenuController();
    Matcher matcher = null;
    @Override
    public void check (String input) {
        if((matcher = ProductCommands.ShowProducts.getMatcher(input)) != null) {
            controller.showProducts(matcher.group("sort"), 0);
        } else if((matcher = ProductCommands.ShowNext10Products.getMatcher(input)) != null) { // fix logic
            controller.showNext10Products();
        } else if((matcher = ProductCommands.ShowPast10Products.getMatcher(input)) != null) { // fix logic
            controller.showPrevious10Products();
        } else if((matcher = ProductCommands.ShowInformation.getMatcher(input)) != null) {
            controller.showProductInfo(matcher.group("id"));
        } else if((matcher = ProductCommands.RateProduct.getMatcher(input)) != null) {
            String message = matcher.group("message");
            if(matcher.group("message") == null) message = "";
            controller.rateProduct(matcher.group("rate"), message,
                    matcher.group("id"));
        } else if((matcher = ProductCommands.AddToCart.getMatcher(input)) != null) {
            controller.addToCart(matcher.group("productId"), matcher.group("quantity"));
        } else if((matcher = ProductCommands.GoBack.getMatcher(input)) != null) {
            controller.goBack();
        } else if((matcher = ProductCommands.ShowNext10Products.getMatcher(input)) != null) {
            controller.showNext10Products();
        } else if((matcher = ProductCommands.ShowPast10Products.getMatcher(input)) != null) {
            controller.showPrevious10Products();
        } else controller.invalidCommand();
    }
}
