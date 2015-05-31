package ru.kholodnyak.Thermostat;

import com.nikitaend.polproject.time.TemperatureListener;

public class TemperatureListen implements TemperatureListener {
    
    @Override
    public void update(double currentTemperature, double targetTemperature) {
        System.out.println("Current Temperature is " + currentTemperature);
        System.out.println("Target Temperature is " + targetTemperature);
    }
}
