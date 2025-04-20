package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserCommands implements Command {
    ListOrders("\\s*list my orders\\s*"),
    ShowOrderDetails("\\s*show order details\\s+-id\\s+(?<orderId>-?\\d+)\\s*"),
    EditName("\\s*edit name\\s+-fn\\s+(?<firstName>\\S+(\\s*\\S+)*)\\s+" +
            "-ln\\s+(?<lastName>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s*"),
    EditEmail("\\s*edit email\\s+-e\\s+(?<email>\\S+(\\s*\\S+)*)\\s+" +
            "-p\\s+(?<password>\\S+(\\s*\\S+)*)\\s*"),
    EditPassword("\\s*edit password\\s+-np\\s+(?<newPassword>\\S+(\\s*\\S+)*)\\s+" +
            "-op\\s+(?<oldPassword>\\S+(\\s*\\S+)*)\\s*"),
    ShowMyInfo("\\s*show my info\\s*"),
    AddAddress("\\s*add address\\s+-country\\s+(?<country>.*?)\\s+" +
            "-city\\s+(?<city>.*?)\\s+" +
            "-street\\s+(?<street>.*?)\\s+" +
            "-postal\\s+(?<postal>.*?)\\s*"),
    Postal("\\d{10}"),
    DeleteAddress("\\s*delete address\\s+-id\\s+(?<addressId>-?\\d+)\\s*"),
    ListMyAddresses("\\s*list my addresses\\s*"),
    AddCreditCard("\\s*add a credit card\\s+-number\\s(?<cardNumber>.*?)\\s+" +
            "-ed\\s+(?<expirationDate>\\S+(\\s*\\S+)*)\\s+" +
            "-cvv\\s+(?<cvv>\\S+(\\s*\\S+)*)\\s+" +
            "-initialValue\\s+(?<initialValue>-?\\d+(\\.\\d*)*)\\s*"),
    CardNumber("\\d{16}"),
    ExpirationDate("(0[1-9]|1[0-2])\\/\\d{2}"),
    CVV("\\d{3,4}"),
    Value("\\d+(\\.\\d+)?"),
    ChargeCreditCard("\\s*Charge credit card\\s+-a\\s+(?<amount>-?\\d+(\\.\\d*)*)\\s+" +
            "-id\\s+(?<cardId>-?\\d+)"),
    CheckCreditCardBalance("\\s*Check credit card balance\\s+-id\\s+(?<cardId>-?\\d+)\\s*"),
    ShowProductsinCart("\\s*show products in cart\\s*"),
    CheckOut("\\s*checkout\\s+-card\\s+(?<cardID>-?\\d+)\\s+" +
            "-address\\s+(?<addressId>-?\\d+)\\s*"),
    RemoveFromCart("\\s*remove from cart\\s+-product\\s+(?<productId>-?\\d+)\\s+" +
            "-quantity\\s+(?<amount>-?\\d+)\\s*"),
    GoBack("\\s*go back\\s*");

    private final String pattern;

    UserCommands(String pattern) {
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
