package ru.kholodnyak.Thermostat;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Расписание интервалов температур на неделю.
 */
public class WeekSchedule implements Serializable {
    /**
     * Список расписаний на каждый день недели
     */
    ArrayList<DaySchedule> daySchedules;


    public WeekSchedule() {
        initSchedule();
    }

    /**
     * Adds new interval in day schedule
     *
     * @param weekday  weekday
     * @param interval interval
     * @return ID of added users
     * @throws Exception
     */
    public int addInterval(Weekday weekday, TimeInterval interval) throws Exception {
        for (DaySchedule daySchedule : daySchedules) {
            if (daySchedule.getWeekday() == weekday) {
                daySchedule.addInterval(interval);
                return interval.getId();
            }
        }

        throw new Exception("Weekday not found!");
    }

    /**
     * Removes interval by ID
     *
     * @param intervalID Unique interval ID
     */
    public void removeInterval(int intervalID) {
        for (DaySchedule daySchedule : daySchedules) {
            daySchedule.removeInterval(intervalID);
        }
    }

    /**
     * Creates and initialize week schedule
     */
    private void initSchedule() {
        daySchedules = new ArrayList<>();

        for (Weekday weekday : Weekday.values()) {
            daySchedules.add(new DaySchedule(weekday));
        }
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();

        for (DaySchedule daySchedule : this.daySchedules) {
            resultString.append(daySchedule.toString());
            resultString.append("\n");
        }

        return resultString.toString();
    }
}
