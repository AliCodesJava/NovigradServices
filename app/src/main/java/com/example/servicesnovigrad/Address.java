package com.example.servicesnovigrad;

public class Address {
    private String streetNumber; // can contain letters, so kept it as a String
    private String streetName;
    private short apartmentNumber;
    private String city;
    private String postalCode;

    public Address(String streetNumber, short apartmentNumber, String streetName,
                   String city, String postalCode) {
        this.streetNumber = streetNumber;
        this.apartmentNumber = apartmentNumber;
        this.streetName = streetName;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getStreetNumber() {
        return streetNumber;
    }
    public short getApartmentNumber() {
        return apartmentNumber;
    }
    public String getStreetName() {
        return streetName;
    }
    public String getCity() {
        return city;
    }
    public String getPostalCode() {
        return postalCode;
    }

    public void setStreetNumber(String streetNumber) { this.streetNumber = streetNumber; }
    public void setApartmentNumber(short apartmentNumber) { this.apartmentNumber = apartmentNumber; }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() { //TODO prochaine livrable (2) check for official formatting
        return "com.example.servicesnovigrad.Address{" +
                "streetNumber='" + streetNumber + '\'' +
                ", apartmentNumber=" + apartmentNumber +
                ", streetName='" + streetName + '\'' +
                ", city='" + city + '\'' +
                ", postalCode='" + postalCode + '\'' +
                '}';
    }
}