package Views;

import Controllers.LoginMenuController;
import Models.enums.LoginCommands;

import java.util.regex.Matcher;

public class LoginMenu implements AppMenu{
    LoginMenuController controller = new LoginMenuController();

    @Override
    public void check(String input) {
        Matcher matcher = null;

        if((matcher = LoginCommands.CreateUser.getMatcher(input)) != null) {
            controller.createUserAccount(matcher.group("firstName"), matcher.group("lastName"),
                    matcher.group("password"), matcher.group("repeatPassword"), matcher.group("email"));
        } else if((matcher = LoginCommands.CreateStoreAccount.getMatcher(input)) != null) {
            controller.createStoreAccount(matcher.group("brand"), matcher.group("password"),
                    matcher.group("repeatedPassword"), matcher.group("email"));
        } else if((matcher = LoginCommands.LoginAsUser.getMatcher(input)) != null) {
            controller.loginAsUser(matcher.group("email"), matcher.group("password"));
        } else if((matcher = LoginCommands.LoginAsStore.getMatcher(input)) != null) {
            controller.loginAsStore(matcher.group("email"), matcher.group("password"));
        } else if((matcher = LoginCommands.Logout.getMatcher(input)) != null) {
            controller.logout();
        } else if((matcher = LoginCommands.DeleteAccount.getMatcher(input)) != null) {
            controller.deleteAccount(matcher.group("password"), matcher.group("repeatedPassword"));
        } else if((matcher = LoginCommands.GoBack.getMatcher(input)) != null) {
            controller.goBack();
        } else controller.invalidCommand();
    }
}
