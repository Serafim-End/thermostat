package ru.kholodnyak.Thermostat;


import com.nikitaend.polproject.time.CurrentTimeListener;

public class CurrentTimeListen implements CurrentTimeListener {
    @Override
    public void update(String currentTime) {
        System.out.println("Current time is " + currentTime);
    }
}
