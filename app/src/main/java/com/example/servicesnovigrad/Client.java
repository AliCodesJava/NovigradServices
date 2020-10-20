package com.example.servicesnovigrad;

public class Client extends Person{
    public Client(){

    }
    public Client(String username, String password, String emailAddress,
                  String firstName, String lastName, Branch mainBranch) {
        super(username, password, emailAddress, firstName, lastName, mainBranch);
    }
}
