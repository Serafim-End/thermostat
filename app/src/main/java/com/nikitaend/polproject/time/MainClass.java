package com.nikitaend.polproject.time;

public class MainClass {

    public static void main(String[] args) throws Exception {

////
////
//        DaySchedule mondaySchedule = new DaySchedule(Weekday.MONDAY);
//        mondaySchedule.addInterval(interval);
//        mondaySchedule.addInterval(interval2);
//        System.out.println(mondaySchedule);
//
//        int hash = interval.getId();
//////
//        System.out.println(interval2.hashCode());
//        System.out.println(interval.hashCode());
//
//        mondaySchedule.removeInterval(hash);
//        System.out.println(mondaySchedule);

//        System.out.println(interval2.isIntersectWith(interval));

//        System.out.println(interval);


        Thermostat thermostat = Thermostat.getInstance(23, 19);

        thermostat.addInterval("Mon 2:35", "Mon 2:40");
        System.out.println(thermostat);

        thermostat.removeIntervalByIndex(0, Weekday.MONDAY);

        System.out.println(thermostat);

        Thread thread = new Thread(thermostat);
        thread.start();
        thermostat.run();

//        WeekSchedule schedule = new WeekSchedule();
//
//


//


//        Time startTime1 = new Time("Mon 00:25");
//        Time endTime1 = new Time("Mon 00:30");
//        Time startTime2 = new Time("Mon 10:25");
//        Time endTime2 = new Time("Mon 10:26");
//
//        System.out.println(startTime1);
//        System.out.println(startTime2);
//        Time startTime3 = new Time("Tue 05:20");
//        Time endTime3 = new Time("Tue 10:20");
//
//        Temperature temperature = new Temperature(6.0 - 1);
//
//        TimeInterval interval = new TimeInterval(10, startTime1, endTime1);
//        TimeInterval interval2 = new TimeInterval(temperature, startTime2, endTime2);
//        TimeInterval interval3 = new TimeInterval(temperature, startTime3, endTime3);
//
////
//        schedule.addInterval(Weekday.MONDAY, interval);
////        schedule.addInterval(Weekday.MONDAY, interval2);
//        schedule.addInterval(Weekday.MONDAY, interval3);
//
//
//
//        System.out.println(schedule);
//
//        System.out.println(schedule.getNextInterval(interval3));
//
//
//        FileOutputStream fos = new FileOutputStream("temp.out");
//        ObjectOutputStream oos = new ObjectOutputStream(fos);
//
//        oos.writeObject(schedule);
//        oos.flush();
//        oos.close();
////
//        FileInputStream fis = new FileInputStream("temp.out");
//        ObjectInputStream oin = new ObjectInputStream(fis);
//        WeekSchedule ts = (WeekSchedule) oin.readObject();
//
//
//        System.out.println(ts);
    }

    public static Weekday test() {
        return Weekday.FRIDAY;
    }


}

