package com.example.servicesnovigrad;

import java.io.Serializable;

public class Address implements Serializable {
    private String streetNumber; // can contain letters, so kept it as a String
    private String streetName;
    private int apartmentNumber;
    private String city;
    private String postalCode;
    public Address(){}
    public Address(String streetNumber, int apartmentNumber, String streetName,
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
    public int getApartmentNumber() {
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
    public void setApartmentNumber(int apartmentNumber) { this.apartmentNumber = apartmentNumber; }
    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /*
        Checks if the elements of the input, split on spaces,
        (turned bare-bone) is contained in the address
     */

    public boolean contains(String s){
        String[] elems = s.split(" ");
        for (String elem:
                elems) {
            if(!this.toString().toLowerCase().contains(elem.toLowerCase())){
                return false;
            }
        }
        return true;
    }

    @Override
    public boolean equals(Object o){
        Address a;
        if(o == null)
            return false;
        if(o.getClass().equals(this.getClass()))
            a = (Address)o;
        else
            return false;
        if(
                this.apartmentNumber==(a.getApartmentNumber()) &&
                        this.city.equals(a.getCity()) &&
                        this.postalCode.equals(a.getPostalCode()) &&
                        this.streetName.equals(a.getStreetName()) &&
                        this.streetNumber.equals(a.streetNumber)
        )
            return true;
        return false;
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