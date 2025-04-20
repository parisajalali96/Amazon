package Models.enums;

public enum SortByTypes {
    Rate("\\s*rate\\s*"),
    HigherPriceToLower("\\s*higher price to lower\\s*"),
    LowerPriceToHigher("\\s*lower price to higher\\s*"),
    NumberSold("\\s*number of sold\\s*");

    private final String pattern;
    SortByTypes(String pattern) {
        this.pattern = pattern;
    }
    public boolean matches(String s) {
        return s.matches(pattern);
    }
}
