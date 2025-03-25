package Models;

public class CreditCard {
    String cardNumber;
    String expiryDate;
    String cvv;
    double balance;
    int cardID;

    public CreditCard(String cardNumber, String expiryDate, String cvv, double balance,  int cardID) {
        this.cardNumber = cardNumber;
        this.expiryDate = expiryDate;
        this.cvv = cvv;
        this.balance = balance;
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
    public double getBalance() {
        return balance;
    }
    public void increaseBalance(double amount) {
        balance += amount;
    }
    public int getCardID() {
        return cardID;
    }
}
