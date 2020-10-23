package io.rbetik12.models;

import java.io.Serializable;

public class Coordinates implements Comparable, Serializable {
    private final Double x; //Поле не может быть null
    private final Double y; //Максимальное значение поля: 57, Поле не может быть null

    public Coordinates() {
        x = 0.0;
        y = 0.0;
    }

    public Coordinates(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public String toString() {
        return "X: " + x + " Y: " + y;
    }

    @Override
    public int compareTo(Object o) {
        Coordinates coordinates = (Coordinates) o;

        if (x.equals(coordinates.x) && y.equals(coordinates.y)) {
            return 0;
        }

        if (x * x + y * y > coordinates.x * coordinates.x + coordinates.y * coordinates.y) {
            return 1;
        }

        return -1;
    }
}
