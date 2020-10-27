package com.example.servicesnovigrad;

public class Employee extends Person{
    private String employeeID;

    public Employee(){} // For Firebase purposes
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
