package com.nikitaend.polproject.time;

import android.content.SharedPreferences;
import android.nfc.FormatException;

import java.io.Serializable;

/**
 * @author Endaltsev Nikita
 *         start at 30.05.15.
 */
public class NewTime implements Serializable {
    /**
     * ante meridiem.
     */
    public static final String A_M = "a.m.";

    /**
     * post meridiem.
     */
    public static final String P_M = "p.m.";
    public static final long VORTEX = 300;

    public static final int SUNDAY = 0;
    public static final int MONDAY = 1;
    public static final int TUESDAY = 2;
    public static final int WEDNESDAY = 3;
    public static final int THURSDAY = 4;
    public static final int FRIDAY = 5;
    public static final int SATURDAY = 6;
    /**
     * Hour value.
     */
    private int hours;

    /**
     * Minute value.
     */
    private int minutes;

    /**
     * Creates instance of time.
     *
     * @param hour   Hours
     * @param minute Minutes
     */
    public NewTime(int hour, int minute) {
        hours = hour;
        minutes = minute;
    }

    /**
     * TODO: Сделать что-то более адекватное
     * @param i
     * @return
     */
    public static String getWeekDay(int i) {
        i = Math.abs(i) % 7;
        switch (i) {
            case MONDAY:
                return "Monday";
            case TUESDAY:
                return "Tuesday";
            case WEDNESDAY:
                return "Wednesday";
            case THURSDAY:
                return "Thursday";
            case FRIDAY:
                return "Friday";
            case SATURDAY:
                return "Saturday";
            case SUNDAY:
                return "Sunday";
        }
        return "";
    }

    public static void send(String key, NewTime time, SharedPreferences.Editor bundle) {
        bundle.putInt(key + "Hours", time.hours);
        bundle.putInt(key + "Minutes", time.minutes);
    }

    public static NewTime retrieve(String key, SharedPreferences bundle) {
        android.text.format.Time time = new android.text.format.Time();
        time.setToNow();
        return new NewTime(bundle.getInt(key + "Hours", time.hour),
                bundle.getInt(key + "Minutes", time.minute));
    }

    public static NewTime parseTime(String value) throws FormatException {
        String[] values = value.split(":");
        if (values.length != 2) {
            throw new FormatException("Time does not match format");
        }
        int h;
        try {
            h = Integer.parseInt(values[0]);
            if (h < 0 || h > 23) {
                throw new FormatException("Hours must be in range from 0 to 23");

            }
        } catch (NumberFormatException e) {
            throw new FormatException("Hour is not a number");
        }
        int m;
        try {
            m = Integer.parseInt(values[1]);
            if (m < 0 || m > 59) {
                throw new FormatException("Minutes must be in range from 0 to 59");
            }
        } catch (NumberFormatException e) {
            throw new FormatException("Minute is not a number");
        }
        return new NewTime(h, m);
    }

    /**
     * Gets hours.
     *
     * @return hours
     */
    public int getHours() {
        return hours;
    }


    public static void checkRange(double v, String name, double min, double max) throws Exception {
        if (v < min || v > max) {
            throw new Exception(name + " must be in [" + min + ", " + max + "]");
        }
    }
    
    /**
     * Sets hours.
     *
     * @param hours hours
     */
    public void setHours(int hours) throws Exception {
        checkRange(hours, "Hours", 0, 23);
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
     * Sets minutes.
     *
     * @param minutes minutes
     */
    public void setMinutes(int minutes) throws Exception {
        checkRange(minutes, "Minutes", 0, 59);
        this.minutes = minutes;
    }

    @Override
    public String toString() {
        return toString(Representation.TWENTY_FOUR_HOURS);
    }

    /**
     * Returns string representation of time.
     *
     * @param representation Type of representation.
     * @return string representation of time.
     */
    private String toString(Representation representation) {
        StringBuilder res = new StringBuilder();
        if (representation == Representation.TWENTY_FOUR_HOURS) {
            if (hours < 10) {
                res.append(0);
            }
            res.append(hours);
            appendMinutes(res);
        } else if (representation == Representation.AM_PM) {
            String amPmHours;
            String amPm;
            if (hours == 0) {
                amPmHours = "12";
                amPm = A_M;
            } else if (hours < 12) {
                amPmHours = String.valueOf(hours);
                if (hours < 10) {
                    amPmHours = "0" + amPmHours;
                }
                amPm = A_M;
            } else if (hours == 12) {
                amPmHours = "12";
                amPm = P_M;
            } else {
                amPmHours = String.valueOf(hours - 12);
                if (hours - 12 < 10) {
                    amPmHours = "0" + amPmHours;
                }
                amPm = P_M;
            }
            res.append(amPmHours);
            appendMinutes(res);
            res.append(" ");
            res.append(amPm);
        }
        return res.toString();
    }

    /**
     * Appends minutes to the target builder.
     *
     * @param builder the target builder.
     */
    private void appendMinutes(StringBuilder builder) {
        builder.append(":");
        if (minutes < 10) {
            builder.append(0);
        }
        builder.append(minutes);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NewTime)) return false;

        NewTime time = (NewTime) o;

        if (hours != time.hours) return false;
        if (minutes != time.minutes) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = hours;
        result = 31 * result + minutes;
        return result;
    }

    /**
     * Types of representations of time.
     */
    private enum Representation {
        /**
         * 12-hour representation.
         */
        AM_PM,

        /**
         * 24-hour representation.
         */
        TWENTY_FOUR_HOURS
    }
}