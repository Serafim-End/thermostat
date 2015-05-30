package com.nikitaend.polproject.time;

import java.util.GregorianCalendar;

/**
 * @author Endaltsev Nikita
 *         start at 30.05.15.
 */
public class DateChanger implements Observer {
    
    public DateChanger(GregorianCalendar date) {
        this.currDate = date;
    }
    @Override
    public void update(GregorianCalendar date) {
        currDate = date;
    }

    private GregorianCalendar currDate;

    public GregorianCalendar getCurrDate() {
        return currDate;
    }
}
