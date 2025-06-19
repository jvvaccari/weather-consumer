package com.weather.weather_consumer.controllers;

import com.weather.weather_consumer.services.WeatherConsumerService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/weather")
public class WeatherConsumerController {
    private final WeatherConsumerService weatherConsumerService;

    public WeatherConsumerController(WeatherConsumerService weatherConsumerService) {
        this.weatherConsumerService = weatherConsumerService;
    }

    public String hello(){
        return "Hello from weather consumer!";
    }

}
