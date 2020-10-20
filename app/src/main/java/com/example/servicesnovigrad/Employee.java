package com.example.servicesnovigrad;

public class Employee extends Person{
    String employeeID;

    public Employee(String username, String password, String emailAddress,
                    String firstName, String lastName, Branch mainBranch) {
        super(username, password, emailAddress, firstName, lastName, mainBranch);
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
}
