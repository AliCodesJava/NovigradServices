package com.example.servicesnovigrad;

import androidx.core.util.Pair;

import java.time.DayOfWeek;

public class Employee extends Person{
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
        return mainBranch;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }
    public void setMainBranch(Branch mainBranch) {
        this.mainBranch = mainBranch;
    }

    public void setMainBranchAddress(Address address){
        this.mainBranch.setAddress(address);
    }
    public void resetBranch(){
        mainBranch = new Branch();
    }


    public class Branch {
        private Address address;
        private WeeklySchedule schedule;

        public Branch(){
            this.address = null;
            this.schedule = null;
        }
        public Branch(Address address, WeeklySchedule schedule) {
            this.address = address;
            this.schedule = schedule;
        }

        public Address getAddress() { return address; }
        public WeeklySchedule getSchedule() { return schedule; }

        public void setAddress(Address address) { this.address = address; }
        public void setSchedule(Address address) { this.schedule = schedule; }

        public boolean isOpen(DayOfWeek day, short time/*from 0:00*/){
            for (Pair<Short, Short> p:
                    schedule.getOpenHours(day)) {
                if(time>=p.first && time<=p.second)
                    return true;
            }
            return false;
        }
    }
}
