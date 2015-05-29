package com.nikitaend.polproject;

import java.util.Date;

/**
 * Created by denis on 29.05.15.
 */


public class TimeManager implements Runnable {
    Date date;
    ManagerListener listener;
    int interval = 60000;
    TimeManager(ManagerListener l) {
        date = new Date();
        listener = l;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.currentThread().sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            listener.timeDidChanged(updateTime());
        }
    }

    Date updateTime () {
        return new Date(date.getTime() + interval);
    }
}