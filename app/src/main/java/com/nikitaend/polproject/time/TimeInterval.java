package com.nikitaend.polproject.time;

import java.io.Serializable;

/**
 * Интервал c заданной температурой.
 * <p>
 * По условию, в программе можно задавать
 * интервалы только с "дневной" температурой.
 */
public class TimeInterval implements Serializable {

    /**
     * Interval ID.
     */
    private int id;
    /**
     * Время начала интервала.
     */
    private Time startTime;

    /**
     * Время конца интервала.
     */
    private Time endTime;

    /**
     * Температура, заданная на всём интервале
     */
    private Temperature temperature;

    /**
     * Создает объект с заданной температурой и временем начала и конца
     *
     * @param temperature Объект температуры
     * @param startTime   Время начала интервала
     * @param endTime     Время конца интервала
     */
    public TimeInterval(final Temperature temperature, final Time startTime, final Time endTime) throws Exception {
        this.temperature = temperature;

        // conditions taken from the task description
        if (startTime.getWeekday() != endTime.getWeekday()) {
            throw new Exception("startTime.weekday must be equal endTime.weekday!");
        }

        if (!endTime.isLaterThan(startTime)) {
            throw new Exception("Start time must be later than end time!");
        }

        this.startTime = startTime;
        this.endTime = endTime;
    }

    /**
     * Создает объект с заданной температурой и временем начала и конца
     * <p>
     * ! Синтаксический сахар, температуру можно задать double значением
     *
     * @param temperatureValue Значение температуры на интервале
     * @param startTime        Время начала интервала
     * @param endTime          Время конца интервала
     * @throws Exception если значение температуры выходит за допустимые рамки
     */
    public TimeInterval(double temperatureValue, final Time startTime, final Time endTime) throws Exception {
        this(new Temperature(temperatureValue), startTime, endTime);
    }

    @Override
    public String toString() {
        return "TimeInterval{" +
                "startTime=" + startTime +
                ", endTime=" + endTime +
                ", temperature=" + temperature +
                ", id=" + getId() +
                '}';
    }

    /**
     * Check whether the current interval is later than the other interval.
     *
     * @param interval the other interval
     * @return true, if current interval is later, else false
     */
    public boolean isLaterThan(TimeInterval interval) {
        return this.startTime.isLaterThan(interval.endTime);
    }

    /**
     * Check whether the current interval is intersects with the other interval.
     *
     * @param interval the other interval
     * @return true, if intervals are intersect, else false
     */

    public boolean isIntersectWith(TimeInterval interval) {
        // Check weekdays
        if (this.startTime.weekday != interval.startTime.getWeekday()) {
            return false;
        }

        if (!startTime.isLaterThan(interval.getStartTime())) {
            return !(interval.getStartTime().isLaterThan(this.endTime));
        } else {
            return !(this.startTime.isLaterThan(interval.getEndTime()));
        }
    }

    public boolean containsTime(Time time) {

        return time.isLaterOrEqualThan(this.startTime) && this.endTime.isLaterOrEqualThan(time);
    }

    public boolean equals(TimeInterval o) {

        return ((this.getStartTime() == o.getStartTime() &&
                this.getEndTime() == o.getEndTime()
        ));
    }

    /**
     * Gets unique ID of this time interval
     *
     * @return unique id
     */
    public int getId() {
        return hashCode();
    }

    /*
    * Accessors below
    */


    public Time getStartTime() {
        return startTime;
    }

    private void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Time getEndTime() {
        return endTime;
    }

    private void setEndTime(Time endTime) {
        this.endTime = endTime;
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Temperature temperature) {
        this.temperature = temperature;
    }

}
