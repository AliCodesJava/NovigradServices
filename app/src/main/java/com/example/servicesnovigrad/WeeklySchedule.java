package com.example.servicesnovigrad;


import java.io.Serializable;
import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.HashMap;

public class WeeklySchedule implements Serializable {
    private HashMap<String, ArrayList<Pair<Integer, Integer>>> openHours;


    public WeeklySchedule() {
        openHours = new HashMap();
    }

    public ArrayList<Pair<Integer, Integer>> getOpenHours(DayOfWeek day) {
        if (!openHours.containsKey(day.toString()))
            openHours.put(day.toString(), new ArrayList<Pair<Integer, Integer>>());
        return openHours.get(day.toString());
    }
    public HashMap<String, ArrayList<Pair<Integer, Integer>>> getOpenHours() {
        return openHours;
    }
    public void setOpenHours(HashMap<String, ArrayList<Pair<Integer, Integer>>> openHours){
        this.openHours = openHours;
    }

    /*
        Adds a pair of openingTime, closingTime for a specific day of the week;
        Time is in minutes from 0:00
    */
    public void addOpenHours(DayOfWeek day, int openingTime, int closingTime) {
        if (openingTime > closingTime)
            throw new InvalidParameterException("ERROR : Opening time is larger than closing time");
        if (openingTime >= 60 * 24) openingTime = 60 * 24 - 1;
        if (closingTime >= 60 * 24) closingTime = 60 * 24 - 1;

        if (!openHours.containsKey(day.toString()))
            openHours.put(day.toString(), new ArrayList<Pair<Integer, Integer>>());
        openHours.get(day.toString()).add(new Pair<Integer, Integer>(openingTime, closingTime));
    }
    /*
        Removes the pair of openingTime, closingTime of index
        in the array contained for a particular day
    */
    public Pair<Integer, Integer> removeOpenHours(DayOfWeek day, int index){
        if(index<0)
            throw new NullPointerException("Cannot access this position: " + index);
        if (!openHours.containsKey(day.toString()) || openHours.get(day.toString()).size()<=index)
            throw new InvalidParameterException("This day does not contain such an open hour");
        else
            return openHours.get(day.toString()).remove(index);
    }

    @Override
    public String toString() {
        int i;
        String s = "";
        for (DayOfWeek day:
             DayOfWeek.values()) {
            s += day + "\n";
            if (openHours.containsKey(day.toString())){
                i=1;
            for (Pair<Integer, Integer> p:
            openHours.get(day.toString())) {
                s += i+". ";
                s += " from " + p.getFirst()/60 + ":" + p.getFirst()%60;
                s += " to " + p.getSecond()/60 + ":" + p.getSecond()%60 + "\n";
                i++;
            }}
        }
        return s;
    }
}