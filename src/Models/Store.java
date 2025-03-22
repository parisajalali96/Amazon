package Models;

public class Store {
    String brand;
    String password;
    String email;
    int id = 0;

    public Store(String brand, String password, String email) {
        this.brand = brand;
        this.password = password;
        this.email = email;
        id++;
    }

    public String getBrand() {
        return brand;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public int getId() {
        return id;
    }
}
