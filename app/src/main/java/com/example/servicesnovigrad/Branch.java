package com.example.servicesnovigrad;


import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Branch implements Serializable {
    private Address address;
    private WeeklySchedule schedule;
    private ArrayList<Service> serviceList;
    private ArrayList<ServiceApplication> applicationList;

    public Branch(){
        serviceList = new ArrayList<Service>();
        LoginForm.branchList.add(this);
    }
    public Branch(Address address, WeeklySchedule schedule) {
        this.address = address;
        this.schedule = schedule;
        LoginForm.branchList.add(this);
    }

    public ArrayList<Service> getServiceList() {
        if(serviceList == null){
            serviceList = new ArrayList<Service>();
        }
        return serviceList;
    }


    public void setServiceList(ArrayList<Service> serviceList) {
        this.serviceList = serviceList;
    }

    public ArrayList<ServiceApplication> getApplicationList() {
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

    public void setAddress(Address address) { this.address = address; }
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
}
