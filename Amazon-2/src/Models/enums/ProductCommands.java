package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum ProductCommands implements Command {
    ID("\\s*-?\\d+\\s*"),
    ShowProducts("\\s*show products\\s+-sortBy\\s+(?<sort>(rate|higher price to lower|lower price to higher|number of sold))\\s*"),
    ShowInformation("\\s*show information of\\s+-id\\s+(?<id>\\S+(\\s*\\S+)*)\\s*"),
    RateProduct("\\s*Rate\\s*product\\s+-r\\s+(?<rate>-?\\d+(\\.\\d+)?)\\s*(?:-m\\s+(?<message>.+?))?\\s*-id\\s+(?<id>-?\\d+)\\s*"),
    AddToCart("\\s*add \\s*to \\s*cart\\s+-product\\s+(?<productId>\\S+(\\s*\\S+)*)\\s+" +
            "-quantity\\s+(?<quantity>-?\\d+)\\s*"),
    GoBack("\\s*go back\\s*"),
    ShowNext10Products("\\s*show next 10 products\\s*"),
    ShowPast10Products("\\s*show past 10 products\\s*");
    private final String pattern;

    ProductCommands(String pattern) {
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
