package com.example.servicesnovigrad;

import androidx.core.util.Pair;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Employee extends Person implements Serializable {
    private String employeeID;
    private Branch mainBranch;

    public Employee(){} // For Firebase purposes
    public Employee(String username, String password, String emailAddress,
                    String firstName, String lastName, Branch mainBranch) {
        super(username, password, emailAddress, firstName, lastName);
        this.mainBranch = mainBranch;
    }

    public String getEmployeeID() {
        return employeeID;
    }
    public Branch getMainBranch() {
        if(mainBranch == null)
            mainBranch = new Branch();
        return mainBranch;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public void setMainBranch(Branch mainBranch) {
        this.mainBranch = mainBranch;
    }
    public void resetBranch(){
        mainBranch = new Branch();
    }
}
