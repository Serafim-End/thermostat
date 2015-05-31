package com.nikitaend.polproject.time;

/**
 * Следит за изменением текущего времени
 */
public interface CurrentTimeObserver {
    void update(String currentTime);
}
