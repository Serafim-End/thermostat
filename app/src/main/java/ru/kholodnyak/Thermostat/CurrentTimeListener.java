package ru.kholodnyak.Thermostat;

/**
 * Следит за изменением текущего времени
 */
public interface CurrentTimeListener {
    void update(String currentTime);
}
