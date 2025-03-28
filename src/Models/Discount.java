package Models;

public class Discount {
    int quantity;
    int discountPercentage;

    public Discount(int quantity, int discountPercentage) {
        this.quantity = quantity;
        this.discountPercentage = discountPercentage;
    }

    public int getQuantity() {
        return quantity;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public int getDiscountPercentage() {
        return discountPercentage;
    }
    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
