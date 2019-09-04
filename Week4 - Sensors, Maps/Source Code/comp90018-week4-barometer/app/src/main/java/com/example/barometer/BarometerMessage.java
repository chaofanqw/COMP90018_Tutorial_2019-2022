package com.example.barometer;

public class BarometerMessage {
    public float getPressure() {
        return pressure;
    }

    private float pressure;

    public BarometerMessage(float pressure) {
        this.pressure = pressure;
    }

}
