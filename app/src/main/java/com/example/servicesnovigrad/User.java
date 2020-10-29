package com.example.servicesnovigrad;

import java.io.Serializable;

public class User implements Serializable {
    private String username;
    private String password;
    private String emailAddress;

    public User(){}
    public User(String username, String password, String emailAddress) {
        this.username = username;
        this.password = password;
        this.emailAddress = emailAddress;
    }

    public String getUsername() {
        return username;
    }
    public String getEmailAddress() {
        return emailAddress;
    }
    public String getPassword(){ return password; }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public boolean checkPassword(String password){
        return this.password.equals(password);
    }
}