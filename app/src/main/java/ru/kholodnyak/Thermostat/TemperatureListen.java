package ru.kholodnyak.Thermostat;

public class TemperatureListen implements TemperatureListener {
    @Override
    public void update(double currentTemperature, double targetTemperature) {
        System.out.println("Current Temperature is " + currentTemperature);
        System.out.println("Target Temperature is " + targetTemperature);
    }
}
