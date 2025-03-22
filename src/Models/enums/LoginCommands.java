package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands implements Command {
    Name("[A-Z][a-zA-Z]{2,}"),
    Password("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,}"),
    Email("[A-Za-z0-9]+(?:\\.[A-Za-z0-9]+)?@[a-z]+.com"),
    CreateUser("create a user account\\s+-fn\\s+(?<firstName>\\S+)\\s+" +
            "-ln\\s+(?<lastName>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)\\s+" +
            "-rp\\s+(?<repeatPassword>\\S+)\\s+" +
            "-email\\s+(?<email>\\S+)"),
    CreateStoreAccount("create a store account\\s+-b\\s+(?<brand>.+{3,})\\s+" +
            "-p\\s+(?<password>\\S+)\\s+" +
            "-rp\\s+(?<repeatedPassword>\\S+)\\s+" +
            "-email\\s+(?<email>\\S+)"),
    LoginAsUser("login as user\\s+-e\\s+(?<email>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)"),
    LoginAsStore("login as store\\s+-e\\s+(?<email>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)"),
    Logout("logout"),
    DeleteAccount("delete account\\s+-p\\s+(?<password>\\S+)\\s+" +
            "-rp\\s+(?<repeatedPassword>\\S+)"),
    GoBack("go back");




    private final String pattern;
    LoginCommands(String pattern) {
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
