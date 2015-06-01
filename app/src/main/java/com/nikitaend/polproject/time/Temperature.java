package com.nikitaend.polproject.time;

import java.io.Serializable;

/**
 * Класс, представляющий сущность "Температура".
 * <p>
 * Нужен для создания дневной и ночной температуры.
 */
public class Temperature implements Serializable {
    /**
     * Значение температуры
     */
    private double value;

    /**
     * Минимальная возможная температура
     */
    public static final double MINIMUM = 5.0;

    /**
     * Максимальная возможная температура
     */
    public static final double MAXIMUM = 30.0;

    /**
     * Создает объект с заданной температурой
     *
     * @param value значение задаваемой температуры
     * @throws Exception если значение температуры выходит за рамки
     */
    public Temperature(double value) throws Exception {
        this.setValue(value);
    }

    /**
     * Возвращает значение температуры
     *
     * @return значение температуры
     */
    public double getValue() {
        return value;
    }

    /**
     * Устанавливает значение температуры
     *
     * @param value значение температуры
     * @throws Exception если значение температуры вышло за допустимые пределы
     */
    public void setValue(double value) throws Exception {
        if (value < Temperature.MINIMUM || value > Temperature.MAXIMUM) {
            throw new Exception("Temperature must be in [" +
                    Temperature.MINIMUM + ";" + Temperature.MAXIMUM + "]");
        }


        this.value = round(value, 1);
    }

    /**
     * Rounds the value of number
     *
     * @return round number
     */
    private double round(double number, int scale) {
        int pow = 10;
        for (int i = 1; i < scale; i++)
            pow *= 10;
        double tmp = number * pow;
        return (double) (int) ((tmp - (int) tmp) >= 0.5 ? tmp + 1 : tmp) / pow;
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }
}
