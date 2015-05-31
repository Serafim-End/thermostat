package com.nikitaend.polproject.time;


public class CurrentTimeListener implements CurrentTimeObserver {
    @Override
    public void update(String currentTime) {
        System.out.println("Current time is " + currentTime);
    }
}
