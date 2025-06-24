package com.weather.weather_consumer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import com.weather.weather_consumer.classes.WeatherInternalPublisher;
import com.weather.weather_consumer.model.WeatherData;
import java.util.Optional;
import com.weather.weather_consumer.services.HistoryService;

@Component
public class DashboardService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;
    private WeatherData dashboardData;
    private final HistoryService historyService;

    public DashboardService(WeatherInternalPublisher publisher, ObjectMapper objectMapper,HistoryService historyService) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
        this.historyService = historyService;
    }

    @PostConstruct
    public void init() {
        publisher.registerListener(this);
    }

    @Override
    public void onDataReceived(String jsonData) {
        try {
            JsonNode root = objectMapper.readTree(jsonData);

            if (!root.isArray() || root.size() == 0) {
                System.err.println("[ERRO] JSON recebido não é um array válido ou está vazio.");
                return;
            }

            JsonNode data = root.get(0);

            WeatherData parsedData = new WeatherData();
            parsedData.setTemperature(data.path("temperature2m").asDouble());
            parsedData.setApparentTemperature(data.path("apparentTemperature").asDouble());
            parsedData.setRelativeHumidity(data.path("relativeHumidity2m").asInt());
            parsedData.setProbabilityOfPrecipitation(data.path("precipitationProbability").asInt());
            parsedData.setPrecipitation(data.path("precipitation").asDouble());
            parsedData.setRain(data.path("rain").asDouble());
            parsedData.setSeaLevelPressure(data.path("pressureMsl").asDouble());
            parsedData.setCloudCover(data.path("cloudCover").asInt());
            parsedData.setWindSpeed(data.path("windSpeed10m").asDouble());

            parsedData.setSnowfall(0.0);
            parsedData.setWindGusts(0.0);
            parsedData.setVisibility(0.0);

            this.dashboardData = parsedData;

            historyService.updateDataIfChanged(parsedData);

        } catch (Exception e) {
            System.err.println("[ERRO] Erro ao processar JSON: " + e.getMessage());
        }
    }

    public Optional<WeatherData> getDashboardData() {
        return Optional.ofNullable(dashboardData);
    }
}
