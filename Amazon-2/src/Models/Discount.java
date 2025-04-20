package Models;

public class Discount {
    int quantity;
    int discountPercentage;
    int numberApplied;

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
    public int getNumberApplied() {
        return numberApplied;
    }
    public void addNumberApplied(int numberApplied) {
        this.numberApplied += numberApplied;
    }
    public void setNumberApplied(int numberApplied) {
        this.numberApplied = numberApplied;
    }
}
