package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum MainMenuCommands implements Command {
    GoToMenu("\\s*go to\\s+-m\\s+(?<menu>\\S+(\\s*\\S+)*)\\s*"),
    LoginMenu("\\s*LoginMenu\\s*"),
    UserMenu("\\s*UserMenu\\s*"),
    StoreMenu("\\s*StoreMenu\\s*"),
    ProductMenu("\\s*ProductMenu\\s*");


    private final String pattern;
    MainMenuCommands(String pattern) {
        this.pattern = pattern;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = Pattern.compile(this.pattern).matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}
