package Models;

public class Review {
    User customer;
    String comment;
    int rating;

    public Review(User customer, String comment, int rating) {
        this.customer = customer;
        this.comment = comment;
        this.rating = rating;
    }

    public User getCustomer() {
        return customer;
    }
    public String getComment() {
        return comment;
    }
    public int getRating() {
        return rating;
    }
}
