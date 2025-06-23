package com.weather.weather_consumer.controllers;

import com.weather.weather_consumer.services.DashboardService;
import com.weather.weather_consumer.services.AlertService;
import com.fasterxml.jackson.databind.JsonNode;
import com.weather.weather_consumer.model.WeatherData;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/weather")
public class WeatherConsumerController {

    private final DashboardService dashboardService;
    private final AlertService alertService;

    public WeatherConsumerController(DashboardService dashboardService,AlertService alertService) {
        this.dashboardService = dashboardService;
        this.alertService = alertService;
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

}
