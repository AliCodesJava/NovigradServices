package com.example.servicesnovigrad;

import androidx.core.util.Pair;

import java.security.InvalidParameterException;
import java.time.DayOfWeek;
import java.util.ArrayList;
import java.util.EnumMap;

public class WeeklySchedule {
    private EnumMap<DayOfWeek, ArrayList<Pair<Short, Short>>> openHours;

    public WeeklySchedule() {
        openHours = new EnumMap<DayOfWeek, ArrayList<Pair<Short, Short>>>(DayOfWeek.class);
    }

    public ArrayList<Pair<Short, Short>> getOpenHours(DayOfWeek day) {
        if (!openHours.containsKey(day))
            openHours.put(day, new ArrayList<Pair<Short, Short>>());
        return openHours.get(day);
    }
    public EnumMap<DayOfWeek, ArrayList<Pair<Short, Short>>> getOpenHours() {
        return openHours;
    }

    /*
        Adds a pair of openingTime, closingTime for a specific day of the week;
        Time is in minutes from 0:00
    */
    public void addOpenHours(DayOfWeek day, short openingTime, short closingTime) {
        if (openingTime > closingTime)
            throw new InvalidParameterException("ERROR : Opening time is larger than closing time");
        if (openingTime >= 60 * 24) openingTime = 60 * 24 - 1;
        if (closingTime >= 60 * 24) closingTime = 60 * 24 - 1;

        if (!openHours.containsKey(day))
            openHours.put(day, new ArrayList<Pair<Short, Short>>());
        openHours.get(day).add(new Pair<Short, Short>(openingTime, closingTime));
    }

    /*
        Removes the pair of openingTime, closingTime of index
        in the array contained for a particular day
    */
    public Pair<Short, Short> removeOpenHours(DayOfWeek day, int index){
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
            for (Pair<Short, Short> p:
            openHours.get(day)) {
                s += " from " + p.first/60 + ":" + p.first%60;
                s += " to " + p.second/60 + ":" + p.second%60 + "/n";
            }
        }
        return s;
    }
}
