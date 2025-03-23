package Models;

public class Product {
    String name;
    String brand;
    double rating;
    Price price;
    int ID;

    public String getName () {
        return name;
    }
    public int getID () {
        return ID;
    }
    public String getBrand () {
        return brand;
    }
    public double getRating () {
        return rating;
    }
    public Price getPrice () {
        return price;
    }

}
