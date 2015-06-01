package ru.kholodnyak.Thermostat;


public class CurrentTimeListener implements CurrentTimeObserver {
    @Override
    public void update(String currentTime) {
        System.out.println("Current time is " + currentTime);
    }
}
