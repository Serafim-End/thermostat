package com.nikitaend.polproject.time;

import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Endaltsev Nikita
 *         start at 30.05.15.
 */
public class Prototype implements Runnable {

    public static interface ChangeTimeTemperatureListener {
        public abstract void OnTimeTemperatureChanged(double temperature, String time);
    }
    
    public static int SECONDS_PER_ONE = 300;
    
    public static double currentTemperature;
    public static String currentTime;
    ChangeTimeTemperatureListener mChangeTimeTemperatureListener;
    TextView timeTextView;
    
    public Prototype(GregorianCalendar date, Double currTemperature, TextView textView) {
        this.currDate = date;
        
        boiler = new Boiler(currTemperature);
        dateChanger = new DateChanger(date);
        timeTextView = textView;

        observers  = new LinkedList<Observer>();
        observers.add(boiler);
        observers.add(dateChanger);
    }

    public String getCurrTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String time = timeFormat.format(currDate.getTime());
        return time;
    }

    public Double getCurrTemperature() {
        return boiler.getCurrTemperature();
    }

    public void setGoalTemperature(Double goalTemperature) {
        boiler.setGoalTemperature(goalTemperature);
    }

    public GregorianCalendar getCurrDate() {
        return currDate;
    }

    public Boiler boiler;
    public DateChanger dateChanger;

    private GregorianCalendar currDate;

    /**
     * When an object implementing interface <code>Runnable</code> is used
     * to create a thread, starting the thread causes the object's
     * <code>run</code> method to be called in that separately executing
     * thread.
     * <p/>
     * The general contract of the method <code>run</code> is that it may
     * take any action whatsoever.
     *
     * @see Thread#run()
     */
    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new ChangerTask(), 0, 1000);
    }

    private class ChangerTask extends TimerTask {

        /**
         * The action to be performed by this timer task.
         */
        @Override
        public void run() {
            currDate.add(GregorianCalendar.MINUTE, SECONDS_PER_ONE);
            updateObservers(currDate);
            
            currentTemperature = getCurrTemperature();
            currentTime = getCurrTime();

            System.out.println(getCurrTemperature() + "C ::: " + getCurrTime());
        }
    }

    List<Observer> observers;

    public void updateObservers(GregorianCalendar date) {
        for (Observer observer: observers) {
            observer.update(date);
        }
    }
}