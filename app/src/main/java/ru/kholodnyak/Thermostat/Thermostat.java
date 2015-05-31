package ru.kholodnyak.Thermostat;

import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
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
     * Текущее время.
     * По условию, оно течёт быстрее стандартного в 300 раз
     */
    private Time currentTime;


    private final static int MINUTES_PER_ONE = 5;

    public Thermostat() throws Exception {
        schedule = new WeekSchedule();
        currentTime = new Time(getCurrTime());
    }

    /**
     * Возвращает текушую температуру, установленную в термостате
     *
     * @return текущая температура в термостате
     */
    public double getCurrentTemperature() {
        // TODO: Implement
        return 0;
    }

    /**
     * Возвращает target-температуру, установленную в термостате
     *
     * @return tatget-температура в термостате
     */
    public double getTargetTemperature() {
        // TODO : Implement
        return 0;
    }

    /**
     * Устанавливает в термостат расписание "отпуска".
     * <p>
     * В этом режиме температура в термостате остается
     * постоянной. Расписание интервалов с температурами
     * в этом режиме тоже не действует.
     * <p>
     * Температура в режиме "отпуска" равна ночной температуре.
     */
    public void setPermanentMode(boolean isVacation) {
        // TODO: Implement
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


    private void findNearesInterval() {
//        this.currentTime
    }

    //TODO: Remove all after this line

    @Override
    public void run() {
        Timer timer = new Timer();
        timer.schedule(new TestTask(), 0, 100);
    }

    public String getCurrTime() {
        SimpleDateFormat timeFormat = new SimpleDateFormat("E HH:mm");
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String time = timeFormat.format(currDate.getTime());
        return time;
    }

    private GregorianCalendar currDate = new GregorianCalendar();

    private class TestTask extends TimerTask {
        @Override
        public void run() {
            currDate.add(GregorianCalendar.MINUTE, MINUTES_PER_ONE);
            String[] fulltime = getCurrTime().split(" ");

            currentTime.setWeekday(Weekday.getWeekDayByString(fulltime[0]));
            String[] time = fulltime[1].split(":");
            int hours = Integer.parseInt(time[0]);
            int minutes = Integer.parseInt(time[1]);

            try {
                currentTime.setHours(hours);
                currentTime.setMinutes(minutes);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

            System.out.println(currentTime);
        }


    }
}
