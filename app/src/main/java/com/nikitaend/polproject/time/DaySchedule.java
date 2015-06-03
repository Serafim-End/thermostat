package com.nikitaend.polproject.time;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

/**
 * Расписание интервалов температур на один день недели.
 */
public class DaySchedule implements Serializable {

    public static int MAXIMAL_NUMBER_OF_INTERVALS = 5;
    /**
     * Weekday
     */
    private Weekday weekday;

    /**
     * Список с интервалами, на которых температура должна измениться
     */
    private ArrayList<TimeInterval> intervals;

    /**
     * Создает расписание на один день недели
     *
     * @param weekday current weekday.
     */
    public DaySchedule(Weekday weekday) {
        this.weekday = weekday;
        this.intervals = new ArrayList<>();
    }

    /**
     * Adds a new time interval to the list of intervals.
     *
     * @param interval new interval
     * @throws Exception
     */
    public int addInterval(TimeInterval interval) throws Exception {
        if (!areAvailableSlots()) {
            throw new Exception("You have exceeded the maximum number of slots per day (max = " + MAXIMAL_NUMBER_OF_INTERVALS + ")!");
        }

        for (TimeInterval currentInterval : intervals) {
            if (currentInterval.isIntersectWith(interval)) {
                throw new Exception("The interval intersects with other interval!");
            }
        }
        intervals.add(interval);
        sortIntervals();

        return interval.getId();
    }


    /**
     * Removes interval
     *
     * @param intervalID Unique interval ID
     */
    public void removeInterval(int intervalID) {
        for (TimeInterval interval : intervals) {
            if (interval.getId() == intervalID) {
                intervals.remove(interval);
                sortIntervals();
                return;
            }
        }
    }

    /**
     * Removes by interval
     *
     * @param intervalIndex interval index
     */
    public void removeIntervalByIndex(int intervalIndex) {
        if (intervals.size() < intervalIndex) {
            return;
        }

        intervals.remove(intervalIndex);
        sortIntervals();
    }

    /**
     * Gets intervals of a day
     *
     * @return intervalse of a day
     */
    public ArrayList<TimeInterval> getIntervals() {
        return intervals;
    }

    public TimeInterval getNextInterval(TimeInterval interval) {
        boolean flag = false;
        for (TimeInterval currentInterval : intervals) {
            if (flag) {
                return currentInterval;
            }
            if (currentInterval.equals(interval)) {
                flag = true;
            }
        }
        return null;
    }

    public TimeInterval getNextInterval(Time time) {
        boolean flag = false;

        for (TimeInterval currentInterval : intervals) {
            if (currentInterval.getStartTime().isLaterThan(time)) {
                return currentInterval;
            }
        }

        return null;
    }

    public TimeInterval getFirstInterval() {
        if (intervals.size() == 0) {
            return null;
        }

        return intervals.get(0);
    }

    /**
     * Is it possible to add one more interval in this day.
     *
     * @return true, if it is possible, else false.
     */
    private boolean areAvailableSlots() {
        return intervals.size() < MAXIMAL_NUMBER_OF_INTERVALS;
    }

    /**
     * Sorts intervals by Interval.startTime
     */
    private void sortIntervals() {
        Collections.sort(intervals, new TimerIntervalComparator());
    }

    @Override
    public String toString() {
        StringBuilder resultString = new StringBuilder();

        for (TimeInterval interval : this.intervals) {
            resultString.append(interval.toString());
            resultString.append("\n");
        }

        return resultString.toString();
    }

    public Weekday getWeekday() {
        return weekday;
    }
}
