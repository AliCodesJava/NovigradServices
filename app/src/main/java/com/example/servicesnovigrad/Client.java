package com.example.servicesnovigrad;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Client extends Person{
    public Client(){} // For Firebase purposes
    public Client(String username, String password, String emailAddress,
                  String firstName, String lastName) {
        super(username, password, emailAddress, firstName, lastName);
    }

    public ArrayList<Branch> getBranchListBy(
            int time, //Time in minutes from midnight
            DayOfWeek day
    ){
        ArrayList<Branch> branchList = new ArrayList<>();
        for (Branch b:LoginForm.branchList) {
            for (Pair<Integer, Integer> openHours:
                 b.getSchedule().getOpenHours(day)) {
                if(openHours.getFirst()<=time && openHours.getSecond() >= time)
                    branchList.add(b);
            }
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
            Address address
    ){
        ArrayList<Branch> branchList = new ArrayList<>();
        for (Branch b:LoginForm.branchList) {
            if (b.getAddress().equals(address)) {
                branchList.add(b);
            }
        }
        return branchList;
    }
}
