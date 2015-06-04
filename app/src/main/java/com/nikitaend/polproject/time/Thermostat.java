package com.nikitaend.polproject.time;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Главный класс модели.
 * Представляет собой  физическую "железку" термостата.
 * По сути, это класс-фасад (паттерн проектирования),
 * через него происходит все взаимодействие с "железкой".
 */
public class Thermostat implements Runnable {

    /**
     * Singleton instance of Thermostat
     */
    private static Thermostat instance;

    private WeekSchedule schedule;

    /**
     * Night temperature
     */
    private Temperature nightTemperature;

    /**
     * Day temperature
     */
    private Temperature dayTemperature;

    /**
     * Current temperature
     */
    private Temperature currentTemperature;

    /**
     * Target temperature
     */
    private Temperature targetTemperature;

    private Temperature manualTemperature;

    /**
     * Текущее время.
     * По условию, оно течёт быстрее стандартного в 300 раз
     */
    private Time currentTime;

    /**
     * Temperature observers
     */
    ArrayList<TemperatureListener> temperatureListeners;

    /**
     * Current Time observers
     */
    ArrayList<CurrentTimeListener> currentTimeListeners;

    private boolean isVacationMode = false;

    private boolean isManualMode = false;

    private final static int MINUTES_PER_ONE = 1;

    private Thermostat(double nightTemperature, double dayTemperature) throws Exception {
        schedule = new WeekSchedule();
        currentTime = new Time(getCurrTime());

        //TODO: Edit constant:
        this.dayTemperature = new Temperature(dayTemperature);
        this.nightTemperature = new Temperature(nightTemperature);

        this.currentTemperature = new Temperature(this.dayTemperature.getValue());
        this.targetTemperature = new Temperature(this.dayTemperature.getValue());

        // Temperature observers
        temperatureListeners = new ArrayList<>();

        // Current time observers
        currentTimeListeners = new ArrayList<>();

        // TODO: Remove this
        insertInitialsData();
//        insertIntervalDataHash();
    }


    /**
     * Singleton
     *
     * @return Thermostat instance
     * @throws Exception if something going wrong
     */
    public static Thermostat getInstance(double nightTemperature, double dayTemperature) throws Exception {
        if (instance == null) {
            instance = new Thermostat(nightTemperature, dayTemperature);
        }
        return instance;
    }

    public boolean isVacationMode() {
        return isVacationMode;
    }

    public void addInterval(String startTimeString, String endTimeString) throws Exception {

        Time startTime = new Time(startTimeString);
        Time endTime = new Time(endTimeString);

        TimeInterval interval = new TimeInterval(this.dayTemperature, startTime, endTime);
        schedule.addInterval(startTime.getWeekday(), interval);
    }

    public void removeIntervalByIndex(int intervalIndex, Weekday weekday) {
        schedule.removeIntervalByIndex(intervalIndex, weekday);
    }

    /**
     * Возвращает текушую температуру, установленную в термостате
     *
     * @return текущая температура в термостате
     */
    public double getCurrentTemperature() {
        return currentTemperature.getValue();
    }

    /**
     * Возвращает target-температуру, установленную в термостате
     *
     * @return tatget-температура в термостате
     */
    public double getTargetTemperature() {
        return targetTemperature.getValue();
    }

    /**
     * Устанавливает в термостат расписание "отпуска".
     * <p/>
     * В этом режиме температура в термостате остается
     * постоянной. Расписание интервалов с температурами
     * в этом режиме тоже не действует.
     * <p/>
     * Температура в режиме "отпуска" равна ночной температуре.
     */
    public void setVacationMode(boolean isWorking) {
        this.isVacationMode = isWorking;
    }

    public void setManualMode(boolean isWorking) {
        this.isManualMode = isWorking;
    }

    /**
     * Gets night temperature value
     *
     * @return night
     */
    public double getNightTemperatureValue() {
        return nightTemperature.getValue();
    }

    public void setNightTemperatureValue(double value) throws Exception {
        // This variable is not redundant
        Temperature nightTemperature = new Temperature(value);

        this.nightTemperature = nightTemperature;
    }

    /**
     * Gets day temperature value
     *
     * @return day temperature value
     */
    public double getDayTemperatureValue() {
        return dayTemperature.getValue();
    }

    public void setDayTemperatureValue(double value) throws Exception {
        this.dayTemperature = new Temperature(value);
        updateDayTimeTemperature();
    }

    public void setManualTemperatureValue(double value) throws Exception {
        this.manualTemperature = new Temperature(value);
    }

    @Override
    public String toString() {
        return schedule.toString();
    }

    private void updateTemperature() {
        for (TemperatureListener temperatureObserver : temperatureListeners) {
            temperatureObserver.update(this.currentTemperature.getValue(), this.targetTemperature.getValue());
        }
    }

    private void updateCurrentTime() {
        for (CurrentTimeListener currentTimeObserver : currentTimeListeners) {
            currentTimeObserver.update(this.currentTime.toString());
        }
    }

    public void addCurrentTimeListener(CurrentTimeListener listener) {
        currentTimeListeners.add(listener);
    }

    public void addTemperatureListener(TemperatureListener listener) {
        temperatureListeners.add(listener);
    }

    //TODO: Remove all after this line

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TemperatureWatcher(), 0, 200);
    }

    public String getCurrTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("E HH:mm", Locale.US);
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return timeFormat.format(currDate.getTime());
    }

    private GregorianCalendar currDate = new GregorianCalendar();


    private void insertInitialsData() throws Exception {
//        Time startTime1 = new Time("Mon 4:25");
//        Time endTime1 = new Time("Mon 4:30");
//
//        Time startTime2 = new Time("Mon 03:20");
//        Time endTime2 = new Time("Mon 03:21");
//
//        Time startTime3 = new Time("Tue 05:20");
//        Time endTime3 = new Time("Tue 10:20");
//
//        Temperature temperature = new Temperature(6.0);
//
//        TimeInterval interval = new TimeInterval(10, startTime1, endTime1);
//        TimeInterval interval2 = new TimeInterval(temperature.getValue() + 1, startTime2, endTime2);
//        TimeInterval interval3 = new TimeInterval(temperature.getValue() + 2, startTime3, endTime3);
//
//
//        schedule.addInterval(Weekday.MONDAY, interval);
//        schedule.addInterval(Weekday.MONDAY, interval2);
//        schedule.addInterval(Weekday.TUESDAY, interval3);

    }

//    public void insertIntervalDataHash() {
//        try {
//            for (int i = 0; i < ScheduleActivity.temperatureHoldersHash.size(); i++) {
//                ArrayList<TemperatureHolder> temperatureHolders = ScheduleActivity.temperatureHoldersHash.get(ScheduleDaysActivity.weekDays[i]);
//                for (TemperatureHolder temperatureHolder : temperatureHolders) {
//                    String newTitle = ScheduleDaysActivity.weekDays[i].substring(0, 3);
//                    String[] temp1 = temperatureHolder.startTime.split(" ");
//                    Time startTime = new Time(newTitle + temp1[0]);
//                    String[] temp2 = temperatureHolder.endTime.split(" ");
//                    Time endTime = new Time(newTitle + temp2[0]);
//
//                    TimeInterval interval1 = new TimeInterval(getCurrentTemperature(), startTime, endTime);
//                    schedule.addInterval(Weekday.getWeekDayByString(newTitle), interval1);
//                }
//            }
//        } catch (Exception e) {}
//    }

    /**
     * Устанавливает новую дневную температуру для уже существующих интервалов
     */
    private void updateDayTimeTemperature() {
        for (DaySchedule dayShedule : schedule.getDaysSchedule()) {
            for (TimeInterval interval : dayShedule.getIntervals()) {

                interval.setTemperature(this.dayTemperature);
            }
        }
    }

    private class TemperatureWatcher extends TimerTask {
        @Override
        public void run() {
            observeTemperature();

            updateTimer();
            updateCurrentTime();
            // System.out.println(currentTime);
//            System.out.println(currentTemperature.getValue());
        }

        private void updateTimer() {
            currDate.add(GregorianCalendar.MINUTE, MINUTES_PER_ONE);
            String[] fulltime = getCurrTime().split(" ");

            currentTime.setWeekday(Weekday.getWeekDayByString(fulltime[0]));
            String[] time = fulltime[1].split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);
//            minutes -= (minutes % 5);
            try {
                currentTime.setHours(hours);
                currentTime.setMinutes(minutes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }

        private void observeTemperature() {
            if (isVacationMode) {
                currentTemperature = nightTemperature;
                targetTemperature = nightTemperature;
                System.out.println("Vacation Mode");
                updateTemperature();
                return;
            }

            if (isManualMode) {
                if (currentTime.isMidnight()) {
                    setManualMode(false);
                    return;
                }
                currentTemperature = manualTemperature;
                targetTemperature = nightTemperature;
                updateTemperature();

            }

            for (DaySchedule daySchedule : schedule.getDaysSchedule()) {
                for (TimeInterval interval : daySchedule.getIntervals()) {

                    if (interval.containsTime(currentTime)) {
                        setManualMode(false);
                        currentTemperature = interval.getTemperature();
                        targetTemperature = nightTemperature;
                        updateTemperature();
                        return;

                    }
                }
            }
            if (!isManualMode) {
                currentTemperature = nightTemperature;
                targetTemperature = getTargetTemperature();
            }
            updateTemperature();
        }

        private Temperature getTargetTemperature() {
            TimeInterval nextInterval = schedule.getNextInterval(currentTime);
            if (nextInterval != null) {
                return nextInterval.getTemperature();
            } else {
                return nightTemperature;
            }
        }
    }
}
