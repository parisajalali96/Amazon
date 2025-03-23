package Models;

public class CreditCard {
    String cardNumber;
    String expiryDate;
    String cvv;
    double value;
    int cardID;

    public CreditCard(String cardNumber, String expiryDate, String cvv, double value,  int cardID) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.value = value;
        this.cardID = cardID;
    }
    public String getCardNumber() {
        return cardNumber;
    }
    public String getExpiryDate() {
        return expiryDate;
    }
    public String getCvv() {
        return cvv;
    }
    public double getValue() {
        return value;
    }
    public void increaseValue(double amount) {
        value += amount;
    }
    public int getCardID() {
        return cardID;
    }
}
