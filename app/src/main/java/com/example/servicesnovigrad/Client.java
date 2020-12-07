package com.example.servicesnovigrad;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Client extends Person{
    private ArrayList<String> notifications;
    public Client(){} // For Firebase purposes
    public Client(String username, String password, String emailAddress,
                  String firstName, String lastName) {
        super(username, password, emailAddress, firstName, lastName);
    }

    public ArrayList<String> getNotifications() {
        if (notifications == null)
            notifications = new ArrayList<>();
        return notifications;
    }

    public void setNotifications(ArrayList<String> notifications) {
        this.notifications = notifications;
    }

    public ArrayList<Branch> getBranchListBy(
            int time, //Time in minutes from midnight
            DayOfWeek day
    ){
        ArrayList<Branch> branchList = new ArrayList<>();
        for (Branch b:LoginForm.branchList) {
            if(b.isOpen(day, time))
                branchList.add(b);
        }
        return branchList;
    }
    public ArrayList<Branch> getBranchListBy(
            Service service
    ){
        ArrayList<Branch> branchList = new ArrayList<>();
        for (Branch b:LoginForm.branchList) {
            if (b.getServiceList().contains(service)) {
                branchList.add(b);
            }
        }
        return branchList;
    }
    public ArrayList<Branch> getBranchListBy(
            String addressSearch
    ){
        ArrayList<Branch> branchList = new ArrayList<>();
        for (Branch b:LoginForm.branchList) {
            if (b.getAddress().contains(addressSearch)) {
                branchList.add(b);
            }
        }
        return branchList;
    }
}
