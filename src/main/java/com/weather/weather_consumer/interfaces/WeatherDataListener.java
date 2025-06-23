package com.weather.weather_consumer.interfaces;
import java.util.Optional;

public interface WeatherDataListener {
    void onDataReceived(String jsonData);
}
