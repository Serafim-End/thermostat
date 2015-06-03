package com.nikitaend.polproject.adapter.holder;


import com.nikitaend.polproject.time.TimeInterval;

import java.util.Comparator;

public class TemperatureHolderComparator implements Comparator<TemperatureHolder> {
    @Override
    public int compare(TemperatureHolder lhs, TemperatureHolder rhs) {
        return toMinutes(lhs.endTime) > toMinutes(rhs.endTime) ? 1 : -1;
    }

    /**
     * Переводит строку формата hh:mm в минуты
     *
     * @param time строка формата hh:mm
     * @return minutes
     */
    private int toMinutes(String time) {
        String hoursMinutesSeparator = ":";
        String[] timeArray = time.split(hoursMinutesSeparator);

        int hour = Integer.parseInt(timeArray[0]);
        int minutes = Integer.parseInt(timeArray[1]);

        int minutesInHour = 60;
        return hour * minutesInHour + minutes;
    }
}
