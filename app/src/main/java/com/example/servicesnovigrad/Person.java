package com.example.servicesnovigrad;

import java.util.ArrayList;
import java.util.NoSuchElementException;

public class Person extends User{
    private String firstName;
    private String lastName;
    private ArrayList<Address> addressList;

    public Person(){}
    public Person(String username, String password, String emailAddress,
                  String firstName, String lastName) {
        super(username, password, emailAddress);
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressList = new ArrayList<Address>();
    }

    public String getFirstName() {
        return firstName;
    }
    public String getLastName() {
        return lastName;
    }
    public ArrayList<Address> getAddressList() {
        return addressList;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void addAddress(Address address){
        addressList.add(address);
    }
    public Address removeAddress(Address address){
        if (!addressList.contains(address))
            throw new NoSuchElementException("This address is not present in the person's addressList");
        addressList.remove(address);
        return address;
    }
}