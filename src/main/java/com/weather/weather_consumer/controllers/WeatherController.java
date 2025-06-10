package com.weather.weather_consumer.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WeatherController {

    @GetMapping("/")
    public String hello(){
        return "Hello from weather consumer!";
    }
}