package ru.kholodnyak.Thermostat;

public class TemperatureListener implements TemperatureObserver{
    @Override
    public void update(double currentTemperature, double targetTemperature) {
        System.out.println("Current Temperature is " + currentTemperature);
        System.out.println("Target Temperature is " + targetTemperature);
    }
}
