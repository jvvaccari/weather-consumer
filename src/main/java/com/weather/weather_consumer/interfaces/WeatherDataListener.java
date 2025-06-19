package com.weather.weather_consumer.interfaces;

public interface WeatherDataListener {
    void onDataReceived(String jsonData);
}
