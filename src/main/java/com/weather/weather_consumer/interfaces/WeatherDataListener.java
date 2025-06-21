package com.weather.weather_consumer.interfaces;
import com.fasterxml.jackson.databind.JsonNode;
import java.util.Optional;

public interface WeatherDataListener {
    Optional<JsonNode> onDataReceived(String jsonData);
}
