package com.nikitaend.polproject.time;

import java.io.Serializable;

/**
 * Перечисление "день недели".
 * <p>
 * Начинается с понедельника(value = 1),
 * заканчивается в воскресенье(value = 7).
 */
public enum Weekday implements Serializable {
    MONDAY(1), TUESDAY(2), WEDNESDAY(3),
    THURSDAY(4), FRIDAY(5), SATURDAY(6),
    SUNDAY(7), EMPTY(8);

    /**
     * Номер дня недели, начиная с единицы
     */
    private int value;

    Weekday(int value) {
        this.value = value;
    }

    /**
     * Check whether the current weekday is later than the other weekday
     *
     * @param weekday the other weekday
     * @return true, if the current weekday is later, else false
     */
    boolean isLaterThan(Weekday weekday) {
        return getDayNumber() > weekday.getDayNumber();
    }

    /**
     * Возвращает номер дня недели.
     * <p>
     * Начинается с понедельника(value = 1),
     * заканчивается в воскресенье(value = 7).
     *
     * @return номер дня недели.
     */
    int getDayNumber() {
        return this.value;
    }

    static Weekday getWeekDayByString(String weekday) {
        weekday = weekday.toLowerCase().trim();

        // :-(
        switch (weekday) {
            case "mon":
                return Weekday.MONDAY;
            case "tue":
                return Weekday.TUESDAY;
            case "wed":
                return Weekday.WEDNESDAY;
            case "thu":
                return Weekday.THURSDAY;
            case "fri":
                return Weekday.FRIDAY;
            case "sat":
                return Weekday.SATURDAY;
            case "sun":
                return Weekday.SUNDAY;
            default:
                // Exception ?
                return Weekday.EMPTY;
        }
    }
}