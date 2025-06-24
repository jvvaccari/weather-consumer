package com.weather.weather_consumer.controllers;

import com.weather.weather_consumer.services.DashboardService;
import com.weather.weather_consumer.services.AlertService;
import com.weather.weather_consumer.services.HistoryService;
import com.weather.weather_consumer.classes.WeatherHistoryResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.weather.weather_consumer.model.WeatherData;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;
import java.time.LocalDateTime;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/weather")
public class WeatherConsumerController {

    private final DashboardService dashboardService;
    private final AlertService alertService;
    private final HistoryService historyService;

    public WeatherConsumerController(DashboardService dashboardService,AlertService alertService,HistoryService historyService) {
        this.dashboardService = dashboardService;
        this.alertService = alertService;
        this.historyService = historyService;
    }

    @GetMapping("/data")
    public ResponseEntity<WeatherData> getWeatherData() {
        Optional<WeatherData> optionalData = dashboardService.getDashboardData();

        return optionalData
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/alerts")
    public ResponseEntity<JsonNode> getWeatherAlerts() {
        Optional<JsonNode> optionalAlert = alertService.getAlerts();

        return optionalAlert
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/history")
    public ResponseEntity<WeatherHistoryResponse> getWeatherHistory() {
        Optional<WeatherData> prevData = historyService.getPreviousData();
        Optional<LocalDateTime> prevDataTime = historyService.getPreviousDataUpdateTime();

        Optional<JsonNode> prevAlerts = historyService.getPreviousAlerts();
        Optional<LocalDateTime> prevAlertsTime = historyService.getPreviousAlertsUpdateTime();

        if (prevData.isEmpty() && prevAlerts.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        WeatherHistoryResponse response = new WeatherHistoryResponse(
                prevData.orElse(null),
                prevDataTime.orElse(null),
                prevAlerts.orElse(null),
                prevAlertsTime.orElse(null)
        );

        return ResponseEntity.ok(response);
    }

}
