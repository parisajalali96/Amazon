package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreCommands implements Command {
    AddProduct("\\s*add \\s*product\\s+-n\\s+\"(?<name>[^\"]+)\"\\s+" +
            "-pc\\s+(?<productCost>-?\\d+(\\.\\d*)*)\\s+" +
            "-p\\s+(?<price>-?\\d+(\\.\\d*)*)\\s+" +
            "-about\\s+\"(?<about>[^\"]+)\"\\s+" +
            "-np\\s+(?<numberToSell>-?\\d+)\\s*"),
    ApplyDiscount("\\s*apply discount\\s+-p\\s+(?<productId>-?\\d+)\\s+" +
            "-d\\s+(?<discountPercentage>-?\\d+)\\s+" +
            "-q\\s+(?<quantity>-?\\d+)\\s*"),
    ShowProfit("\\s*show profit\\s*"),
    ShowListOfProducts("\\s*show list of products\\s*"),
    AddStock("\\s*add\\s*stock\\s+-product\\s+(?<productId>-?\\d+)\\s+" +
            "-amount\\s+(?<amount>-?\\d+)\\s*"),
    UpdatePrice("\\s*update price\\s+-product\\s+(?<productId>-?\\d+)\\s+" +
            "-price\\s+(?<newPrice>-?\\d+(\\.\\d*)*)\\s*"),
    GoBack("\\s*go back\\s*");

    private final String pattern;
    StoreCommands(String pattern) {
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
