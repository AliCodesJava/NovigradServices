package com.example.servicesnovigrad;

public class User {
    private String username;
    private String password;
    private String emailAddress;

    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }
    public boolean checkPassword(String password){
        return this.password == password;
    }
    public String getUsername() {
        return username;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
