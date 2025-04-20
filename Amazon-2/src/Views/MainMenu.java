package Views;

import Controllers.MainMenuController;
import Models.enums.MainMenuCommands;

import java.util.regex.Matcher;

public class MainMenu implements AppMenu{
    MainMenuController controller = new MainMenuController();
    Matcher matcher = null;

    @Override
    public void check (String input) {
        if ((matcher = MainMenuCommands.GoToMenu.getMatcher(input)) != null) {
            controller.goToMenu(matcher.group("menu"));
        } else controller.invalidCommand();
    }
}
