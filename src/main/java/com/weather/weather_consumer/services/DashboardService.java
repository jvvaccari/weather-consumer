package com.weather.weather_consumer.classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;
import com.weather.weather_consumer.interfaces.WeatherDataListener;
import java.util.Optional;

@Component
public class DashboardService implements WeatherDataListener {

    private final WeatherInternalPublisher publisher;
    private final ObjectMapper objectMapper;

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

            JsonNode firstElement = root.get(0);

            System.out.println("Dados servidos ao front: " + firstElement);
            return Optional.of(firstElement);
        } catch (Exception e) {
            return createError(e.getMessage());
        }
    }
}
