package com.nikitaend.polproject.time;

import java.util.Date;

public class TimeManager implements Runnable {
    Date date;
    ManagerListener listener;
    
    int interval = 120000;
    
    public TimeManager(ManagerListener l) {
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

    public static interface ManagerListener {
        void timeDidChanged(Date date);
    }
}
