package Views;

import Models.App;
import Models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = App.getScanner();
        App.setCurrentMenu(Menu.MainMenu);
        while (true) {
            String input = scanner.nextLine();
            if(input.equals("exit")) break;
            App.getCurrentMenu().check(input);
        }
    }
}
