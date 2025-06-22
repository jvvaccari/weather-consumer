package com.weather.weather_consumer.controllers;

import com.weather.weather_consumer.services.DashboardService;
import com.weather.weather_consumer.model.WeatherData;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8082")
@RestController
@RequestMapping("/weather")
public class WeatherConsumerController {

    private final DashboardService dashboardService;

    public WeatherConsumerController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }

    @GetMapping
    public ResponseEntity<WeatherData> getWeather() {
        Optional<WeatherData> optionalData = dashboardService.getDashboardData();

        return optionalData
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @GetMapping("/hello")
    public String hello() {
        return "Hello from weather consumer!";
    }
}
