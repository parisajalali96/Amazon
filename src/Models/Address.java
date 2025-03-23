package Models;

public class Address {
    String country;
    String city;
    String street;
    int postalCode;
    int addressId;

    public Address(String country, String city, String street, int postalCode, int addressId) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.addressId = addressId;
    }

    public String getCountry() {
        return country;
    }
    public String getCity() {
        return city;
    }
    public String getStreet() {
        return street;
    }
    public int getPostalCode() {
        return postalCode;
    }
    public int getAddressId() {
        return addressId;
    }


}
