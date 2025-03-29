package Views;

import Controllers.UserMenuController;
import Models.enums.LoginCommands;
import Models.enums.UserCommands;

import java.util.regex.Matcher;

public class UserMenu implements AppMenu {
    UserMenuController controller = new UserMenuController();

    @Override
    public void check(String input) {
        Matcher matcher = null;

        if((matcher = UserCommands.ListOrders.getMatcher(input)) != null) {
            controller.listMyOrders();
        } else if((matcher = UserCommands.ShowOrderDetails.getMatcher(input)) != null){
            controller.showOrderDetails(matcher.group("orderId"));
        } else if((matcher = UserCommands.EditName.getMatcher(input)) != null){
            controller.editName(matcher.group("firstName"), matcher.group("lastName"), matcher.group("password"));
        } else if((matcher = UserCommands.EditEmail.getMatcher(input)) != null){
            controller.editEmail(matcher.group("email"), matcher.group("password"));
        } else if((matcher = UserCommands.ShowMyInfo.getMatcher(input)) != null){
            controller.showMyInfo();
        } else if((matcher = UserCommands.AddAddress.getMatcher(input)) != null){
            controller.addAddress(matcher.group("country"), matcher.group("city"), matcher.group("street"), matcher.group("postal"));
        } else if((matcher = UserCommands.EditPassword.getMatcher(input)) != null){
            controller.editPassword(matcher.group("newPassword"), matcher.group("oldPassword"));
        } else if((matcher = UserCommands.DeleteAddress.getMatcher(input)) != null){
            controller.deleteAddress(matcher.group("addressId"));
        } else if((matcher = UserCommands.ListMyAddresses.getMatcher(input)) != null){
            controller.listAddresses();
        } else if((matcher = UserCommands.AddCreditCard.getMatcher(input)) != null){
            controller.addCreditCard(matcher.group("cardNumber"), matcher.group("expirationDate"),
                    matcher.group("cvv"), matcher.group("initialValue"));
        } else if((matcher = UserCommands.ChargeCreditCard.getMatcher(input)) != null){
            controller.chargeCreditCard(matcher.group("amount"), matcher.group("cardId"));
        } else if((matcher = UserCommands.CheckCreditCardBalance.getMatcher(input)) != null){
            controller.creditCardBalance(matcher.group("cardId"));
        } else if((matcher = UserCommands.ShowProductsinCart.getMatcher(input)) != null) {
            controller.showCart();
        } else if((matcher = UserCommands.CheckOut.getMatcher(input)) != null) {
            controller.checkout(matcher.group("cardID"), matcher.group("addressId"));
        } else if((matcher = UserCommands.RemoveFromCart.getMatcher(input)) != null) {
            controller.removeFromShoppingCart(matcher.group("productId"), matcher.group("amount"));
        } else if((matcher = UserCommands.GoBack.getMatcher(input)) != null) {
            controller.goBack();
        }
    }
}
