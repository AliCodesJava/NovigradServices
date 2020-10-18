import androidx.core.util.Pair;

import java.time.DayOfWeek;
import java.util.ArrayList;

public class Branch {
    private Address address;
    private WeeklySchedule schedule;
    private ArrayList<Employee> employeeList;

    public Branch(Address address, WeeklySchedule schedule) {
        this.address = address;
        this.schedule = schedule;
    }

    public Address getAddress() {
        return address;
    }

    public WeeklySchedule getSchedule() {
        return schedule;
    }

    public ArrayList<Employee> getEmployeeList() {
        return employeeList;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public boolean isOpen(DayOfWeek day, short time/*from 0:00*/){
        for (Pair<Short, Short> p:
             schedule.getOpenHours(day)) {
            if(time>=p.first && time<=p.second)
                return true;
        }
        return false;
    }
}
