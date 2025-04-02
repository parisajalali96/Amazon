package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum StoreCommands implements Command {
    AddProduct("\\s*add product\\s+-n\\s+\"(?<name>.*?)\"\\s+" +
            "-pc\\s+(?<productCost>.*?)\\s+" +
            "-p\\s+(?<price>.*?)\\s+" +
            "-about\\s+\"(?<about>.*?)\"\\s+" +
            "-np\\s+(?<numberToSell>.*?)\\s*"),
    ApplyDiscount("\\s*apply discount\\s+-p\\s+(?<productId>.*?)\\s+" +
            "-d\\s+(?<discountPercentage>.*?)\\s+" +
            "-q\\s+(?<quantity>.*?)\\s*"),
    ShowProfit("\\s*show profit\\s*"),
    ShowListOfProducts("\\s*show list of products\\s*"),
    AddStock("\\s+add stock\\s+-product\\s+(?<productId>.*?)\\s+" +
            "-amount\\s+(?<amount>.*?)\\s*"),
    UpdatePrice("\\s*update price\\s+-product\\s+(?<productId>.*?)\\s+" +
            "-price\\s+(?<newPrice>.*?)\\s*"),
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
