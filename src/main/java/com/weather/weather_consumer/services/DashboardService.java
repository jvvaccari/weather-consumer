package com.weather.weather_consumer.services;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import com.weather.weather_consumer.classes.WeatherInternalPublisher;
import com.weather.weather_consumer.model.WeatherData;
import java.util.Optional;

@Component
public class DashboardService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;
    private WeatherData dashboardData;

    public DashboardService(WeatherInternalPublisher publisher, ObjectMapper objectMapper) {
        this.publisher = publisher;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() {
        publisher.registerListener(this);
    }

    private Optional<JsonNode> createError(String message) {
        ObjectNode errorNode = objectMapper.createObjectNode();
        errorNode.put("error", message);
        return Optional.of(errorNode);
    }

    @Override
    public Optional<JsonNode> onDataReceived(String jsonData) {
        try {
            JsonNode root = objectMapper.readTree(jsonData);

            if (!root.isArray() || root.size() == 0) {
                return createError("JSON recebido não é um array válido ou está vazio.");
            }

            JsonNode data = root.get(0);

            WeatherData parsed = new WeatherData();
            parsed.setTemperature(data.path("temperature2m").asDouble());
            parsed.setApparentTemperature(data.path("apparentTemperature").asDouble());
            parsed.setRelativeHumidity(data.path("relativeHumidity2m").asInt());
            parsed.setProbabilityOfPrecipitation(data.path("precipitationProbability").asInt());
            parsed.setPrecipitation(data.path("precipitation").asDouble());
            parsed.setRain(data.path("rain").asDouble());
            parsed.setSeaLevelPressure(data.path("pressureMsl").asDouble());
            parsed.setCloudCover(data.path("cloudCover").asInt());
            parsed.setWindSpeed(data.path("windSpeed10m").asDouble());

            parsed.setSnowfall(0.0);
            parsed.setWindGusts(0.0);
            parsed.setVisibility(0.0);

            this.dashboardData = parsed;

            System.out.println("[DASHBOARD SERVICE] Dados recebidos e processados: " + data.toString());

            return Optional.of(data);

        } catch (Exception e) {
            return createError("Erro ao processar JSON: " + e.getMessage());
        }
    }

    public Optional<WeatherData> getDashboardData() {
        return Optional.ofNullable(dashboardData);
    }
}
