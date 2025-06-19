package com.weather.weather_consumer.classes;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import com.weather.weather_consumer.interfaces.WeatherDataListener;

@Component
public class WeatherInternalPublisher {

    private final List<WeatherDataListener> listeners = new CopyOnWriteArrayList<>();

    public void registerListener(WeatherDataListener listener) {
        listeners.add(listener);
    }

    public void notifyListeners(String jsonData) {
        for (WeatherDataListener listener : listeners) {
            listener.onDataReceived(jsonData);
        }
    }
}
