package com.nikitaend.polproject.time;

import java.io.Serializable;

/**
 * Класс, представляющий сущность "Время".
 * <p>
 * Стандартную библиотеку использовать нельзя
 * из-за специфичности условия работы программы
 * (время должно идти в 300 раз быстрее, каждые
 * 7 дней время "сбрасывается на ноль").
 * <p>
 * Формат хранения - 24-часовой,
 * но есть специальные хелперы для вывода в 12-часовом.
 */
public class Time implements Serializable {

    /**
     * Hour value.
     */
    private int hours;

    /**
     * Minute value
     */
    private int minutes;

    /**
     * Weekday.
     */
    Weekday weekday;

    /**
     * Gets hours.
     *
     * @return hours
     */
    public int getHours() {
        return hours;
    }

    /**
     * Creates time object.
     * <p>
     * !! Внимание !! Прочитай формат входных данных
     * !! Внимание !!
     *
     * @param time Строка формата "Monday 13:48",
     *             где первое слово – день недели на английском языке,
     *             второе слово – время в формате "hh:mm" (без минут)
     * @throws Exception if input time format is incorrect
     */
    public Time(String time) throws Exception {
        String splitSymbol = " ";
        String[] timeList = time.split(splitSymbol);

        if (timeList.length != 2) {
            throw new Exception("Incorrect time format!");
        }

        validateWeekday(timeList[0]);
        validateDate(timeList[1]);
    }

    /**
     * Validates and sets weekday.
     *
     * @param weekday weekday in the string
     * @throws Exception if the format of weekday is incorrect
     */
    private void validateWeekday(String weekday) throws Exception {
        Weekday tmpWeekday = Weekday.getWeekDayByString(weekday);

        if (tmpWeekday == Weekday.EMPTY) {
            throw new Exception("The format of weekday is incorrect!");
        }
        this.weekday = tmpWeekday;
    }

    /**
     * Validates and sets hours and minutes
     *
     * @param date date in "hh:mm" format
     * @throws Exception if the format of date is incorrect
     */
    private void validateDate(String date) throws Exception {
        String splitSymbol = ":";
        String[] timeList = date.split(splitSymbol);

        if (timeList.length != 2) {
            throw new Exception("Incorrect time format!");
        }

        int hours = Integer.parseInt(timeList[0]);
        this.setHours(hours);

        int minutes = Integer.parseInt(timeList[1]);
        this.setMinutes(minutes);
    }

    @Override
    public String toString() {

        // add symbol '0' if needed
        String minutes = (this.minutes < 10) ? "0" + this.minutes
                : Integer.toString(this.minutes);
        String hours = (this.hours < 10) ? "0" + this.hours
                : Integer.toString(this.hours);

        return (weekday + " " + hours + ":" + minutes);
    }

    /**
     * Sets hours.
     *
     * @param hours hours
     */
    public void setHours(int hours) throws Exception {
        int minHour = 0, maxHour = 23;

        if (!isInRange(hours, minHour, maxHour)) {
            throw new Exception("Hours must be in [" + minHour + ", " + maxHour + "]");
        }

        this.hours = hours;
    }

    /**
     * Gets minutes.
     *
     * @return minutes
     */
    public int getMinutes() {
        return minutes;
    }

    /**
     * Gets weekday.
     *
     * @return weekday
     */
    public Weekday getWeekday() {
        return weekday;
    }

    /**
     * Sets weekday
     *
     * @param weekday weekday
     */
    public void setWeekday(Weekday weekday) {
        this.weekday = weekday;
    }

    /**
     * Sets minutes.
     *
     * @param minutes minutes
     */
    public void setMinutes(int minutes) throws Exception {
        int minMinute = 0, maxMinute = 59;

        if (!isInRange(minutes, minMinute, maxMinute)) {
            throw new Exception("Minutes must be in [" + minMinute + ", " + maxMinute + "]");
        }
        this.minutes = minutes;
    }

    /**
     * Проверяет текущий объект на равенство другому.
     *
     * @param o сравниваемый объект
     * @return true, если объекты равны, false – иначе
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Time)) return false;

        Time time = (Time) o;

        return ((weekday == time.weekday) &&
                (hours == time.hours) &&
                (minutes == time.minutes));
    }

    /**
     * Проверяет, является ли текущее время полночью
     * @return true, если время == полночи, false – иначе
     */
    public boolean isMidnight() {
        return ((0 == this.hours) &&
                (0 == this.minutes));
    }

    /**
     * Check whether the current time is later than the other time
     *
     * @param time the other time
     * @return true, if current time is later, else false
     */
    public boolean isLaterThan(Time time) {
        if (this.weekday.isLaterThan(time.weekday)) {
            return true;
        } else if (time.weekday.isLaterThan(this.weekday)) {
            return false;
        }

        int minutesInHour = 60;

        int currentTotalMinutes = this.hours * minutesInHour + minutes;
        int anotherTimeTotalMinutes = time.getHours() * minutesInHour + time.getMinutes();

        return currentTotalMinutes > anotherTimeTotalMinutes;
    }

    /**
     * Check whether the current time is later or equal than the other time
     *
     * @param time the other time
     * @return true, if current time is later or equal, else false
     */
    public boolean isLaterOrEqualThan(Time time) {
        return this.equals(time) || this.isLaterThan(time);
    }

    public int minusAnother(Time time) throws Exception {
        if (time.getWeekday() != this.getWeekday()) {
            throw new Exception("You can compare the dates only for one day!");
        }

        if (time.equals(this)) {
            return 0;
        }

        int minutesInHour = 60;

        int currentTotalMinutes = this.hours * minutesInHour + minutes;
        int anotherTimeTotalMinutes = time.getHours() * minutesInHour + time.getMinutes();

//        if (time.isLaterThan(this)) {
//            return anotherTimeTotalMinutes - currentTotalMinutes;
//        }
        return currentTotalMinutes - anotherTimeTotalMinutes;
    }

    /**
     * Возвращает принадлежность значения интервалу.
     *
     * @param value проверяемое значение
     * @param start начало интервала
     * @param end   конец интервала
     * @return true, если принадлежит, иначе - false.
     */
    private boolean isInRange(int value, int start, int end) {
        return (value >= start && value <= end);
    }

}