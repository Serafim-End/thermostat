package com.nikitaend.polproject.time;

/**
 * Следит за изменением текущей температуры
 */
public interface TemperatureObserver {
    void update(double currentTemperature, double targetTemperature);
}
