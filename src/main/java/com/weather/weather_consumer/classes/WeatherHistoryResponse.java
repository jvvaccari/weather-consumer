package com.weather.weather_consumer.classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.weather.weather_consumer.model.WeatherData;

import java.time.LocalDateTime;

public class WeatherHistoryResponse {


    private WeatherData data;
    private LocalDateTime dataUpdateTime;

    private JsonNode alerts;
    private LocalDateTime alertsUpdateTime;

    public WeatherHistoryResponse(
            WeatherData data,
            LocalDateTime dataUpdateTime,
            JsonNode alerts,
            LocalDateTime alertsUpdateTime) {
        this.data = data;
        this.dataUpdateTime = dataUpdateTime;
        this.alerts = alerts;
        this.alertsUpdateTime = alertsUpdateTime;
    }

    public WeatherData getData() {
        return data;
    }

    public LocalDateTime getDataUpdateTime() {
        return dataUpdateTime;
    }

    public JsonNode getAlerts() {
        return alerts;
    }

    public LocalDateTime getAlertsUpdateTime() {
        return alertsUpdateTime;
    }
}
