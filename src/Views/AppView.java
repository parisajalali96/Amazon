package Views;

import Models.App;
import Models.enums.Menu;

import java.util.Scanner;

public class AppView {
    public void run() {
        Scanner scanner = App.getScanner();
        while (true) {
            String input = scanner.nextLine();
            App.setCurrentMenu(Menu.MainMenu);
            if(input.equals("exit")) break;
            App.getCurrentMenu().check(input);
        }
    }
}
