package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreCommands implements Command {
    AddProduct("\\s*add product\\s+-n\\s+\"(?<name>\\S+(\\s*\\S+)*)\"\\s+" +
            "-pc\\s+(?<productCost>\\d+(\\.\\d*)?)\\s+" +
            "-p\\s+(?<price>\\d+(\\.\\d*)?)\\s+" +
            "-about\\s+\"(?<about>\\S+(\\s*\\S+)*)\"\\s+" +
            "-np\\s+(?<numberToSell>-?\\d+)\\s*"),
    ApplyDiscount("\\s*apply discount\\s+-p\\s+(?<productId>-?\\d+)\\s+" +
            "-d\\s+(?<discountPercentage>\\d+)\\s+" +
            "-q\\s+(?<quantity>\\d+)\\s*"),
    ShowProfit("\\s*show profit\\s*"),
    ShowListOfProducts("\\s*show list of products\\s*"),
    AddStock("\\s+add stock\\s+-product\\s+(?<productId>\\d+)\\s+" +
            "-amount\\s+(?<amount>-?\\d+)\\s*"),
    UpdatePrice("\\s*update price\\s+-product\\s+(?<productId>-?\\d+)\\s+" +
            "-price\\s+(?<newPrice>\\d+)\\s*"),
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
