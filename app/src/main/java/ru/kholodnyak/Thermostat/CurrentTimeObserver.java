package ru.kholodnyak.Thermostat;

/**
 * Следит за изменением текущего времени
 */
public interface CurrentTimeObserver {
    void update(String currentTime);
}
