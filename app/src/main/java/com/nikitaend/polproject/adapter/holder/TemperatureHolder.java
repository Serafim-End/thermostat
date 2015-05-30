package com.nikitaend.polproject.adapter.holder;

/**
 * 
 * holder for card list with temperatures
 * activity - ScheduleActivity
 * adapter - TemperatureAdapter
 * xml screen  - card_schedule
 * @author Endaltsev Nikita
 *         start at 11.05.15.
 */
public class TemperatureHolder {
    
    public String startTime;
    public String endTime;
    public String dayNight; // AM or PM
    public boolean isEnabled;
    
    public TemperatureHolder(String startTime, String endTime, String dayNight, boolean isEnabled) {
        this.dayNight = dayNight;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isEnabled = isEnabled;
        
    }
}
