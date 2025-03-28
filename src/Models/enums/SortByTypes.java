package Models.enums;

public enum SortByTypes {
    Rate("rate"),
    HigherPriceToLower("higher price to lower"),
    LowerPriceToHigher("lower price to higher"),
    NumberSold("number of sold");

    private final String pattern;
    SortByTypes(String pattern) {
        this.pattern = pattern;
    }
    public boolean matches(String s) {
        return s.matches(pattern);
    }
}
