package com.nikitaend.polproject.time;

import java.util.GregorianCalendar;

/**
 * @author Endaltsev Nikita
 *         start at 30.05.15.
 */
public class Boiler implements Observer {

    public Boiler(Double currTemperature) {
        this.currTemperature = currTemperature;
    }
    
    @Override
    public void update(GregorianCalendar date) {
        if (currTemperature > goalTemperature) {
            currTemperature -= CELSIUM_CHANGE_PER_3_MINUTES;
            if (currTemperature < goalTemperature) {
                currTemperature = goalTemperature;
            }
        }

        if (currTemperature < goalTemperature ) {
            currTemperature += CELSIUM_CHANGE_PER_3_MINUTES;
            if (currTemperature > goalTemperature) {
                currTemperature = goalTemperature;
            }
        }
    }

    public void setGoalTemperature(Double goalTemperature) {
        this.goalTemperature = goalTemperature;
    }

    private Double currTemperature;
    private Double goalTemperature;

    public Double getCurrTemperature() {
        return (double)Math.round(currTemperature * 10) / 10;
    }

    public Double getGoalTemperature() {
        return (double)Math.round(goalTemperature * 10) / 10;
    }

    private static final Double CELSIUM_CHANGE_PER_3_MINUTES = 0.3;
}

