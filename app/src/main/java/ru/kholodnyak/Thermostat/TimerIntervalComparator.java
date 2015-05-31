package ru.kholodnyak.Thermostat;

import java.util.Comparator;

/**
 * Comparator for TimerInterval class
 */
public class TimerIntervalComparator implements Comparator<TimeInterval> {
    @Override
    public int compare(TimeInterval o1, TimeInterval o2) {
        return o1.isLaterThan(o2) ? 1 : -1;
    }
}
