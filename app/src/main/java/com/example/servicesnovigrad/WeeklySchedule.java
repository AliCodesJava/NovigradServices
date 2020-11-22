package com.example.servicesnovigrad;

import androidx.core.util.Pair;

import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;

public class WeeklySchedule {
    private EnumMap<DayOfWeek, ArrayList<Pair<Integer, Integer>>> openHours;

    public WeeklySchedule() {
        openHours = new EnumMap<>(DayOfWeek.class);
    }

    public ArrayList<Pair<Integer, Integer>> getOpenHours(DayOfWeek day) {
        if (!openHours.containsKey(day))
            openHours.put(day, new ArrayList<Pair<Integer, Integer>>());
        return openHours.get(day);
    }
    public EnumMap<DayOfWeek, ArrayList<Pair<Integer, Integer>>> getOpenHours() {
        return openHours;
    }
    public void setOpenHours(EnumMap<DayOfWeek, ArrayList<Pair<Integer, Integer>>> openHours){
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

        if (!openHours.containsKey(day))
            openHours.put(day, new ArrayList<Pair<Integer, Integer>>());
        openHours.get(day).add(new Pair<Integer, Integer>(openingTime, closingTime));
    }
    /*
        Removes the pair of openingTime, closingTime of index
        in the array contained for a particular day
    */
    public Pair<Integer, Integer> removeOpenHours(DayOfWeek day, int index){
        if(index<0)
            throw new NullPointerException("Cannot access this position: " + index);
        if (!openHours.containsKey(day) || openHours.get(day).size()<=index)
            throw new InvalidParameterException("This day does not contain any scheduled openHours");
        else
            return openHours.get(day).remove(index);
    }

    @Override
    public String toString() {
        String s = "";
        for (DayOfWeek day:
             DayOfWeek.values()) {
            s += day + "/n";
            if (openHours.containsKey(day))
            for (Pair<Integer, Integer> p:
            openHours.get(day)) {
                s += " from " + p.first/60 + ":" + p.first%60;
                s += " to " + p.second/60 + ":" + p.second%60 + "/n";
            }
        }
        return s;
    }
}