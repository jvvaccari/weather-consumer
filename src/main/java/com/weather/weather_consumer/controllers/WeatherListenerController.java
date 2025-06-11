package com.weather.weather_consumer.controllers;

import com.weather.weather_consumer.services.WeatherListenerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherListenerController {
    private final WeatherListenerService weatherListenerService;

    public WeatherListenerController(WeatherListenerService weatherListenerService) {
        this.weatherListenerService = weatherListenerService;
    }

    public String hello(){
        return "Hello from weather consumer!";
    }

    @GetMapping("/lastMessage")
    public String getLastMessage(){
        return weatherListenerService.getLastMessage();
    }
}
