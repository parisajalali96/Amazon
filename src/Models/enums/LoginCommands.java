package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum LoginCommands implements Command {
    Name("[A-Z][a-zA-Z]{2,}"),
    Password("(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{3,}"),
    Email("[A-Za-z0-9]+(?:\\.[A-Za-z0-9]+)?@[a-z]+.com"),
    CreateUser("\\s*create a user account\\s+-fn\\s+(?<firstName>\\S+(\\s*\\S+)*)\\s+" +
            "-ln\\s+(?<lastName>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s+" +
            "-rp\\s+(?<repeatPassword>\\S+(\\s*\\S+)*)\\s+" +
            "-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s*"),
    CreateStoreAccount("\\s*create a store account\\s+-b\\s+(?<brand>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s+" +
            "-rp\\s+(?<repeatedPassword>\\S+(\\s*\\S+)*)\\s+" +
            "-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s*"),
    LoginAsUser("\\s*login as user\\s+-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s*"),
    LoginAsStore("\\s*login as store\\s+-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s*"),
    Logout("\\s*logout\\s*"),
    DeleteAccount("\\s*delete account\\s+-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s+" +
            "-rp\\s+(?<repeatedPassword>\\S+(\\s*\\S+)*)\\s*"),
    GoBack("\\s*go back\\s*");




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
