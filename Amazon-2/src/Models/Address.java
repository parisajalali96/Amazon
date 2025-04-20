package Models;

public class Address {
    String country;
    String city;
    String street;
    String postalCode;
    int addressId;
    private static int numberOfAddresses;

    public Address(String country, String city, String street, String postalCode, int addressId) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.postalCode = postalCode;
        this.addressId = addressId;
        numberOfAddresses++;
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
    public String getPostalCode() {
        return postalCode;
    }
    public int getAddressId() {
        return addressId;
    }
    public int getNumberOfAddresses() {
        return numberOfAddresses;
    }


}
