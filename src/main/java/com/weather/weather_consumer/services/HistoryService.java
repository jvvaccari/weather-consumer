package com.weather.weather_consumer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.weather.weather_consumer.model.WeatherData;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

@Component
public class HistoryService {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private WeatherData currentData = null;
    private WeatherData previousData = null;
    private LocalDateTime currentDataUpdateTime = null;
    private LocalDateTime previousDataUpdateTime = null;

    private JsonNode currentAlerts = null;
    private JsonNode previousAlerts = null;
    private LocalDateTime currentAlertsUpdateTime = null;
    private LocalDateTime previousAlertsUpdateTime = null;

    public Optional<WeatherData> getPreviousData() {
        return Optional.ofNullable(previousData);
    }

    public Optional<LocalDateTime> getPreviousDataUpdateTime() {
        return Optional.ofNullable(previousDataUpdateTime);
    }

    public Optional<JsonNode> getPreviousAlerts() {
        return Optional.ofNullable(previousAlerts);
    }

    public Optional<LocalDateTime> getPreviousAlertsUpdateTime() {
        return Optional.ofNullable(previousAlertsUpdateTime);
    }

    public void updateDataIfChanged(WeatherData newData) {
        if (currentData == null) {
            this.currentData = newData;
            this.currentDataUpdateTime = LocalDateTime.now();

            this.previousData = newData;
            this.previousDataUpdateTime = this.currentDataUpdateTime;

            System.out.println("[HISTORY SERVICE] Dados inicializados.");
            return;
        }

        if (hasChanged(currentData, newData)) {
            this.previousData = this.currentData;
            this.previousDataUpdateTime = this.currentDataUpdateTime;

            this.currentData = newData;
            this.currentDataUpdateTime = LocalDateTime.now();

            System.out.println("[HISTORY SERVICE] Dados atualizados.");
        }
    }

    public void updateAlertsIfChanged(JsonNode newAlerts) {
        if (currentAlerts == null) {
            this.currentAlerts = newAlerts;
            this.currentAlertsUpdateTime = LocalDateTime.now();

            this.previousAlerts = newAlerts;
            this.previousAlertsUpdateTime = this.currentAlertsUpdateTime;

            System.out.println("[HISTORY SERVICE] Alertas inicializados.");
            return;
        }

        if (!Objects.equals(currentAlerts, newAlerts)) {
            this.previousAlerts = this.currentAlerts;
            this.previousAlertsUpdateTime = this.currentAlertsUpdateTime;

            this.currentAlerts = newAlerts;
            this.currentAlertsUpdateTime = LocalDateTime.now();

            System.out.println("[HISTORY SERVICE] Alertas atualizados.");
        }
    }

    private boolean hasChanged(WeatherData oldData, WeatherData newData) {
        if (oldData == null && newData == null) return false;
        if (oldData == null || newData == null) return true;

        Map<String, Object> oldMap = objectMapper.convertValue(oldData, Map.class);
        Map<String, Object> newMap = objectMapper.convertValue(newData, Map.class);

        for (String key : newMap.keySet()) {
            if (!Objects.equals(oldMap.get(key), newMap.get(key))) {
                return true;
            }
        }
        return false;
    }
}
