package com.example.servicesnovigrad;

import androidx.core.util.Pair;

import java.io.Serializable;
import java.time.DayOfWeek;
import java.util.ArrayList;

public class Branch implements Serializable {
    private Address address;
    private WeeklySchedule schedule;
    private ArrayList<ServiceApplication> applicationList;

    public Branch(){
    }
    public Branch(Address address, WeeklySchedule schedule) {
        this.address = address;
        this.schedule = schedule;
    }

    public ArrayList<ServiceApplication> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(ArrayList<ServiceApplication> applicationList) {
        this.applicationList = applicationList;
    }

    public Address getAddress() { return address; }
    public WeeklySchedule getSchedule() { return schedule; }

    public void setAddress(Address address) { this.address = address; }
    public void setSchedule(WeeklySchedule schedule) {
        this.schedule = schedule;
    }

    public boolean isOpen(DayOfWeek day, int time/*from 0:00*/){
        for (Pair<Integer, Integer> p:
                schedule.getOpenHours(day)) {
            if(time>=p.first && time<=p.second)
                return true;
        }
        return false;
    }
}
