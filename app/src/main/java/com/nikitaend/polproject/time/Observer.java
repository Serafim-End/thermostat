package com.nikitaend.polproject.time;

import java.util.GregorianCalendar;

/**
 * @author Endaltsev Nikita
 *         start at 30.05.15.
 */
public interface Observer {
    void update(GregorianCalendar date);
}
