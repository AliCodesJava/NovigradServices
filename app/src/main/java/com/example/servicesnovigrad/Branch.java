package com.example.servicesnovigrad;


import android.util.Log;

import com.google.android.material.snackbar.Snackbar;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

public class Branch implements Serializable {
    private int branchNumber;
    private Address address;
    private WeeklySchedule schedule;
    private ArrayList<Service> serviceList;
    private ArrayList<ServiceApplication> applicationList;
    private HashMap<String, Float> ratings;
    private String employeeUserName;

    public Branch(){
        serviceList = new ArrayList<Service>();
    }
    public Branch(int branchNumber,Address address, WeeklySchedule schedule) {
        this.address = address;
        this.branchNumber = branchNumber;
        this.setSchedule(schedule);
        if(LoginForm.branchList.contains(this)){
            LoginForm.branchList.remove(this); // removes it from list
        }
        LoginForm.branchList.add(this);
    }

    public String getEmployeeUserName() {
        return employeeUserName;
    }

    public void setEmployeeUserName(String employeeUserName) {
        this.employeeUserName = employeeUserName;
    }

    public ArrayList<Service> getServiceList() {
        if(serviceList == null){
            serviceList = new ArrayList<Service>();
        }
        return serviceList;
    }

    public HashMap<String, Float> getRatings() {
        if(ratings == null)
            ratings = new HashMap<>();
        return ratings;
    }

    public void setRatings(HashMap<String, Float> ratings) {
        this.ratings = ratings;
    }

    public int getBranchNumber() {
        return branchNumber;
    }

    public void setBranchNumber(int branchNumber) {
        this.branchNumber = branchNumber;
        if(LoginForm.branchList.contains(this)){
            Log.d("tag","oy !!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            LoginForm.branchList.remove(this); // removes it from list
        }
        LoginForm.branchList.add(this);
    }

    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<ServiceApplication> getApplicationList() {
        if(this.applicationList == null)
            this.applicationList = new ArrayList<>();
        return applicationList;
    }

    public void setApplicationList(ArrayList<ServiceApplication> applicationList) {
        this.applicationList = applicationList;
    }

    public Address getAddress() { return address; }
    public WeeklySchedule getSchedule() {
        if(schedule==null)
            schedule = new WeeklySchedule();
        return schedule; }

    public void setAddress(Address address) {
        this.address = address;
    }
    public void setSchedule(WeeklySchedule schedule) {
        this.schedule = schedule;
    }

    public boolean isOpen(DayOfWeek day, int time/*from 0:00*/){
        for (Pair<Integer, Integer> p:
                schedule.getOpenHours(day)) {
            if(time>=p.getFirst() && time<=p.getSecond())
                return true;
        }
        return false;
    }


    @Override
    public boolean equals(Object o){
        if(this == null || ((Branch)o) == null || !o.getClass().equals(this.getClass())){
            return false;
        }
        if(((Branch)o).getBranchNumber() == this.getBranchNumber())
            return true;
        return false;
    }
}
