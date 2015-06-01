package ru.kholodnyak.Thermostat;


public class CurrentTimeListen implements CurrentTimeListener {
    @Override
    public void update(String currentTime) {
        System.out.println("Current time is " + currentTime);
    }
}
