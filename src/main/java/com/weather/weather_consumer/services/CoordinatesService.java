package com.weather.weather_consumer.services;

import com.weather.weather_consumer.model.CoordinatesRequest;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class CoordinatesService {

    private volatile CoordinatesRequest currentCoordinates;

    public void setCurrentCoordinates(CoordinatesRequest coordinates) {
        this.currentCoordinates = coordinates;
    }

    public Optional<CoordinatesRequest> getCurrentCoordinates() {
        return Optional.ofNullable(currentCoordinates);
    }
}
