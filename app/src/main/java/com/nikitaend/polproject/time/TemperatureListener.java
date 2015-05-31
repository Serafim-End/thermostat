package com.nikitaend.polproject.time;

/**
 * Следит за изменением текущей температуры
 */
public interface TemperatureListener {
    void update(double currentTemperature, double targetTemperature);
}
