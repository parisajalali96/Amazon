package Models.enums;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum UserCommands implements Command {
    ListOrders("list my orders"),
    ShowOrderDetails("show order details\\s+-id\\s+(?<orderId>\\d+)"),
    EditName("edit name\\s+-fn\\s+(?<firstName>\\S+)\\s+" +
            "-ln\\s+(?<lastName>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)"),
    EditEmail("edit email\\s+-e\\s+(?<email>\\S+)\\s+" +
            "-p\\s+(?<password>\\S+)"),
    EditPassword("edit password\\s+-np\\s+(?<newPassword>\\S+)\\s+" +
            "-op\\s+(?<oldPassword>\\S+)"),
    ShowMyInfo("show my info"),
    AddAddress("add address\\s+-country\\s+(?<country>.+)\\s+" +
            "-city\\s+(?<city>.+)\\s+" +
            "-street\\s+(?<street>.+)\\s+" +
            "-postal\\s+(?<postal>\\S+)"),
    Postal("\\d{10}"),
    DeleteAddress("delete address\\s+-id\\s+(?<addressId>\\d+)"),
    ListMyAddresses("list my my addresses"),
    AddCreditCard("add a credit card\\s+-number\\s(?<cardNumber>\\d+)\\s+" +
            "-ed\\s+(?<expirationDate>\\S+)\\s+" +
            "-cvv\\s+(?<cvv>\\d+)\\s+" +
            "-initialvalue\\s+(?<initialValue>\\S+)"),
    CardNumber("\\d{16}"),
    ExpirationDate("(0[1-9]|1[0-2])\\\\/\\\\d{2}"),
    CVV("\\d{3,4}"),
    Value("\\d+(\\\\.\\\\d+)?"),
    isValueNeg("-?\\\\d+(\\\\.\\\\d+)?"),
    ChargeCreditCard("Charge credit card\\s+-a\\s+(?<amount>\\S+)\\s+" +
            "-id\\s+(?<cardId>\\d+)"),
    CheckCreditCardBalance("Check credit card balance\\s+-id\\s+(?<cardId>\\d+)"),
    ShowProductsinCart("show products in cart"),
    CheckOut("checkout\\s+-card\\s+(?<cardID>\\d+)\\s+" +
            "-address\\s+(?<addressId>\\d+)"),
    RemoveFromCart("remove from cart\\s+-product\\s+(?<productID>\\d+)\\s+" +
            "-quantity\\s+(?<amount>\\S+)"),
    GoBack("go back");

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
